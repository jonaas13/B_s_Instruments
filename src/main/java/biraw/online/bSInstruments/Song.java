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
    private static final int REST_NOTE_ID = Integer.MIN_VALUE;
    private static final int MAX_DURATION_TICKS = 180 * 20;
    private static final int TARGET_MIN_DURATION_TICKS = 120 * 20;
    private static final int PHRASE_REST_TICKS = 4;

    private final String id;
    private final String title;
    private final String style;
    private final int tempoTicks;
    private final double durationUnitTicks;
    private final List<SongNote> notes;
    private final List<List<SongNote>> layerNotes;
    private final int durationTicks;

    public Song(String title, String style, int bpm, String pattern) {
        this(title, style, bpm, pattern, List.of(), shouldPreserveMelody(style), true);
    }

    public Song(String title, String style, int bpm, String pattern, List<String> layerPatterns) {
        this(title, style, bpm, pattern, layerPatterns, shouldPreserveMelody(style), layerPatterns.isEmpty());
    }

    public Song(String title, String style, int bpm, String pattern, List<String> layerPatterns, boolean preserveMelody) {
        this(title, style, bpm, pattern, layerPatterns, preserveMelody, layerPatterns.isEmpty());
    }

    public Song(String title, String style, int bpm, String pattern, List<String> layerPatterns, boolean preserveMelody, boolean extendArrangement) {
        this.id = toId(title, style);
        this.title = title;
        this.style = style;
        this.durationUnitTicks = 600.0 / Math.max(1, bpm);
        this.tempoTicks = Math.max(1, (int) Math.round(durationUnitTicks));
        this.notes = extendArrangement ? arrange(parsePattern(pattern), preserveMelody) : parsePattern(pattern);
        this.durationTicks = getTotalDuration(notes);
        this.layerNotes = layerPatterns.isEmpty() ? arrangeLayers(notes) : arrangeCustomLayers(notes, layerPatterns);
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
        double exactElapsedTicks = 0.0;
        int roundedElapsedTicks = 0;
        for (String token : pattern.split("\\s+")) {
            if (token.isBlank()) continue;
            String[] parts = token.split(":");
            if (parts.length != 2) throw new IllegalArgumentException("Invalid song token: " + token);

            exactElapsedTicks += parseDurationUnits(parts[1]) * durationUnitTicks;
            int nextRoundedElapsedTicks = Math.max(roundedElapsedTicks + 1, (int) Math.round(exactElapsedTicks));
            int durationTicks = nextRoundedElapsedTicks - roundedElapsedTicks;
            roundedElapsedTicks = nextRoundedElapsedTicks;
            if (parts[0].equalsIgnoreCase("R")) {
                parsed.add(rest(durationTicks));
                continue;
            }

            parsed.add(new SongNote(toNoteId(parts[0]), durationTicks));
        }
        return List.copyOf(parsed);
    }

    private double parseDurationUnits(String duration) {
        if (duration.contains("/")) {
            String[] fractionParts = duration.split("/");
            if (fractionParts.length != 2) throw new IllegalArgumentException("Invalid song duration: " + duration);

            double numerator = Double.parseDouble(fractionParts[0]);
            double denominator = Double.parseDouble(fractionParts[1]);
            if (denominator == 0.0) throw new IllegalArgumentException("Invalid song duration: " + duration);
            return numerator / denominator;
        }

        return Double.parseDouble(duration);
    }

    static boolean shouldPreserveMelody(String style) {
        String normalizedStyle = style.toLowerCase(Locale.ROOT);
        return !List.of(
                "original",
                "pop",
                "rock",
                "alt",
                "dance",
                "comedy",
                "theme",
                "soul",
                "hiphop",
                "country",
                "indie",
                "modern pop",
                "retro pop",
                "sixties rock",
                "disco",
                "synth pop",
                "arena pop",
                "pop rock",
                "pop punk",
                "dance pop",
                "cinematic",
                "adventure",
                "sci fi",
                "fantasy",
                "screen theme"
        ).contains(normalizedStyle);
    }

    private List<SongNote> arrange(List<SongNote> baseNotes, boolean preserveMelody) {
        int baseDuration = getTotalDuration(baseNotes);
        if (baseDuration >= TARGET_MIN_DURATION_TICKS) return baseNotes;

        List<SongNote> arranged = new ArrayList<>();
        int variation = 0;
        while (getTotalDuration(arranged) < TARGET_MIN_DURATION_TICKS) {
            List<SongNote> phrase = preserveMelody ? baseNotes : varyPhrase(baseNotes, variation);
            int phraseDuration = getTotalDuration(phrase);
            int restDuration = arranged.isEmpty() ? 0 : PHRASE_REST_TICKS;
            if (!arranged.isEmpty() && getTotalDuration(arranged) + restDuration + phraseDuration > MAX_DURATION_TICKS) break;
            if (!arranged.isEmpty()) arranged.add(rest(restDuration));
            arranged.addAll(phrase);
            variation++;
        }

        return List.copyOf(arranged);
    }

    private List<SongNote> varyPhrase(List<SongNote> baseNotes, int variation) {
        List<SongNote> varied = new ArrayList<>();
        for (int i = 0; i < baseNotes.size(); i++) {
            SongNote note = baseNotes.get(i);
            if (note.isRest()) {
                varied.add(note);
                continue;
            }

            int noteId = note.noteId();
            if (variation % 4 == 1 && isStrongBeat(varied)) noteId += 12;
            if (variation % 4 == 2 && i % 6 == 3) noteId -= 12;
            if (variation % 4 == 3 && i % 8 == 4 && note.durationTicks() >= 2) {
                int restTicks = Math.max(1, note.durationTicks() / 2);
                varied.add(rest(restTicks));
                varied.add(new SongNote(noteId, note.durationTicks() - restTicks));
                continue;
            }
            varied.add(new SongNote(noteId, note.durationTicks()));
        }
        return List.copyOf(varied);
    }

    private List<List<SongNote>> arrangeLayers(List<SongNote> melody) {
        List<BarHarmony> harmony = analyzeHarmony(melody);
        List<List<SongNote>> layers = new ArrayList<>();
        layers.add(melody);
        layers.add(makeBassLayer(harmony));
        layers.add(makeChordPadLayer(harmony));
        layers.add(makeChordToneLayer(melody, harmony, true));
        layers.add(makeCounterLayer(melody, harmony));
        return List.copyOf(layers);
    }

    private List<List<SongNote>> arrangeCustomLayers(List<SongNote> melody, List<String> layerPatterns) {
        List<BarHarmony> harmony = analyzeHarmony(melody);
        List<List<SongNote>> layers = new ArrayList<>();
        layers.add(melody);
        int layerIndex = 1;
        for (String layerPattern : layerPatterns) {
            layers.add(arrangeLayerToDuration(parsePattern(layerPattern), durationTicks, layerIndex));
            layerIndex++;
        }

        if (layers.size() < 5) layers.add(makeBassLayer(harmony));
        if (layers.size() < 5) layers.add(makeChordPadLayer(harmony));
        if (layers.size() < 5) layers.add(makeChordToneLayer(melody, harmony, true));
        if (layers.size() < 5) layers.add(makeCounterLayer(melody, harmony));
        return List.copyOf(layers);
    }

    private List<SongNote> arrangeLayerToDuration(List<SongNote> baseNotes, int targetDurationTicks, int layerIndex) {
        if (baseNotes.isEmpty()) return List.of(rest(targetDurationTicks));

        List<SongNote> arranged = new ArrayList<>();
        int totalTicks = 0;
        int cycle = 0;
        while (totalTicks < targetDurationTicks) {
            if (!arranged.isEmpty()) {
                int restTicks = Math.min(PHRASE_REST_TICKS, targetDurationTicks - totalTicks);
                arranged.add(rest(restTicks));
                totalTicks += restTicks;
            }

            for (SongNote note : developLayerPhrase(baseNotes, cycle, layerIndex)) {
                if (totalTicks >= targetDurationTicks) break;
                int durationTicks = Math.min(note.durationTicks(), targetDurationTicks - totalTicks);
                arranged.add(new SongNote(note.noteId(), durationTicks));
                totalTicks += durationTicks;
            }
            cycle++;
        }
        return List.copyOf(arranged);
    }

    private List<SongNote> developLayerPhrase(List<SongNote> baseNotes, int cycle, int layerIndex) {
        List<SongNote> developed = new ArrayList<>();
        int noteNumber = 0;
        for (SongNote note : baseNotes) {
            if (note.isRest()) {
                developed.add(note);
                continue;
            }

            int noteId = note.noteId();
            if (layerIndex == 1 && cycle % 2 == 1 && noteNumber % 4 == 0) noteId -= 12;
            if (layerIndex == 2 && cycle % 3 == 1 && noteNumber % 2 == 0) noteId += 7;
            if (layerIndex == 3 && cycle % 3 == 2 && noteNumber % 3 == 0) noteId += 12;

            if (layerIndex >= 3 && cycle % 4 == 3 && note.durationTicks() >= 2 && noteNumber % 4 == 1) {
                int restTicks = Math.max(1, note.durationTicks() / 2);
                developed.add(rest(restTicks));
                developed.add(new SongNote(noteId, note.durationTicks() - restTicks));
            } else {
                developed.add(new SongNote(noteId, note.durationTicks()));
            }
            noteNumber++;
        }
        return List.copyOf(developed);
    }

    private List<SongNote> makeChordToneLayer(List<SongNote> melody, List<BarHarmony> harmony, boolean aboveMelody) {
        List<SongNote> layer = new ArrayList<>();
        int elapsedTicks = 0;
        for (SongNote note : melody) {
            if (note.isRest() || !isLayerEntrance(elapsedTicks, note.durationTicks())) {
                layer.add(rest(note.durationTicks()));
            } else {
                BarHarmony barHarmony = harmony.get(Math.min(harmony.size() - 1, elapsedTicks / barTicks()));
                layer.add(new SongNote(nearestChordTone(note.noteId(), barHarmony, aboveMelody), note.durationTicks()));
            }
            elapsedTicks += note.durationTicks();
        }
        return List.copyOf(layer);
    }

    private List<SongNote> makeBassLayer(List<BarHarmony> harmony) {
        List<SongNote> layer = new ArrayList<>();
        int halfBarTicks = barTicks() / 2;
        for (BarHarmony barHarmony : harmony) {
            layer.add(new SongNote(barHarmony.rootNoteId() - 12, tempoTicks * 2));
            layer.add(rest(Math.max(1, halfBarTicks - tempoTicks * 2)));
            layer.add(new SongNote(barHarmony.fifthNoteId() - 12, tempoTicks * 2));
            layer.add(rest(Math.max(1, halfBarTicks - tempoTicks * 2)));
        }
        return trimToDuration(layer, durationTicks);
    }

    private List<SongNote> makeChordPadLayer(List<BarHarmony> harmony) {
        List<SongNote> layer = new ArrayList<>();
        int halfBarTicks = barTicks() / 2;
        for (BarHarmony barHarmony : harmony) {
            layer.add(new SongNote(barHarmony.thirdNoteId(), halfBarTicks));
            layer.add(new SongNote(barHarmony.fifthNoteId(), halfBarTicks));
        }
        return trimToDuration(layer, durationTicks);
    }

    private List<SongNote> makeCounterLayer(List<SongNote> melody, List<BarHarmony> harmony) {
        List<SongNote> layer = new ArrayList<>();
        int elapsedTicks = 0;
        int notesSinceAnswer = 0;
        for (SongNote note : melody) {
            if (note.isRest() || note.durationTicks() < tempoTicks || notesSinceAnswer < 3) {
                layer.add(rest(note.durationTicks()));
            } else {
                BarHarmony barHarmony = harmony.get(Math.min(harmony.size() - 1, elapsedTicks / barTicks()));
                int restTicks = Math.max(1, note.durationTicks() / 2);
                int answerTicks = note.durationTicks() - restTicks;
                layer.add(rest(restTicks));
                if (answerTicks > 0) layer.add(new SongNote(barHarmony.fifthNoteId(), answerTicks));
                notesSinceAnswer = 0;
            }

            if (!note.isRest()) notesSinceAnswer++;
            elapsedTicks += note.durationTicks();
        }
        return List.copyOf(layer);
    }

    private List<BarHarmony> analyzeHarmony(List<SongNote> melody) {
        int barTicks = barTicks();
        int barCount = Math.max(1, (int) Math.ceil((double) durationTicks / barTicks));
        List<BarHarmony> harmony = new ArrayList<>();
        int elapsedTicks = 0;
        int noteIndex = 0;
        BarHarmony previous = new BarHarmony(6, false);

        for (int bar = 0; bar < barCount; bar++) {
            int barStartTick = bar * barTicks;
            int barEndTick = Math.min(durationTicks, barStartTick + barTicks);
            int[] pitchWeights = new int[12];

            while (noteIndex < melody.size() && elapsedTicks + melody.get(noteIndex).durationTicks() <= barStartTick) {
                elapsedTicks += melody.get(noteIndex).durationTicks();
                noteIndex++;
            }

            int scanIndex = noteIndex;
            int scanTick = elapsedTicks;
            while (scanIndex < melody.size() && scanTick < barEndTick) {
                SongNote note = melody.get(scanIndex);
                int noteEndTick = scanTick + note.durationTicks();
                if (!note.isRest()) {
                    int overlapTicks = Math.max(0, Math.min(noteEndTick, barEndTick) - Math.max(scanTick, barStartTick));
                    pitchWeights[pitchClass(note.noteId())] += overlapTicks;
                }
                scanTick = noteEndTick;
                scanIndex++;
            }

            BarHarmony current = bestHarmony(pitchWeights, previous);
            harmony.add(current);
            previous = current;
        }

        return List.copyOf(harmony);
    }

    private BarHarmony bestHarmony(int[] pitchWeights, BarHarmony fallback) {
        int bestRoot = -1;
        int bestScore = 0;
        boolean bestMinor = false;

        for (int root = 0; root < 12; root++) {
            int majorScore = chordScore(pitchWeights, root, false);
            if (majorScore > bestScore) {
                bestScore = majorScore;
                bestRoot = root;
                bestMinor = false;
            }

            int minorScore = chordScore(pitchWeights, root, true);
            if (minorScore > bestScore) {
                bestScore = minorScore;
                bestRoot = root;
                bestMinor = true;
            }
        }

        if (bestRoot < 0) return fallback;
        return new BarHarmony(noteIdForPitchClass(bestRoot, 0), bestMinor);
    }

    private int chordScore(int[] pitchWeights, int root, boolean minor) {
        int third = Math.floorMod(root + (minor ? 3 : 4), 12);
        int fifth = Math.floorMod(root + 7, 12);
        return pitchWeights[root] * 4 + pitchWeights[third] * 3 + pitchWeights[fifth] * 3;
    }

    private boolean isLayerEntrance(int elapsedTicks, int durationTicks) {
        int halfBarTicks = barTicks() / 2;
        return elapsedTicks % halfBarTicks == 0 || durationTicks >= tempoTicks * 2;
    }

    private int nearestChordTone(int melodyNoteId, BarHarmony harmony, boolean aboveMelody) {
        int[] chordTones = {
                harmony.rootNoteId(),
                harmony.thirdNoteId(),
                harmony.fifthNoteId(),
                harmony.rootNoteId() + 12,
                harmony.thirdNoteId() + 12,
                harmony.fifthNoteId() + 12
        };

        int bestNoteId = harmony.thirdNoteId();
        int bestDistance = Integer.MAX_VALUE;
        for (int chordTone : chordTones) {
            int candidate = chordTone;
            int distance = candidate - melodyNoteId;
            if (aboveMelody && distance <= 0) distance += 12;
            if (!aboveMelody && distance >= 0) distance -= 12;
            if (Math.abs(distance) < 3) distance += aboveMelody ? 12 : -12;

            candidate = melodyNoteId + distance;
            int absoluteDistance = Math.abs(candidate - melodyNoteId);
            if (absoluteDistance < bestDistance) {
                bestDistance = absoluteDistance;
                bestNoteId = candidate;
            }
        }
        return bestNoteId;
    }

    private List<SongNote> trimToDuration(List<SongNote> notes, int targetDurationTicks) {
        List<SongNote> trimmed = new ArrayList<>();
        int totalTicks = 0;
        for (SongNote note : notes) {
            if (totalTicks >= targetDurationTicks) break;
            int durationTicks = Math.min(note.durationTicks(), targetDurationTicks - totalTicks);
            trimmed.add(new SongNote(note.noteId(), durationTicks));
            totalTicks += durationTicks;
        }
        if (totalTicks < targetDurationTicks) trimmed.add(rest(targetDurationTicks - totalTicks));
        return List.copyOf(trimmed);
    }

    private SongNote rest(int durationTicks) {
        return new SongNote(REST_NOTE_ID, durationTicks);
    }

    private int barTicks() {
        return tempoTicks * 8;
    }

    private int pitchClass(int noteId) {
        return Math.floorMod(noteId + 6, 12);
    }

    private int noteIdForPitchClass(int pitchClass, int preferredOctaveOffset) {
        int noteId = Math.floorMod(pitchClass, 12) - 6 + preferredOctaveOffset;
        return noteId;
    }

    private record BarHarmony(int rootNoteId, boolean minor) {
        private int thirdNoteId() {
            return rootNoteId + (minor ? 3 : 4);
        }

        private int fifthNoteId() {
            return rootNoteId + 7;
        }
    }

    private boolean isStrongBeat(List<SongNote> songNotes) {
        int elapsedTicks = getTotalDuration(songNotes);
        return elapsedTicks % (tempoTicks * 4) == 0;
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
            return noteId == REST_NOTE_ID;
        }
    }
}
