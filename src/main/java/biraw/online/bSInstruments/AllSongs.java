package biraw.online.bSInstruments;

import biraw.online.bSInstruments.Obtaining.ItemDelivery;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public final class AllSongs {
    private static final Material SONG_MATERIAL = Material.PAPER;
    private static final String SONG_KEY_PREFIX = "song_";
    private static final float SONG_CUSTOM_MODEL_DATA = (float) Song.CUSTOM_MODEL_DATA;
    private static final int SONG_TEMPO_TICKS = 4;

    private static final List<SongSeed> SONG_SEEDS = List.of(
            new SongSeed("Ode to Joy", "Classical", phrase(
                    "E4:2 E4:2 F4:2 G4:2 G4:2 F4:2 E4:2 D4:2 C4:2 C4:2 D4:2 E4:2 E4:3 D4:1 D4:4",
                    "E4:2 E4:2 F4:2 G4:2 G4:2 F4:2 E4:2 D4:2 C4:2 C4:2 D4:2 E4:2 D4:3 C4:1 C4:4")),
            new SongSeed("Twinkle Twinkle Little Star", "Folk", phrase(
                    "C4:2 C4:2 G4:2 G4:2 A4:2 A4:2 G4:4 F4:2 F4:2 E4:2 E4:2 D4:2 D4:2 C4:4",
                    "G4:2 G4:2 F4:2 F4:2 E4:2 E4:2 D4:4 G4:2 G4:2 F4:2 F4:2 E4:2 E4:2 D4:4",
                    "C4:2 C4:2 G4:2 G4:2 A4:2 A4:2 G4:4 F4:2 F4:2 E4:2 E4:2 D4:2 D4:2 C4:4")),
            new SongSeed("Mary Had a Little Lamb", "Folk", phrase(
                    "E4:2 D4:2 C4:2 D4:2 E4:2 E4:2 E4:4 D4:2 D4:2 D4:4 E4:2 G4:2 G4:4",
                    "E4:2 D4:2 C4:2 D4:2 E4:2 E4:2 E4:2 E4:2 D4:2 D4:2 E4:2 D4:2 C4:8")),
            new SongSeed("Frere Jacques", "Folk", phrase(
                    "C4:2 D4:2 E4:2 C4:2 C4:2 D4:2 E4:2 C4:2 E4:2 F4:2 G4:4 E4:2 F4:2 G4:4",
                    "G4:1 A4:1 G4:1 F4:1 E4:2 C4:2 G4:1 A4:1 G4:1 F4:1 E4:2 C4:2 C4:2 G3:2 C4:4 C4:2 G3:2 C4:4")),
            new SongSeed("Row Row Row Your Boat", "Folk", phrase(
                    "C4:3 C4:1 C4:2 D4:1 E4:3 E4:1 D4:1 E4:1 F4:1 G4:4",
                    "C5:1 C5:1 C5:1 G4:1 G4:1 G4:1 E4:1 E4:1 E4:1 C4:1 C4:1 C4:1 G4:2 F4:1 E4:1 D4:1 C4:4")),
            new SongSeed("London Bridge", "Folk", phrase(
                    "G4:2 A4:2 G4:2 F4:2 E4:2 F4:2 G4:4 D4:2 E4:2 F4:4 E4:2 F4:2 G4:4",
                    "G4:2 A4:2 G4:2 F4:2 E4:2 F4:2 G4:4 D4:4 G4:4 E4:2 C4:4")),
            new SongSeed("Bingo", "Folk", phrase(
                    "G4:2 G4:2 G4:2 D4:2 E4:2 E4:2 D4:4 G4:2 G4:2 A4:2 A4:2 G4:4",
                    "D5:2 D5:2 C5:2 C5:2 B4:2 B4:2 A4:4 G4:2 G4:2 A4:2 A4:2 G4:4")),
            new SongSeed("Happy Birthday", "Celebration", phrase(
                    "C4:2 C4:1 D4:3 C4:3 F4:3 E4:6 C4:2 C4:1 D4:3 C4:3 G4:3 F4:6",
                    "C4:2 C4:1 C5:3 A4:3 F4:3 E4:3 D4:6 A#4:2 A#4:1 A4:3 F4:3 G4:3 F4:6")),
            new SongSeed("Amazing Grace", "Traditional", phrase(
                    "G3:3 C4:1 E4:4 C4:2 E4:2 D4:4 C4:2 A3:2 G3:4 G3:3 C4:1 E4:4",
                    "C4:2 E4:2 D4:4 E4:2 G4:2 E4:4 G4:3 E4:1 C4:4 C4:2 A3:2 G3:4")),
            new SongSeed("Greensleeves", "Traditional", phrase(
                    "A3:2 C4:4 D4:2 E4:3 F4:1 E4:2 D4:4 B3:2 G3:3 A3:1 B3:2 C4:4 A3:4",
                    "A3:2 C4:4 D4:2 E4:3 F4:1 E4:2 D4:4 B3:2 G3:3 A3:1 B3:2 C4:4 A3:4")),
            new SongSeed("Scarborough Fair", "Traditional", phrase(
                    "D4:3 D4:1 A4:2 A4:2 E4:2 F4:2 E4:4 D4:2 C4:2 D4:2 E4:2 D4:4",
                    "A3:2 C4:2 D4:4 E4:2 F4:2 G4:2 A4:2 G4:4 F4:2 E4:2 D4:4")),
            new SongSeed("Auld Lang Syne", "Traditional", phrase(
                    "G3:2 C4:3 C4:1 C4:2 E4:2 D4:3 C4:1 D4:2 E4:2 C4:3 C4:1 E4:2 G4:4",
                    "A4:3 G4:1 E4:2 E4:2 D4:3 C4:1 D4:2 E4:2 C4:3 A3:1 A3:2 G3:4")),
            new SongSeed("When the Saints Go Marching In", "Traditional", phrase(
                    "C4:2 E4:2 F4:2 G4:4 C4:2 E4:2 F4:2 G4:4 C4:2 E4:2 F4:2 G4:2 E4:2 C4:2 E4:2 D4:4",
                    "E4:2 E4:2 D4:2 C4:4 C4:2 E4:2 G4:2 G4:2 F4:2 E4:2 F4:2 G4:4")),
            new SongSeed("Yankee Doodle", "Traditional", phrase(
                    "C4:2 C4:2 D4:2 E4:2 C4:2 E4:2 D4:4 C4:2 C4:2 D4:2 E4:2 C4:4 B3:4",
                    "C4:2 C4:2 D4:2 E4:2 F4:2 E4:2 D4:2 C4:2 B3:2 G3:2 A3:2 B3:2 C4:4")),
            new SongSeed("Oh Susanna", "Traditional", phrase(
                    "C4:2 D4:2 E4:2 G4:2 G4:2 A4:2 G4:4 E4:2 C4:2 D4:2 E4:2 E4:2 D4:2 C4:4",
                    "C4:2 D4:2 E4:2 G4:2 G4:2 A4:2 G4:4 E4:2 C4:2 D4:2 E4:2 D4:2 C4:2 D4:4")),
            new SongSeed("Drunken Sailor", "Sea Shanty", phrase(
                    "D4:2 D4:2 D4:2 D4:2 D4:2 D4:2 A3:2 C4:2 D4:4",
                    "D4:2 D4:2 D4:2 D4:2 E4:2 F4:2 E4:2 D4:4")),
            new SongSeed("Wellerman", "Sea Shanty", phrase(
                    "A3:2 C4:2 D4:2 E4:2 D4:2 C4:2 A3:4 A3:2 C4:2 D4:2 E4:2 G4:2 E4:2 D4:4",
                    "D4:2 E4:2 G4:2 A4:2 G4:2 E4:2 D4:4 C4:2 D4:2 E4:2 D4:2 C4:2 A3:4")),
            new SongSeed("Jingle Bells", "Holiday", phrase(
                    "E4:2 E4:2 E4:4 E4:2 E4:2 E4:4 E4:2 G4:2 C4:3 D4:1 E4:8",
                    "F4:2 F4:2 F4:3 F4:1 F4:2 E4:2 E4:2 E4:1 E4:1 E4:2 D4:2 D4:2 E4:2 D4:4 G4:4")),
            new SongSeed("Silent Night", "Holiday", phrase(
                    "G4:3 A4:1 G4:2 E4:6 G4:3 A4:1 G4:2 E4:6 D5:4 D5:2 B4:6",
                    "C5:4 C5:2 G4:6 A4:4 A4:2 C5:3 B4:1 A4:2 G4:3 A4:1 G4:2 E4:6")),
            new SongSeed("Deck the Halls", "Holiday", phrase(
                    "G4:2 F4:1 E4:1 D4:2 C4:2 D4:2 E4:2 C4:2 D4:1 E4:1 F4:2 D4:1 E4:1 F4:2 G4:2",
                    "G4:2 F4:1 E4:1 D4:2 C4:2 D4:2 E4:2 C4:2 A4:1 A4:1 G4:2 F4:1 E4:1 D4:2 C4:4")),
            new SongSeed("Joy to the World", "Holiday", phrase(
                    "C5:3 B4:1 A4:2 G4:4 F4:3 E4:1 D4:2 C4:4 G4:3 A4:1 A4:3 B4:1 B4:4",
                    "C5:3 C5:1 B4:2 A4:2 G4:3 F4:1 E4:2 C5:2 C5:1 B4:1 A4:2 G4:2 F4:2 E4:2 D4:2 C4:4")),
            new SongSeed("O Christmas Tree", "Holiday", phrase(
                    "G4:3 C5:1 C5:2 C5:2 D5:3 E5:1 E5:2 E5:2 E5:2 D5:2 E5:2 F5:2 B4:4 D5:4 C5:4",
                    "G4:3 C5:1 C5:2 C5:2 D5:3 E5:1 E5:2 E5:2 E5:2 D5:2 E5:2 F5:2 B4:4 D5:4 C5:4")),
            new SongSeed("Eine Kleine Nachtmusik", "Classical", phrase(
                    "G4:1 R:1 D4:1 R:1 G4:1 R:1 D4:1 R:1 G4:1 B4:1 D5:2 B4:1 G4:1 B4:1 D5:2",
                    "C5:1 A4:1 C5:1 A4:1 F#4:1 A4:1 D4:2 G4:1 R:1 D4:1 R:1 G4:1 R:1 D4:1 R:1 G4:4")),
            new SongSeed("Canon in D", "Classical", phrase(
                    "D4:4 A3:4 B3:4 F#3:4 G3:4 D3:4 G3:4 A3:4",
                    "D4:2 F#4:2 A4:2 G4:2 F#4:2 E4:2 D4:4 A3:4 B3:4 F#3:4 G3:4 A3:4")),
            new SongSeed("Spring", "Classical", phrase(
                    "E4:1 E4:1 E4:2 D4:1 C4:1 D4:2 E4:2 F4:2 G4:2 G4:2 F4:2 E4:2 D4:2 C4:4",
                    "E4:1 E4:1 E4:2 D4:1 C4:1 D4:2 E4:2 F4:2 G4:2 G4:2 F4:2 E4:2 D4:2 C4:4")),
            new SongSeed("Blue Danube", "Classical", phrase(
                    "D4:3 F#4:1 A4:3 R:1 A4:3 R:1 A4:2 B4:2 C#5:2 D5:4",
                    "D4:3 F#4:1 A4:3 R:1 A4:3 R:1 A4:2 B4:2 C#5:2 D5:4")),
            new SongSeed("William Tell Overture", "Classical", phrase(
                    "E4:1 E4:1 E4:2 E4:1 E4:1 E4:2 E4:1 G4:1 C4:1 D4:1 E4:4",
                    "F4:1 F4:1 F4:1 F4:1 F4:1 E4:1 E4:1 E4:1 E4:1 D4:1 D4:1 E4:1 D4:4")),
            new SongSeed("Fur Elise", "Classical", phrase(
                    "E5:1 D#5:1 E5:1 D#5:1 E5:1 B4:1 D5:1 C5:1 A4:4",
                    "C4:1 E4:1 A4:1 B4:4 E4:1 G#4:1 B4:1 C5:4 E4:1 E5:1 D#5:1 E5:1 D#5:1 E5:1 B4:1 D5:1 C5:1 A4:4")),
            new SongSeed("Beethoven Fifth", "Classical", phrase(
                    "G4:1 G4:1 G4:1 Eb4:6 R:1 F4:1 F4:1 F4:1 D4:6 R:1",
                    "G4:1 G4:1 G4:1 Eb4:4 R:2 F4:1 F4:1 F4:1 D4:4")),
            new SongSeed("Can Can", "Classical", phrase(
                    "G4:1 G4:1 A4:1 B4:1 C5:1 C5:1 A4:1 G4:1 E4:1 E4:1 F#4:1 G4:1 A4:1 A4:1 F#4:1 E4:1",
                    "G4:1 G4:1 A4:1 B4:1 C5:1 C5:1 A4:1 G4:1 E4:1 E4:1 F#4:1 G4:1 A4:2 G4:2")),
            new SongSeed("The Entertainer", "Ragtime", phrase(
                    "D4:1 D#4:1 E4:2 C5:1 E4:1 C5:1 E4:1 C5:4 C5:1 B4:1 A4:1 G4:1 F#4:1 A4:1 C5:1 E5:4",
                    "D4:1 D#4:1 E4:2 C5:1 E4:1 C5:1 E4:1 C5:4 C5:1 B4:1 A4:1 G4:1 F#4:1 A4:1 C5:1 E5:4")),
            new SongSeed("La Cucaracha", "Traditional", phrase(
                    "C4:2 C4:2 C4:2 F4:2 A4:4 C4:2 C4:2 C4:2 F4:2 A4:4",
                    "F4:2 F4:2 E4:2 E4:2 D4:2 D4:2 C4:4")),
            new SongSeed("Korobeiniki", "Traditional", phrase(
                    "E4:2 B3:1 C4:1 D4:2 C4:1 B3:1 A3:2 A3:1 C4:1 E4:2 D4:1 C4:1 B3:4",
                    "C4:1 D4:1 E4:2 C4:2 A3:2 A3:4 B3:1 C4:1 D4:2 B3:2 G#3:2 G#3:4")),
            new SongSeed("Hava Nagila", "Traditional", phrase(
                    "E4:1 F4:1 G4:2 G4:2 F4:1 E4:1 F4:2 E4:2 D4:2",
                    "E4:1 F4:1 G4:2 G4:2 F4:1 E4:1 F4:2 E4:4")),
            new SongSeed("Simple Gifts", "Traditional", phrase(
                    "G4:2 C5:2 D5:2 E5:4 D5:2 C5:2 A4:4 G4:2 A4:2 C5:2 D5:2 C5:4",
                    "G4:2 C5:2 D5:2 E5:4 D5:2 C5:2 A4:4 G4:2 A4:2 C5:2 D5:2 C5:4")),
            new SongSeed("America the Beautiful", "Patriotic", phrase(
                    "C4:2 C4:2 E4:2 E4:2 G4:2 G4:2 C5:4 B4:2 A4:2 G4:2 F4:2 E4:4",
                    "D4:2 E4:2 F4:2 G4:2 A4:2 G4:2 E4:4 C5:2 C5:2 B4:2 A4:2 G4:4")),
            new SongSeed("Star Spangled Banner", "Patriotic", phrase(
                    "G4:2 E4:2 C4:4 E4:4 G4:4 C5:8 E5:2 D5:2 C5:4 E4:4 F#4:4 G4:8",
                    "G4:2 G4:2 E5:4 D5:2 C5:2 B4:4 A4:2 B4:2 C5:4 C5:4 G4:4 E4:4 C4:8"))
    );

    private static final List<Song> ALL_SONGS;
    private static final Map<String, Song> SONGS_BY_NAME;
    private static final List<String> SONG_NAMES;

    static {
        List<Song> songs = new ArrayList<>();
        Map<String, Song> songsByName = new HashMap<>();
        List<String> songNames = new ArrayList<>();

        for (SongSeed seed : SONG_SEEDS) {
            Song song = new Song(seed.title(), seed.style(), SONG_TEMPO_TICKS, seed.pattern());
            songs.add(song);
            String lookupName = song.lookupName();
            if (songsByName.put(lookupName, song) != null) {
                throw new IllegalStateException("Duplicate song id: " + lookupName);
            }
            songNames.add(lookupName);
        }

        ALL_SONGS = List.copyOf(songs);
        SONGS_BY_NAME = Map.copyOf(songsByName);
        SONG_NAMES = List.copyOf(songNames);
    }

    private AllSongs() {
    }

    public static Song getSongByName(String name) {
        if (name == null) return null;
        return SONGS_BY_NAME.get(name.toLowerCase(Locale.ROOT));
    }

    public static Song getSongFromItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() != SONG_MATERIAL) return null;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return null;
        if (!hasSongCustomModelData(meta)) return null;

        String value = meta.getPersistentDataContainer().get(BSInstruments.NSKEY, PersistentDataType.STRING);
        if (value == null || !value.startsWith(SONG_KEY_PREFIX)) return null;
        return getSongByName(value.substring(SONG_KEY_PREFIX.length()));
    }

    public static List<Song> getAllSongs() {
        return ALL_SONGS;
    }

    public static List<String> getAllSongNames() {
        return SONG_NAMES;
    }

    public static void giveAllSongs(Player player) {
        int given = 0;
        for (Song song : ALL_SONGS) {
            if (!ItemDelivery.giveToInventory(player, song.getItem())) break;
            given++;
        }
        player.sendMessage("§aAdded §e" + given + "§a sheet music items to your inventory.");
        if (given < ALL_SONGS.size()) player.sendMessage("§cInventory full. Some sheet music was not added.");
    }

    public static Song getRandomSong() {
        return ALL_SONGS.get(ThreadLocalRandom.current().nextInt(ALL_SONGS.size()));
    }

    public static boolean isSameSong(ItemStack itemStack, Song song) {
        Song itemSong = getSongFromItem(itemStack);
        return itemSong == song;
    }

    private static boolean hasSongCustomModelData(ItemMeta meta) {
        return meta.hasCustomModelDataComponent()
                && meta.getCustomModelDataComponent().getFloats().contains(SONG_CUSTOM_MODEL_DATA);
    }

    private static String phrase(String... parts) {
        return String.join(" ", parts);
    }

    private record SongSeed(String title, String style, String pattern) {
    }
}
