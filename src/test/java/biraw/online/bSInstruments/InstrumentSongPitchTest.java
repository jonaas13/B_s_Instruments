package biraw.online.bSInstruments;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InstrumentSongPitchTest {
    private static final String[][] INSTRUMENT_SOUNDS = {
            {"Piano", "harp"}, {"Harp", "harp"}, {"Bass Drum", "basedrum"},
            {"Snare Drum", "snare"}, {"Sticks", "click"}, {"Bass Guitar", "bass"},
            {"Flute", "flute"}, {"Bell", "bell"}, {"Guitar", "guitar"}, {"Chime", "chime"},
            {"Xylophone", "xylophone"}, {"Iron Xylophone", "iron_xylophone"},
            {"Cow Bell", "cow_bell"}, {"Didgeridoo", "didgeridoo"}, {"Bit", "bit"},
            {"Banjo", "banjo"}, {"Pling", "pling"}, {"Trumpet", "trumpet"},
            {"Exposed Trumpet", "trumpet_exposed"}, {"Weathered Trumpet", "trumpet_weathered"},
            {"Oxidized Trumpet", "trumpet_oxidized"}
    };
    private static final List<Instrument> instruments = new ArrayList<>();
    private static Field serverField;
    private static Server previousServer;
    private static Method getSongSoundNote;
    private static Method sound;
    private static Method pitch;
    private static Method volume;

    @BeforeClass
    public static void setUp() throws Exception {
        // Instrument construction only needs listener registration, not a running server.
        PluginManager pluginManager = (PluginManager) Proxy.newProxyInstance(
                PluginManager.class.getClassLoader(), new Class<?>[]{PluginManager.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("registerEvents")) return null;
                    throw new UnsupportedOperationException(method.getName());
                });
        serverField = Bukkit.class.getDeclaredField("server");
        serverField.setAccessible(true);
        previousServer = Bukkit.getServer();
        // Avoid Bukkit.setServer's logging, which requires a real Paper build-info service.
        serverField.set(null, Proxy.newProxyInstance(
                Server.class.getClassLoader(), new Class<?>[]{Server.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "getPluginManager" -> pluginManager;
                    default -> throw new UnsupportedOperationException(method.getName());
                }));

        getSongSoundNote = Instrument.class.getDeclaredMethod("getSongSoundNote",
                int.class, int.class, double.class, Instrument.SongPlaybackTuning.class);
        getSongSoundNote.setAccessible(true);
        Class<?> soundNote = getSongSoundNote.getReturnType();
        sound = soundNote.getDeclaredMethod("sound");
        pitch = soundNote.getDeclaredMethod("pitch");
        volume = soundNote.getDeclaredMethod("volume");
        sound.setAccessible(true);
        pitch.setAccessible(true);
        volume.setAccessible(true);

        for (String[] definition : INSTRUMENT_SOUNDS) {
            for (int octave = -2; octave <= 2; octave++) {
                // Note playback uses sound keys; Bukkit's item and instrument registries are unnecessary here.
                instruments.add(new Instrument(definition[0], null, octave, null, "block.note_block." + definition[1]));
            }
        }
    }

    @AfterClass
    public static void restoreServer() throws Exception {
        if (serverField != null) serverField.set(null, previousServer);
    }

    @Test
    public void ordinaryNotesKeepTheirExistingSoundPitchAndVolume() throws Exception {
        for (Instrument instrument : instruments) {
            for (int midiNote = 54; midiNote <= 78; midiNote++) {
                Object note = note(instrument, midiNote, 96, 0.0);
                assertEquals(description(instrument, midiNote), normalSound(instrument), sound.invoke(note));
                float expectedPitch = (float) Math.pow(2.0, (midiNote - 66) / 12.0);
                assertEquals(description(instrument, midiNote), expectedPitch, (float) pitch.invoke(note), 0.0f);
                assertEquals(3.0f, (float) volume.invoke(note), 0.0f);
            }
        }
    }

    @Test
    public void lowestImportedFUsesAnAdjacentSampleAtTheCorrectPitch() throws Exception {
        for (Instrument instrument : instruments) {
            if (instrument.octave == -2) continue;
            Object note = note(instrument, 53, 48, 0.0);
            int sampleOctave = instrument.octave - 1;
            assertEquals(description(instrument, 53), soundAtOctave(instrument, sampleOctave), sound.invoke(note));
            assertSoundingNote(note, 53 + instrument.octave * 12, sampleOctave);
            assertEquals(1.5f, (float) volume.invoke(note), 0.0f);
        }
    }

    @Test
    public void fractionalPitchAtEitherBoundaryStaysInTune() throws Exception {
        Instrument instrument = piano(0);
        Object low = note(instrument, 54, 96, -0.25);
        assertEquals("block.note_block.harp_-1", sound.invoke(low));
        assertSoundingNote(low, 53.75, -1);

        Object high = note(instrument, 78, 96, 0.25);
        assertEquals("block.note_block.harp_1", sound.invoke(high));
        assertSoundingNote(high, 78.25, 1);
    }

    @Test
    public void beyondAvailableSamplesKeepsTheNoteNameInAPlayableOctave() throws Exception {
        Instrument lowInstrument = piano(-2);
        Object low = note(lowInstrument, 53, 96, 0.0);
        assertEquals("block.note_block.harp_-2", sound.invoke(low));
        assertSoundingNote(low, 41, -2); // F2: the pack cannot reach F1.

        Instrument highInstrument = piano(2);
        Object high = note(highInstrument, 79, 96, 0.0);
        assertEquals("block.note_block.harp_2", sound.invoke(high));
        assertSoundingNote(high, 91, 2); // G6: the pack cannot reach G7.
    }

    @Test
    public void aBoundaryNoteDoesNotRetuneTheFollowingNotes() throws Exception {
        Instrument instrument = piano(0);
        Instrument.SongPlaybackTuning tuning = instrument.createSongPlaybackTuning();
        getSongSoundNote.invoke(instrument, 53, 96, 0.0, tuning);
        Object following = getSongSoundNote.invoke(instrument, 66, 96, 0.0, tuning);
        assertEquals("block.note_block.harp", sound.invoke(following));
        assertEquals(1.0f, (float) pitch.invoke(following), 0.0f);
        assertEquals(0, tuning.soundOffset());
    }

    @Test
    public void instrumentsWithoutCustomSamplesKeepTheirExistingFallback() throws Exception {
        Instrument instrument = new Instrument("Piano", null, 0, null);
        Object note = note(instrument, 53, 96, 0.0);
        assertEquals("block.note_block.harp", sound.invoke(note));
        assertSoundingNote(note, 65, 0);
    }

    private static Object note(Instrument instrument, int midiNote, int velocity, double bend) throws Exception {
        return getSongSoundNote.invoke(instrument, midiNote, velocity, bend, instrument.createSongPlaybackTuning());
    }

    private static Instrument piano(int octave) {
        return new Instrument("Piano", null, octave, null, "block.note_block.harp");
    }

    private static void assertSoundingNote(Object note, double expectedMidiNote, int sampleOctave) throws Exception {
        float requestedPitch = (float) pitch.invoke(note);
        assertTrue("Minecraft must not clamp the pitch: " + requestedPitch,
                requestedPitch >= 0.5f && requestedPitch <= 2.0f);
        double soundingMidiNote = 66 + sampleOctave * 12 + 12 * Math.log(requestedPitch) / Math.log(2);
        assertEquals(expectedMidiNote, soundingMidiNote, 0.00001);
    }

    private static String normalSound(Instrument instrument) {
        return soundAtOctave(instrument, instrument.octave);
    }

    private static String soundAtOctave(Instrument instrument, int octave) {
        if (octave == 0) return instrument.sname.equals("sticks") ? "block.note_block.hat" : instrument.customSoundBase;
        return instrument.customSoundBase + "_" + octave;
    }

    private static String description(Instrument instrument, int midiNote) {
        return instrument.sname + " octave " + instrument.octave + " MIDI " + midiNote;
    }
}
