package biraw.online.bSInstruments;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class MuteManager {
    private static final Set<UUID> Muted = new HashSet<>();
    public static boolean isMuted(Player player){return Muted.contains(player.getUniqueId());}

    public static void setMuted(Player player, boolean muted) {
        if (muted) {
            Muted.add(player.getUniqueId());
        } else {
            Muted.remove(player.getUniqueId());
        }
    }

    public static void sendMuteStatus(Player player) {
        if (isMuted(player)) player.sendMessage("§cInstruments are MUTED for you.");
        else player.sendMessage("§aInstruments are NOT MUTED for you.");
    }
}
