package biraw.online.bSInstruments.Obtaining;

import biraw.online.bSInstruments.AllInstruments;
import biraw.online.bSInstruments.AllSongs;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.loot.LootTable;

import java.util.concurrent.ThreadLocalRandom;

public class LootSpawning implements Listener {
    private static final LootProfile DEFAULT_CHEST_PROFILE = new LootProfile(0.18, 0.03, 0.28, 0.06);
    private static final LootProfile MUSIC_RICH_PROFILE = new LootProfile(0.40, 0.10, 0.55, 0.18);
    private static final LootProfile TREASURE_PROFILE = new LootProfile(0.32, 0.08, 0.44, 0.14);
    private static final LootProfile ADVENTURE_PROFILE = new LootProfile(0.25, 0.06, 0.36, 0.10);
    private static final LootProfile COMMON_PROFILE = new LootProfile(0.14, 0.02, 0.22, 0.04);

    @EventHandler
    private void OnLootSpawning(LootGenerateEvent event){
        LootProfile profile = getLootProfile(event.getLootTable());
        if (profile == null) return;

        ThreadLocalRandom random = ThreadLocalRandom.current();

        if (random.nextDouble() <= profile.instrumentChance()) {
            event.getLoot().add(AllInstruments.GetRandomInstrument().getItem());
            if (random.nextDouble() <= profile.bonusInstrumentChance()) {
                event.getLoot().add(AllInstruments.GetRandomInstrument().getItem());
            }
        }

        if (random.nextDouble() <= profile.songChance()) {
            var song = AllSongs.getRandomSong();
            if (song == null) return;
            event.getLoot().add(song.getItem());
            if (random.nextDouble() <= profile.bonusSongChance()) {
                var bonusSong = AllSongs.getRandomSong();
                if (bonusSong != null) event.getLoot().add(bonusSong.getItem());
            }
        }
    }

    private LootProfile getLootProfile(LootTable lootTable) {
        if (lootTable == null) return null;

        NamespacedKey key = lootTable.getKey();
        if (key == null || !"minecraft".equals(key.getNamespace())) return null;

        String path = key.getKey();
        if (!path.startsWith("chests/")) return null;

        if (path.contains("ancient_city")
                || path.contains("stronghold_library")
                || path.contains("woodland_mansion")) {
            return MUSIC_RICH_PROFILE;
        }

        if (path.contains("buried_treasure")
                || path.contains("end_city_treasure")
                || path.contains("bastion_treasure")
                || path.contains("ruined_portal")) {
            return TREASURE_PROFILE;
        }

        if (path.contains("abandoned_mineshaft")
                || path.contains("desert_pyramid")
                || path.contains("jungle_temple")
                || path.contains("pillager_outpost")
                || path.contains("shipwreck")
                || path.contains("underwater_ruin")
                || path.contains("trial_chambers")
                || path.contains("stronghold")) {
            return ADVENTURE_PROFILE;
        }

        if (path.contains("village")
                || path.contains("spawn_bonus_chest")
                || path.contains("igloo_chest")) {
            return COMMON_PROFILE;
        }

        return DEFAULT_CHEST_PROFILE;
    }

    private record LootProfile(
            double instrumentChance,
            double bonusInstrumentChance,
            double songChance,
            double bonusSongChance
    ) {
    }
}
