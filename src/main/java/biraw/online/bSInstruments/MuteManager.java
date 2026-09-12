package biraw.online.bSInstruments;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public final class MuteManager {
    private static final Set<UUID> MUTED_PLAYERS = new HashSet<>();

    private MuteManager() {
    }

    public static boolean isMuted(Player player) {
        return MUTED_PLAYERS.contains(player.getUniqueId());
    }

    public static void setMuted(Player player, boolean muted) {
        if (muted) {
            MUTED_PLAYERS.add(player.getUniqueId());
        } else {
            MUTED_PLAYERS.remove(player.getUniqueId());
        }
    }

    public static void clear(Player player) {
        MUTED_PLAYERS.remove(player.getUniqueId());
    }

    static void clearAll() {
        MUTED_PLAYERS.clear();
    }

    public static void sendMuteStatus(Player player) {
        if (isMuted(player)) player.sendMessage("§cInstruments are MUTED for you.");
        else player.sendMessage("§aInstruments are NOT MUTED for you.");
    }
}
