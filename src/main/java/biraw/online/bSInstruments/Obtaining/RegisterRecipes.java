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

import java.util.ArrayList;
import java.util.List;

public class RegisterRecipes implements Listener {
    private static final List<Material> SONG_RECIPE_ACCENTS = List.of(
            Material.FEATHER,
            Material.GOLD_NUGGET,
            Material.REDSTONE,
            Material.LAPIS_LAZULI,
            Material.COPPER_INGOT,
            Material.AMETHYST_SHARD,
            Material.GLOWSTONE_DUST,
            Material.SNOWBALL,
            Material.KELP,
            Material.STRING,
            Material.BONE_MEAL,
            Material.COAL,
            Material.FLINT,
            Material.CLAY_BALL,
            Material.BRICK,
            Material.QUARTZ,
            Material.SUGAR,
            Material.GUNPOWDER,
            Material.ECHO_SHARD,
            Material.PRISMARINE_SHARD,
            Material.NAUTILUS_SHELL,
            Material.ENDER_PEARL,
            Material.BLAZE_POWDER,
            Material.SLIME_BALL,
            Material.HONEYCOMB,
            Material.RABBIT_FOOT,
            Material.PHANTOM_MEMBRANE,
            Material.FIREWORK_STAR,
            Material.WHITE_DYE,
            Material.ORANGE_DYE,
            Material.MAGENTA_DYE,
            Material.LIGHT_BLUE_DYE,
            Material.YELLOW_DYE,
            Material.LIME_DYE,
            Material.PINK_DYE,
            Material.GRAY_DYE,
            Material.LIGHT_GRAY_DYE,
            Material.CYAN_DYE,
            Material.PURPLE_DYE,
            Material.BLUE_DYE,
            Material.BROWN_DYE,
            Material.GREEN_DYE,
            Material.RED_DYE,
            Material.BLACK_DYE
    );

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
        new BSRecipe(AllInstruments.GetInstrumentByName("guitar").getItem(),
                null,Material.STRING,Material.STICK,
                Material.STRING,Material.STICK,Material.STRING,
                Material.NOTE_BLOCK,Material.STRING,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("guitar+1").getItem(),
                null,Material.STRING,Material.STICK,
                null,Material.STICK,Material.STRING,
                Material.NOTE_BLOCK,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("bass-drum").getItem(),
                Material.LEATHER,Material.LEATHER,Material.LEATHER,
                Material.STICK,null,Material.STICK,
                Material.STICK,Material.STICK,Material.STICK);
        new BSRecipe(AllInstruments.GetInstrumentByName("bass-drum+1").getItem(),
                Material.RABBIT_HIDE,Material.RABBIT_HIDE,Material.RABBIT_HIDE,
                Material.STICK,null,Material.STICK,
                Material.STICK,Material.STICK,Material.STICK);

        new BSRecipe(AllInstruments.GetInstrumentByName("snare-drum").getItem(),
                Material.LEATHER,Material.LEATHER,Material.LEATHER,
                Material.STICK,null,Material.STICK,
                null,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("snare-drum+1").getItem(),
                Material.RABBIT_HIDE,Material.RABBIT_HIDE,Material.RABBIT_HIDE,
                Material.STICK,null,Material.STICK,
                null,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("sticks+1").getItem(),
                Material.BONE,null,Material.BONE,
                Material.STICK,null,Material.STICK,
                Material.STICK,null,Material.STICK);
        new BSRecipe(AllInstruments.GetInstrumentByName("sticks").getItem(),
                Material.BONE,null,Material.BONE,
                Material.BAMBOO,null,Material.BAMBOO,
                Material.BAMBOO,null,Material.BAMBOO);

