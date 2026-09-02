package biraw.online.bSInstruments;

import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class DirectorMode implements Listener {
    private static final int INVENTORY_SIZE = 54;
    private static final int STOP_SLOT = 0;
    private static final int SONG_SLOT = 4;
    private static final int INVITE_ALL_SLOT = 8;
    private static final int PLAYER_START_SLOT = 9;
    private static final int INVITE_RADIUS_BLOCKS = 24;
    private static final int INVITE_RADIUS_SQUARED = INVITE_RADIUS_BLOCKS * INVITE_RADIUS_BLOCKS;
    private static final int INVITE_EXPIRY_TICKS = 20 * 60;
    private static final Map<UUID, PendingInvite> PENDING_INVITES = new HashMap<>();

    public static void open(Player director) {
        Song song = AllSongs.getSongFromItem(director.getInventory().getItemInMainHand());
        if (song == null) {
            director.sendMessage("§cHold sheet music in your main hand to use director mode.");
            return;
        }

        List<Player> nearbyPlayers = nearbyPlayers(director);
        DirectorHolder holder = new DirectorHolder(director.getUniqueId(), song);
        Inventory inventory = Bukkit.createInventory(
                holder,
                INVENTORY_SIZE,
                Component.text("Director: " + trimTitle(song.title(), 20), NamedTextColor.DARK_PURPLE)
        );
        holder.setInventory(inventory);
        inventory.setItem(STOP_SLOT, button(Material.BARRIER, "Stop nearby song", NamedTextColor.RED, List.of(
                Component.text("Stops this song for nearby players.", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
        )));
        inventory.setItem(SONG_SLOT, song.getItem());
        inventory.setItem(INVITE_ALL_SLOT, button(Material.BELL, "Invite all nearby", NamedTextColor.GOLD, List.of(
                Component.text(nearbyPlayers.size() + " player(s) in range.", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
        )));

        if (nearbyPlayers.isEmpty()) {
            inventory.setItem(31, button(Material.BARRIER, "No nearby players", NamedTextColor.RED, List.of(
                    Component.text("Players must be within " + INVITE_RADIUS_BLOCKS + " blocks.", NamedTextColor.GRAY)
                            .decoration(TextDecoration.ITALIC, false)
            )));
        } else {
            int slot = PLAYER_START_SLOT;
            for (Player player : nearbyPlayers) {
                if (slot >= INVENTORY_SIZE) break;
                holder.setTarget(slot, player.getUniqueId());
                inventory.setItem(slot, playerButton(player));
                slot++;
            }
        }

        director.openInventory(inventory);
    }

    public static boolean accept(Player player) {
        PendingInvite invite = PENDING_INVITES.get(player.getUniqueId());
        if (invite == null || invite.isExpired()) {
            PENDING_INVITES.remove(player.getUniqueId());
            player.sendMessage("§cYou do not have a pending song invite.");
            return true;
        }

        Player director = Bukkit.getPlayer(invite.directorId());
        if (director == null || !director.isOnline() || !isInInviteRange(director, player)) {
            player.sendMessage("§cThat song invite is no longer nearby.");
            return true;
        }

        Instrument instrument = AllInstruments.GetInstrumentFromItem(player.getInventory().getItemInOffHand());
        if (instrument == null) {
            player.sendMessage("§eHold an instrument in your offhand, then use §f/instrument accept §eagain.");
            return true;
        }

        PENDING_INVITES.remove(player.getUniqueId());
        SongPlayer.tryStartInvited(player, instrument, invite.song());
        player.sendMessage("§aJoined " + invite.song().title() + ".");
        player.sendMessage("§eUse §f/instrument stop §eor play a manual note to stop.");
        return true;
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();
        if (!(inventory.getHolder() instanceof DirectorHolder holder)) return;

        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player director)) return;
        if (!director.getUniqueId().equals(holder.directorId())) return;
        if (event.getRawSlot() < 0 || event.getRawSlot() >= inventory.getSize()) return;

        int slot = event.getRawSlot();
        if (slot == STOP_SLOT) {
            int stopped = SongPlayer.stopNearbyPerformance(director, holder.song());
            director.sendMessage("§aStopped " + stopped + " player(s) playing " + holder.song().title() + ".");
            return;
        }

        if (slot == INVITE_ALL_SLOT) {
            int sent = 0;
            for (Player target : nearbyPlayers(director)) {
                sendInvite(director, target, holder.song());
                sent++;
            }
            director.sendMessage("§aSent " + sent + " song invite(s).");
            return;
        }

        UUID targetId = holder.target(slot);
        if (targetId == null) return;
        Player target = Bukkit.getPlayer(targetId);
        if (target == null || !target.isOnline() || !isInInviteRange(director, target)) {
            director.sendMessage("§cThat player is no longer nearby.");
            open(director);
            return;
        }

        sendInvite(director, target, holder.song());
        director.sendMessage("§aInvited " + target.getName() + " to play " + holder.song().title() + ".");
    }

    private static void sendInvite(Player director, Player target, Song song) {
        PENDING_INVITES.put(target.getUniqueId(), new PendingInvite(director.getUniqueId(), song, Bukkit.getCurrentTick() + INVITE_EXPIRY_TICKS));
        target.sendMessage(Component.text(director.getName(), NamedTextColor.LIGHT_PURPLE)
                .append(Component.text(" invited you to play ", NamedTextColor.GRAY))
                .append(Component.text(song.title(), NamedTextColor.WHITE))
                .append(Component.text(". ", NamedTextColor.GRAY))
                .append(Component.text("[Accept]", NamedTextColor.GREEN)
                        .clickEvent(ClickEvent.runCommand("/instrument accept"))
                        .hoverEvent(HoverEvent.showText(Component.text("Accept song invite", NamedTextColor.GREEN)))));
        Instrument instrument = AllInstruments.GetInstrumentFromItem(target.getInventory().getItemInOffHand());
        if (instrument == null) {
            target.sendMessage("§eHold an instrument in your offhand before accepting.");
        } else {
            target.sendMessage("§eYour offhand instrument will be used: §f" + instrument.name + "§e.");
        }
        target.sendMessage("§eYou do not need the sheet music item to accept this invite.");
        target.sendMessage("§eUse §f/instrument stop §eto stop after joining.");
    }

    private static List<Player> nearbyPlayers(Player director) {
        List<Player> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player == director) continue;
            if (!isInInviteRange(director, player)) continue;
            players.add(player);
        }
        players.sort(Comparator.comparing(Player::getName, String.CASE_INSENSITIVE_ORDER));
        return players;
    }

    private static boolean isInInviteRange(Player director, Player player) {
        return director.getWorld().equals(player.getWorld())
                && director.getLocation().distanceSquared(player.getLocation()) <= INVITE_RADIUS_SQUARED;
    }

    private static ItemStack playerButton(Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta instanceof SkullMeta skullMeta) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
            skullMeta.setOwningPlayer(offlinePlayer);
            itemMeta = skullMeta;
        }
        Instrument instrument = AllInstruments.GetInstrumentFromItem(player.getInventory().getItemInOffHand());
        Component instrumentLine = instrument == null
                ? Component.text("No offhand instrument", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)
                : Component.text("Offhand: " + instrument.name, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false);
        itemMeta.displayName(Component.text(player.getName(), NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        itemMeta.lore(List.of(
                Component.text("Click to invite", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                instrumentLine
        ));
        item.setItemMeta(itemMeta);
        return item;
    }

    private static ItemStack button(Material material, String name, NamedTextColor color, List<Component> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name, color).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static String trimTitle(String title, int maxLength) {
        if (title.length() <= maxLength) return title;
        return title.substring(0, Math.max(0, maxLength - 3)) + "...";
    }

    private record PendingInvite(UUID directorId, Song song, int expiresAtTick) {
        private boolean isExpired() {
            return Bukkit.getCurrentTick() > expiresAtTick;
        }
    }

    private static final class DirectorHolder implements InventoryHolder {
        private final UUID directorId;
        private final Song song;
        private final Map<Integer, UUID> targetsBySlot = new HashMap<>();
        private Inventory inventory;

        private DirectorHolder(UUID directorId, Song song) {
            this.directorId = directorId;
            this.song = song;
        }

        private UUID directorId() {
            return directorId;
        }

        private Song song() {
            return song;
        }

        private void setTarget(int slot, UUID targetId) {
            targetsBySlot.put(slot, targetId);
        }

        private UUID target(int slot) {
            return targetsBySlot.get(slot);
        }

        private void setInventory(Inventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public @NotNull Inventory getInventory() {
            return inventory;
        }
    }
}
