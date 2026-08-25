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

import java.util.ArrayList;
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
    private static final int MIDI_NOTE_BLOCK_F_SHARP_3 = 54;
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
        meta.lore(instrumentLore(octaveDescription()));
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

    private String octaveDescription() {
        return switch (octave) {
            case -2 -> "♫ Extra Low ♫";
            case -1 -> "♫ Low ♫";
            case 1 -> "♪ High ♪";
            case 2 -> "♪ Extra High ♪";
            default -> "♪ Normal ♪";
        };
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

    SongPlaybackTuning createSongPlaybackTuning() {
        if (customSoundBase == null) return new SongPlaybackTuning(0);
        return new SongPlaybackTuning(customSoundOffsetForOctave());
    }

    void playSongNotes(Player player, List<Song.SongNoteEvent> events, SongPlaybackTuning tuning) {
        if (events.isEmpty()) return;

        warnIfMuted(player, Bukkit.getCurrentTick());
        List<Player> listeners = getSongListeners(player);
        if (listeners.isEmpty()) return;

        for (Song.SongNoteEvent event : events) {
            SoundNote soundNote = getSongSoundNote(event.midiNote(), event.velocity(), event.pitchOffsetSemitones(), tuning);
            for (Player listener : listeners) {
                listener.playSound(player.getLocation(), soundNote.sound(), SoundCategory.RECORDS, soundNote.volume(), soundNote.pitch());
            }
        }
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
        if (usesCustomOctaveSound()) return 0;
        if (octave >= 0 && octave <= 1) return octave;
        return 0;
    }

    private boolean usesCustomOctaveSound() {
        return customSoundBase != null && octave != 0;
    }

    private String getCustomOctaveSound() {
        return customSongSound(customSoundOffsetForOctave());
    }

    boolean matchesSongLayer(Song.SongLayer layer) {
        return songLayerMatchScore(layer) > 0;
    }

    int songLayerMatchScore(Song.SongLayer layer) {
        String preferredInstrumentName = layer.preferredInstrumentName();
        if (preferredInstrumentName.equals("percussion")) {
            return sname.equals("bass-drum")
                    || sname.equals("snare-drum")
                    || sname.equals("sticks")
                    || sname.equals("cow-bell")
                    ? 100
                    : 0;
        }
        if (preferredInstrumentName.equals(sname)) return 100;
        if (sname.startsWith("exposed-trumpet") || sname.startsWith("weathered-trumpet") || sname.startsWith("oxidized-trumpet")) {
            return preferredInstrumentName.equals("trumpet") ? 90 : 0;
        }
        if (isKeyboardLike(preferredInstrumentName) && isKeyboardLike(sname)) return 60;
        if (isMalletLike(preferredInstrumentName) && isMalletLike(sname)) return 60;
        if (isWindLike(preferredInstrumentName) && isWindLike(sname)) return 60;
        return 0;
    }

    boolean canUseSongLayerAsFallback(Song.SongLayer layer) {
        String preferredInstrumentName = layer.preferredInstrumentName();
        if (preferredInstrumentName.equals("percussion")) return isPercussionLike();
        if (preferredInstrumentName.equals("bass-guitar") || preferredInstrumentName.equals("didgeridoo")) return isBassLike();
        return !isPercussionLike() && !isBassLike();
    }

    private boolean isPercussionLike() {
        return sname.equals("bass-drum")
                || sname.equals("snare-drum")
                || sname.equals("sticks")
                || sname.equals("cow-bell");
    }

    private boolean isBassLike() {
        return sname.equals("bass-guitar")
                || sname.equals("didgeridoo");
    }

    private boolean isKeyboardLike(String instrumentName) {
        return instrumentName.equals("piano")
                || instrumentName.equals("pling")
                || instrumentName.equals("bit");
    }

    private boolean isMalletLike(String instrumentName) {
        return instrumentName.equals("bell")
                || instrumentName.equals("chime")
                || instrumentName.equals("xylophone")
                || instrumentName.equals("iron-xylophone");
    }

    private boolean isWindLike(String instrumentName) {
        return instrumentName.equals("flute")
                || instrumentName.equals("trumpet")
                || instrumentName.equals("exposed-trumpet")
                || instrumentName.equals("weathered-trumpet")
                || instrumentName.equals("oxidized-trumpet");
    }

    private int tunedSongNoteId(int midiNote) {
        return (midiNote - MIDI_NOTE_BLOCK_F_SHARP_3)
                + (octave * 12)
                + BSInstruments.getSongPitchOffsetSemitones();
    }

    private SoundNote getSongSoundNote(int midiNote, int velocity, double pitchOffsetSemitones, SongPlaybackTuning tuning) {
        int tunedNoteId = tunedSongNoteId(midiNote);
        if (customSoundBase != null) {
            return new SoundNote(
                    customSongSound(tuning.soundOffset()),
                    transposeIntoRangePreservingTone(tunedNoteId - tuning.soundOffset(), MIN_SONG_NOTE_ID, MAX_SONG_NOTE_ID),
                    velocity,
                    pitchOffsetSemitones
            );
        }

        return new SoundNote(
                "block.note_block.harp",
                transposeIntoRangePreservingTone(tunedNoteId, MIN_SONG_NOTE_ID, MAX_SONG_NOTE_ID),
                velocity,
                pitchOffsetSemitones
        );
    }

    private int transposeIntoRangePreservingTone(int noteId, int minNoteId, int maxNoteId) {
        while (noteId < minNoteId) noteId += 12;
        while (noteId > maxNoteId) noteId -= 12;
        return noteId;
    }

    private int customSoundOffsetForOctave() {
        if (octave <= -2) return -24;
        if (octave == -1) return -12;
        if (octave == 1) return 12;
        if (octave >= 2) return 24;
        return 0;
    }

    private String customSongSound(int soundOffset) {
        return switch (soundOffset) {
            case -24 -> customSoundBase + "_-2";
            case -12 -> customSoundBase + "_-1";
            case 12 -> customSoundBase + "_1";
            case 24 -> customSoundBase + "_2";
            default -> customSoundBase;
        };
    }

    record SongPlaybackTuning(int soundOffset) {
    }

    private List<Player> getSongListeners(Player player) {
        List<Player> listeners = new ArrayList<>();
        for (Player listener : Bukkit.getOnlinePlayers()) {
            if (MuteManager.isMuted(listener)) continue;
            if (!listener.getWorld().equals(player.getWorld())) continue;
            if (listener.getLocation().distanceSquared(player.getLocation()) > BSInstruments.getSongHearingRadiusSquared()) continue;
            listeners.add(listener);
        }
        return listeners;
    }

    private record SoundNote(String sound, int noteId, int velocity, double pitchOffsetSemitones) {
        private float volume() {
            return NOTE_VOLUME * Math.max(0.35f, Math.min(1.0f, velocity / 96.0f));
        }

        private float pitch() {
            return (float) Math.pow(2.0, ((noteId + pitchOffsetSemitones) - 12) / 12.0);
        }
    }

    private void playForListeners(Player player, Note note) {
        for (Player listener : Bukkit.getOnlinePlayers()) {
            if (MuteManager.isMuted(listener)) continue;

            if (usesCustomOctaveSound()) {
                listener.playSound(player.getLocation(), getCustomOctaveSound(), SoundCategory.RECORDS, NOTE_VOLUME, note.getPitch());
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
