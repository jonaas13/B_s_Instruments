package biraw.online.bSInstruments;

import biraw.online.bSInstruments.Obtaining.CommandManager;
import biraw.online.bSInstruments.Obtaining.LootSpawning;
import biraw.online.bSInstruments.Obtaining.RegisterRecipes;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class BSInstruments extends JavaPlugin {

    private static BSInstruments instance;
    public static BSInstruments getInstance(){return instance;}
    public static NamespacedKey NSKEY;

    private static int lastID = 1;

    public static int getIntForRecipe(){
        lastID+=1;
        return lastID;
    }

    @Override
    public void onEnable() {
        instance = this;
        NSKEY = new NamespacedKey(instance, "bsi");

        CommandManager cm = new CommandManager();
        instance.getCommand("instrument").setExecutor(cm);
        instance.getCommand("instrument").setTabCompleter(cm);

        Bukkit.getPluginManager().registerEvents(new LootSpawning(),instance);
        Bukkit.getPluginManager().registerEvents(new RegisterRecipes(),instance);
        Bukkit.getPluginManager().registerEvents(new PlayerStateCleanup(), instance);

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
}
