package biraw.online.bSInstruments;

import biraw.online.bSInstruments.Obtaining.CommandManager;
import biraw.online.bSInstruments.Obtaining.LootSpawning;
import biraw.online.bSInstruments.Obtaining.RegisterRecipes;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public final class BSInstruments extends JavaPlugin {

    private static BSInstruments instance;
    public static BSInstruments getInstance(){return instance;}
    public static NamespacedKey NSKEY;

    private static int lastID = 1;
    private static int songPitchOffsetSemitones;
    private static double songHearingRadiusSquared;
    private static boolean registerSongRecipes;
    private static ItemUseAnimation instrumentUseAnimation;
    private static int directorStartCountdownSeconds;

    public static int getIntForRecipe(){
        lastID+=1;
        return lastID;
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
        NSKEY = new NamespacedKey(instance, "bsi");
        saveDefaultConfig();
        loadSettings();

        CommandManager cm = new CommandManager();
        instance.getCommand("instrument").setExecutor(cm);
        instance.getCommand("instrument").setTabCompleter(cm);

        Bukkit.getPluginManager().registerEvents(new LootSpawning(),instance);
        Bukkit.getPluginManager().registerEvents(new RegisterRecipes(),instance);
        Bukkit.getPluginManager().registerEvents(new PlayerStateCleanup(), instance);
        Bukkit.getPluginManager().registerEvents(new SongBookMenu(), instance);
        Bukkit.getPluginManager().registerEvents(new DirectorMode(), instance);

        Bukkit.getLogger().info(" ");
        Bukkit.getLogger().info("O=========================================================O");
        Bukkit.getLogger().info("    MinearchyInstruments has loaded successfully!");
        Bukkit.getLogger().info("       This is MinearchyInstruments for Minecraft JDK 25."    );
        Bukkit.getLogger().info("                       Author: BiRaw");
        Bukkit.getLogger().info("         Discord: https://discord.gg/XwFqu7uahX :>");
        Bukkit.getLogger().info("O=========================================================O");
        Bukkit.getLogger().info(" ");
    }

    @Override
    public void onDisable() {
        SongPlayer.stopAll();
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
}
