package biraw.online.bSInstruments;

import org.bukkit.Bukkit;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SongPlayer {
    private static final Map<UUID, BukkitTask> ACTIVE_SONGS = new HashMap<>();

    public static boolean tryStart(Player player, Instrument instrument) {
        UUID playerId = player.getUniqueId();
        if (ACTIVE_SONGS.containsKey(playerId)) return true;

        Song song = AllSongs.getSongFromItem(player.getInventory().getItemInMainHand());
        if (song == null) return false;

        SongTask songTask = new SongTask(player, instrument, song);
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(BSInstruments.getInstance(), songTask, 0L, 1L);
        songTask.setTask(task);
        ACTIVE_SONGS.put(playerId, task);
        player.sendActionBar("§d♪ " + song.title() + " ♪");
        return true;
    }

    public static void stop(Player player) {
        BukkitTask task = ACTIVE_SONGS.remove(player.getUniqueId());
        if (task != null) task.cancel();
    }

    public static boolean isActive(Player player) {
        return ACTIVE_SONGS.containsKey(player.getUniqueId());
    }

    public static void stopAll() {
        for (BukkitTask task : ACTIVE_SONGS.values()) {
            task.cancel();
        }
        ACTIVE_SONGS.clear();
    }

    private static class SongTask implements Runnable {
        private final Player player;
        private final Instrument instrument;
        private final Song song;
        private BukkitTask task;
        private int noteIndex;
        private int ticksUntilNextNote;

        private SongTask(Player player, Instrument instrument, Song song) {
            this.player = player;
            this.instrument = instrument;
            this.song = song;
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

            if (ticksUntilNextNote > 0) {
                ticksUntilNextNote--;
                return;
            }

            if (noteIndex >= song.notes().size()) {
                finish();
                return;
            }

            Song.SongNote songNote = song.notes().get(noteIndex);
            noteIndex++;
            ticksUntilNextNote = Math.max(1, songNote.durationTicks()) - 1;

            if (!songNote.isRest()) {
                int noteId = Math.max(0, Math.min(24, songNote.noteId()));
                instrument.playSongNote(player, new Note(noteId));
            }
        }

        private void finish() {
            ACTIVE_SONGS.remove(player.getUniqueId());
            if (task != null) task.cancel();
        }
    }
}