        new BSRecipe(AllInstruments.GetInstrumentByName("bass-guitar").getItem(),
                null,Material.IRON_NUGGET,Material.STICK,
                Material.IRON_NUGGET,Material.STICK,Material.IRON_NUGGET,
                Material.NOTE_BLOCK,Material.IRON_NUGGET,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("bass-guitar+1").getItem(),
                null,Material.IRON_NUGGET,Material.STICK,
                null,Material.STICK,Material.IRON_NUGGET,
                Material.NOTE_BLOCK,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("flute").getItem(),
                null,Material.IRON_INGOT,null,
                null,Material.COPPER_INGOT,null,
                null,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("flute+1").getItem(),
                null,Material.IRON_NUGGET,null,
                null,Material.COPPER_INGOT,null,
                null,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("cow-bell").getItem(),
                null,Material.BELL,null,
                null,Material.BLAZE_ROD,null,
                null,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("cow-bell+1").getItem(),
                null,Material.BELL,null,
                null,Material.STICK,null,
                null,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("piano").getItem(),
                null,null,null,
                null,null,null,
                Material.QUARTZ_SLAB,Material.QUARTZ_SLAB,Material.QUARTZ_SLAB);
        new BSRecipe(AllInstruments.GetInstrumentByName("piano+1").getItem(),
                null,null,null,
                null,null,null,
                Material.BLACKSTONE_SLAB,Material.BLACKSTONE_SLAB,Material.BLACKSTONE_SLAB);
        new BSRecipe(AllInstruments.GetInstrumentByName("harp").getItem(),
                null,Material.STRING,null,
                Material.STRING,Material.NOTE_BLOCK,Material.STRING,
                Material.STICK,null,Material.STICK);
        new BSRecipe(AllInstruments.GetInstrumentByName("harp+1").getItem(),
                null,Material.STRING,null,
                null,Material.NOTE_BLOCK,Material.STRING,
                Material.STICK,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("chime+1").getItem(),
                Material.STICK,Material.STICK,Material.STICK,
                Material.POINTED_DRIPSTONE,Material.POINTED_DRIPSTONE,Material.POINTED_DRIPSTONE,
                null,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("chime").getItem(),
                Material.STICK,Material.STICK,Material.STICK,
                Material.POINTED_DRIPSTONE,Material.POINTED_DRIPSTONE,Material.POINTED_DRIPSTONE,
                Material.POINTED_DRIPSTONE,Material.POINTED_DRIPSTONE,Material.POINTED_DRIPSTONE);

        new BSRecipe(AllInstruments.GetInstrumentByName("xylophone+1").getItem(),
                null,null,null,
                Material.BONE,Material.BONE,Material.BONE,
                Material.STICK,null,Material.STICK);
        new BSRecipe(AllInstruments.GetInstrumentByName("xylophone").getItem(),
                null,null,null,
                Material.BONE_BLOCK,Material.BONE_BLOCK,Material.BONE_BLOCK,
                Material.STICK,null,Material.STICK);

        new BSRecipe(AllInstruments.GetInstrumentByName("iron-xylophone").getItem(),
                null,null,null,
                Material.IRON_INGOT,Material.IRON_INGOT,Material.IRON_INGOT,
                Material.STICK,null,Material.STICK);
        new BSRecipe(AllInstruments.GetInstrumentByName("iron-xylophone+1").getItem(),
                null,null,null,
                Material.IRON_NUGGET,Material.IRON_NUGGET,Material.IRON_NUGGET,
                Material.STICK,null,Material.STICK);

        new BSRecipe(AllInstruments.GetInstrumentByName("bell+1").getItem(),
                null,Material.GOLD_INGOT,null,
                null,Material.GOLD_NUGGET,null,
                null,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("bell").getItem(),
                null,Material.IRON_INGOT,null,
                null,Material.IRON_NUGGET,null,
                null,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("didgeridoo").getItem(),
                null,Material.MANGROVE_ROOTS,null,
                null,Material.MANGROVE_ROOTS,null,
                null,Material.MANGROVE_ROOTS,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("didgeridoo+1").getItem(),
                null,Material.MANGROVE_ROOTS,null,
                null,Material.MANGROVE_ROOTS,null,
                null,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("bit").getItem(),
                null,null,null,
                Material.STONE_PRESSURE_PLATE,Material.STONE_PRESSURE_PLATE,Material.STONE_PRESSURE_PLATE,
                null,Material.CALIBRATED_SCULK_SENSOR,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("bit+1").getItem(),
                null,null,null,
                Material.STONE_BUTTON,Material.STONE_BUTTON,Material.STONE_BUTTON,
                null,Material.CALIBRATED_SCULK_SENSOR,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("banjo").getItem(),
                null,Material.STRING,Material.STICK,
                null,Material.STICK,Material.STRING,
                Material.PUMPKIN,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("banjo+1").getItem(),
                null,Material.STRING,Material.STICK,
                null,Material.STICK,Material.STRING,
                Material.CARVED_PUMPKIN,null,null);

