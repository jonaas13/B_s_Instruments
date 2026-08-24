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

    private final String id;
    private final String title;
    private final String style;
    private final int transpose;
    private final int tempoTicks;
    private final List<SongNote> notes;

    public Song(String title, String style, int transpose, int tempoTicks, String pattern) {
        this.id = toId(title, style, transpose);
        this.title = title;
        this.style = style;
        this.transpose = transpose;
        this.tempoTicks = tempoTicks;
        this.notes = parsePattern(pattern);
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

    public int transpose() {
        return transpose;
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

    private static String toId(String title, String style, int transpose) {
        String base = (title + "-" + style)
                .toLowerCase(Locale.ROOT)
                .replace("'", "")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        if (transpose == 0) return base;
        return base + (transpose > 0 ? "-up-" + transpose : "-down-" + Math.abs(transpose));
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

            parsed.add(new SongNote(toNoteId(parts[0]) + transpose, durationTicks));
        }
        return List.copyOf(parsed);
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
