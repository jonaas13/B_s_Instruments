package biraw.online.bSInstruments;

import org.junit.Test;

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
    private static final List<Instrument> INSTRUMENTS = createInstruments();

    @Test
    public void ordinaryNotesKeepTheirExistingSoundPitchAndVolume() {
        for (Instrument instrument : INSTRUMENTS) {
            for (int midiNote = 54; midiNote <= 78; midiNote++) {
                Instrument.SoundNote note = note(instrument, midiNote, 96, 0.0);
                assertEquals(description(instrument, midiNote), normalSound(instrument), note.sound());
                float expectedPitch = (float) Math.pow(2.0, (midiNote - 66) / 12.0);
                assertEquals(description(instrument, midiNote), expectedPitch, note.pitch(), 0.0f);
                assertEquals(3.0f, note.volume(), 0.0f);
            }
        }
    }

    @Test
    public void lowestImportedFUsesAnAdjacentSampleAtTheCorrectPitch() {
        for (Instrument instrument : INSTRUMENTS) {
            if (instrument.octave == -2) continue;
            Instrument.SoundNote note = note(instrument, 53, 48, 0.0);
            int sampleOctave = instrument.octave - 1;
            assertEquals(description(instrument, 53), soundAtOctave(instrument, sampleOctave), note.sound());
            assertSoundingNote(note, 53 + instrument.octave * 12, sampleOctave);
            assertEquals(1.5f, note.volume(), 0.0f);
        }
    }

    @Test
    public void fractionalPitchAtEitherBoundaryStaysInTune() {
        Instrument instrument = piano(0);
        Instrument.SoundNote low = note(instrument, 54, 96, -0.25);
        assertEquals("block.note_block.harp_-1", low.sound());
        assertSoundingNote(low, 53.75, -1);

        Instrument.SoundNote high = note(instrument, 78, 96, 0.25);
        assertEquals("block.note_block.harp_1", high.sound());
        assertSoundingNote(high, 78.25, 1);
    }

    @Test
    public void beyondAvailableSamplesKeepsTheNoteNameInAPlayableOctave() {
        Instrument.SoundNote low = note(piano(-2), 53, 96, 0.0);
        assertEquals("block.note_block.harp_-2", low.sound());
        assertSoundingNote(low, 41, -2);

        Instrument.SoundNote high = note(piano(2), 79, 96, 0.0);
        assertEquals("block.note_block.harp_2", high.sound());
        assertSoundingNote(high, 91, 2);
    }

    @Test
    public void aBoundaryNoteDoesNotRetuneTheFollowingNotes() {
        Instrument instrument = piano(0);
        Instrument.SongPlaybackTuning tuning = instrument.createSongPlaybackTuning();
        instrument.getSongSoundNote(53, 96, 0.0, tuning);
        Instrument.SoundNote following = instrument.getSongSoundNote(66, 96, 0.0, tuning);
        assertEquals("block.note_block.harp", following.sound());
        assertEquals(1.0f, following.pitch(), 0.0f);
        assertEquals(0, tuning.soundOffset());
    }

    @Test
    public void instrumentsWithoutCustomSamplesKeepTheirExistingFallback() {
        Instrument.SoundNote note = note(new Instrument("Piano", null, 0, null), 53, 96, 0.0);
        assertEquals("block.note_block.harp", note.sound());
        assertSoundingNote(note, 65, 0);
    }

    private static List<Instrument> createInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        for (String[] definition : INSTRUMENT_SOUNDS) {
            for (int octave = -2; octave <= 2; octave++) {
                instruments.add(new Instrument(
                        definition[0],
                        null,
                        octave,
                        null,
                        "block.note_block." + definition[1]
                ));
            }
        }
        return List.copyOf(instruments);
    }

    private static Instrument.SoundNote note(Instrument instrument, int midiNote, int velocity, double bend) {
        return instrument.getSongSoundNote(
                midiNote,
                velocity,
                bend,
                instrument.createSongPlaybackTuning()
        );
    }

    private static Instrument piano(int octave) {
        return new Instrument("Piano", null, octave, null, "block.note_block.harp");
    }

    private static void assertSoundingNote(Instrument.SoundNote note, double expectedMidiNote, int sampleOctave) {
        float requestedPitch = note.pitch();
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
