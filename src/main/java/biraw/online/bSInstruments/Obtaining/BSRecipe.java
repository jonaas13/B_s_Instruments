package biraw.online.bSInstruments.Obtaining;

import biraw.online.bSInstruments.BSInstruments;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;

public final class BSRecipe {
    private static final int RECIPE_SIZE = 9;
    private static final int ROW_SIZE = 3;
    private static final String INGREDIENT_SYMBOLS = "ABCDEFGHI";
    private static final List<NamespacedKey> RECIPE_KEYS = new ArrayList<>();

    private BSRecipe() {
    }

    public static void register(ItemStack resultItem, Material... ingredients) {
        if (ingredients.length != RECIPE_SIZE) {
            throw new IllegalArgumentException("A shaped instrument recipe must contain exactly nine slots");
        }

        NamespacedKey key = new NamespacedKey(BSInstruments.getInstance(),
                "recipe_" + BSInstruments.nextRecipeId());
        ShapedRecipe recipe = new ShapedRecipe(key, resultItem);
        recipe.shape(
                recipeRow(ingredients, 0),
                recipeRow(ingredients, ROW_SIZE),
                recipeRow(ingredients, ROW_SIZE * 2)
        );

        for (int slot = 0; slot < ingredients.length; slot++) {
            Material ingredient = ingredients[slot];
            if (ingredient != null) recipe.setIngredient(INGREDIENT_SYMBOLS.charAt(slot), ingredient);
        }

        Bukkit.addRecipe(recipe);
        RECIPE_KEYS.add(key);
    }

    public static List<NamespacedKey> keys() {
        return List.copyOf(RECIPE_KEYS);
    }

    public static void track(NamespacedKey key) {
        RECIPE_KEYS.add(key);
    }

    public static void unregisterAll() {
        // Paper can reload recipe data on every removal. Leave server recipes alone
        // during shutdown; only a plugin disable on a running server needs removal.
        if (!Bukkit.isStopping()) {
            for (NamespacedKey key : RECIPE_KEYS) {
                Bukkit.removeRecipe(key);
            }
        }
        RECIPE_KEYS.clear();
    }

    private static String recipeRow(Material[] ingredients, int startIndex) {
        StringBuilder row = new StringBuilder(ROW_SIZE);
        for (int slot = startIndex; slot < startIndex + ROW_SIZE; slot++) {
            row.append(ingredients[slot] == null ? ' ' : INGREDIENT_SYMBOLS.charAt(slot));
        }
        return row.toString();
    }
}
