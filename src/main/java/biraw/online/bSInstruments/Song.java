package biraw.online.bSInstruments;

import org.bukkit.Material;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Song {
    static final int CUSTOM_MODEL_DATA = 101;
    private static final int MAX_DURATION_TICKS = 180 * 20;
    private static final int TARGET_MIN_DURATION_TICKS = 120 * 20;
    private static final int PHRASE_REST_TICKS = 8;

    private final String id;
    private final String title;
    private final String style;
    private final int tempoTicks;
    private final List<SongNote> notes;
    private final List<List<SongNote>> layerNotes;
    private final int durationTicks;

    public Song(String title, String style, int tempoTicks, String pattern) {
        this.id = toId(title, style);
        this.title = title;
        this.style = style;
        this.tempoTicks = tempoTicks;
        this.notes = arrange(parsePattern(pattern));
        this.layerNotes = arrangeLayers(notes);
        this.durationTicks = getTotalDuration(notes);
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
                .append(Component.text(title, NamedTextColor.WHITE)));
        meta.lore(List.of(
                Component.text(style, NamedTextColor.GRAY),
                Component.text("Hold in main hand and play an instrument", NamedTextColor.GREEN),
                Component.text("MinearchyInstruments", NamedTextColor.AQUA)
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
        for (String token : pattern.split("\\s+")) {
            if (token.isBlank()) continue;
            String[] parts = token.split(":");
            if (parts.length != 2) throw new IllegalArgumentException("Invalid song token: " + token);

            int durationTicks = parseDurationTicks(parts[1]);
            if (parts[0].equalsIgnoreCase("R")) {
                parsed.add(new SongNote(-1, durationTicks));
                continue;
            }

            parsed.add(new SongNote(toNoteId(parts[0]), durationTicks));
        }
        return List.copyOf(parsed);
    }

    private int parseDurationTicks(String duration) {
        if (duration.contains("/")) {
            String[] fractionParts = duration.split("/");
            if (fractionParts.length != 2) throw new IllegalArgumentException("Invalid song duration: " + duration);

            double numerator = Double.parseDouble(fractionParts[0]);
            double denominator = Double.parseDouble(fractionParts[1]);
            if (denominator == 0.0) throw new IllegalArgumentException("Invalid song duration: " + duration);
            return Math.max(1, (int) Math.round((numerator / denominator) * tempoTicks));
        }

        return Math.max(1, (int) Math.round(Double.parseDouble(duration) * tempoTicks));
    }

    private List<SongNote> arrange(List<SongNote> baseNotes) {
        int baseDuration = getTotalDuration(baseNotes);
        if (baseDuration >= TARGET_MIN_DURATION_TICKS) return baseNotes;

        List<SongNote> arranged = new ArrayList<>();
        int variation = 0;
        while (getTotalDuration(arranged) < TARGET_MIN_DURATION_TICKS) {
            List<SongNote> phrase = varyPhrase(baseNotes, variation);
            int phraseDuration = getTotalDuration(phrase);
            int restDuration = arranged.isEmpty() ? 0 : PHRASE_REST_TICKS;
            if (!arranged.isEmpty() && getTotalDuration(arranged) + restDuration + phraseDuration > MAX_DURATION_TICKS) break;
            if (!arranged.isEmpty()) arranged.add(new SongNote(-1, restDuration));
            arranged.addAll(phrase);
            variation++;
        }

        return List.copyOf(arranged);
    }

    private List<SongNote> varyPhrase(List<SongNote> baseNotes, int variation) {
        int transpose = switch (variation % 6) {
            case 1 -> 2;
            case 2 -> -5;
            case 3 -> 7;
            case 4 -> -2;
            case 5 -> 5;
            default -> 0;
        };

        List<SongNote> varied = new ArrayList<>();
        for (int i = 0; i < baseNotes.size(); i++) {
            SongNote note = baseNotes.get(i);
            if (note.isRest()) {
                varied.add(note);
                continue;
            }

            int noteId = note.noteId() + transpose;
            if (variation % 3 == 2 && i % 4 == 0) noteId -= 12;
            if (variation % 4 == 3 && i % 5 == 2) noteId += 12;
            varied.add(new SongNote(noteId, note.durationTicks()));
        }
        return List.copyOf(varied);
    }

    private List<List<SongNote>> arrangeLayers(List<SongNote> melody) {
        List<List<SongNote>> layers = new ArrayList<>();
        layers.add(melody);
        layers.add(makeParallelLayer(melody, -5, 2));
        layers.add(makeBassLayer(melody));
        layers.add(makeParallelLayer(melody, 7, 3));
        layers.add(makeCounterLayer(melody));
        return List.copyOf(layers);
    }

    private List<SongNote> makeParallelLayer(List<SongNote> melody, int transpose, int entranceEvery) {
        List<SongNote> layer = new ArrayList<>();
        int noteNumber = 0;
        for (SongNote note : melody) {
            if (note.isRest() || noteNumber % entranceEvery != 0) {
                layer.add(new SongNote(-1, note.durationTicks()));
            } else {
                layer.add(new SongNote(note.noteId() + transpose, note.durationTicks()));
            }
            if (!note.isRest()) noteNumber++;
        }
        return List.copyOf(layer);
    }

    private List<SongNote> makeBassLayer(List<SongNote> melody) {
        List<SongNote> layer = new ArrayList<>();
        int ticksSinceBass = tempoTicks * 4;
        for (SongNote note : melody) {
            if (!note.isRest() && ticksSinceBass >= tempoTicks * 4) {
                layer.add(new SongNote(note.noteId() - 12, note.durationTicks()));
                ticksSinceBass = 0;
            } else {
                layer.add(new SongNote(-1, note.durationTicks()));
                ticksSinceBass += note.durationTicks();
            }
        }
        return List.copyOf(layer);
    }

    private List<SongNote> makeCounterLayer(List<SongNote> melody) {
        List<SongNote> layer = new ArrayList<>();
        int previousNoteId = -1;
        int noteNumber = 0;
        for (SongNote note : melody) {
            if (note.isRest()) {
                layer.add(note);
            } else if (previousNoteId >= 0 && noteNumber % 2 == 1) {
                layer.add(new SongNote(previousNoteId + 7, note.durationTicks()));
            } else {
                layer.add(new SongNote(-1, note.durationTicks()));
            }

            if (!note.isRest()) {
                previousNoteId = note.noteId();
                noteNumber++;
            }
        }
        return List.copyOf(layer);
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
        return ((octave - 3) * 12 + semitone) - 6;
    }

    public record SongNote(int noteId, int durationTicks) {
        public boolean isRest() {
            return noteId < 0;
        }
    }
}
