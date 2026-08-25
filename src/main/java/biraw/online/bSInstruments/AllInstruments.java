package biraw.online.bSInstruments;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import biraw.online.bSInstruments.Obtaining.ItemDelivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
            new InstrumentDefinition("Pling", org.bukkit.Instrument.PLING, "block.note_block.pling"),
            new InstrumentDefinition("Trumpet", org.bukkit.Instrument.TRUMPET, "block.note_block.trumpet"),
            new InstrumentDefinition("Exposed Trumpet", org.bukkit.Instrument.TRUMPET_EXPOSED, "block.note_block.trumpet_exposed"),
            new InstrumentDefinition("Weathered Trumpet", org.bukkit.Instrument.TRUMPET_WEATHERED, "block.note_block.trumpet_weathered"),
            new InstrumentDefinition("Oxidized Trumpet", org.bukkit.Instrument.TRUMPET_OXIDIZED, "block.note_block.trumpet_oxidized")
    );
    private static final int[] OCTAVES = {2, 1, 0, -1, -2};

    public static final List<Instrument> AllInstruments;
    private static final Map<String, Instrument> INSTRUMENTS_BY_NAME;
    private static final List<String> INSTRUMENT_NAMES;

    static {
        List<Instrument> instruments = new ArrayList<>();
        Map<String, Instrument> instrumentsByName = new HashMap<>();
        List<String> instrumentNames = new ArrayList<>();

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
                String lookupName = getLookupName(instrument);
                instrumentsByName.put(lookupName, instrument);
                instrumentNames.add(lookupName);
            }
        }

        AllInstruments = List.copyOf(instruments);
        INSTRUMENTS_BY_NAME = Map.copyOf(instrumentsByName);
        INSTRUMENT_NAMES = List.copyOf(instrumentNames);
    }

    public static Instrument GetInstrumentByName(String name){
        if (name == null) return null;
        return INSTRUMENTS_BY_NAME.get(name.toLowerCase(Locale.ROOT));
    }

    public static List<String> GetAllInstrumentNames(){
        return INSTRUMENT_NAMES;
    }

    public static void GiveAllInstruments(Player player){
        int given = 0;
        for (Instrument i : AllInstruments){
            if (!ItemDelivery.giveToInventory(player, i.getItem())) break;
            given++;
        }
        player.sendMessage("§aAdded §e" + given + "§a instruments to your inventory.");
        if (given < AllInstruments.size()) player.sendMessage("§cInventory full. Some instruments were not added.");
    }

    public static Instrument GetRandomInstrument(){
        return AllInstruments.get(ThreadLocalRandom.current().nextInt(AllInstruments.size()));
    }

    public static Instrument GetInstrumentFromItem(org.bukkit.inventory.ItemStack itemStack) {
        for (Instrument instrument : AllInstruments) {
            if (instrument.isThisInstrument(itemStack)) return instrument;
        }
        return null;
    }

    private static String getLookupName(Instrument instrument) {
        if (instrument.octave == 0) return instrument.sname;
        if (instrument.octave > 0) return instrument.sname+"+"+instrument.octave;
        return instrument.sname+instrument.octave;
    }

    private record InstrumentDefinition(String name, org.bukkit.Instrument bukkitInstrument, String customSoundBase) {
    }
}
