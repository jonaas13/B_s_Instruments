package biraw.online.bSInstruments.Obtaining;

import biraw.online.bSInstruments.AllInstruments;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;

public class LootSpawning implements Listener {
    private static final double INSTRUMENT_LOOT_CHANCE = 0.25;
    private static final double BONUS_INSTRUMENT_LOOT_CHANCE = 0.05;

    @EventHandler
    private void OnLootSpawning(LootGenerateEvent event){
        if (!(event.getInventoryHolder() instanceof Chest)) return;
        if (Math.random() > INSTRUMENT_LOOT_CHANCE) return;

        event.getLoot().add(AllInstruments.GetRandomInstrument().getItem());
        if (Math.random() <= BONUS_INSTRUMENT_LOOT_CHANCE) {
            event.getLoot().add(AllInstruments.GetRandomInstrument().getItem());
        }
    }
}
