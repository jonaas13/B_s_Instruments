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
        Song song = AllSongs.getSongFromItem(player.getInventory().getItemInMainHand());
        if (song == null) return false;
        if (DirectorMode.tryStartDirectorSession(player, instrument, song)) return true;

        return tryStart(player, instrument, song, true, true);
    }

    public static boolean tryStartInvited(Player player, Instrument instrument, Song song) {
        if (song == null) return false;

        return tryStart(player, instrument, song, false, true);
    }

    static boolean tryJoinDirectorPerformance(Player player, Instrument instrument, Song song, Player director) {
        if (song == null || director == null) return false;

        Participant directorParticipant = ACTIVE_PLAYERS.get(director.getUniqueId());
        if (directorParticipant == null || directorParticipant.song() != song) return false;

        return tryStart(player, instrument, song, false, false, directorParticipant.performance());
    }

    static boolean startDirectorPerformance(Player director, Instrument directorInstrument, Song song, Map<Player, Instrument> invitedPlayers) {
        if (song == null || directorInstrument == null) return false;

        if (!tryStart(director, directorInstrument, song, true, true)) return false;

        for (Map.Entry<Player, Instrument> entry : invitedPlayers.entrySet()) {
            Player invitedPlayer = entry.getKey();
            Instrument invitedInstrument = entry.getValue();
            if (invitedPlayer == null || invitedInstrument == null) continue;
            if (!invitedPlayer.isOnline()) continue;

            tryJoinDirectorPerformance(invitedPlayer, invitedInstrument, song, director);
        }
        return true;
    }

    private static boolean tryStart(Player player, Instrument instrument, Song song, boolean requiresSheetMusic, boolean createIfMissing) {
        return tryStart(player, instrument, song, requiresSheetMusic, createIfMissing, null);
    }

    private static boolean tryStart(Player player, Instrument instrument, Song song, boolean requiresSheetMusic, boolean createIfMissing, Performance forcedPerformance) {
        UUID playerId = player.getUniqueId();
        Participant activeParticipant = ACTIVE_PLAYERS.get(playerId);
        if (activeParticipant != null) {
            if (activeParticipant.isValid() && activeParticipant.song() == song) {
                activeParticipant.setInstrument(instrument);
                return true;
            }

            activeParticipant.performance().remove(activeParticipant);
        }

        Performance performance = forcedPerformance != null ? forcedPerformance : findNearbyPerformance(player, song);
        if (performance == null && !createIfMissing) return false;
        if (performance == null) {
            performance = new Performance(song, Bukkit.getCurrentTick() + 1);
            ACTIVE_PERFORMANCES.add(performance);
            performance.setTask(Bukkit.getScheduler().runTaskTimer(BSInstruments.getInstance(), performance, 0L, 1L));
        }

        int layer = performance.nextAvailableLayer(player, instrument);
        Participant participant = new Participant(player, instrument, song, layer, performance.currentSongTick(), requiresSheetMusic);
        performance.add(participant);
        ACTIVE_PLAYERS.put(playerId, participant);
        performance.playCurrentTickFor(participant);

        player.sendActionBar("§d♪ " + song.title() + " · Layer " + (layer + 1) + " ♪");
        return true;
    }

    public static boolean stop(Player player) {
        Participant participant = ACTIVE_PLAYERS.remove(player.getUniqueId());
        if (participant == null) return false;

        participant.performance().remove(participant);
        return true;
    }

    public static int stopNearbyPerformance(Player player, Song song) {
        Performance performance = null;
        Participant activeParticipant = ACTIVE_PLAYERS.get(player.getUniqueId());
        if (activeParticipant != null && activeParticipant.song() == song) {
            performance = activeParticipant.performance();
        }

        if (performance == null) {
            performance = findNearbyPerformance(player, song);
        }
        if (performance == null) return 0;

        return performance.stopParticipants();
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
        private final Map<UUID, Integer> rememberedLayers = new HashMap<>();
        private BukkitTask task;
        private int lastRunTick = Integer.MIN_VALUE;

        private Performance(Song song, int startTick) {
            this.song = song;
            this.startTick = startTick;
        }

        private void setTask(BukkitTask task) {
            this.task = task;
        }

        @Override
        public void run() {
            if (song.durationTicks() <= 0) {
                finish();
                return;
            }

            lastRunTick = Bukkit.getCurrentTick();
            int songTick = currentSongTick();
            for (Participant participant : List.copyOf(participants.values())) {
                if (!participant.isValid()) {
                    remove(participant);
                    continue;
                }

                participant.playDueNote(songTick);
            }

            if (participants.isEmpty()) finish();
        }

        private int currentSongTick() {
            int elapsedTicks = Bukkit.getCurrentTick() - startTick;
            if (elapsedTicks <= 0) return 0;
            return elapsedTicks % song.durationTicks();
        }

        private void add(Participant participant) {
            participant.setPerformance(this);
            participants.put(participant.playerId(), participant);
        }

        private void playCurrentTickFor(Participant participant) {
            if (lastRunTick != Bukkit.getCurrentTick()) return;
            if (!participant.isValid()) {
                remove(participant);
                return;
            }

            participant.playDueNote(currentSongTick());
        }

        private void remove(Participant participant) {
            participants.remove(participant.playerId());
            rememberedLayers.put(participant.playerId(), participant.layer());
            ACTIVE_PLAYERS.remove(participant.playerId());
            if (participants.isEmpty()) finish();
        }

        private int nextAvailableLayer(Player player, Instrument instrument) {
            Integer rememberedLayer = rememberedLayers.get(player.getUniqueId());
            if (rememberedLayer != null
                    && isLayerAvailable(rememberedLayer)
                    && instrument.matchesSongLayer(song.layer(rememberedLayer))) {
                return rememberedLayer;
            }

            return bestLayerFor(instrument, -1, null);
        }

        private boolean isLayerAvailable(int layer) {
            for (Participant participant : participants.values()) {
                if (participant.layer() == layer) return false;
            }
            return true;
        }

        private int bestLayerFor(Instrument instrument, int currentLayer, UUID currentPlayerId) {
            Set<Integer> usedLayers = new HashSet<>();
            for (Participant participant : participants.values()) {
                if (participant.playerId().equals(currentPlayerId)) continue;
                usedLayers.add(participant.layer());
            }

            if (usedLayers.isEmpty() && currentLayer < 0) {
                return firstPlayableLayerFor(instrument);
            }

            if (currentLayer >= 0
                    && !usedLayers.contains(currentLayer)
                    && instrument.matchesSongLayer(song.layer(currentLayer))) {
                return currentLayer;
            }

            int bestUnusedMatchingLayer = bestMatchingLayer(instrument, usedLayers);
            if (bestUnusedMatchingLayer >= 0) return bestUnusedMatchingLayer;

            int unusedFallbackLayer = firstFallbackLayer(instrument, usedLayers);
            if (unusedFallbackLayer >= 0) return unusedFallbackLayer;

            int bestMatchingLayer = bestMatchingLayer(instrument, Set.of());
            if (bestMatchingLayer >= 0) return bestMatchingLayer;

            int fallbackLayer = firstFallbackLayer(instrument, Set.of());
            if (fallbackLayer >= 0) return fallbackLayer;

            return 0;
        }

        private int firstPlayableLayerFor(Instrument instrument) {
            if (instrument.matchesSongLayer(song.layer(0))) return 0;

            int bestMatchingLayer = bestMatchingLayer(instrument, Set.of());
            if (bestMatchingLayer >= 0 && song.layer(bestMatchingLayer).preferredInstrumentName().equals("percussion")) {
                return bestMatchingLayer;
            }
            return 0;
        }

        private int firstFallbackLayer(Instrument instrument, Set<Integer> skippedLayers) {
            for (int layer = 0; layer < song.layerCount(); layer++) {
                if (skippedLayers.contains(layer)) continue;
                if (instrument.canUseSongLayerAsFallback(song.layer(layer))) return layer;
            }
            return -1;
        }

        private int bestMatchingLayer(Instrument instrument, Set<Integer> skippedLayers) {
            int bestLayer = -1;
            int bestScore = 0;
            for (int layer = 0; layer < song.layerCount(); layer++) {
                if (skippedLayers.contains(layer)) continue;

                int score = instrument.songLayerMatchScore(song.layer(layer));
                if (score > bestScore) {
                    bestLayer = layer;
                    bestScore = score;
                }
            }
            return bestLayer;
        }

        private boolean isJoinableBy(Player joiningPlayer, Song joiningSong) {
            return song == joiningSong
                    && !participants.isEmpty()
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

        private int stopParticipants() {
            int stopped = participants.size();
            for (Participant participant : List.copyOf(participants.values())) {
                ACTIVE_PLAYERS.remove(participant.playerId());
            }
            participants.clear();
            finish();
            return stopped;
        }
    }

    private static class Participant {
        private final Player player;
        private Instrument instrument;
        private final Song song;
        private int layer;
        private Instrument.SongPlaybackTuning tuning;
        private Performance performance;
        private int lastSongTick = -1;
        private final boolean requiresSheetMusic;

        private Participant(Player player, Instrument instrument, Song song, int layer, int songTick, boolean requiresSheetMusic) {
            this.player = player;
            this.instrument = instrument;
            this.song = song;
            this.layer = layer;
            this.tuning = instrument.createSongPlaybackTuning();
            this.lastSongTick = songTick <= 0 ? song.durationTicks() - 1 : songTick - 1;
            this.requiresSheetMusic = requiresSheetMusic;
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

        private Song song() {
            return song;
        }

        private void setInstrument(Instrument instrument) {
            this.instrument = instrument;
            this.tuning = instrument.createSongPlaybackTuning();
            reassignLayerForInstrument();
        }

        private Performance performance() {
            return performance;
        }

        private void setPerformance(Performance performance) {
            this.performance = performance;
        }

        private void reassignLayerForInstrument() {
            if (performance == null) return;

            int nextLayer = performance.bestLayerFor(instrument, layer, playerId());
            if (nextLayer == layer) return;

            layer = nextLayer;
            tuning = instrument.createSongPlaybackTuning();
            player.sendActionBar("§d♪ " + song.title() + " · Layer " + (layer + 1) + " ♪");
        }

        private boolean isValid() {
            if (!player.isOnline()) return false;

            Instrument currentInstrument = AllInstruments.GetInstrumentFromItem(player.getInventory().getItemInOffHand());
            if (currentInstrument != null && currentInstrument != instrument) setInstrument(currentInstrument);

            return currentInstrument != null
                    && (!requiresSheetMusic || AllSongs.isSameSong(player.getInventory().getItemInMainHand(), song));
        }

        private void playDueNote(int songTick) {
            int previousSongTick = lastSongTick;
            lastSongTick = songTick;

            instrument.playSongNotes(player, song.eventsBetweenTicks(layer, previousSongTick, songTick), tuning);
        }
    }
}
