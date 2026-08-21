package biraw.online.bSInstruments.Obtaining;

import biraw.online.bSInstruments.AllInstruments;
import biraw.online.bSInstruments.BSInstruments;
import biraw.online.bSInstruments.Instrument;
import biraw.online.bSInstruments.MuteManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabExecutor {
    private static final List<String> SUBCOMMANDS = List.of("get", "all", "mute");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player player))
        {
            BSInstruments.getInstance().getLogger().warning("This command can only be used by players!");
            return true;
        }

        if (strings.length < 1) return false;

        if (strings[0].equalsIgnoreCase("all")) {
            if (!hasPermission(player, "minearchyinstruments.instrument.all")) return true;
            AllInstruments.GiveAllInstruments(player);
            return true;
        }

        if (strings[0].equalsIgnoreCase("get")) {
            if (!hasPermission(player, "minearchyinstruments.instrument.get")) return true;
            if (strings.length < 2) return false;
            return giveInstrument(player, strings[1]);
        }

        if (strings[0].equalsIgnoreCase("mute")) {
            if (!hasPermission(player, "minearchyinstruments.instrument.mute")) return true;
            return handleMute(player, strings);
        }

        if (!hasPermission(player, "minearchyinstruments.instrument.get")) return true;
        return giveInstrument(player, strings[0]);
    }

    private boolean hasPermission(Player player, String permission) {
        if (player.hasPermission(permission)) return true;
        player.sendMessage("§cYou don't have permission to use this command!");
        return false;
    }

    private boolean giveInstrument(Player player, String name) {
        Instrument item = AllInstruments.GetInstrumentByName(name);
        if (item == null) return false;
        player.give(item.getItem());
        return true;
    }

    private boolean handleMute(Player player, String[] args) {
        if (args.length < 2) {
            MuteManager.setMuted(player, !MuteManager.isMuted(player));
            MuteManager.sendMuteStatus(player);
            return true;
        }

        if (args[1].equalsIgnoreCase("true")) {
            MuteManager.setMuted(player, true);
            MuteManager.sendMuteStatus(player);
            return true;
        }

        if (args[1].equalsIgnoreCase("false")) {
            MuteManager.setMuted(player, false);
            MuteManager.sendMuteStatus(player);
            return true;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length == 1) {
            List<String> completions = new ArrayList<>(SUBCOMMANDS);
            completions.addAll(AllInstruments.GetAllInstrumentNames());
            return filterCompletions(completions, strings[0]);
        }

        if (strings.length == 2 && strings[0].equalsIgnoreCase("get")) {
            return filterCompletions(AllInstruments.GetAllInstrumentNames(), strings[1]);
        }

        if (strings.length == 2 && strings[0].equalsIgnoreCase("mute")) {
            return filterCompletions(List.of("true", "false"), strings[1]);
        }

        return List.of();
    }

    private List<String> filterCompletions(List<String> values, String input) {
        List<String> completions = new ArrayList<>();
        for (String value : values) {
            if (value.toLowerCase().startsWith(input.toLowerCase())) completions.add(value);
        }
        return completions;
    }
}
