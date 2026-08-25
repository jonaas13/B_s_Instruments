package biraw.online.bSInstruments;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

final class MidiSongImporter {
    private static final int DEFAULT_TEMPO_MPQ = 500_000;
    private static final int MINECRAFT_TICKS_PER_SECOND = 20;
    private static final int MAX_NOTES_PER_LAYER_TICK = 4;
    private static final int MAX_RAW_NOTE_EVENTS_PER_SONG = 25_000;
    private static final int MAX_SOURCE_LAYERS_PER_SONG = 32;
    private static final int MAX_PERFORMANCE_LAYERS_PER_SONG = 8;
    private static final int PERCUSSION_CHANNEL = 9;
    private static final int MIN_PLAYABLE_MIDI_NOTE = 54;
    private static final int MAX_PLAYABLE_MIDI_NOTE = 78;
    private static final int CENTER_PLAYABLE_MIDI_NOTE = 66;

    private MidiSongImporter() {
    }

    static Song importSong(String title, InputStream inputStream)
            throws IOException, InvalidMidiDataException {
        Sequence sequence;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            sequence = MidiSystem.getSequence(bufferedInputStream);
        }

        List<TempoChange> tempoChanges = readTempoChanges(sequence);
        Map<LayerKey, List<Song.SongNoteEvent>> eventsByLayer = new HashMap<>();
        int durationTicks = Math.max(1, toMinecraftTick(sequence.getTickLength(), sequence, tempoChanges) + 1);
        int[] rawNoteEventCount = {0};

        Track[] tracks = sequence.getTracks();
        for (int trackIndex = 0; trackIndex < tracks.length; trackIndex++) {
            readTrack(sequence, tempoChanges, tracks[trackIndex], trackIndex, eventsByLayer, rawNoteEventCount);
        }

        List<SourceLayer> sourceLayers = buildSourceLayers(eventsByLayer);
        List<Song.SongLayer> layers = buildPerformanceLayers(sourceLayers);
        if (layers.isEmpty()) {
            throw new InvalidMidiDataException("MIDI file has no playable note events");
        }
        layers = withPercussionFallback(layers);