        new BSRecipe(AllInstruments.GetInstrumentByName("pling").getItem(),
                null,Material.AMETHYST_SHARD,null,
                null,Material.REDSTONE_BLOCK,null,
                null,null,null);
        new BSRecipe(AllInstruments.GetInstrumentByName("pling+1").getItem(),
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
        new BSRecipe(AllInstruments.GetInstrumentByName(instrumentName).getItem(),
                null,bodyMaterial,null,
                bodyMaterial,Material.NOTE_BLOCK,bodyMaterial,
                null,Material.STICK,null);
        new BSRecipe(AllInstruments.GetInstrumentByName(instrumentName+"+1").getItem(),
                null,bodyMaterial,null,
                bodyMaterial,Material.NOTE_BLOCK,null,
                null,Material.STICK,null);
    }

    private void addTuningRecipes() {
        for (String instrumentName : AllInstruments.GetAllInstrumentNames()) {
            if (!isDefaultInstrumentName(instrumentName)) continue;

            String baseName = instrumentName;
            addTuningRecipe(baseName+"+1", baseName, Material.AMETHYST_SHARD);
            addTuningRecipe(baseName+"+2", baseName+"+1", Material.AMETHYST_BLOCK);
            addTuningRecipe(baseName+"-1", baseName, Material.DEEPSLATE);
            addTuningRecipe(baseName+"-2", baseName+"-1", Material.OBSIDIAN);
        }
    }

    private boolean isDefaultInstrumentName(String instrumentName) {
        return !instrumentName.endsWith("+1")
                && !instrumentName.endsWith("+2")
                && !instrumentName.endsWith("-1")
                && !instrumentName.endsWith("-2");
    }

    private void addSongRecipes() {
        if (!BSInstruments.shouldRegisterSongRecipes()) return;

        List<MaterialPair> accentPairs = getSongRecipeAccentPairs();
        int recipeIndex = 0;
        for (Song song : AllSongs.getAllSongs()) {
            if (recipeIndex >= accentPairs.size()) break;

            MaterialPair accents = accentPairs.get(recipeIndex);
            addSongRecipe(song, accents.first(), accents.second());
            recipeIndex++;
        }
    }

    private void addSongRecipe(Song song, Material firstAccent, Material secondAccent) {
        NamespacedKey key = new NamespacedKey(BSInstruments.getInstance(),
                "song_recipe_" + BSInstruments.getIntForRecipe());
        ShapelessRecipe recipe = new ShapelessRecipe(key, song.getItem());
        recipe.addIngredient(Material.PAPER);
        recipe.addIngredient(Material.INK_SAC);
        recipe.addIngredient(Material.NOTE_BLOCK);
        recipe.addIngredient(firstAccent);
        recipe.addIngredient(secondAccent);
        Bukkit.addRecipe(recipe);
        BSRecipe.AllRecipeKeys.add(key);
    }

    private List<MaterialPair> getSongRecipeAccentPairs() {
        List<MaterialPair> pairs = new ArrayList<>();
        for (int first = 0; first < SONG_RECIPE_ACCENTS.size(); first++) {
            for (int second = first; second < SONG_RECIPE_ACCENTS.size(); second++) {
                pairs.add(new MaterialPair(SONG_RECIPE_ACCENTS.get(first), SONG_RECIPE_ACCENTS.get(second)));
            }
        }
        return pairs;
    }

    private record MaterialPair(Material first, Material second) {
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
