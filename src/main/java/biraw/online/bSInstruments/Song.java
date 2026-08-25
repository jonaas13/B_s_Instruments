package biraw.online.bSInstruments;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Song {
    static final int CUSTOM_MODEL_DATA = 101;

    private final String id;
    private final String title;
    private final List<SongLayer> layers;
    private final int durationTicks;

    public Song(String title, List<SongLayer> layers, int durationTicks) {
        this.id = toId(title);
        this.title = title;
        this.layers = List.copyOf(layers);
        this.durationTicks = Math.max(1, durationTicks);
        if (this.layers.isEmpty()) throw new IllegalArgumentException("Song must have at least one layer: " + title);
    }

    public String id() {
        return id;
    }

    public String lookupName() {
        return id;
    }

    public String title() {
        return title;
    }

    public SongLayer layer(int layer) {
        return layers.get(Math.floorMod(layer, layers.size()));
    }

    public List<SongNoteEvent> eventsAtTick(int layer, int tick) {
        return layer(layer).eventsAtTick(tick);
    }

    public List<SongNoteEvent> eventsBetweenTicks(int layer, int fromExclusive, int toInclusive) {
        SongLayer songLayer = layer(layer);
        if (fromExclusive < toInclusive) {
            return songLayer.eventsBetweenTicks(fromExclusive + 1, toInclusive);
        }
        if (fromExclusive == toInclusive) return songLayer.eventsAtTick(toInclusive);

        List<SongNoteEvent> wrappedEvents = new ArrayList<>();
        wrappedEvents.addAll(songLayer.eventsBetweenTicks(fromExclusive + 1, durationTicks - 1));
        wrappedEvents.addAll(songLayer.eventsBetweenTicks(0, toInclusive));
        return List.copyOf(wrappedEvents);
    }

    public int layerCount() {
        return layers.size();
    }

    public int durationTicks() {
        return durationTicks;
    }

    public int totalEventCount() {
        int count = 0;
        for (SongLayer layer : layers) {
            count += layer.events().size();
        }
        return count;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Sheet Music: ", NamedTextColor.LIGHT_PURPLE)
                .append(Component.text(title, NamedTextColor.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        meta.lore(List.of(
                Component.text("Hold in main hand and play an instrument", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                Component.text("MinearchyInstruments", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)
        ));
        CustomModelDataComponent customModelData = meta.getCustomModelDataComponent();
        customModelData.setFloats(List.of((float) CUSTOM_MODEL_DATA));
        meta.setCustomModelDataComponent(customModelData);
        meta.getPersistentDataContainer().set(BSInstruments.NSKEY, PersistentDataType.STRING, "song_" + id);
        item.setItemMeta(meta);
        return item;
    }

    static String toId(String title) {
        return title
                .toLowerCase(Locale.ROOT)
                .replace("'", "")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }

    public static final class SongLayer {
        private final String name;
        private final String preferredInstrumentName;
        private final List<SongNoteEvent> events;
        private final NavigableMap<Integer, List<SongNoteEvent>> eventsByTick;

        public SongLayer(String name, String preferredInstrumentName, List<SongNoteEvent> events) {
            this.name = name == null || name.isBlank() ? "Layer" : name;
            this.preferredInstrumentName = preferredInstrumentName == null || preferredInstrumentName.isBlank()
                    ? "piano"
                    : preferredInstrumentName;
            this.events = sorted(events);
            this.eventsByTick = indexByTick(this.events);
        }

        public String name() {
            return name;
        }

        public String preferredInstrumentName() {
            return preferredInstrumentName;
        }

        public List<SongNoteEvent> events() {
            return events;
        }

        public List<SongNoteEvent> eventsAtTick(int tick) {
            List<SongNoteEvent> matching = eventsByTick.get(tick);
            return matching == null ? List.of() : matching;
        }

        public List<SongNoteEvent> eventsBetweenTicks(int fromInclusive, int toInclusive) {
            if (fromInclusive > toInclusive) return List.of();

            List<SongNoteEvent> matching = new ArrayList<>();
            for (List<SongNoteEvent> tickEvents : eventsByTick.subMap(fromInclusive, true, toInclusive, true).values()) {
                matching.addAll(tickEvents);
            }
            return List.copyOf(matching);
        }

        private static NavigableMap<Integer, List<SongNoteEvent>> indexByTick(List<SongNoteEvent> events) {
            Map<Integer, List<SongNoteEvent>> eventsByTick = new HashMap<>();
            for (SongNoteEvent event : events) {
                eventsByTick.computeIfAbsent(event.tick(), ignored -> new ArrayList<>()).add(event);
            }
            NavigableMap<Integer, List<SongNoteEvent>> immutable = new TreeMap<>();
            for (Map.Entry<Integer, List<SongNoteEvent>> entry : eventsByTick.entrySet()) {
                immutable.put(entry.getKey(), List.copyOf(entry.getValue()));
            }
            return java.util.Collections.unmodifiableNavigableMap(immutable);
        }

        private static List<SongNoteEvent> sorted(List<SongNoteEvent> events) {
            if (events == null) return List.of();
            List<SongNoteEvent> sortedEvents = new ArrayList<>(events);
            sortedEvents.sort(Comparator.comparingInt(SongNoteEvent::tick)
                    .thenComparing(Comparator.comparingInt(SongNoteEvent::velocity).reversed())
                    .thenComparingInt(SongNoteEvent::midiNote));
            return List.copyOf(sortedEvents);
        }
    }

    public record SongNoteEvent(int tick, int midiNote, int velocity, double pitchOffsetSemitones) {
        public SongNoteEvent(int tick, int midiNote, int velocity) {
            this(tick, midiNote, velocity, 0.0);
        }

        public SongNoteEvent {
            tick = Math.max(0, tick);
            midiNote = Math.max(0, Math.min(127, midiNote));
            velocity = Math.max(1, Math.min(127, velocity));
            pitchOffsetSemitones = Math.max(-2.0, Math.min(2.0, pitchOffsetSemitones));
        }
    }
}
