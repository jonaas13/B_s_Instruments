package biraw.online.bSInstruments.Obtaining;

import biraw.online.bSInstruments.AllInstruments;
import biraw.online.bSInstruments.AllSongs;
import biraw.online.bSInstruments.BSInstruments;
import biraw.online.bSInstruments.Instrument;
import biraw.online.bSInstruments.Song;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;

public class RegisterRecipes implements Listener {

    @EventHandler
    private void Descovery(CraftItemEvent event){
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() != Material.NOTE_BLOCK) return;
        for (NamespacedKey n : BSRecipe.AllRecipeKeys){
            if (!event.getWhoClicked().hasDiscoveredRecipe(n))
                event.getWhoClicked().discoverRecipe(n);
        }
    }

    public RegisterRecipes(){
        new BSRecipe(AllInstruments.GetInstrumentByName("guitar-0").getItem(),
                null,Material.STRING,Material.STICK,
                Material.STRING,Material.STICK,Material.STRING,
                Material.NOTE_BLOCK,Material.STRING,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("guitar-1").getItem(),
                null,Material.STRING,Material.STICK,
                null,Material.STICK,Material.STRING,
                Material.NOTE_BLOCK,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("bass-drum-0").getItem(),
                Material.LEATHER,Material.LEATHER,Material.LEATHER,
                Material.STICK,null,Material.STICK,
                Material.STICK,Material.STICK,Material.STICK);
        new BSRecipe(AllInstruments.GetInstrumentByName("bass-drum-1").getItem(),
                Material.RABBIT_HIDE,Material.RABBIT_HIDE,Material.RABBIT_HIDE,
                Material.STICK,null,Material.STICK,
                Material.STICK,Material.STICK,Material.STICK);

        new BSRecipe(AllInstruments.GetInstrumentByName("snare-drum-0").getItem(),
                Material.LEATHER,Material.LEATHER,Material.LEATHER,
                Material.STICK,null,Material.STICK,
                null,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("snare-drum-1").getItem(),
                Material.RABBIT_HIDE,Material.RABBIT_HIDE,Material.RABBIT_HIDE,
                Material.STICK,null,Material.STICK,
                null,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("sticks-1").getItem(),
                Material.BONE,null,Material.BONE,
                Material.STICK,null,Material.STICK,
                Material.STICK,null,Material.STICK);
        new BSRecipe(AllInstruments.GetInstrumentByName("sticks-0").getItem(),
                Material.BONE,null,Material.BONE,
                Material.BAMBOO,null,Material.BAMBOO,
                Material.BAMBOO,null,Material.BAMBOO);

        new BSRecipe(AllInstruments.GetInstrumentByName("bass-guitar-0").getItem(),
                null,Material.IRON_NUGGET,Material.STICK,
                Material.IRON_NUGGET,Material.STICK,Material.IRON_NUGGET,
                Material.NOTE_BLOCK,Material.IRON_NUGGET,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("bass-guitar-1").getItem(),
                null,Material.IRON_NUGGET,Material.STICK,
                null,Material.STICK,Material.IRON_NUGGET,
                Material.NOTE_BLOCK,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("flute-0").getItem(),
                null,Material.IRON_INGOT,null,
                null,Material.COPPER_INGOT,null,
                null,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("flute-1").getItem(),
                null,Material.IRON_NUGGET,null,
                null,Material.COPPER_INGOT,null,
                null,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("cow-bell-0").getItem(),
                null,Material.BELL,null,
                null,Material.BLAZE_ROD,null,
                null,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("cow-bell-1").getItem(),
                null,Material.BELL,null,
                null,Material.STICK,null,
                null,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("piano-0").getItem(),
                null,null,null,
                null,null,null,
                Material.QUARTZ_SLAB,Material.QUARTZ_SLAB,Material.QUARTZ_SLAB);
        new BSRecipe(AllInstruments.GetInstrumentByName("piano-1").getItem(),
                null,null,null,
                null,null,null,
                Material.BLACKSTONE_SLAB,Material.BLACKSTONE_SLAB,Material.BLACKSTONE_SLAB);

        new BSRecipe(AllInstruments.GetInstrumentByName("chime-1").getItem(),
                Material.STICK,Material.STICK,Material.STICK,
                Material.POINTED_DRIPSTONE,Material.POINTED_DRIPSTONE,Material.POINTED_DRIPSTONE,
                null,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("chime-0").getItem(),
                Material.STICK,Material.STICK,Material.STICK,
                Material.POINTED_DRIPSTONE,Material.POINTED_DRIPSTONE,Material.POINTED_DRIPSTONE,
                Material.POINTED_DRIPSTONE,Material.POINTED_DRIPSTONE,Material.POINTED_DRIPSTONE);

        new BSRecipe(AllInstruments.GetInstrumentByName("xylophone-1").getItem(),
                null,null,null,
                Material.BONE,Material.BONE,Material.BONE,
                Material.STICK,null,Material.STICK);
        new BSRecipe(AllInstruments.GetInstrumentByName("xylophone-0").getItem(),
                null,null,null,
                Material.BONE_BLOCK,Material.BONE_BLOCK,Material.BONE_BLOCK,
                Material.STICK,null,Material.STICK);

        new BSRecipe(AllInstruments.GetInstrumentByName("iron-xylophone-0").getItem(),
                null,null,null,
                Material.IRON_INGOT,Material.IRON_INGOT,Material.IRON_INGOT,
                Material.STICK,null,Material.STICK);
        new BSRecipe(AllInstruments.GetInstrumentByName("iron-xylophone-1").getItem(),
                null,null,null,
                Material.IRON_NUGGET,Material.IRON_NUGGET,Material.IRON_NUGGET,
                Material.STICK,null,Material.STICK);

        new BSRecipe(AllInstruments.GetInstrumentByName("bell-1").getItem(),
                null,Material.GOLD_INGOT,null,
                null,Material.GOLD_NUGGET,null,
                null,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("bell-0").getItem(),
                null,Material.IRON_INGOT,null,
                null,Material.IRON_NUGGET,null,
                null,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("didgeridoo-0").getItem(),
                null,Material.MANGROVE_ROOTS,null,
                null,Material.MANGROVE_ROOTS,null,
                null,Material.MANGROVE_ROOTS,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("didgeridoo-1").getItem(),
                null,Material.MANGROVE_ROOTS,null,
                null,Material.MANGROVE_ROOTS,null,
                null,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("bit-0").getItem(),
                null,null,null,
                Material.STONE_PRESSURE_PLATE,Material.STONE_PRESSURE_PLATE,Material.STONE_PRESSURE_PLATE,
                null,Material.CALIBRATED_SCULK_SENSOR,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("bit-1").getItem(),
                null,null,null,
                Material.STONE_BUTTON,Material.STONE_BUTTON,Material.STONE_BUTTON,
                null,Material.CALIBRATED_SCULK_SENSOR,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("banjo-0").getItem(),
                null,Material.STRING,Material.STICK,
                null,Material.STICK,Material.STRING,
                Material.PUMPKIN,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("banjo-1").getItem(),
                null,Material.STRING,Material.STICK,
                null,Material.STICK,Material.STRING,
                Material.CARVED_PUMPKIN,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("pling-0").getItem(),
                null,Material.AMETHYST_SHARD,null,
                null,Material.REDSTONE_BLOCK,null,
                null,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("pling-1").getItem(),
                null,Material.AMETHYST_SHARD,null,
                null,Material.REDSTONE,null,
                null,null,null);

        addTrumpetRecipes("trumpet", Material.COPPER_INGOT);
        addTrumpetRecipes("exposed-trumpet", Material.EXPOSED_COPPER);
        addTrumpetRecipes("weathered-trumpet", Material.WEATHERED_COPPER);
        addTrumpetRecipes("oxidized-trumpet", Material.OXIDIZED_COPPER);

        addSongRecipes();
        addTuningRecipes();
    }

    private void addTrumpetRecipes(String instrumentName, Material bodyMaterial) {
        new BSRecipe(AllInstruments.GetInstrumentByName(instrumentName+"-0").getItem(),
                null,bodyMaterial,null,
                bodyMaterial,Material.NOTE_BLOCK,bodyMaterial,
                null,Material.STICK,null);
        new BSRecipe(AllInstruments.GetInstrumentByName(instrumentName+"-1").getItem(),
                null,bodyMaterial,null,
                bodyMaterial,Material.NOTE_BLOCK,null,
                null,Material.STICK,null);
    }

    private void addTuningRecipes() {
        for (String instrumentName : AllInstruments.GetAllInstrumentNames()) {
            if (!instrumentName.endsWith("-0")) continue;

            String baseName = instrumentName.substring(0, instrumentName.length() - 2);
            addTuningRecipe(baseName+"-1", baseName+"-0", Material.AMETHYST_SHARD);
            addTuningRecipe(baseName+"-high-2", baseName+"-1", Material.AMETHYST_BLOCK);
            addTuningRecipe(baseName+"-low-1", baseName+"-0", Material.DEEPSLATE);
            addTuningRecipe(baseName+"-low-2", baseName+"-low-1", Material.REINFORCED_DEEPSLATE);
        }
    }

    private void addSongRecipes() {
        addSongRecipe("ode-to-joy-classical", Material.FEATHER);
        addSongRecipe("canon-in-d-classical", Material.DIAMOND);
        addSongRecipe("greensleeves-traditional", Material.OAK_LEAVES);
        addSongRecipe("amazing-grace-traditional", Material.GOLD_NUGGET);
        addSongRecipe("drunken-sailor-sea-shanty", Material.KELP);
        addSongRecipe("twinkle-twinkle-folk", Material.GLOWSTONE_DUST);
        addSongRecipe("happy-birthday-celebration", Material.CAKE);
        addSongRecipe("jingle-bells-holiday", Material.SNOWBALL);
        addSongRecipe("fur-elise-classical", Material.AMETHYST_SHARD);
        addSongRecipe("the-entertainer-ragtime", Material.REDSTONE);
        addSongRecipe("simple-blues-original", Material.LAPIS_LAZULI);
        addSongRecipe("miners-march-original", Material.COPPER_INGOT);
    }

    private void addSongRecipe(String songName, Material accent) {
        Song song = AllSongs.getSongByName(songName);
        if (song == null) return;

        NamespacedKey key = new NamespacedKey(BSInstruments.getInstance(),
                "song_recipe_" + BSInstruments.getIntForRecipe());
        ShapelessRecipe recipe = new ShapelessRecipe(key, song.getItem());
        recipe.addIngredient(Material.PAPER);
        recipe.addIngredient(Material.INK_SAC);
        recipe.addIngredient(Material.NOTE_BLOCK);
        recipe.addIngredient(accent);
        Bukkit.addRecipe(recipe);
        BSRecipe.AllRecipeKeys.add(key);
    }

    private void addTuningRecipe(String resultName, String sourceName, Material modifier) {
        Instrument result = AllInstruments.GetInstrumentByName(resultName);
        Instrument source = AllInstruments.GetInstrumentByName(sourceName);
        if (result == null || source == null) return;

        NamespacedKey key = new NamespacedKey(BSInstruments.getInstance(),
                "tuning_recipe_" + BSInstruments.getIntForRecipe());
        ShapelessRecipe recipe = new ShapelessRecipe(key, result.getItem());
        recipe.addIngredient(new RecipeChoice.ExactChoice(source.getItem()));
        recipe.addIngredient(modifier);
        Bukkit.addRecipe(recipe);
        BSRecipe.AllRecipeKeys.add(key);
    }
}
