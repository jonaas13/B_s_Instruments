package biraw.online.bSInstruments;

import biraw.online.bSInstruments.Obtaining.ItemDelivery;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public final class AllSongs {
    private static final Material SONG_MATERIAL = Material.PAPER;
    private static final String SONG_KEY_PREFIX = "song_";
    private static final float SONG_CUSTOM_MODEL_DATA = (float) Song.CUSTOM_MODEL_DATA;
    private static final String SONG_DIRECTORY = "songs";
    private static final String BUNDLED_SONG_INDEX = "songs/index.txt";
    private static final int MAX_LOADED_SONGS = 750;
    private static final long MAX_MIDI_FILE_BYTES = 4L * 1024L * 1024L;

    private static List<Song> allSongs;
    private static Map<String, Song> songsByName;
    private static List<String> songNames;
    private static ImportStats importStats;

    private AllSongs() {
    }

    public static Song getSongByName(String name) {
        ensureLoaded();
        if (name == null) return null;
        return songsByName.get(name.toLowerCase(Locale.ROOT));
    }

    public static Song getSongFromItem(ItemStack itemStack) {
        ensureLoaded();
        if (itemStack == null || itemStack.getType() != SONG_MATERIAL) return null;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return null;
        if (!hasSongCustomModelData(meta)) return null;

        String value = meta.getPersistentDataContainer().get(BSInstruments.NSKEY, PersistentDataType.STRING);
        if (value == null || !value.startsWith(SONG_KEY_PREFIX)) return null;
        return getSongByName(value.substring(SONG_KEY_PREFIX.length()));
    }

    public static List<Song> getAllSongs() {
        ensureLoaded();
        return allSongs;
    }

    public static List<String> getAllSongNames() {
        ensureLoaded();
        return songNames;
    }

    public static void giveAllSongs(Player player) {
        ensureLoaded();
        if (allSongs.isEmpty()) {
            player.sendMessage("§cNo MIDI songs are loaded. Add .mid or .midi files to the plugin songs folder.");
            return;
        }

        int given = 0;
        for (Song song : allSongs) {
            if (!ItemDelivery.giveToInventory(player, song.getItem())) break;
            given++;
        }
        player.sendMessage("§aAdded §e" + given + "§a sheet music items to your inventory.");
        if (given < allSongs.size()) player.sendMessage("§cInventory full. Some sheet music was not added.");
    }

    public static Song getRandomSong() {
        ensureLoaded();
        if (allSongs.isEmpty()) return null;
        return allSongs.get(ThreadLocalRandom.current().nextInt(allSongs.size()));
    }

    public static boolean isSameSong(ItemStack itemStack, Song song) {
        Song itemSong = getSongFromItem(itemStack);
        return itemSong == song;
    }

    private static void ensureLoaded() {
        if (allSongs != null) return;

        List<Song> loadedSongs = new ArrayList<>();
        importStats = new ImportStats();
        loadBundledSongs(loadedSongs);
        loadExternalSongs(loadedSongs);
        indexSongs(loadedSongs);
    }

    private static void loadBundledSongs(List<Song> songs) {
        InputStream indexStream = BSInstruments.getInstance().getResource(BUNDLED_SONG_INDEX);
        if (indexStream == null) return;

        try (InputStream closeableIndexStream = indexStream) {
            List<String> resourceNames = new String(closeableIndexStream.readAllBytes())
                    .lines()
                    .map(String::trim)
                    .filter(line -> !line.isBlank() && !line.startsWith("#"))
                    .toList();
            for (String resourceName : resourceNames) {
                if (songs.size() >= MAX_LOADED_SONGS) {
                    warn("MIDI song limit reached. Skipping remaining bundled songs.");
                    importStats.skipped++;
                    break;
                }
                if (!isMidiName(resourceName)) continue;
                try (InputStream songStream = BSInstruments.getInstance().getResource(resourceName)) {
                    if (songStream == null) {
                        warn("Bundled MIDI is listed but missing: " + resourceName);
                        importStats.skipped++;
                        continue;
                    }
                    String title = MidiSongImporter.titleFromFileName(Path.of(resourceName));
                    songs.add(MidiSongImporter.importSong(title, songStream));
                    importStats.loaded++;
                } catch (IOException | InvalidMidiDataException exception) {
                    warn("Skipping bundled MIDI " + resourceName + ": " + exception.getMessage());
                    importStats.skipped++;
                }
            }
        } catch (IOException exception) {
            warn("Could not read bundled MIDI index: " + exception.getMessage());
            importStats.skipped++;
        }
    }

    private static void loadExternalSongs(List<Song> songs) {
        Path songDirectory = BSInstruments.getInstance().getDataFolder().toPath().resolve(SONG_DIRECTORY);
        try {
            Files.createDirectories(songDirectory);
        } catch (IOException exception) {
            warn("Could not create MIDI song folder " + songDirectory + ": " + exception.getMessage());
            importStats.skipped++;
            return;
        }

        List<Path> midiFiles;
        try (var paths = Files.list(songDirectory)) {
            midiFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> isMidiName(path.getFileName().toString()))
                    .sorted(Comparator.comparing(path -> path.getFileName().toString().toLowerCase(Locale.ROOT)))
                    .toList();
        } catch (IOException exception) {
            warn("Could not scan MIDI song folder " + songDirectory + ": " + exception.getMessage());
            importStats.skipped++;
            return;
        }

        for (Path midiFile : midiFiles) {
            if (songs.size() >= MAX_LOADED_SONGS) {
                warn("MIDI song limit reached. Skipping remaining external songs.");
                importStats.skipped++;
                break;
            }
            try {
                if (Files.size(midiFile) > MAX_MIDI_FILE_BYTES) {
                    warn("Skipping MIDI " + midiFile.getFileName() + ": file is larger than " + MAX_MIDI_FILE_BYTES + " bytes");
                    importStats.skipped++;
                    continue;
                }
            } catch (IOException exception) {
                warn("Skipping MIDI " + midiFile.getFileName() + ": could not read file size");
                importStats.skipped++;
                continue;
            }
            try (InputStream inputStream = Files.newInputStream(midiFile)) {
                songs.add(MidiSongImporter.importSong(
                        MidiSongImporter.titleFromFileName(midiFile),
                        inputStream
                ));
                importStats.loaded++;
            } catch (IOException | InvalidMidiDataException exception) {
                warn("Skipping MIDI " + midiFile.getFileName() + ": " + exception.getMessage());
                importStats.skipped++;
            }
        }
    }

    private static void indexSongs(List<Song> songs) {
        Map<String, Song> loadedSongsByName = new HashMap<>();
        List<String> loadedSongNames = new ArrayList<>();
        List<Song> uniqueSongs = new ArrayList<>();

        for (Song song : songs) {
            String lookupName = song.lookupName();
            if (loadedSongsByName.containsKey(lookupName)) {
                warn("Skipping duplicate MIDI song id: " + lookupName);
                importStats.skipped++;
                continue;
            }
            loadedSongsByName.put(lookupName, song);
            loadedSongNames.add(lookupName);
            uniqueSongs.add(song);
        }

        allSongs = List.copyOf(uniqueSongs);
        songsByName = Map.copyOf(loadedSongsByName);
        songNames = List.copyOf(loadedSongNames);
        int totalLayers = 0;
        int totalEvents = 0;
        for (Song song : allSongs) {
            totalLayers += song.layerCount();
            totalEvents += song.totalEventCount();
        }
        BSInstruments.getInstance().getLogger().info(
                "Loaded " + allSongs.size()
                        + " MIDI songs (" + totalLayers
                        + " layers, " + totalEvents
                        + " note events, " + importStats.skipped
                        + " skipped)."
        );
    }

    private static boolean hasSongCustomModelData(ItemMeta meta) {
        return meta.hasCustomModelDataComponent()
                && meta.getCustomModelDataComponent().getFloats().contains(SONG_CUSTOM_MODEL_DATA);
    }

    private static boolean isMidiName(String name) {
        String lowerName = name.toLowerCase(Locale.ROOT);
        return lowerName.endsWith(".mid") || lowerName.endsWith(".midi");
    }

    private static void warn(String message) {
        BSInstruments.getInstance().getLogger().warning(message);
    }

    private static final class ImportStats {
        private int loaded;
        private int skipped;
    }
}
