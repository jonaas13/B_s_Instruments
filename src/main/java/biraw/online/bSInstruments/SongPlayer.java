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
    private static final Set<Performance> ACTIVE_PERFORMANCES = new HashSet<>();
    private static final Map<UUID, Participant> ACTIVE_PLAYERS = new HashMap<>();

    public static boolean tryStart(Player player, Instrument instrument) {
        UUID playerId = player.getUniqueId();
        if (ACTIVE_PLAYERS.containsKey(playerId)) return true;

        Song song = AllSongs.getSongFromItem(player.getInventory().getItemInMainHand());
        if (song == null) return false;

        Performance performance = findNearbyPerformance(player, song);
        if (performance == null) {
            performance = new Performance(song, Bukkit.getCurrentTick() + 1);
            ACTIVE_PERFORMANCES.add(performance);
            performance.setTask(Bukkit.getScheduler().runTaskTimer(BSInstruments.getInstance(), performance, 0L, 1L));
        }

        int layer = performance.nextAvailableLayer();
        Participant participant = new Participant(player, instrument, song, layer, Bukkit.getCurrentTick() - performance.startTick());
        performance.add(participant);
        ACTIVE_PLAYERS.put(playerId, participant);

        player.sendActionBar("§d♪ " + song.title() + " · Layer " + (layer + 1) + " ♪");
        return true;
    }

    public static void stop(Player player) {
        Participant participant = ACTIVE_PLAYERS.remove(player.getUniqueId());
        if (participant != null) participant.performance().remove(participant);
    }

    public static boolean isActive(Player player) {
        return ACTIVE_PLAYERS.containsKey(player.getUniqueId());
    }

    public static void stopAll() {
        for (Performance performance : List.copyOf(ACTIVE_PERFORMANCES)) {
            performance.cancel();
        }
        ACTIVE_PERFORMANCES.clear();
        ACTIVE_PLAYERS.clear();
    }

    private static Performance findNearbyPerformance(Player player, Song song) {
        Performance nearest = null;
        double nearestDistanceSquared = Double.MAX_VALUE;
        for (Performance performance : ACTIVE_PERFORMANCES) {
            if (!performance.isJoinableBy(player, song)) continue;

            double distanceSquared = performance.nearestDistanceSquared(player);
            if (distanceSquared < nearestDistanceSquared) {
                nearestDistanceSquared = distanceSquared;
                nearest = performance;
            }
        }
        return nearest;
    }

    private static class Performance implements Runnable {
        private final Song song;
        private final int startTick;
        private final Map<UUID, Participant> participants = new HashMap<>();
        private BukkitTask task;

        private Performance(Song song, int startTick) {
            this.song = song;
            this.startTick = startTick;
        }

        private void setTask(BukkitTask task) {
            this.task = task;
        }

        @Override
        public void run() {
            int songTick = Bukkit.getCurrentTick() - startTick;
            if (songTick >= song.durationTicks()) {
                finish();
                return;
            }

            for (Participant participant : List.copyOf(participants.values())) {
                if (!participant.isValid()) {
                    remove(participant);
                    continue;
                }

                participant.playDueNote(songTick);
            }

            if (participants.isEmpty()) finish();
        }

        private int startTick() {
            return startTick;
        }

        private void add(Participant participant) {
            participant.setPerformance(this);
            participants.put(participant.playerId(), participant);
        }

        private void remove(Participant participant) {
            participants.remove(participant.playerId());
            ACTIVE_PLAYERS.remove(participant.playerId());
            if (participants.isEmpty()) finish();
        }

        private int nextAvailableLayer() {
            Set<Integer> usedLayers = new HashSet<>();
            for (Participant participant : participants.values()) {
                usedLayers.add(participant.layer());
            }

            for (int layer = 0; layer < song.layerCount(); layer++) {
                if (!usedLayers.contains(layer)) return layer;
            }
            return usedLayers.size() % song.layerCount();
        }

        private boolean isJoinableBy(Player joiningPlayer, Song joiningSong) {
            return song == joiningSong
                    && !participants.isEmpty()
                    && Bukkit.getCurrentTick() - startTick < song.durationTicks()
                    && nearestDistanceSquared(joiningPlayer) <= SYNC_RADIUS_SQUARED;
        }

        private double nearestDistanceSquared(Player player) {
            double nearestDistanceSquared = Double.MAX_VALUE;
            for (Participant participant : participants.values()) {
                if (!participant.player().isOnline()) continue;
                if (!participant.player().getWorld().equals(player.getWorld())) continue;

                Location participantLocation = participant.player().getLocation();
                double distanceSquared = participantLocation.distanceSquared(player.getLocation());
                if (distanceSquared < nearestDistanceSquared) nearestDistanceSquared = distanceSquared;
            }
            return nearestDistanceSquared;
        }

        private void finish() {
            for (Participant participant : List.copyOf(participants.values())) {
                ACTIVE_PLAYERS.remove(participant.playerId());
            }
            participants.clear();
            ACTIVE_PERFORMANCES.remove(this);
            cancel();
        }

        private void cancel() {
            if (task != null) task.cancel();
        }
    }

    private static class Participant {
        private final Player player;
        private final Instrument instrument;
        private final Song song;
        private final int layer;
        private final List<Song.SongNote> notes;
        private Performance performance;
        private int noteIndex;
        private int nextNoteStartTick;

        private Participant(Player player, Instrument instrument, Song song, int layer, int songTick) {
            this.player = player;
            this.instrument = instrument;
            this.song = song;
            this.layer = layer;
            this.notes = song.notesForLayer(layer);
            seek(songTick);
        }

        private UUID playerId() {
            return player.getUniqueId();
        }

        private Player player() {
            return player;
        }

        private int layer() {
            return layer;
        }

        private Performance performance() {
            return performance;
        }

        private void setPerformance(Performance performance) {
            this.performance = performance;
        }

        private boolean isValid() {
            return player.isOnline()
                    && instrument.isThisInstrument(player.getInventory().getItemInOffHand())
                    && AllSongs.isSameSong(player.getInventory().getItemInMainHand(), song);
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
            }
        }

        private void playDueNote(int songTick) {
            for (Song.SongNote songNote : getDueNotes(songTick)) {
                if (!songNote.isRest()) {
                    instrument.playSongNote(player, songNote.noteId());
                }
            }
        }

        private List<Song.SongNote> getDueNotes(int songTick) {
            List<Song.SongNote> dueNotes = new java.util.ArrayList<>();
            while (noteIndex < notes.size() && songTick >= nextNoteStartTick) {
                Song.SongNote note = notes.get(noteIndex);
                int noteEndTick = nextNoteStartTick + Math.max(1, note.durationTicks());
                dueNotes.add(note);

                noteIndex++;
                nextNoteStartTick = noteEndTick;
                if (songTick < noteEndTick) break;
            }
            return dueNotes;
        }
    }
}
