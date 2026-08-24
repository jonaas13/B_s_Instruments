package biraw.online.bSInstruments.Obtaining;

import biraw.online.bSInstruments.AllInstruments;
import biraw.online.bSInstruments.AllSongs;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;

import java.util.concurrent.ThreadLocalRandom;

public class LootSpawning implements Listener {
    private static final double INSTRUMENT_LOOT_CHANCE = 0.25;
    private static final double BONUS_INSTRUMENT_LOOT_CHANCE = 0.05;
    private static final double SONG_LOOT_CHANCE = 0.35;
    private static final double BONUS_SONG_LOOT_CHANCE = 0.08;

    @EventHandler
    private void OnLootSpawning(LootGenerateEvent event){
        if (!(event.getInventoryHolder() instanceof Chest)) return;
        ThreadLocalRandom random = ThreadLocalRandom.current();

        if (random.nextDouble() <= INSTRUMENT_LOOT_CHANCE) {
            event.getLoot().add(AllInstruments.GetRandomInstrument().getItem());
            if (random.nextDouble() <= BONUS_INSTRUMENT_LOOT_CHANCE) {
                event.getLoot().add(AllInstruments.GetRandomInstrument().getItem());
            }
        }

        if (random.nextDouble() <= SONG_LOOT_CHANCE) {
            event.getLoot().add(AllSongs.getRandomSong().getItem());
            if (random.nextDouble() <= BONUS_SONG_LOOT_CHANCE) {
                event.getLoot().add(AllSongs.getRandomSong().getItem());
            }
        }
    }
}
