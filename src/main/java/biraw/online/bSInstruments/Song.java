package biraw.online.bSInstruments;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Song {
    static final int CUSTOM_MODEL_DATA = 101;
    private static final int MAX_DURATION_TICKS = 30 * 20;
    private static final int TARGET_MIN_DURATION_TICKS = 18 * 20;
    private static final int REPEAT_REST_TICKS = 8;

    private final String id;
    private final String title;
    private final String style;
    private final int tempoTicks;
    private final List<SongNote> notes;

    public Song(String title, String style, int tempoTicks, String pattern) {
        this.id = toId(title, style);
        this.title = title;
        this.style = style;
        this.tempoTicks = tempoTicks;
        this.notes = arrange(parsePattern(pattern));
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

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dSheet Music: §f" + title);
        meta.setLore(List.of(
                "§7" + style,
                "§aHold in main hand and play an instrument",
                "§bMinearchyInstruments"
        ));
        meta.setCustomModelData(CUSTOM_MODEL_DATA);
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

            int durationTicks = Integer.parseInt(parts[1]) * tempoTicks;
            if (parts[0].equalsIgnoreCase("R")) {
                parsed.add(new SongNote(-1, durationTicks));
                continue;
            }

            parsed.add(new SongNote(toNoteId(parts[0]), durationTicks));
        }
        return List.copyOf(parsed);
    }

    private List<SongNote> arrange(List<SongNote> baseNotes) {
        int baseDuration = getTotalDuration(baseNotes);
        if (baseDuration >= TARGET_MIN_DURATION_TICKS) return baseNotes;

        List<SongNote> arranged = new ArrayList<>(baseNotes);
        while (getTotalDuration(arranged) + REPEAT_REST_TICKS + baseDuration <= MAX_DURATION_TICKS
                && getTotalDuration(arranged) < TARGET_MIN_DURATION_TICKS) {
            arranged.add(new SongNote(-1, REPEAT_REST_TICKS));
            arranged.addAll(baseNotes);
        }

        return List.copyOf(arranged);
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
