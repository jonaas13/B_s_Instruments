package biraw.online.bSInstruments;

import biraw.online.bSInstruments.Obtaining.CommandManager;
import biraw.online.bSInstruments.Obtaining.BSRecipe;
import biraw.online.bSInstruments.Obtaining.LootSpawning;
import biraw.online.bSInstruments.Obtaining.RegisterRecipes;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Objects;

public final class BSInstruments extends JavaPlugin {

    private static BSInstruments instance;
    private static NamespacedKey itemKey;

    private static int lastRecipeId = 1;
    private static int songPitchOffsetSemitones;
    private static double songHearingRadiusSquared;
    private static boolean registerSongRecipes;
    private static ItemUseAnimation instrumentUseAnimation;
    private static int directorStartCountdownSeconds;

    public static BSInstruments getInstance() {
        return instance;
    }

    public static NamespacedKey getItemKey() {
        return itemKey;
    }

    public static int nextRecipeId() {
        return ++lastRecipeId;
    }

    public static int getSongPitchOffsetSemitones() {
        return songPitchOffsetSemitones;
    }

    public static double getSongHearingRadiusSquared() {
        return songHearingRadiusSquared;
    }

    public static boolean shouldRegisterSongRecipes() {
        return registerSongRecipes;
    }

    public static ItemUseAnimation getInstrumentUseAnimation() {
        return instrumentUseAnimation;
    }

    public static int getDirectorStartCountdownSeconds() {
        return directorStartCountdownSeconds;
    }

    @Override
    public void onEnable() {
        instance = this;
        itemKey = new NamespacedKey(this, "bsi");
        saveDefaultConfig();
        loadSettings();

        CommandManager commandManager = new CommandManager();
        PluginCommand instrumentCommand = Objects.requireNonNull(
                getCommand("instrument"),
                "instrument command is missing from plugin.yml"
        );
        instrumentCommand.setExecutor(commandManager);
        instrumentCommand.setTabCompleter(commandManager);

        AllInstruments.registerListeners(getServer().getPluginManager(), this);
        registerListeners(
                new LootSpawning(),
                new RegisterRecipes(),
                new PlayerStateCleanup(),
                new SongBookMenu(),
                new DirectorMode()
        );

        getLogger().info("MinearchyInstruments loaded successfully.");
    }

    @Override
    public void onDisable() {
        SongPlayer.stopAll();
        DirectorMode.clearAll();
        Instrument.clearAllPlayerState();
        MuteManager.clearAll();
        BSRecipe.unregisterAll();
        AllSongs.clearCache();
        lastRecipeId = 1;
        instance = null;
        itemKey = null;
    }

    private void loadSettings() {
        songPitchOffsetSemitones = getConfig().getInt("song-pitch-offset-semitones", 0);
        double songHearingRadiusBlocks = Math.max(1.0, getConfig().getDouble("song-hearing-radius-blocks", 48.0));
        songHearingRadiusSquared = songHearingRadiusBlocks * songHearingRadiusBlocks;
        registerSongRecipes = getConfig().getBoolean("register-song-recipes", true);
        instrumentUseAnimation = parseInstrumentUseAnimation(getConfig().getString("instrument-use-animation", "TOOT_HORN"));
        directorStartCountdownSeconds = Math.max(0, getConfig().getInt("director-start-countdown-seconds", 3));
    }

    private ItemUseAnimation parseInstrumentUseAnimation(String configuredAnimation) {
        if (configuredAnimation == null || configuredAnimation.isBlank()) return ItemUseAnimation.TOOT_HORN;

        try {
            return ItemUseAnimation.valueOf(configuredAnimation.trim().toUpperCase(Locale.ROOT).replace('-', '_'));
        } catch (IllegalArgumentException exception) {
            getLogger().warning("Unknown instrument-use-animation '" + configuredAnimation + "'. Falling back to TOOT_HORN.");
            return ItemUseAnimation.TOOT_HORN;
        }
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }
}