        return new Song(title, layers, durationTicks);
    }

    static String titleFromFileName(Path path) {
        String fileName = path.getFileName().toString();
        int dot = fileName.lastIndexOf('.');
        if (dot > 0) fileName = fileName.substring(0, dot);
        String[] words = fileName.replace('_', ' ').replace('-', ' ').trim().split("\\s+");
        List<String> titleWords = new ArrayList<>();
        for (String word : words) {
            if (word.isBlank()) continue;
            titleWords.add(word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1));
        }
        return titleWords.isEmpty() ? "Untitled MIDI" : String.join(" ", titleWords);
    }

    private static void readTrack(
            Sequence sequence,
            List<TempoChange> tempoChanges,
            Track track,
            int trackIndex,
            Map<LayerKey, List<Song.SongNoteEvent>> eventsByLayer,
            int[] rawNoteEventCount
    ) {
        int[] programsByChannel = new int[16];
        double[] pitchBendsByChannel = new double[16];
        String trackName = "Track " + (trackIndex + 1);

        for (int eventIndex = 0; eventIndex < track.size(); eventIndex++) {
            MidiEvent event = track.get(eventIndex);
            MidiMessage message = event.getMessage();
            if (message instanceof MetaMessage metaMessage && metaMessage.getType() == 0x03) {
                trackName = new String(metaMessage.getData()).trim();
                if (trackName.isBlank()) trackName = "Track " + (trackIndex + 1);
                continue;
            }
            if (!(message instanceof ShortMessage shortMessage)) continue;

            int channel = shortMessage.getChannel();
            int command = shortMessage.getCommand();
            if (command == ShortMessage.PROGRAM_CHANGE) {
                programsByChannel[channel] = shortMessage.getData1();
                continue;
            }
            if (command == ShortMessage.PITCH_BEND) {
                pitchBendsByChannel[channel] = pitchBendSemitones(shortMessage);
                continue;
            }

            if (command != ShortMessage.NOTE_ON || shortMessage.getData2() <= 0) continue;
            if (rawNoteEventCount[0] >= MAX_RAW_NOTE_EVENTS_PER_SONG) continue;
            rawNoteEventCount[0]++;

            int minecraftTick = toMinecraftTick(event.getTick(), sequence, tempoChanges);
            int program = channel == PERCUSSION_CHANNEL ? 128 : programsByChannel[channel];
            LayerKey layerKey = new LayerKey(trackIndex, channel, program, trackName);
            eventsByLayer.computeIfAbsent(layerKey, ignored -> new ArrayList<>())
                    .add(new Song.SongNoteEvent(
                            minecraftTick,
                            shortMessage.getData1(),
                            shortMessage.getData2(),
                            channel == PERCUSSION_CHANNEL ? 0.0 : pitchBendsByChannel[channel]
                    ));
        }
    }

    private static double pitchBendSemitones(ShortMessage shortMessage) {
        int value = (shortMessage.getData2() << 7) | shortMessage.getData1();
        return ((value - 8192) / 8192.0) * 2.0;
    }

    private static List<SourceLayer> buildSourceLayers(Map<LayerKey, List<Song.SongNoteEvent>> eventsByLayer) {
        List<Map.Entry<LayerKey, List<Song.SongNoteEvent>>> entries = new ArrayList<>(eventsByLayer.entrySet());
        entries.sort(Comparator.comparingInt((Map.Entry<LayerKey, List<Song.SongNoteEvent>> entry) -> entry.getKey().trackIndex())
                .thenComparingInt(entry -> entry.getKey().channel())
                .thenComparingInt(entry -> entry.getKey().program()));

        List<SourceLayer> layers = new ArrayList<>();
        for (Map.Entry<LayerKey, List<Song.SongNoteEvent>> entry : entries) {
            if (layers.size() >= MAX_SOURCE_LAYERS_PER_SONG) break;

            boolean percussion = entry.getKey().program() == 128;
            List<Song.SongNoteEvent> normalizedEvents = normalizeDenseTicks(entry.getValue());
            String preferredInstrumentName = percussion ? "percussion" : mapProgramToInstrumentName(entry.getKey().program());
            if (percussion) normalizedEvents = normalizePercussionEvents(normalizedEvents);
            else normalizedEvents = transposeLayerIntoPlayableRange(normalizedEvents);
            if (normalizedEvents.isEmpty()) continue;
            layers.add(new SourceLayer(
                    entry.getKey().displayName(),
                    preferredInstrumentName,
                    normalizedEvents
            ));
        }
        return List.copyOf(layers);
    }

    private static List<Song.SongLayer> buildPerformanceLayers(List<SourceLayer> sourceLayers) {
        List<Song.SongLayer> layers = new ArrayList<>();

        SourceLayer leadSourceLayer = bestLeadSourceLayer(sourceLayers);
        List<Song.SongNoteEvent> lead = leadSourceLayer == null ? List.of() : buildLeadLayer(leadSourceLayer);
        if (leadSourceLayer != null && !lead.isEmpty()) {
            layers.add(new Song.SongLayer("Lead", leadSourceLayer.preferredInstrumentName(), lead));
        }

        layers.addAll(buildPercussionLayers(sourceLayers));

        List<Song.SongNoteEvent> bass = new ArrayList<>(mergeEventsForRole(sourceLayers, "bass-guitar", event -> event.midiNote() <= 64));
        bass.addAll(mergeEventsForRole(sourceLayers, "didgeridoo", event -> event.midiNote() <= 64));
        bass = normalizeDenseTicks(bass);
        if (!bass.isEmpty()) layers.add(new Song.SongLayer("Bass", "bass-guitar", bass));

        List<Song.SongNoteEvent> harmony = buildHarmonyLayer(sourceLayers, leadSourceLayer, lead);
        if (!harmony.isEmpty()) layers.add(new Song.SongLayer("Harmony", "piano", harmony));

        List<Song.SongNoteEvent> texture = buildTextureLayer(sourceLayers, leadSourceLayer, lead, harmony);
        if (!texture.isEmpty()) layers.add(new Song.SongLayer("Texture", "pling", texture));

        while (layers.size() > MAX_PERFORMANCE_LAYERS_PER_SONG) {
            layers.remove(layers.size() - 1);
        }
        return List.copyOf(layers);
    }

    private static List<Song.SongLayer> buildPercussionLayers(List<SourceLayer> sourceLayers) {
        List<Song.SongNoteEvent> percussion = mergeEventsForRole(sourceLayers, "percussion", event -> true);
        if (percussion.isEmpty()) return List.of();

        List<Song.SongNoteEvent> coreBeat = new ArrayList<>();
        List<Song.SongNoteEvent> highBeat = new ArrayList<>();
        List<Song.SongNoteEvent> fills = new ArrayList<>();

        for (Song.SongNoteEvent event : percussion) {
            switch (event.midiNote()) {
                case 54, 62 -> coreBeat.add(event);
                case 66, 70 -> highBeat.add(event);
                case 58 -> fills.add(event);
                default -> coreBeat.add(event);
            }
        }

        List<Song.SongLayer> layers = new ArrayList<>();
        addPercussionLayer(layers, "Percussion", coreBeat);
        addPercussionLayer(layers, "Percussion Hats", highBeat);
        addPercussionLayer(layers, "Percussion Fills", fills);

        if (layers.isEmpty()) layers.add(new Song.SongLayer("Percussion", "percussion", percussion));
        return List.copyOf(layers);
    }

    private static void addPercussionLayer(List<Song.SongLayer> layers, String name, List<Song.SongNoteEvent> events) {
        List<Song.SongNoteEvent> normalizedEvents = normalizeDenseTicks(events);
        if (normalizedEvents.isEmpty()) return;
        layers.add(new Song.SongLayer(name, "percussion", normalizedEvents));
    }

    private static SourceLayer bestLeadSourceLayer(List<SourceLayer> sourceLayers) {
        SourceLayer bestLayer = null;
        int bestScore = Integer.MIN_VALUE;
        for (SourceLayer sourceLayer : sourceLayers) {
            if (!isMelodicLeadCandidate(sourceLayer)) continue;

            int score = leadSourceScore(sourceLayer);
            if (score > bestScore) {
                bestLayer = sourceLayer;
                bestScore = score;
            }
        }

        if (bestLayer != null) return bestLayer;
        for (SourceLayer sourceLayer : sourceLayers) {
            if (!sourceLayer.preferredInstrumentName().equals("percussion")) return sourceLayer;
        }
        return null;
    }

    private static boolean isMelodicLeadCandidate(SourceLayer sourceLayer) {
        String preferredInstrumentName = sourceLayer.preferredInstrumentName();
        return !preferredInstrumentName.equals("percussion")
                && !preferredInstrumentName.equals("bass-guitar")
                && !preferredInstrumentName.equals("didgeridoo")
                && !sourceLayer.events().isEmpty();
    }

    private static int leadSourceScore(SourceLayer sourceLayer) {
        LayerRange range = centralSourceRange(sourceLayer.events());
        int score = Math.min(sourceLayer.events().size(), 600);
        score -= Math.abs(range.center() - CENTER_PLAYABLE_MIDI_NOTE) * 8;
        score -= Math.max(0, 58 - range.center()) * 20;

        String name = sourceLayer.name().toLowerCase(Locale.ROOT);
        if (name.contains("melody") || name.contains("lead") || name.contains("vocal") || name.contains("main")) score += 500;
        if (name.contains("right") || name.contains("treble")) score += 150;
        if (name.contains("chord") || name.contains("harmony") || name.contains("pad")) score -= 250;
        if (name.contains("arp") || name.contains("arpeggio")) score -= 350;

        return score;
    }

    private static List<Song.SongNoteEvent> buildLeadLayer(SourceLayer leadSourceLayer) {
        Map<Integer, List<Song.SongNoteEvent>> byTick = eventsByTick(leadSourceLayer.events());
        List<Song.SongNoteEvent> lead = new ArrayList<>();
        for (int tick : sortedTicks(byTick)) {
            byTick.get(tick).stream()
                    .max(Comparator.comparingInt(Song.SongNoteEvent::midiNote)
                            .thenComparingInt(Song.SongNoteEvent::velocity))
                    .ifPresent(lead::add);
        }
        return normalizeDenseTicks(lead);
    }

    private static List<Song.SongNoteEvent> buildHarmonyLayer(
            List<SourceLayer> sourceLayers,
            SourceLayer leadSourceLayer,
            List<Song.SongNoteEvent> lead
    ) {
        Set<LayerNoteKey> leadKeys = layerNoteKeys(lead);
        List<Song.SongNoteEvent> melodicEvents = mergeMelodicEventsExcept(sourceLayers, leadSourceLayer);
        Map<Integer, List<Song.SongNoteEvent>> byTick = eventsByTick(melodicEvents);
        List<Song.SongNoteEvent> harmony = new ArrayList<>();
        for (int tick : sortedTicks(byTick)) {
            List<Song.SongNoteEvent> candidates = byTick.get(tick).stream()
                    .filter(event -> !leadKeys.contains(new LayerNoteKey(event.tick(), event.midiNote())))
                    .filter(event -> event.midiNote() >= 50)
                    .sorted(Comparator.comparingInt(Song.SongNoteEvent::velocity).reversed())
                    .toList();
            int addedAtTick = 0;
            for (Song.SongNoteEvent event : candidates) {
                harmony.add(event);
                addedAtTick++;
                if (addedAtTick >= 2) break;
            }
        }
        return normalizeDenseTicks(harmony);
    }

    private static List<Song.SongNoteEvent> buildTextureLayer(
            List<SourceLayer> sourceLayers,
            SourceLayer leadSourceLayer,
            List<Song.SongNoteEvent> lead,
            List<Song.SongNoteEvent> harmony
    ) {
        Set<LayerNoteKey> usedKeys = layerNoteKeys(lead);
        usedKeys.addAll(layerNoteKeys(harmony));
        List<Song.SongNoteEvent> texture = mergeMelodicEventsExcept(sourceLayers, leadSourceLayer).stream()
                .filter(event -> !usedKeys.contains(new LayerNoteKey(event.tick(), event.midiNote())))
                .filter(event -> event.velocity() >= 55)
                .toList();
        return normalizeDenseTicks(texture);
    }

    private static List<Song.SongNoteEvent> mergeEventsForRole(
            List<SourceLayer> sourceLayers,
            String preferredInstrumentName,
            java.util.function.Predicate<Song.SongNoteEvent> predicate
    ) {
        List<Song.SongNoteEvent> events = new ArrayList<>();
        for (SourceLayer sourceLayer : sourceLayers) {
            if (!sourceLayer.preferredInstrumentName().equals(preferredInstrumentName)) continue;
            for (Song.SongNoteEvent event : sourceLayer.events()) {
                if (predicate.test(event)) events.add(event);
            }
        }
        return normalizeDenseTicks(events);
    }

    private static List<Song.SongNoteEvent> mergeMelodicEventsExcept(List<SourceLayer> sourceLayers, SourceLayer excludedLayer) {
        List<Song.SongNoteEvent> events = new ArrayList<>();
        for (SourceLayer sourceLayer : sourceLayers) {
            if (sourceLayer == excludedLayer) continue;
            if (sourceLayer.preferredInstrumentName().equals("percussion")) continue;
            if (sourceLayer.preferredInstrumentName().equals("bass-guitar")) continue;
            if (sourceLayer.preferredInstrumentName().equals("didgeridoo")) continue;
            events.addAll(sourceLayer.events());
        }
        return normalizeDenseTicks(events);
    }

    private static List<Song.SongNoteEvent> mergeMelodicEvents(List<SourceLayer> sourceLayers) {
        List<Song.SongNoteEvent> events = new ArrayList<>();
        for (SourceLayer sourceLayer : sourceLayers) {
            if (sourceLayer.preferredInstrumentName().equals("percussion")) continue;
            if (sourceLayer.preferredInstrumentName().equals("bass-guitar")) continue;
            if (sourceLayer.preferredInstrumentName().equals("didgeridoo")) continue;
            events.addAll(sourceLayer.events());
        }
        return normalizeDenseTicks(events);
    }

    private static Map<Integer, List<Song.SongNoteEvent>> eventsByTick(List<Song.SongNoteEvent> events) {
        Map<Integer, List<Song.SongNoteEvent>> byTick = new HashMap<>();
        for (Song.SongNoteEvent event : events) {
            byTick.computeIfAbsent(event.tick(), ignored -> new ArrayList<>()).add(event);
        }
        return byTick;
    }

    private static List<Integer> sortedTicks(Map<Integer, List<Song.SongNoteEvent>> byTick) {
        List<Integer> ticks = new ArrayList<>(byTick.keySet());
        ticks.sort(Integer::compareTo);
        return ticks;
    }

    private static Set<LayerNoteKey> layerNoteKeys(List<Song.SongNoteEvent> events) {
        Set<LayerNoteKey> keys = new HashSet<>();
        for (Song.SongNoteEvent event : events) {
            keys.add(new LayerNoteKey(event.tick(), event.midiNote()));
        }
        return keys;
    }

    private static List<Song.SongLayer> withPercussionFallback(List<Song.SongLayer> layers) {
        for (Song.SongLayer layer : layers) {
            if (layer.preferredInstrumentName().equals("percussion")) return layers;
        }

        List<Song.SongNoteEvent> sourceEvents = layers.stream()
                .max(Comparator.comparingInt(layer -> layer.events().size()))
                .map(Song.SongLayer::events)
                .orElse(List.of());
        if (sourceEvents.isEmpty()) return layers;

        Set<Integer> usedTicks = new HashSet<>();
        List<Song.SongNoteEvent> rhythmEvents = new ArrayList<>();
        for (Song.SongNoteEvent event : sourceEvents) {
            if (!usedTicks.add(event.tick())) continue;
            int drumNote = rhythmEvents.size() % 4 == 0 ? 54 : 62;
            rhythmEvents.add(new Song.SongNoteEvent(event.tick(), drumNote, Math.max(70, event.velocity())));
            if (rhythmEvents.size() >= 512) break;
        }
        if (rhythmEvents.isEmpty()) return layers;

        List<Song.SongLayer> withFallback = new ArrayList<>(layers);
        withFallback.add(new Song.SongLayer("Rhythm Fallback", "percussion", rhythmEvents));
        return List.copyOf(withFallback);
    }

    private static List<Song.SongNoteEvent> transposeLayerIntoPlayableRange(List<Song.SongNoteEvent> events) {
        if (events.isEmpty()) return events;

        LayerRange sourceRange = fullSourceRange(events);
        int bestShift = 0;
        int bestScore = Integer.MAX_VALUE;
        for (int shift = -72; shift <= 72; shift += 12) {
            int shiftedLow = sourceRange.min() + shift;
            int shiftedHigh = sourceRange.max() + shift;
            int shiftedCenter = sourceRange.center() + shift;
            int score = Math.abs(shiftedCenter - CENTER_PLAYABLE_MIDI_NOTE);
            if (shiftedLow < MIN_PLAYABLE_MIDI_NOTE) score += (MIN_PLAYABLE_MIDI_NOTE - shiftedLow) * 100;
            if (shiftedHigh > MAX_PLAYABLE_MIDI_NOTE) score += (shiftedHigh - MAX_PLAYABLE_MIDI_NOTE) * 100;

            if (score < bestScore) {
                bestShift = shift;
                bestScore = score;
            }
        }

        if (bestShift == 0) return events;

        List<Song.SongNoteEvent> shiftedEvents = new ArrayList<>();
        for (Song.SongNoteEvent event : events) {
            shiftedEvents.add(new Song.SongNoteEvent(
                    event.tick(),
                    event.midiNote() + bestShift,
                    event.velocity(),
                    event.pitchOffsetSemitones()
            ));
        }
        return List.copyOf(shiftedEvents);
    }

    private static LayerRange centralSourceRange(List<Song.SongNoteEvent> events) {
        List<Integer> notes = events.stream()
                .map(Song.SongNoteEvent::midiNote)
                .sorted()
                .toList();
        int lowIndex = Math.max(0, Math.round((notes.size() - 1) * 0.10f));
        int highIndex = Math.max(lowIndex, Math.round((notes.size() - 1) * 0.90f));
        return new LayerRange(
                notes.get(lowIndex),
                notes.get(highIndex),
                notes.get(notes.size() / 2)
        );
    }

    private static LayerRange fullSourceRange(List<Song.SongNoteEvent> events) {
        List<Integer> notes = events.stream()
                .map(Song.SongNoteEvent::midiNote)
                .sorted()
                .toList();
        return new LayerRange(
                notes.get(0),
                notes.get(notes.size() - 1),
                notes.get(notes.size() / 2)
        );
    }

    private static List<Song.SongNoteEvent> normalizePercussionEvents(List<Song.SongNoteEvent> events) {
        List<Song.SongNoteEvent> normalizedEvents = new ArrayList<>();
        for (Song.SongNoteEvent event : events) {
            normalizedEvents.add(new Song.SongNoteEvent(
                    event.tick(),
                    mapPercussionMidiNote(event.midiNote()),
                    event.velocity()
            ));
        }
        return List.copyOf(normalizedEvents);
    }

    private static int mapPercussionMidiNote(int midiNote) {
        return switch (midiNote) {
            case 35, 36 -> 54;
            case 38, 40 -> 62;
            case 37, 39, 75, 76, 77 -> 66;
            case 42, 44, 46, 49, 51, 52, 55, 57, 59 -> 70;
            case 41, 43, 45, 47, 48, 50 -> 58;
            default -> 66;
        };
    }

    private static List<Song.SongNoteEvent> normalizeDenseTicks(List<Song.SongNoteEvent> events) {
        Map<Integer, List<Song.SongNoteEvent>> byTick = new HashMap<>();
        for (Song.SongNoteEvent event : events) {
            byTick.computeIfAbsent(event.tick(), ignored -> new ArrayList<>()).add(event);
        }

        List<Song.SongNoteEvent> normalized = new ArrayList<>();
        List<Integer> ticks = new ArrayList<>(byTick.keySet());
        ticks.sort(Integer::compareTo);
        for (int tick : ticks) {
            List<Song.SongNoteEvent> tickEvents = removeDuplicatePitches(byTick.get(tick));
            if (tickEvents.size() > MAX_NOTES_PER_LAYER_TICK) tickEvents = keepMostMusicalChordNotes(tickEvents);
            normalized.addAll(tickEvents);
        }
        normalized.sort(Comparator.comparingInt(Song.SongNoteEvent::tick)
                .thenComparing(Comparator.comparingInt(Song.SongNoteEvent::velocity).reversed())
                .thenComparingInt(Song.SongNoteEvent::midiNote));
        return List.copyOf(normalized);
    }

    private static List<Song.SongNoteEvent> removeDuplicatePitches(List<Song.SongNoteEvent> events) {
        List<Song.SongNoteEvent> sorted = new ArrayList<>(events);
        sorted.sort(Comparator.comparingInt(Song.SongNoteEvent::velocity).reversed());
        Set<Integer> usedMidiNotes = new HashSet<>();
        List<Song.SongNoteEvent> deduplicated = new ArrayList<>();
        for (Song.SongNoteEvent event : sorted) {
            if (usedMidiNotes.add(event.midiNote())) deduplicated.add(event);
        }
        return deduplicated;
    }

    private static List<Song.SongNoteEvent> keepMostMusicalChordNotes(List<Song.SongNoteEvent> events) {
        Song.SongNoteEvent lowest = events.stream().min(Comparator.comparingInt(Song.SongNoteEvent::midiNote)).orElseThrow();
        Song.SongNoteEvent highest = events.stream().max(Comparator.comparingInt(Song.SongNoteEvent::midiNote)).orElseThrow();

        List<Song.SongNoteEvent> byVelocity = new ArrayList<>(events);
        byVelocity.sort(Comparator.comparingInt(Song.SongNoteEvent::velocity).reversed());

        List<Song.SongNoteEvent> kept = new ArrayList<>();
        addIfMissing(kept, lowest);
        addIfMissing(kept, highest);
        for (Song.SongNoteEvent event : byVelocity) {
            addIfMissing(kept, event);
            if (kept.size() >= MAX_NOTES_PER_LAYER_TICK) break;
        }
        return kept;
    }

    private static void addIfMissing(List<Song.SongNoteEvent> events, Song.SongNoteEvent event) {
        for (Song.SongNoteEvent existing : events) {
            if (existing.midiNote() == event.midiNote()) return;
        }
        events.add(event);
    }

    private static List<TempoChange> readTempoChanges(Sequence sequence) {
        List<TempoChange> changes = new ArrayList<>();
        changes.add(new TempoChange(0, DEFAULT_TEMPO_MPQ, 0));
        if (sequence.getDivisionType() != Sequence.PPQ) return List.copyOf(changes);

        for (Track track : sequence.getTracks()) {
            for (int eventIndex = 0; eventIndex < track.size(); eventIndex++) {
                MidiEvent event = track.get(eventIndex);
                MidiMessage message = event.getMessage();
                if (!(message instanceof MetaMessage metaMessage) || metaMessage.getType() != 0x51) continue;
                byte[] data = metaMessage.getData();
                if (data.length != 3) continue;
                int tempoMpq = ((data[0] & 0xFF) << 16) | ((data[1] & 0xFF) << 8) | (data[2] & 0xFF);
                changes.add(new TempoChange(event.getTick(), tempoMpq, 0));
            }
        }

        changes.sort(Comparator.comparingLong(TempoChange::midiTick));
        List<TempoChange> normalized = new ArrayList<>();
        long lastMidiTick = 0;
        long lastMicros = 0;
        int lastTempoMpq = DEFAULT_TEMPO_MPQ;
        for (TempoChange change : changes) {
            if (!normalized.isEmpty() && change.midiTick() == lastMidiTick) {
                normalized.set(normalized.size() - 1, new TempoChange(change.midiTick(), change.tempoMpq(), lastMicros));
                lastTempoMpq = change.tempoMpq();
                continue;
            }

            lastMicros += ticksToMicros(change.midiTick() - lastMidiTick, sequence.getResolution(), lastTempoMpq);
            normalized.add(new TempoChange(change.midiTick(), change.tempoMpq(), lastMicros));
            lastMidiTick = change.midiTick();
            lastTempoMpq = change.tempoMpq();
        }
        return List.copyOf(normalized);
    }

    private static int toMinecraftTick(long midiTick, Sequence sequence, List<TempoChange> tempoChanges) {
        long micros;
        if (sequence.getDivisionType() == Sequence.PPQ) {
            TempoChange activeTempo = tempoChanges.get(0);
            for (TempoChange tempoChange : tempoChanges) {
                if (tempoChange.midiTick() > midiTick) break;
                activeTempo = tempoChange;
            }
            micros = activeTempo.microsAtChange()
                    + ticksToMicros(midiTick - activeTempo.midiTick(), sequence.getResolution(), activeTempo.tempoMpq());
        } else {
            micros = Math.round((midiTick * 1_000_000.0) / (sequence.getDivisionType() * sequence.getResolution()));
        }

        return Math.max(0, (int) Math.round(micros / (1_000_000.0 / MINECRAFT_TICKS_PER_SECOND)));
    }

    private static long ticksToMicros(long ticks, int resolution, int tempoMpq) {
        if (ticks <= 0) return 0;
        return Math.round((ticks * (double) tempoMpq) / Math.max(1, resolution));
    }

    private static String mapProgramToInstrumentName(int program) {
        if (program == 128) return "sticks";
        if (program <= 7) return "piano";
        if (program <= 15) return "bell";
        if (program <= 23) return "chime";
        if (program <= 31) return "guitar";
        if (program <= 39) return "bass-guitar";
        if (program <= 47) return "xylophone";
        if (program <= 55) return "flute";
        if (program <= 63) return "trumpet";
        if (program <= 71) return "flute";
        if (program <= 79) return "flute";
        if (program <= 87) return "bit";
        if (program <= 95) return "pling";
        if (program <= 103) return "pling";
        if (program <= 111) return "banjo";
        if (program <= 119) return "cow-bell";
        return "didgeridoo";
    }

    private record LayerKey(int trackIndex, int channel, int program, String trackName) {
        private String displayName() {
            if (program == 128) return trackName + " Percussion";
            return trackName + " Ch " + (channel + 1);
        }
    }

    private record SourceLayer(String name, String preferredInstrumentName, List<Song.SongNoteEvent> events) {
    }

    private record LayerNoteKey(int tick, int midiNote) {
    }

    private record TempoChange(long midiTick, int tempoMpq, long microsAtChange) {
    }

    private record LayerRange(int min, int max, int center) {
    }
}
