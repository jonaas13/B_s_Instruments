package biraw.online.bSInstruments;

import org.bukkit.Material;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Song {
    static final int CUSTOM_MODEL_DATA = 101;
    private static final int REST_NOTE_ID = Integer.MIN_VALUE;
    private static final int MIN_NOTE_ID = 0;
    private static final int MAX_NOTE_ID = 24;
    private static final double TICKS_PER_MINUTE = 30.0 * 20.0;

    private final String id;
    private final String title;
    private final String style;
    private final double durationUnitTicks;
    private final List<SongNote> notes;
    private final List<List<SongNote>> layerNotes;
    private final int durationTicks;

    public Song(String title, String style, int bpm, String pattern) {
        this(title, style, bpm, pattern, List.of());
    }

    public Song(String title, String style, int bpm, String pattern, List<String> layerPatterns) {
        this.id = toId(title, style);
        this.title = title;
        this.style = style;
        this.durationUnitTicks = TICKS_PER_MINUTE / Math.max(1, bpm);
        List<SongNote> melody = parsePattern(pattern);
        List<List<SongNote>> parsedLayers = parseLayers(melody, layerPatterns);
        this.durationTicks = getMaxDuration(parsedLayers);
        this.notes = fitToDuration(melody, durationTicks);
        this.layerNotes = fitLayersToDuration(parsedLayers, durationTicks);
    }

    public String id() {
        return id;
    }

    public String lookupName() {
        return id;
    }

    public String title() {
        return title;
    }

    public List<SongNote> notes() {
        return notes;
    }

    public List<SongNote> notesForLayer(int layer) {
        return layerNotes.get(Math.floorMod(layer, layerNotes.size()));
    }

    public int layerCount() {
        return layerNotes.size();
    }

    public int durationTicks() {
        return durationTicks;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Sheet Music: ", NamedTextColor.LIGHT_PURPLE)
                .append(Component.text(title, NamedTextColor.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        meta.lore(List.of(
                Component.text(style, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                Component.text("Hold in main hand and play an instrument", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                Component.text("MinearchyInstruments", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)
        ));
        CustomModelDataComponent customModelData = meta.getCustomModelDataComponent();
        customModelData.setFloats(List.of((float) CUSTOM_MODEL_DATA));
        meta.setCustomModelDataComponent(customModelData);
        meta.getPersistentDataContainer().set(BSInstruments.NSKEY, PersistentDataType.STRING, "song_" + id);
        item.setItemMeta(meta);
        return item;
    }

    private static String toId(String title, String style) {
        return (title + "-" + style)
                .toLowerCase(Locale.ROOT)
                .replace("'", "")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }

    private List<SongNote> parsePattern(String pattern) {
        List<SongNote> parsed = new ArrayList<>();
        double exactElapsedTicks = 0.0;
        int roundedElapsedTicks = 0;
        for (String token : pattern.split("\\s+")) {
            if (token.isBlank()) continue;
            String[] parts = token.split(":");
            if (parts.length != 2) throw new IllegalArgumentException("Invalid song token: " + token);

            exactElapsedTicks += parseDurationUnits(parts[1]) * durationUnitTicks;
            int nextRoundedElapsedTicks = Math.max(roundedElapsedTicks + 1, (int) Math.round(exactElapsedTicks));
            int durationTicks = nextRoundedElapsedTicks - roundedElapsedTicks;
            roundedElapsedTicks = nextRoundedElapsedTicks;
            if (parts[0].equalsIgnoreCase("R")) {
                parsed.add(rest(durationTicks));
                continue;
            }

            parsed.add(new SongNote(toNoteId(parts[0]), durationTicks));
        }
        return List.copyOf(parsed);
    }

    private double parseDurationUnits(String duration) {
        if (duration.contains("/")) {
            String[] fractionParts = duration.split("/");
            if (fractionParts.length != 2) throw new IllegalArgumentException("Invalid song duration: " + duration);

            double numerator = Double.parseDouble(fractionParts[0]);
            double denominator = Double.parseDouble(fractionParts[1]);
            if (denominator == 0.0) throw new IllegalArgumentException("Invalid song duration: " + duration);
            return numerator / denominator;
        }

        return Double.parseDouble(duration);
    }

    private List<List<SongNote>> parseLayers(List<SongNote> melody, List<String> layerPatterns) {
        List<List<SongNote>> layers = new ArrayList<>();
        layers.add(melody);
        for (String layerPattern : layerPatterns) {
            layers.add(parsePattern(layerPattern));
        }
        return List.copyOf(layers);
    }

    private List<List<SongNote>> fitLayersToDuration(List<List<SongNote>> layers, int targetDurationTicks) {
        List<List<SongNote>> fittedLayers = new ArrayList<>();
        for (List<SongNote> layer : layers) {
            fittedLayers.add(fitToDuration(layer, targetDurationTicks));
        }
        return List.copyOf(fittedLayers);
    }

    private List<SongNote> fitToDuration(List<SongNote> notes, int targetDurationTicks) {
        List<SongNote> fitted = new ArrayList<>();
        int totalTicks = 0;
        for (SongNote note : notes) {
            if (totalTicks >= targetDurationTicks) break;
            int durationTicks = Math.min(note.durationTicks(), targetDurationTicks - totalTicks);
            fitted.add(new SongNote(note.noteId(), durationTicks));
            totalTicks += durationTicks;
        }
        if (totalTicks < targetDurationTicks) fitted.add(rest(targetDurationTicks - totalTicks));
        return List.copyOf(fitted);
    }

    private SongNote rest(int durationTicks) {
        return new SongNote(REST_NOTE_ID, durationTicks);
    }

    private int getMaxDuration(List<List<SongNote>> layers) {
        int maxDurationTicks = 0;
        for (List<SongNote> layer : layers) {
            maxDurationTicks = Math.max(maxDurationTicks, getTotalDuration(layer));
        }
        return maxDurationTicks;
    }

    private int getTotalDuration(List<SongNote> songNotes) {
        int totalTicks = 0;
        for (SongNote songNote : songNotes) {
            totalTicks += songNote.durationTicks();
        }
        return totalTicks;
    }

    private static int toNoteId(String noteName) {
        int octave = Character.digit(noteName.charAt(noteName.length() - 1), 10);
        String pitchName = noteName.substring(0, noteName.length() - 1).toUpperCase(Locale.ROOT);
        int semitone = switch (pitchName) {
            case "C" -> 0;
            case "C#", "DB" -> 1;
            case "D" -> 2;
            case "D#", "EB" -> 3;
            case "E" -> 4;
            case "F" -> 5;
            case "F#", "GB" -> 6;
            case "G" -> 7;
            case "G#", "AB" -> 8;
            case "A" -> 9;
            case "A#", "BB" -> 10;
            case "B" -> 11;
            default -> throw new IllegalArgumentException("Invalid note name: " + noteName);
        };

        // Bukkit note block IDs are the 25 chromatic notes from F#3 through F#5.
        return normalizeNoteId(((octave - 3) * 12 + semitone) - 6);
    }

    private static int normalizeNoteId(int noteId) {
        while (noteId < MIN_NOTE_ID) noteId += 12;
        while (noteId > MAX_NOTE_ID) noteId -= 12;
        return noteId;
    }

    public record SongNote(int noteId, int durationTicks) {
        public boolean isRest() {
            return noteId == REST_NOTE_ID;
        }
    }
}
