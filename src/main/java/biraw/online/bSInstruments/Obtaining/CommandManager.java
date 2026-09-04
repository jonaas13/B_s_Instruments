package biraw.online.bSInstruments.Obtaining;

import biraw.online.bSInstruments.AllInstruments;
import biraw.online.bSInstruments.AllSongs;
import biraw.online.bSInstruments.BSInstruments;
import biraw.online.bSInstruments.DirectorMode;
import biraw.online.bSInstruments.Instrument;
import biraw.online.bSInstruments.MuteManager;
import biraw.online.bSInstruments.Song;
import biraw.online.bSInstruments.SongBookMenu;
import biraw.online.bSInstruments.SongPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandManager implements CommandExecutor, TabExecutor {
    private static final String PERMISSION_GET = "minearchyinstruments.instrument.get";
    private static final String PERMISSION_ALL = "minearchyinstruments.instrument.all";
    private static final String PERMISSION_SONG = "minearchyinstruments.instrument.song";
    private static final String PERMISSION_SONGBOOK = "minearchyinstruments.instrument.songbook";
    private static final String PERMISSION_DIRECTOR = "minearchyinstruments.instrument.director";
    private static final String PERMISSION_MUTE = "minearchyinstruments.instrument.mute";
    private static final List<String> MUTE_OPTIONS = List.of("true", "false");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player player))
        {
            BSInstruments.getInstance().getLogger().warning("This command can only be used by players!");
            return true;
        }

        if (strings.length < 1) return false;

        String subcommand = strings[0].toLowerCase(Locale.ROOT);

        if (subcommand.equals("all")) {
            if (!hasPermission(player, PERMISSION_ALL)) return true;
            AllInstruments.GiveAllInstruments(player);
            return true;
        }

        if (subcommand.equals("songs")) {
            if (strings.length >= 2 && strings[1].equalsIgnoreCase("all")) {
                if (!hasPermission(player, PERMISSION_SONG)) return true;
                int unlocked = AllSongs.unlockAllSongs(player);
                player.sendMessage("§aUnlocked §e" + unlocked + "§a new song(s) in your song book.");
            } else {
                if (!hasPermission(player, PERMISSION_SONGBOOK)) return true;
                SongBookMenu.open(player);
            }
            return true;
        }

        if (subcommand.equals("get")) {
            if (!hasPermission(player, PERMISSION_GET)) return true;
            if (strings.length < 2) return false;
            return giveInstrument(player, strings[1]);
        }

        if (subcommand.equals("song")) {
            if (!hasPermission(player, PERMISSION_SONG)) return true;
            if (strings.length < 2) return false;
            return giveSong(player, strings[1]);
        }

        if (subcommand.equals("accept")) {
            return DirectorMode.accept(player);
        }

        if (subcommand.equals("director")) {
            if (!hasPermission(player, PERMISSION_DIRECTOR)) return true;
            DirectorMode.open(player);
            return true;
        }

        if (subcommand.equals("stop")) {
            if (SongPlayer.stop(player)) {
                player.sendMessage("§aStopped playing the song.");
            } else {
                player.sendMessage("§eYou are not playing a song.");
            }
            return true;
        }

        if (subcommand.equals("mute")) {
            if (!hasPermission(player, PERMISSION_MUTE)) return true;
            return handleMute(player, strings);
        }

        return false;
    }

    private boolean hasPermission(Player player, String permission) {
        if (player.hasPermission(permission)) return true;
        player.sendMessage("§cYou don't have permission to use this command!");
        return false;
    }

    private boolean giveInstrument(Player player, String name) {
        Instrument item = AllInstruments.GetInstrumentByName(name);
        if (item == null) return false;
        if (!ItemDelivery.giveToInventory(player, item.getItem())) {
            player.sendMessage("§cInventory full. Instrument was not added.");
        }
        return true;
    }

    private boolean giveSong(Player player, String name) {
        Song song = AllSongs.getSongByName(name);
        if (song == null) return false;
        if (!ItemDelivery.giveToInventory(player, song.getItem())) {
            player.sendMessage("§cInventory full. Sheet music was not added.");
        }
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
        if (!(commandSender instanceof Player player)) return List.of();

        if (strings.length == 1) {
            List<String> completions = new ArrayList<>();
            completions.add("accept");
            if (player.hasPermission(PERMISSION_GET)) completions.add("get");
            if (player.hasPermission(PERMISSION_DIRECTOR)) completions.add("director");
            if (player.hasPermission(PERMISSION_SONG)) completions.add("song");
            if (player.hasPermission(PERMISSION_SONGBOOK) || player.hasPermission(PERMISSION_SONG)) completions.add("songs");
            completions.add("stop");
            if (player.hasPermission(PERMISSION_ALL)) completions.add("all");
            if (player.hasPermission(PERMISSION_MUTE)) completions.add("mute");
            return filterCompletions(completions, strings[0]);
        }

        if (strings.length == 2
                && strings[0].equalsIgnoreCase("get")
                && player.hasPermission(PERMISSION_GET)) {
            return filterCompletions(AllInstruments.GetAllInstrumentNames(), strings[1]);
        }

        if (strings.length == 2
                && strings[0].equalsIgnoreCase("song")
                && player.hasPermission(PERMISSION_SONG)) {
            return filterCompletions(AllSongs.getAllSongNames(), strings[1]);
        }

        if (strings.length == 2
                && strings[0].equalsIgnoreCase("songs")
                && player.hasPermission(PERMISSION_SONG)) {
            return filterCompletions(List.of("all"), strings[1]);
        }

        if (strings.length == 2
                && strings[0].equalsIgnoreCase("mute")
                && player.hasPermission(PERMISSION_MUTE)) {
            return filterCompletions(MUTE_OPTIONS, strings[1]);
        }

        return List.of();
    }

    private List<String> filterCompletions(List<String> values, String input) {
        List<String> completions = new ArrayList<>();
        String normalizedInput = input.toLowerCase(Locale.ROOT);
        for (String value : values) {
            if (value.startsWith(normalizedInput)) completions.add(value);
        }
        return completions;
    }
}
