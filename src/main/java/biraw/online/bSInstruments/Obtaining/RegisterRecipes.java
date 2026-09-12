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
    private void onCraftItem(CraftItemEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() != Material.NOTE_BLOCK) return;
        for (NamespacedKey n : BSRecipe.keys()) {
            if (!event.getWhoClicked().hasDiscoveredRecipe(n))
                event.getWhoClicked().discoverRecipe(n);
        }
    }

    public RegisterRecipes() {
        BSRecipe.register(AllInstruments.getInstrumentByName("guitar").getItem(),
                null, Material.STRING, Material.STICK,
                Material.STRING, Material.STICK, Material.STRING,
                Material.NOTE_BLOCK, Material.STRING, null);
        BSRecipe.register(AllInstruments.getInstrumentByName("guitar+1").getItem(),
                null, Material.STRING, Material.STICK,
                null, Material.STICK, Material.STRING,
                Material.NOTE_BLOCK, null, null);

        BSRecipe.register(AllInstruments.getInstrumentByName("bass-drum").getItem(),
                Material.LEATHER, Material.LEATHER, Material.LEATHER,
                Material.STICK, null, Material.STICK,
                Material.STICK, Material.STICK, Material.STICK);
        BSRecipe.register(AllInstruments.getInstrumentByName("bass-drum+1").getItem(),
                Material.RABBIT_HIDE, Material.RABBIT_HIDE, Material.RABBIT_HIDE,
                Material.STICK, null, Material.STICK,
                Material.STICK, Material.STICK, Material.STICK);

        BSRecipe.register(AllInstruments.getInstrumentByName("snare-drum").getItem(),
                Material.LEATHER, Material.LEATHER, Material.LEATHER,
                Material.STICK, null, Material.STICK,
                null, null, null);
        BSRecipe.register(AllInstruments.getInstrumentByName("snare-drum+1").getItem(),
                Material.RABBIT_HIDE, Material.RABBIT_HIDE, Material.RABBIT_HIDE,
                Material.STICK, null, Material.STICK,
                null, null, null);

        BSRecipe.register(AllInstruments.getInstrumentByName("sticks+1").getItem(),
                Material.BONE, null, Material.BONE,
                Material.STICK, null, Material.STICK,
                Material.STICK, null, Material.STICK);
        BSRecipe.register(AllInstruments.getInstrumentByName("sticks").getItem(),
                Material.BONE, null, Material.BONE,
                Material.BAMBOO, null, Material.BAMBOO,
                Material.BAMBOO, null, Material.BAMBOO);

        BSRecipe.register(AllInstruments.getInstrumentByName("bass-guitar").getItem(),
                null, Material.IRON_NUGGET, Material.STICK,
                Material.IRON_NUGGET, Material.STICK, Material.IRON_NUGGET,
                Material.NOTE_BLOCK, Material.IRON_NUGGET, null);
        BSRecipe.register(AllInstruments.getInstrumentByName("bass-guitar+1").getItem(),
                null, Material.IRON_NUGGET, Material.STICK,
                null, Material.STICK, Material.IRON_NUGGET,
                Material.NOTE_BLOCK, null, null);

        BSRecipe.register(AllInstruments.getInstrumentByName("flute").getItem(),
                null, Material.IRON_INGOT, null,
                null, Material.COPPER_INGOT, null,
                null, null, null);
        BSRecipe.register(AllInstruments.getInstrumentByName("flute+1").getItem(),
                null, Material.IRON_NUGGET, null,
                null, Material.COPPER_INGOT, null,
                null, null, null);

        BSRecipe.register(AllInstruments.getInstrumentByName("cow-bell").getItem(),
                null, Material.BELL, null,
                null, Material.BLAZE_ROD, null,
                null, null, null);
        BSRecipe.register(AllInstruments.getInstrumentByName("cow-bell+1").getItem(),
                null, Material.BELL, null,
                null, Material.STICK, null,
                null, null, null);

        BSRecipe.register(AllInstruments.getInstrumentByName("piano").getItem(),
                null, null, null,
                null, null, null,
                Material.QUARTZ_SLAB, Material.QUARTZ_SLAB, Material.QUARTZ_SLAB);
        BSRecipe.register(AllInstruments.getInstrumentByName("piano+1").getItem(),
                null, null, null,
                null, null, null,
                Material.BLACKSTONE_SLAB, Material.BLACKSTONE_SLAB, Material.BLACKSTONE_SLAB);
        BSRecipe.register(AllInstruments.getInstrumentByName("harp").getItem(),
                null, Material.STRING, null,
                Material.STRING, Material.NOTE_BLOCK, Material.STRING,
                Material.STICK, null, Material.STICK);
        BSRecipe.register(AllInstruments.getInstrumentByName("harp+1").getItem(),
                null, Material.STRING, null,
                null, Material.NOTE_BLOCK, Material.STRING,
                Material.STICK, null, null);

        BSRecipe.register(AllInstruments.getInstrumentByName("chime+1").getItem(),
                Material.STICK, Material.STICK, Material.STICK,
                Material.POINTED_DRIPSTONE, Material.POINTED_DRIPSTONE, Material.POINTED_DRIPSTONE,
                null, null, null);
        BSRecipe.register(AllInstruments.getInstrumentByName("chime").getItem(),
                Material.STICK, Material.STICK, Material.STICK,
                Material.POINTED_DRIPSTONE, Material.POINTED_DRIPSTONE, Material.POINTED_DRIPSTONE,
                Material.POINTED_DRIPSTONE, Material.POINTED_DRIPSTONE, Material.POINTED_DRIPSTONE);

        BSRecipe.register(AllInstruments.getInstrumentByName("xylophone+1").getItem(),
                null, null, null,
                Material.BONE, Material.BONE, Material.BONE,
                Material.STICK, null, Material.STICK);
        BSRecipe.register(AllInstruments.getInstrumentByName("xylophone").getItem(),
                null, null, null,
                Material.BONE_BLOCK, Material.BONE_BLOCK, Material.BONE_BLOCK,
                Material.STICK, null, Material.STICK);

        BSRecipe.register(AllInstruments.getInstrumentByName("iron-xylophone").getItem(),
                null, null, null,
                Material.IRON_INGOT, Material.IRON_INGOT, Material.IRON_INGOT,
                Material.STICK, null, Material.STICK);
        BSRecipe.register(AllInstruments.getInstrumentByName("iron-xylophone+1").getItem(),
                null, null, null,
                Material.IRON_NUGGET, Material.IRON_NUGGET, Material.IRON_NUGGET,
                Material.STICK, null, Material.STICK);

        BSRecipe.register(AllInstruments.getInstrumentByName("bell+1").getItem(),
                null, Material.GOLD_INGOT, null,
                null, Material.GOLD_NUGGET, null,
                null, null, null);
        BSRecipe.register(AllInstruments.getInstrumentByName("bell").getItem(),
                null, Material.IRON_INGOT, null,
                null, Material.IRON_NUGGET, null,
                null, null, null);

        BSRecipe.register(AllInstruments.getInstrumentByName("didgeridoo").getItem(),
                null, Material.MANGROVE_ROOTS, null,
                null, Material.MANGROVE_ROOTS, null,
                null, Material.MANGROVE_ROOTS, null);
        BSRecipe.register(AllInstruments.getInstrumentByName("didgeridoo+1").getItem(),
                null, Material.MANGROVE_ROOTS, null,
                null, Material.MANGROVE_ROOTS, null,
                null, null, null);

        BSRecipe.register(AllInstruments.getInstrumentByName("bit").getItem(),
                null, null, null,
                Material.STONE_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE,
                null, Material.CALIBRATED_SCULK_SENSOR, null);
        BSRecipe.register(AllInstruments.getInstrumentByName("bit+1").getItem(),
                null, null, null,
                Material.STONE_BUTTON, Material.STONE_BUTTON, Material.STONE_BUTTON,
                null, Material.CALIBRATED_SCULK_SENSOR, null);

        BSRecipe.register(AllInstruments.getInstrumentByName("banjo").getItem(),
                null, Material.STRING, Material.STICK,
                null, Material.STICK, Material.STRING,
                Material.PUMPKIN, null, null);
        BSRecipe.register(AllInstruments.getInstrumentByName("banjo+1").getItem(),
                null, Material.STRING, Material.STICK,
                null, Material.STICK, Material.STRING,
                Material.CARVED_PUMPKIN, null, null);

        BSRecipe.register(AllInstruments.getInstrumentByName("pling").getItem(),
                null, Material.AMETHYST_SHARD, null,
                null, Material.REDSTONE_BLOCK, null,
                null, null, null);
        BSRecipe.register(AllInstruments.getInstrumentByName("pling+1").getItem(),
                null, Material.AMETHYST_SHARD, null,
                null, Material.REDSTONE, null,
                null, null, null);

        addTrumpetRecipes("trumpet", Material.COPPER_INGOT);
        addTrumpetRecipes("exposed-trumpet", Material.EXPOSED_COPPER);
        addTrumpetRecipes("weathered-trumpet", Material.WEATHERED_COPPER);
        addTrumpetRecipes("oxidized-trumpet", Material.OXIDIZED_COPPER);

        addSongRecipes();
        addTuningRecipes();
    }

    private void addTrumpetRecipes(String instrumentName, Material bodyMaterial) {
        BSRecipe.register(AllInstruments.getInstrumentByName(instrumentName).getItem(),
                null, bodyMaterial, null,
                bodyMaterial, Material.NOTE_BLOCK, bodyMaterial,
                null, Material.STICK, null);
        BSRecipe.register(AllInstruments.getInstrumentByName(instrumentName + "+1").getItem(),
                null, bodyMaterial, null,
                bodyMaterial, Material.NOTE_BLOCK, null,
                null, Material.STICK, null);
    }

    private void addTuningRecipes() {
        for (String instrumentName : AllInstruments.getAllInstrumentNames()) {
            if (!isDefaultInstrumentName(instrumentName)) continue;

            addTuningRecipe(instrumentName + "+1", instrumentName, Material.AMETHYST_SHARD);
            addTuningRecipe(instrumentName + "+2", instrumentName + "+1", Material.AMETHYST_BLOCK);
            addTuningRecipe(instrumentName + "-1", instrumentName, Material.DEEPSLATE);
            addTuningRecipe(instrumentName + "-2", instrumentName + "-1", Material.OBSIDIAN);
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
                "song_recipe_" + BSInstruments.nextRecipeId());
        ShapelessRecipe recipe = new ShapelessRecipe(key, song.getItem());
        recipe.addIngredient(Material.PAPER);
        recipe.addIngredient(Material.INK_SAC);
        recipe.addIngredient(Material.NOTE_BLOCK);
        recipe.addIngredient(firstAccent);
        recipe.addIngredient(secondAccent);
        Bukkit.addRecipe(recipe);
        BSRecipe.track(key);
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
        Instrument result = AllInstruments.getInstrumentByName(resultName);
        Instrument source = AllInstruments.getInstrumentByName(sourceName);
        if (result == null || source == null) return;

        NamespacedKey key = new NamespacedKey(BSInstruments.getInstance(),
                "tuning_recipe_" + BSInstruments.nextRecipeId());
        ShapelessRecipe recipe = new ShapelessRecipe(key, result.getItem());
        recipe.addIngredient(new RecipeChoice.ExactChoice(source.getItem()));
        recipe.addIngredient(modifier);
        Bukkit.addRecipe(recipe);
        BSRecipe.track(key);
    }
}
