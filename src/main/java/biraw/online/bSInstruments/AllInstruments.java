package biraw.online.bSInstruments;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllInstruments {
    private static final List<InstrumentDefinition> INSTRUMENT_DEFINITIONS = List.of(
            new InstrumentDefinition("Piano", org.bukkit.Instrument.PIANO, "block.note_block.harp"),
            new InstrumentDefinition("Bass Drum", org.bukkit.Instrument.BASS_DRUM, "block.note_block.basedrum"),
            new InstrumentDefinition("Snare Drum", org.bukkit.Instrument.SNARE_DRUM, "block.note_block.snare"),
            new InstrumentDefinition("Sticks", org.bukkit.Instrument.STICKS, "block.note_block.click"),
            new InstrumentDefinition("Bass Guitar", org.bukkit.Instrument.BASS_GUITAR, "block.note_block.bass"),
            new InstrumentDefinition("Flute", org.bukkit.Instrument.FLUTE, "block.note_block.flute"),
            new InstrumentDefinition("Bell", org.bukkit.Instrument.BELL, "block.note_block.bell"),
            new InstrumentDefinition("Guitar", org.bukkit.Instrument.GUITAR, "block.note_block.guitar"),
            new InstrumentDefinition("Chime", org.bukkit.Instrument.CHIME, "block.note_block.chime"),
            new InstrumentDefinition("Xylophone", org.bukkit.Instrument.XYLOPHONE, "block.note_block.xylophone"),
            new InstrumentDefinition("Iron Xylophone", org.bukkit.Instrument.IRON_XYLOPHONE, "block.note_block.iron_xylophone"),
            new InstrumentDefinition("Cow Bell", org.bukkit.Instrument.COW_BELL, "block.note_block.cow_bell"),
            new InstrumentDefinition("Didgeridoo", org.bukkit.Instrument.DIDGERIDOO, "block.note_block.didgeridoo"),
            new InstrumentDefinition("Bit", org.bukkit.Instrument.BIT, "block.note_block.bit"),
            new InstrumentDefinition("Banjo", org.bukkit.Instrument.BANJO, "block.note_block.banjo"),
            new InstrumentDefinition("Pling", org.bukkit.Instrument.PLING, "block.note_block.pling")
    );
    private static final int[] OCTAVES = {2, 1, 0, -1, -2};

    public static final List<Instrument> AllInstruments;
    private static final Map<String, Instrument> INSTRUMENTS_BY_NAME;

    static {
        List<Instrument> instruments = new ArrayList<>();
        Map<String, Instrument> instrumentsByName = new HashMap<>();

        for (InstrumentDefinition definition : INSTRUMENT_DEFINITIONS) {
            for (int octave : OCTAVES) {
                Instrument instrument = new Instrument(
                        definition.name(),
                        definition.bukkitInstrument(),
                        octave,
                        Material.STICK,
                        definition.customSoundBase()
                );
                instruments.add(instrument);
                instrumentsByName.put(getLookupName(instrument), instrument);
            }
        }

        AllInstruments = List.copyOf(instruments);
        INSTRUMENTS_BY_NAME = Map.copyOf(instrumentsByName);
    }

    public static Instrument GetInstrumentByName(String name){
        if (name == null) return null;
        return INSTRUMENTS_BY_NAME.get(name.toLowerCase());
    }

    public static List<String> GetAllInstrumentNames(){
        List<String> ret = new ArrayList<>();
        for (Instrument i : AllInstruments){
            ret.add(getLookupName(i));
        }
        return ret;
    }

    public static void GiveAllInstruments(Player player){
        for (Instrument i : AllInstruments){
            player.give(i.getItem());
        }
    }

    public static Instrument GetRandomInstrument(){
        return AllInstruments.get((int)(Math.random() * AllInstruments.size()));
    }

    private static String getLookupName(Instrument instrument) {
        if (instrument.octave < 0) return instrument.sname+"-low-"+Math.abs(instrument.octave);
        if (instrument.octave > 1) return instrument.sname+"-high-"+instrument.octave;
        return instrument.sname+"-"+instrument.octave;
    }

    private record InstrumentDefinition(String name, org.bukkit.Instrument bukkitInstrument, String customSoundBase) {
    }
}
