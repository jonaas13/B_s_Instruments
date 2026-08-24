package biraw.online.bSInstruments;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class SongPlayer {
    private static final double SYNC_RADIUS_BLOCKS = 24.0;
    private static final double SYNC_RADIUS_SQUARED = SYNC_RADIUS_BLOCKS * SYNC_RADIUS_BLOCKS;
    private static final Map<UUID, SongTask> ACTIVE_SONGS = new HashMap<>();

    public static boolean tryStart(Player player, Instrument instrument) {
        UUID playerId = player.getUniqueId();
        if (ACTIVE_SONGS.containsKey(playerId)) return true;

        Song song = AllSongs.getSongFromItem(player.getInventory().getItemInMainHand());
        if (song == null) return false;

        SongTask nearbySongTask = findNearbySongTask(player, song);
        int startTick = nearbySongTask == null ? Bukkit.getCurrentTick() : nearbySongTask.startTick();
        int layer = nearbySongTask == null ? 0 : getNextLayer(player, song, startTick);

        SongTask songTask = new SongTask(player, instrument, song, startTick, layer);
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(BSInstruments.getInstance(), songTask, 0L, 1L);
        songTask.setTask(task);
        ACTIVE_SONGS.put(playerId, songTask);
        player.sendActionBar("§d♪ " + song.title() + " · Layer " + (layer + 1) + " ♪");
        return true;
    }

    public static void stop(Player player) {
        SongTask songTask = ACTIVE_SONGS.remove(player.getUniqueId());
        if (songTask != null) songTask.cancel();
    }

    public static boolean isActive(Player player) {
        return ACTIVE_SONGS.containsKey(player.getUniqueId());
    }

    public static void stopAll() {
        for (SongTask songTask : ACTIVE_SONGS.values()) {
            songTask.cancel();
        }
        ACTIVE_SONGS.clear();
    }

    private static SongTask findNearbySongTask(Player player, Song song) {
        SongTask nearest = null;
        double nearestDistanceSquared = Double.MAX_VALUE;
        for (SongTask songTask : ACTIVE_SONGS.values()) {
            if (!songTask.isJoinableBy(player, song)) continue;

            double distanceSquared = player.getLocation().distanceSquared(songTask.playerLocation());
            if (distanceSquared < nearestDistanceSquared) {
                nearestDistanceSquared = distanceSquared;
                nearest = songTask;
            }
        }
        return nearest;
    }

    private static int getNextLayer(Player player, Song song, int startTick) {
        Set<Integer> usedLayers = new HashSet<>();
        for (SongTask songTask : ACTIVE_SONGS.values()) {
            if (!songTask.isSamePerformance(player, song, startTick)) continue;
            usedLayers.add(songTask.layer());
        }

        for (int layer = 0; layer < song.layerCount(); layer++) {
            if (!usedLayers.contains(layer)) return layer;
        }
        return usedLayers.size() % song.layerCount();
    }

    private static class SongTask implements Runnable {
        private final Player player;
        private final Instrument instrument;
        private final Song song;
        private final int startTick;
        private final int layer;
        private final List<Song.SongNote> notes;
        private BukkitTask task;
        private int noteIndex;
        private int nextNoteStartTick;

        private SongTask(Player player, Instrument instrument, Song song, int startTick, int layer) {
            this.player = player;
            this.instrument = instrument;
            this.song = song;
            this.startTick = startTick;
            this.layer = layer;
            this.notes = song.notesForLayer(layer);
            seek(Bukkit.getCurrentTick() - startTick);
        }

        private void setTask(BukkitTask task) {
            this.task = task;
        }

        @Override
        public void run() {
            if (!player.isOnline()
                    || !instrument.isThisInstrument(player.getInventory().getItemInOffHand())
                    || !AllSongs.isSameSong(player.getInventory().getItemInMainHand(), song)) {
                finish();
                return;
            }

            int songTick = Bukkit.getCurrentTick() - startTick;
            if (songTick >= song.durationTicks() || noteIndex >= notes.size()) {
                finish();
                return;
            }

            Song.SongNote songNote = getDueNote(songTick);
            if (songNote == null) return;

            if (!songNote.isRest()) {
                instrument.playSongNote(player, songNote.noteId());
            }
        }

        private int startTick() {
            return startTick;
        }

        private int layer() {
            return layer;
        }

        private Location playerLocation() {
            return player.getLocation();
        }

        private boolean isJoinableBy(Player joiningPlayer, Song joiningSong) {
            return isSamePerformanceSong(joiningSong)
                    && player.isOnline()
                    && player.getWorld().equals(joiningPlayer.getWorld())
                    && player.getLocation().distanceSquared(joiningPlayer.getLocation()) <= SYNC_RADIUS_SQUARED
                    && Bukkit.getCurrentTick() - startTick < song.durationTicks();
        }

        private boolean isSamePerformance(Player joiningPlayer, Song joiningSong, int performanceStartTick) {
            return isJoinableBy(joiningPlayer, joiningSong) && startTick == performanceStartTick;
        }

        private boolean isSamePerformanceSong(Song joiningSong) {
            return song == joiningSong;
        }

        private void seek(int songTick) {
            noteIndex = 0;
            nextNoteStartTick = 0;
            while (noteIndex < notes.size()) {
                Song.SongNote note = notes.get(noteIndex);
                int noteEndTick = nextNoteStartTick + Math.max(1, note.durationTicks());
                if (songTick <= nextNoteStartTick) return;

                noteIndex++;
                nextNoteStartTick = noteEndTick;
                if (songTick < noteEndTick) return;
            }
        }

        private Song.SongNote getDueNote(int songTick) {
            Song.SongNote dueNote = null;
            while (noteIndex < notes.size() && songTick >= nextNoteStartTick) {
                Song.SongNote note = notes.get(noteIndex);
                int noteEndTick = nextNoteStartTick + Math.max(1, note.durationTicks());
                if (songTick < noteEndTick) dueNote = note;

                noteIndex++;
                nextNoteStartTick = noteEndTick;
                if (songTick < noteEndTick) break;
            }
            return dueNote;
        }

        private void cancel() {
            if (task != null) task.cancel();
        }

        private void finish() {
            ACTIVE_SONGS.remove(player.getUniqueId());
            cancel();
        }
    }
}
