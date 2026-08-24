package biraw.online.bSInstruments;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.FoodProperties;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import io.papermc.paper.event.player.PlayerArmSwingEvent;
import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.RayTraceResult;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Instrument implements Listener {
    private static final float NOTE_VOLUME = 3.0f;
    private static final int UNKNOWN_CUSTOM_MODEL_DATA = 999;
    private static final int LEFT_CLICK_REPEAT_TICKS = 4;
    private static final int LEFT_AIR_HOLD_CONFIRM_TICKS = 8;
    private static final int LEFT_AIR_REPEAT_WINDOW_TICKS = 10;
    private static final double LEFT_AIR_REACH = 5.0;
    private static final int RIGHT_CLICK_REPEAT_TICKS = 4;
    private static final int MUTED_WARNING_COOLDOWN_TICKS = 60;
    private static final int MIN_SONG_NOTE_ID = 0;
    private static final int MAX_SONG_NOTE_ID = 24;
    private static final int MIN_CUSTOM_SONG_NOTE_ID = -24;
    private static final int MAX_CUSTOM_SONG_NOTE_ID = 48;
    private static final List<Integer> CUSTOM_SONG_SOUND_OFFSETS = List.of(-24, -12, 0, 24);
    private static final List<Note.Tone> NATURAL_NOTES = List.of(Note.Tone.G, Note.Tone.A, Note.Tone.B, Note.Tone.C, Note.Tone.D, Note.Tone.E, Note.Tone.F);
    private static final List<Note.Tone> SHARP_NOTES = List.of(Note.Tone.F, Note.Tone.G, Note.Tone.A, Note.Tone.B, Note.Tone.C, Note.Tone.D, Note.Tone.E);
    private static final List<String> NATURAL_NOTE_COLORS = List.of(
            "§dG", // G - Light Purple
            "§cA", // A - Red
            "§6B", // B - Gold/Orange
            "§eC", // C - Yellow
            "§aD", // D - Green
            "§bE", // E - Aqua
            "§9F"  // F - Blue
    );
    private static final List<String> SHARP_NOTE_COLORS = List.of(
            "§9F", // F - Blue
            "§dG", // G - Light Purple
            "§cA", // A - Red
            "§6B", // B - Gold/Orange
            "§eC", // C - Yellow
            "§aD", // D - Green
            "§bE"  // E - Aqua
    );
    private static final Map<UUID, Integer> LAST_PLAY_TICK = new HashMap<>();
    private static final Map<UUID, Integer> LAST_LEFT_CLICK_TICK = new HashMap<>();
    private static final Map<UUID, Integer> LEFT_AIR_CLICK_CANDIDATE_TICK = new HashMap<>();
    private static final Map<UUID, Integer> LEFT_AIR_REPEAT_UNTIL_TICK = new HashMap<>();
    private static final Map<UUID, Integer> LAST_LEFT_AIR_SIGNAL_TICK = new HashMap<>();
    private static final Map<UUID, Integer> LAST_MUTED_WARNING_TICK = new HashMap<>();
    private static final Map<UUID, BukkitTask> LEFT_AIR_TASKS = new HashMap<>();
    private static final Map<UUID, BukkitTask> RIGHT_CLICK_TASKS = new HashMap<>();

    final String name;
    final String sname;
    final int octave;
    final org.bukkit.Instrument instrument;
    final Material item;
    final String customSoundBase;
    private final String itemKey;
    private final int customModelData;

    public Instrument(String name, org.bukkit.Instrument instrument, int octave, Material item){
        this(name, instrument, octave, item, null);
    }

    public Instrument(String name, org.bukkit.Instrument instrument, int octave, Material item, String customSoundBase){
        this.octave = octave;
        this.instrument = instrument;
        this.name = name;
        this.item = item;
        this.customSoundBase = customSoundBase;
        this.sname = name.replace(' ', '-').toLowerCase(Locale.ROOT);
        this.itemKey = "instrument_"+sname+"_"+octave;
        this.customModelData = getCustomModelData(sname);

        Bukkit.getServer().getPluginManager().registerEvents(this,BSInstruments.getInstance());
    }

    // get the item of the instrument
    public ItemStack getItem(){
        ItemStack give = new ItemStack(item);
        ItemMeta meta = give.getItemMeta();
        meta.displayName(Component.text(name, NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false));
        if (octave > 1) meta.lore(instrumentLore("♪ Extra high note "+octave+" ♪"));
        else if (octave > 0) meta.lore(instrumentLore("♪ High note ♪"));
        else if (octave < 0) meta.lore(instrumentLore("♫ Extra low note "+Math.abs(octave)+" ♫"));
        else meta.lore(instrumentLore("♫ Low note ♫"));
        meta.getPersistentDataContainer().set(
                BSInstruments.NSKEY,
                PersistentDataType.STRING,
                itemKey
        );
        meta.setCustomModelData(customModelData);
        give.setItemMeta(meta);
        addRightClickUseComponents(give);
        return give;
    }

    private List<Component> instrumentLore(String description) {
        return List.of(
                Component.text(description, NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                Component.text("MinearchyInstruments", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)
        );
    }

    @EventHandler
    private void playerPlayEvent(PlayerInteractEvent event){
        if (event.getHand() != EquipmentSlot.HAND && event.getHand() != EquipmentSlot.OFF_HAND) return;
        if (!isThisInstrument(event.getPlayer().getInventory().getItemInOffHand())) return;

        Player plr = event.getPlayer();
        if (!isInstrumentControlMode(plr)) {
            SongPlayer.stop(plr);
            return;
        }

        if (isRightClick(event.getAction())) {
            stopLeftAirRepeat(plr);
            cancelWorldInteractionButAllowUse(event);
            playNote(plr, false);
            if (event.getHand() == EquipmentSlot.OFF_HAND && !SongPlayer.isActive(plr)) startRightClickRepeat(plr);
            return;
        }

        cancelWorldInteraction(event);
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            rememberLeftAirClick(plr);
            if (!shouldSuppressLeftClick(plr)) playNote(plr, true);
        }
    }

    @EventHandler
    private void playerLeftClickEvent(PlayerAnimationEvent event) {
        Player plr = event.getPlayer();
        if (!isThisInstrument(plr.getInventory().getItemInOffHand())) return;
        if (!isInstrumentControlMode(plr)) {
            SongPlayer.stop(plr);
            return;
        }
        if (isLookingAtAir(plr)) {
            handleLeftAirHoldSignal(plr);
            return;
        }
        if (shouldSuppressLeftClick(plr)) return;

        event.setCancelled(true);
        playNote(plr, true);
    }

    @EventHandler
    private void playerAirLeftClickRefreshEvent(PlayerArmSwingEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player plr = event.getPlayer();
        if (!isThisInstrument(plr.getInventory().getItemInOffHand())) return;
        if (!isInstrumentControlMode(plr)) {
            SongPlayer.stop(plr);
            return;
        }
        if (!isLookingAtAir(plr)) return;

        handleLeftAirHoldSignal(plr);
    }

    private void playNote(Player plr, boolean sharp) {
        if (SongPlayer.tryStart(plr, this)) return;

        int currentTick = Bukkit.getCurrentTick();
        if (Objects.equals(LAST_PLAY_TICK.get(plr.getUniqueId()), currentTick)) return;
        LAST_PLAY_TICK.put(plr.getUniqueId(), currentTick);
        warnIfMuted(plr, currentTick);

        float pitch = plr.getPitch();

        pitch+=90; pitch /= 180; pitch *=NATURAL_NOTES.size()-1; // convert player pitch to a note
        int noteIndex = Math.max(0, Math.min(NATURAL_NOTES.size() - 1, Math.round(pitch)));

        Note note;
        if (plr.isSneaking()) {
            Note.Tone tone = NATURAL_NOTES.get(noteIndex);
            note = Note.flat(getPlayableBukkitOctave(), tone);
            plr.sendActionBar(NATURAL_NOTE_COLORS.get(noteIndex) + "♭");
        } else if (sharp) {
            Note.Tone tone = SHARP_NOTES.get(noteIndex);
            note = Note.sharp(getPlayableBukkitOctave(), tone);
            plr.sendActionBar(SHARP_NOTE_COLORS.get(noteIndex) + "#");
        } else {
            Note.Tone tone = NATURAL_NOTES.get(noteIndex);
            note = Note.natural(getPlayableBukkitOctave(), tone);
            plr.sendActionBar(NATURAL_NOTE_COLORS.get(noteIndex));
        }

        playForListeners(plr, note);
    }

    void playSongNote(Player plr, int songNoteId) {
        warnIfMuted(plr, Bukkit.getCurrentTick());
        playSongForListeners(plr, songNoteId);
    }

    @EventHandler
    private void playerDamageBlockEvent(BlockDamageEvent event) {
        if (isThisInstrument(event.getPlayer().getInventory().getItemInOffHand())) {
            if (!isInstrumentControlMode(event.getPlayer())) {
                SongPlayer.stop(event.getPlayer());
                return;
            }
            event.setCancelled(true);
            resetBlockDamage(event.getPlayer(), event.getBlock());
        }
    }

    @EventHandler
    private void playerBreakBlockEvent(BlockBreakEvent event) {
        if (isThisInstrument(event.getPlayer().getInventory().getItemInOffHand())) {
            if (!isInstrumentControlMode(event.getPlayer())) {
                SongPlayer.stop(event.getPlayer());
                return;
            }
            event.setCancelled(true);
            resetBlockDamage(event.getPlayer(), event.getBlock());
        }
    }

    @EventHandler
    private void playerInteractEntityEvent(PlayerInteractEntityEvent event) {
        if (isThisInstrument(event.getPlayer().getInventory().getItemInOffHand())) {
            if (!isInstrumentControlMode(event.getPlayer())) {
                SongPlayer.stop(event.getPlayer());
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void playerDamageEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player
                && isThisInstrument(player.getInventory().getItemInOffHand())) {
            if (!isInstrumentControlMode(player)) {
                SongPlayer.stop(player);
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void playerConsumeEvent(PlayerItemConsumeEvent event) {
        if (!isThisInstrument(event.getItem())) return;

        event.setCancelled(true);
    }

    @EventHandler
    private void playerStopUsingItemEvent(PlayerStopUsingItemEvent event) {
        if (isThisInstrument(event.getItem())) {
            stopRightClickRepeat(event.getPlayer());
        }
    }

    private void addRightClickUseComponents(ItemStack itemStack) {
        itemStack.setData(
                DataComponentTypes.CONSUMABLE,
                Consumable.consumable()
                        .consumeSeconds(72000.0f)
                        .animation(ItemUseAnimation.NONE)
                        .sound(Key.key("minecraft:intentionally_empty"))
                        .hasConsumeParticles(false)
        );
        itemStack.setData(
                DataComponentTypes.FOOD,
                FoodProperties.food()
                        .nutrition(0)
                        .saturation(0.0f)
                        .canAlwaysEat(true)
        );
    }

    private boolean isRightClick(Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }

    private boolean isInstrumentControlMode(Player player) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        return mainHand == null || mainHand.getType().isAir() || AllSongs.getSongFromItem(mainHand) != null;
    }

    boolean isThisInstrument(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() != item) return false;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return false;
        if (!meta.hasCustomModelData() || meta.getCustomModelData() != customModelData) return false;
        return Objects.equals(meta.getPersistentDataContainer().get(
                BSInstruments.NSKEY,
                PersistentDataType.STRING),
                itemKey
        );
    }

    static void clearPlayerState(Player player) {
        UUID playerId = player.getUniqueId();
        LAST_PLAY_TICK.remove(playerId);
        LAST_LEFT_CLICK_TICK.remove(playerId);
        LEFT_AIR_CLICK_CANDIDATE_TICK.remove(playerId);
        LEFT_AIR_REPEAT_UNTIL_TICK.remove(playerId);
        LAST_LEFT_AIR_SIGNAL_TICK.remove(playerId);
        LAST_MUTED_WARNING_TICK.remove(playerId);

        BukkitTask leftAirTask = LEFT_AIR_TASKS.remove(playerId);
        if (leftAirTask != null) leftAirTask.cancel();

        BukkitTask rightClickTask = RIGHT_CLICK_TASKS.remove(playerId);
        if (rightClickTask != null) rightClickTask.cancel();
    }

    private void cancelWorldInteraction(PlayerInteractEvent event) {
        event.setCancelled(true);
        event.setUseInteractedBlock(Event.Result.DENY);
        event.setUseItemInHand(Event.Result.DENY);
    }

    private void cancelWorldInteractionButAllowUse(PlayerInteractEvent event) {
        event.setUseInteractedBlock(Event.Result.DENY);
        event.setUseItemInHand(event.getHand() == EquipmentSlot.OFF_HAND ? Event.Result.ALLOW : Event.Result.DENY);
    }

    private boolean shouldSuppressLeftClick(Player player) {
        int currentTick = Bukkit.getCurrentTick();
        Integer lastTick = LAST_LEFT_CLICK_TICK.get(player.getUniqueId());
        if (lastTick != null && currentTick - lastTick < LEFT_CLICK_REPEAT_TICKS) return true;

        LAST_LEFT_CLICK_TICK.put(player.getUniqueId(), currentTick);
        return false;
    }

    private void resetBlockDamage(Player player, org.bukkit.block.Block block) {
        stopLeftAirRepeat(player);
        player.sendBlockDamage(block.getLocation(), 0.0f);
        Bukkit.getScheduler().runTask(BSInstruments.getInstance(), () -> {
            player.sendBlockDamage(block.getLocation(), 0.0f);
            player.sendBlockChange(block.getLocation(), block.getBlockData());
        });
    }

    private void startRightClickRepeat(Player player) {
        UUID playerId = player.getUniqueId();
        if (RIGHT_CLICK_TASKS.containsKey(playerId)) return;

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                BSInstruments.getInstance(),
                () -> {
                    if (!player.isOnline()
                            || !isThisInstrument(player.getInventory().getItemInOffHand())) {
                        stopRightClickRepeat(player);
                        return;
                    }

                    playNote(player, false);
                },
                RIGHT_CLICK_REPEAT_TICKS,
                RIGHT_CLICK_REPEAT_TICKS
        );
        RIGHT_CLICK_TASKS.put(playerId, task);
    }

    private void stopRightClickRepeat(Player player) {
        BukkitTask task = RIGHT_CLICK_TASKS.remove(player.getUniqueId());
        if (task != null) task.cancel();
    }

    private void rememberLeftAirClick(Player player) {
        UUID playerId = player.getUniqueId();
        int currentTick = Bukkit.getCurrentTick();
        if (LEFT_AIR_TASKS.containsKey(playerId)) {
            refreshLeftAirRepeat(player);
            return;
        }

        LEFT_AIR_CLICK_CANDIDATE_TICK.put(playerId, currentTick);
    }

    private void handleLeftAirHoldSignal(Player player) {
        UUID playerId = player.getUniqueId();
        int currentTick = Bukkit.getCurrentTick();
        if (Objects.equals(LAST_LEFT_AIR_SIGNAL_TICK.get(playerId), currentTick)) return;
        LAST_LEFT_AIR_SIGNAL_TICK.put(playerId, currentTick);

        if (LEFT_AIR_TASKS.containsKey(playerId)) {
            refreshLeftAirRepeat(player);
            return;
        }

        Integer candidateTick = LEFT_AIR_CLICK_CANDIDATE_TICK.get(playerId);
        if (candidateTick == null) return;
        if (currentTick <= candidateTick + 1) return;
        if (currentTick - candidateTick > LEFT_AIR_HOLD_CONFIRM_TICKS) {
            LEFT_AIR_CLICK_CANDIDATE_TICK.remove(playerId);
            return;
        }

        refreshLeftAirRepeat(player);
        startLeftAirRepeat(player);
    }

    private void refreshLeftAirRepeat(Player player) {
        LEFT_AIR_REPEAT_UNTIL_TICK.put(player.getUniqueId(), Bukkit.getCurrentTick() + LEFT_AIR_REPEAT_WINDOW_TICKS);
    }

    private void startLeftAirRepeat(Player player) {
        UUID playerId = player.getUniqueId();
        if (LEFT_AIR_TASKS.containsKey(playerId)) return;

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                BSInstruments.getInstance(),
                () -> {
                    Integer repeatUntilTick = LEFT_AIR_REPEAT_UNTIL_TICK.get(playerId);
                    if (!player.isOnline()
                            || repeatUntilTick == null
                            || Bukkit.getCurrentTick() > repeatUntilTick
                            || !isThisInstrument(player.getInventory().getItemInOffHand())) {
                        stopLeftAirRepeat(player);
                        return;
                    }

                    playNote(player, true);
                },
                LEFT_CLICK_REPEAT_TICKS,
                LEFT_CLICK_REPEAT_TICKS
        );
        LEFT_AIR_TASKS.put(playerId, task);
    }

    private void stopLeftAirRepeat(Player player) {
        LEFT_AIR_CLICK_CANDIDATE_TICK.remove(player.getUniqueId());
        LEFT_AIR_REPEAT_UNTIL_TICK.remove(player.getUniqueId());
        BukkitTask task = LEFT_AIR_TASKS.remove(player.getUniqueId());
        if (task != null) task.cancel();
    }

    private boolean isLookingAtAir(Player player) {
        RayTraceResult blockResult = player.rayTraceBlocks(LEFT_AIR_REACH);
        if (blockResult != null) return false;

        RayTraceResult entityResult = player.getWorld().rayTraceEntities(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                LEFT_AIR_REACH,
                entity -> entity != player
        );
        return entityResult == null;
    }

    private void warnIfMuted(Player player, int currentTick) {
        if (!MuteManager.isMuted(player)) return;

        Integer lastWarningTick = LAST_MUTED_WARNING_TICK.get(player.getUniqueId());
        if (lastWarningTick != null && currentTick - lastWarningTick < MUTED_WARNING_COOLDOWN_TICKS) return;

        LAST_MUTED_WARNING_TICK.put(player.getUniqueId(), currentTick);
        player.sendMessage("§cYou have instruments muted. Use §e/instrument mute §cto hear them again.");
    }

    private int getPlayableBukkitOctave() {
        if (octave >= 0 && octave <= 1) return octave;
        return 0;
    }

    private boolean usesCustomExtraOctaveSound() {
        return customSoundBase != null && octave != 0 && octave != 1;
    }

    private String getCustomExtraOctaveSound() {
        return customSoundBase+"_"+octave;
    }

    private SoundNote getSongSoundNote(int songNoteId) {
        int tunedNoteId = songNoteId + (octave * 12);
        if (customSoundBase != null) {
            int supportedNoteId = transposeIntoCustomSongRange(tunedNoteId);
            int soundOffset = closestCustomSongSoundOffset(supportedNoteId);
            return new SoundNote(customSongSound(soundOffset), supportedNoteId - soundOffset);
        }

        return new SoundNote("block.note_block.harp", transposeIntoVanillaSongRange(tunedNoteId));
    }

    private int transposeIntoCustomSongRange(int noteId) {
        while (noteId < MIN_CUSTOM_SONG_NOTE_ID) noteId += 12;
        while (noteId > MAX_CUSTOM_SONG_NOTE_ID) noteId -= 12;
        return noteId;
    }

    private int transposeIntoVanillaSongRange(int noteId) {
        while (noteId < MIN_SONG_NOTE_ID) noteId += 12;
        while (noteId > MAX_SONG_NOTE_ID) noteId -= 12;
        return noteId;
    }

    private int closestCustomSongSoundOffset(int noteId) {
        int preferredOffset = preferredCustomSongSoundOffset();
        int closestOffset = 0;
        int closestDistance = Integer.MAX_VALUE;

        for (int soundOffset : CUSTOM_SONG_SOUND_OFFSETS) {
            int pitchedNoteId = noteId - soundOffset;
            if (pitchedNoteId < MIN_SONG_NOTE_ID || pitchedNoteId > MAX_SONG_NOTE_ID) continue;

            int distance = Math.abs(soundOffset - preferredOffset);
            if (distance < closestDistance) {
                closestOffset = soundOffset;
                closestDistance = distance;
            }
        }

        return closestOffset;
    }

    private int preferredCustomSongSoundOffset() {
        if (octave <= -2) return -24;
        if (octave == -1) return -12;
        if (octave >= 2) return 24;
        return 0;
    }

    private String customSongSound(int soundOffset) {
        return switch (soundOffset) {
            case -24 -> customSoundBase + "_-2";
            case -12 -> customSoundBase + "_-1";
            case 24 -> customSoundBase + "_2";
            default -> customSoundBase;
        };
    }

    private void playSongForListeners(Player player, int songNoteId) {
        SoundNote soundNote = getSongSoundNote(songNoteId);
        for (Player listener : Bukkit.getOnlinePlayers()) {
            if (MuteManager.isMuted(listener)) continue;

            listener.playSound(player.getLocation(), soundNote.sound(), SoundCategory.RECORDS, NOTE_VOLUME, soundNote.pitch());
        }
    }

    private record SoundNote(String sound, int noteId) {
        private float pitch() {
            return (float) Math.pow(2.0, (noteId - 12) / 12.0);
        }
    }

    private void playForListeners(Player player, Note note) {
        for (Player listener : Bukkit.getOnlinePlayers()) {
            if (MuteManager.isMuted(listener)) continue;

            if (usesCustomExtraOctaveSound()) {
                listener.playSound(player.getLocation(), getCustomExtraOctaveSound(), SoundCategory.RECORDS, NOTE_VOLUME, note.getPitch());
            } else {
                listener.playNote(player.getLocation(), instrument, note);
            }
        }
    }

    private static int getCustomModelData(String sname) {
        return switch (sname) {
            case "bass-drum" -> 1;
            case "snare-drum" -> 2;
            case "sticks" -> 3;
            case "bass-guitar" -> 4;
            case "flute" -> 5;
            case "bell" -> 6;
            case "guitar" -> 7;
            case "chime" -> 8;
            case "xylophone" -> 9;
            case "iron-xylophone" -> 10;
            case "cow-bell" -> 11;
            case "didgeridoo" -> 12;
            case "bit" -> 13;
            case "banjo" -> 14;
            case "pling" -> 15;
            case "piano" -> 16;
            case "trumpet" -> 17;
            case "exposed-trumpet" -> 18;
            case "weathered-trumpet" -> 19;
            case "oxidized-trumpet" -> 20;
            default -> UNKNOWN_CUSTOM_MODEL_DATA;
        };
    }
}
