package biraw.online.bSInstruments;

import biraw.online.bSInstruments.Obtaining.ItemDelivery;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class SongBookMenu implements Listener {
    private static final int INVENTORY_SIZE = 54;
    private static final int SONG_SLOTS = 45;
    private static final int PREVIOUS_SLOT = 45;
    private static final int COLLECTION_SLOT = 49;
    private static final int NEXT_SLOT = 53;

    public static void open(Player player) {
        open(player, 0);
    }

    private static void open(Player player, int page) {
        List<Song> songs = AllSongs.getUnlockedSongs(player);
        int maxPage = maxPage(songs.size());
        int clampedPage = Math.max(0, Math.min(page, maxPage));
        SongBookHolder holder = new SongBookHolder(clampedPage);
        Inventory inventory = Bukkit.createInventory(
                holder,
                INVENTORY_SIZE,
                Component.text("Song Book " + (clampedPage + 1) + "/" + (maxPage + 1), NamedTextColor.DARK_PURPLE)
        );
        holder.setInventory(inventory);

        if (songs.isEmpty()) {
            inventory.setItem(22, button(Material.BARRIER, "No songs unlocked", NamedTextColor.RED, List.of(
                    Component.text("Use sheet music to add songs here.", NamedTextColor.GRAY)
                            .decoration(TextDecoration.ITALIC, false)
            )));
        } else {
            int startIndex = clampedPage * SONG_SLOTS;
            int endIndex = Math.min(startIndex + SONG_SLOTS, songs.size());
            for (int songIndex = startIndex; songIndex < endIndex; songIndex++) {
                inventory.setItem(songIndex - startIndex, songBookItem(songs.get(songIndex)));
            }
        }

        inventory.setItem(PREVIOUS_SLOT, button(
                Material.ARROW,
                "Previous page",
                clampedPage > 0 ? NamedTextColor.GREEN : NamedTextColor.DARK_GRAY,
                List.of()
        ));
        inventory.setItem(COLLECTION_SLOT, button(Material.WRITABLE_BOOK, "Unlocked songs", NamedTextColor.GOLD, List.of(
                Component.text(songs.size() + " / " + AllSongs.getAllSongs().size() + " collected.", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
        )));
        inventory.setItem(NEXT_SLOT, button(
                Material.ARROW,
                "Next page",
                clampedPage < maxPage ? NamedTextColor.GREEN : NamedTextColor.DARK_GRAY,
                List.of()
        ));

        player.openInventory(inventory);
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();
        if (!(inventory.getHolder() instanceof SongBookHolder holder)) return;

        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getRawSlot() < 0 || event.getRawSlot() >= inventory.getSize()) return;

        int slot = event.getRawSlot();
        List<Song> songs = AllSongs.getUnlockedSongs(player);
        if (slot < SONG_SLOTS) {
            int songIndex = holder.page() * SONG_SLOTS + slot;
            if (songIndex >= songs.size()) return;

            if (!ItemDelivery.giveToInventory(player, songs.get(songIndex).getItem())) {
                player.sendMessage("§cInventory full. Sheet music was not added.");
            }
            return;
        }

        if (slot == PREVIOUS_SLOT && holder.page() > 0) {
            open(player, holder.page() - 1);
            return;
        }

        if (slot == NEXT_SLOT && holder.page() < maxPage(songs.size())) {
            open(player, holder.page() + 1);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onSheetMusicInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!isRightClick(event.getAction())) return;

        Player player = event.getPlayer();
        if (AllInstruments.GetInstrumentFromItem(player.getInventory().getItemInOffHand()) != null) return;

        Song song = AllSongs.getSongFromItem(player.getInventory().getItemInMainHand());
        if (song == null) return;

        event.setCancelled(true);
        if (!AllSongs.unlockSong(player, song)) {
            player.sendMessage("§e" + song.title() + " is already in your song book.");
            return;
        }

        consumeOneMainHandItem(player);
        player.sendMessage("§aAdded " + song.title() + " to your song book.");
    }

    private static int maxPage(int itemCount) {
        if (itemCount <= 0) return 0;
        return (itemCount - 1) / SONG_SLOTS;
    }

    private static boolean isRightClick(Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }

    private static void consumeOneMainHandItem(Player player) {
        if (player.getGameMode() == GameMode.CREATIVE) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getAmount() <= 1) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            return;
        }

        item.setAmount(item.getAmount() - 1);
    }

    private static ItemStack songBookItem(Song song) {
        ItemStack item = song.getItem();
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = new ArrayList<>();
        if (meta.lore() != null) lore.addAll(meta.lore());
        lore.add(Component.text("Click to take sheet music", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        item.setItemMeta(meta);
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

    private static final class SongBookHolder implements InventoryHolder {
        private final int page;
        private Inventory inventory;

        private SongBookHolder(int page) {
            this.page = page;
        }

        private int page() {
            return page;
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
