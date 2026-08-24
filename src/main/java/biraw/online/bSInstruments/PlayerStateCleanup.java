package biraw.online.bSInstruments;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerStateCleanup implements Listener {
    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Instrument.clearPlayerState(event.getPlayer());
        SongPlayer.stop(event.getPlayer());
        MuteManager.clear(event.getPlayer());
    }
}
