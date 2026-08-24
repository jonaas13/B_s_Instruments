package biraw.online.bSInstruments;

import biraw.online.bSInstruments.Obtaining.ItemDelivery;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public final class AllSongs {
    private static final Material SONG_MATERIAL = Material.PAPER;
    private static final String SONG_KEY_PREFIX = "song_";
    private static final float SONG_CUSTOM_MODEL_DATA = (float) Song.CUSTOM_MODEL_DATA;

    private static final List<SongSeed> BASE_SONG_SEEDS = List.of(
            new SongSeed(
                    "Twinkle Twinkle Little Star",
                    "Folk",
                    phrase(
                            "C4:2 C4:2 G4:2 G4:2 A4:2 A4:2 G4:4",
                            "F4:2 F4:2 E4:2 E4:2 D4:2 D4:2 C4:4",
                            "G4:2 G4:2 F4:2 F4:2 E4:2 E4:2 D4:4",
                            "G4:2 G4:2 F4:2 F4:2 E4:2 E4:2 D4:4",
                            "C4:2 C4:2 G4:2 G4:2 A4:2 A4:2 G4:4",
                            "F4:2 F4:2 E4:2 E4:2 D4:2 D4:2 C4:4"
                    ),
                    List.of(
                            phrase(
                                    "E3:2 E3:2 E4:2 E4:2 F4:2 F4:2 E4:4",
                                    "D4:2 D4:2 C4:2 C4:2 B3:2 B3:2 G3:4",
                                    "E4:2 E4:2 D4:2 D4:2 C4:2 C4:2 B3:4",
                                    "E4:2 E4:2 D4:2 D4:2 C4:2 C4:2 B3:4",
                                    "E3:2 E3:2 E4:2 E4:2 F4:2 F4:2 E4:4",
                                    "D4:2 D4:2 C4:2 C4:2 B3:2 B3:2 G3:4"
                            ),
                            phrase(
                                    "C3:4 G3:4 A3:4 G3:4",
                                    "F3:4 C3:4 G3:4 C3:4",
                                    "G3:4 D3:4 C3:4 G3:4",
                                    "G3:4 D3:4 C3:4 G3:4",
                                    "C3:4 G3:4 A3:4 G3:4",
                                    "F3:4 C3:4 G3:4 C3:4"
                            ),
                            phrase(
                                    "R:4 C5:4 C5:4 B4:4",
                                    "A4:4 G4:4 F4:4 E4:4",
                                    "R:4 B4:4 A4:4 G4:4",
                                    "R:4 B4:4 A4:4 G4:4",
                                    "R:4 C5:4 C5:4 B4:4",
                                    "A4:4 G4:4 F4:4 E4:4"
                            ),
                            phrase(
                                    "R:8 C4:2 E4:2 G4:4",
                                    "R:8 F4:2 E4:2 C4:4",
                                    "R:8 G4:2 F4:2 D4:4",
                                    "R:8 G4:2 F4:2 D4:4",
                                    "R:8 C4:2 E4:2 G4:4",
                                    "R:8 F4:2 E4:2 C4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Ode to Joy",
                    "Classical",
                    phrase(
                            "E4:2 E4:2 F4:2 G4:2 G4:2 F4:2 E4:2 D4:2 C4:2 C4:2 D4:2 E4:2 E4:3 D4:1 D4:4",
                            "E4:2 E4:2 F4:2 G4:2 G4:2 F4:2 E4:2 D4:2 C4:2 C4:2 D4:2 E4:2 D4:3 C4:1 C4:4"
                    ),
                    List.of(
                            phrase(
                                    "C4:2 C4:2 D4:2 E4:2 E4:2 D4:2 C4:2 B3:2 A3:2 A3:2 B3:2 C4:2 C4:3 B3:1 B3:4",
                                    "C4:2 C4:2 D4:2 E4:2 E4:2 D4:2 C4:2 B3:2 A3:2 A3:2 B3:2 C4:2 B3:3 A3:1 A3:4"
                            ),
                            phrase(
                                    "C3:4 G3:4 C3:4 G3:4 C3:4 G3:4 C3:4 G3:4",
                                    "C3:4 G3:4 C3:4 G3:4 C3:4 G3:4 C3:4 C3:4"
                            ),
                            phrase(
                                    "R:4 G4:2 G4:2 A4:2 B4:2 B4:2 A4:2 G4:2 F4:2 E4:2 E4:2 F4:2 G4:2 G4:3 F4:1 F4:4",
                                    "G4:2 G4:2 A4:2 B4:2 B4:2 A4:2 G4:2 F4:2 E4:2 E4:2 F4:2 G4:2 F4:3 E4:1 E4:4"
                            ),
                            phrase(
                                    "R:8 G3:4 R:4 E3:4 R:8 G3:4 R:4 C4:4",
                                    "R:8 G3:4 R:4 E3:4 R:8 G3:4 R:4 C4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Mary Had a Little Lamb",
                    "Folk",
                    phrase(
                            "E4:2 D4:2 C4:2 D4:2 E4:2 E4:2 E4:4",
                            "D4:2 D4:2 D4:4 E4:2 G4:2 G4:4",
                            "E4:2 D4:2 C4:2 D4:2 E4:2 E4:2 E4:2 E4:2",
                            "D4:2 D4:2 E4:2 D4:2 C4:8"
                    ),
                    List.of(
                            phrase(
                                    "C4:2 B3:2 A3:2 B3:2 C4:2 C4:2 C4:4",
                                    "B3:2 B3:2 B3:4 C4:2 E4:2 E4:4",
                                    "C4:2 B3:2 A3:2 B3:2 C4:2 C4:2 C4:2 C4:2",
                                    "B3:2 B3:2 C4:2 B3:2 A3:8"
                            ),
                            phrase(
                                    "C3:4 G3:4 C3:4 C3:4",
                                    "G3:4 G3:4 C3:4 C3:4",
                                    "C3:4 G3:4 C3:4 C3:4",
                                    "G3:4 G3:4 C3:8"
                            ),
                            phrase(
                                    "R:4 G4:4 G4:4 E4:4",
                                    "F4:4 F4:4 G4:4 R:4",
                                    "R:4 G4:4 G4:4 E4:4",
                                    "F4:4 F4:4 E4:4 C4:4"
                            ),
                            phrase(
                                    "R:8 C4:2 D4:2 E4:4",
                                    "R:8 D4:2 E4:2 G4:4",
                                    "R:8 C4:2 D4:2 E4:4",
                                    "R:8 D4:2 E4:2 C4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Brother John",
                    "Folk",
                    phrase(
                            "C4:2 D4:2 E4:2 C4:2 C4:2 D4:2 E4:2 C4:2",
                            "E4:2 F4:2 G4:4 E4:2 F4:2 G4:4",
                            "G4:1 A4:1 G4:1 F4:1 E4:2 C4:2 G4:1 A4:1 G4:1 F4:1 E4:2 C4:2",
                            "C4:2 G3:2 C4:4 C4:2 G3:2 C4:4"
                    ),
                    List.of(
                            phrase(
                                    "E3:2 F3:2 G3:2 E3:2 E3:2 F3:2 G3:2 E3:2",
                                    "G3:2 A3:2 B3:4 G3:2 A3:2 B3:4",
                                    "B3:1 C4:1 B3:1 A3:1 G3:2 E3:2 B3:1 C4:1 B3:1 A3:1 G3:2 E3:2",
                                    "E3:2 C3:2 E3:4 E3:2 C3:2 E3:4"
                            ),
                            phrase(
                                    "C3:4 G3:4 C3:4 G3:4",
                                    "C3:4 G3:4 C3:4 G3:4",
                                    "C3:4 G3:4 C3:4 G3:4",
                                    "C3:4 G3:4 C3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 G4:2 A4:2 G4:4",
                                    "R:8 C5:4 B4:4",
                                    "R:8 G4:2 F4:2 E4:4",
                                    "R:8 E4:2 D4:2 C4:4"
                            ),
                            phrase(
                                    "R:4 C4:2 D4:2 E4:4 R:4",
                                    "E4:2 F4:2 G4:4 R:4",
                                    "G4:2 F4:2 E4:2 C4:2 R:4",
                                    "C4:2 G3:2 C4:4 R:4"
                            )
                    )
            ),
            new SongSeed(
                    "Row Row Row Your Boat",
                    "Folk",
                    phrase(
                            "C4:3 C4:1 C4:2 D4:1 E4:3 E4:1",
                            "D4:1 E4:1 F4:1 G4:4",
                            "C5:1 C5:1 C5:1 G4:1 G4:1 G4:1 E4:1 E4:1 E4:1 C4:1 C4:1 C4:1",
                            "G4:2 F4:1 E4:1 D4:1 C4:4"
                    ),
                    List.of(
                            phrase(
                                    "E3:3 E3:1 E3:2 F3:1 G3:3 G3:1",
                                    "F3:1 G3:1 A3:1 B3:4",
                                    "E4:1 E4:1 E4:1 B3:1 B3:1 B3:1 G3:1 G3:1 G3:1 E3:1 E3:1 E3:1",
                                    "B3:2 A3:1 G3:1 F3:1 E3:4"
                            ),
                            phrase(
                                    "C3:4 C3:4 G3:4 C3:4",
                                    "C3:4 G3:4 C3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 G4:4 R:4",
                                    "R:8 C5:2 B4:2 A4:4",
                                    "R:4 G4:4 R:4 E4:4"
                            ),
                            phrase(
                                    "R:4 C4:2 D4:2 E4:4",
                                    "R:4 D4:2 E4:2 F4:4",
                                    "R:8 G4:2 F4:2 E4:4"
                            )
                    )
            ),
            new SongSeed(
                    "London Bridge",
                    "Folk",
                    phrase(
                            "G4:2 A4:2 G4:2 F4:2 E4:2 F4:2 G4:4",
                            "D4:2 E4:2 F4:4 E4:2 F4:2 G4:4",
                            "G4:2 A4:2 G4:2 F4:2 E4:2 F4:2 G4:4",
                            "D4:4 G4:4 E4:2 C4:4"
                    ),
                    List.of(
                            phrase(
                                    "E4:2 F4:2 E4:2 D4:2 C4:2 D4:2 E4:4",
                                    "B3:2 C4:2 D4:4 C4:2 D4:2 E4:4",
                                    "E4:2 F4:2 E4:2 D4:2 C4:2 D4:2 E4:4",
                                    "B3:4 E4:4 C4:2 G3:4"
                            ),
                            phrase(
                                    "C3:4 G3:4 C3:4 G3:4",
                                    "C3:4 G3:4 C3:4 G3:4",
                                    "C3:4 G3:4 C3:4 G3:4",
                                    "G3:4 C3:4 G3:4 C3:4"
                            ),
                            phrase(
                                    "R:4 C5:4 R:4 B4:4",
                                    "A4:4 R:4 G4:4 R:4",
                                    "R:4 C5:4 R:4 B4:4",
                                    "A4:4 G4:4 E4:2 C4:4"
                            ),
                            phrase(
                                    "R:8 G4:2 F4:2 E4:4",
                                    "R:8 D4:2 E4:2 F4:4",
                                    "R:8 G4:2 F4:2 E4:4",
                                    "R:8 D4:2 E4:2 C4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Jingle Bells",
                    "Holiday",
                    phrase(
                            "E4:2 E4:2 E4:4 E4:2 E4:2 E4:4",
                            "E4:2 G4:2 C4:3 D4:1 E4:8",
                            "F4:2 F4:2 F4:3 F4:1 F4:2 E4:2 E4:2 E4:1 E4:1",
                            "E4:2 D4:2 D4:2 E4:2 D4:4 G4:4"
                    ),
                    List.of(
                            phrase(
                                    "C4:2 C4:2 C4:4 C4:2 C4:2 C4:4",
                                    "C4:2 E4:2 G3:3 B3:1 C4:8",
                                    "D4:2 D4:2 D4:3 D4:1 D4:2 C4:2 C4:2 C4:1 C4:1",
                                    "C4:2 B3:2 B3:2 C4:2 B3:4 E4:4"
                            ),
                            phrase(
                                    "C3:4 G3:4 C3:4 G3:4",
                                    "C3:4 G3:4 C3:8",
                                    "F3:4 C3:4 G3:4 C3:4",
                                    "G3:4 G3:4 C3:4 G3:4"
                            ),
                            phrase(
                                    "R:8 G4:4 R:4",
                                    "R:4 C5:4 B4:4 G4:4",
                                    "A4:4 R:4 A4:4 G4:4",
                                    "R:8 G4:4 R:4"
                            ),
                            phrase(
                                    "R:4 E4:2 E4:2 G4:4",
                                    "R:4 E4:2 G4:2 C5:4",
                                    "R:4 F4:2 E4:2 D4:4",
                                    "R:4 D4:2 E4:2 G4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Amazing Grace",
                    "Folk",
                    phrase(
                            "G3:3 C4:1 E4:4 C4:2 E4:2 D4:4",
                            "C4:2 A3:2 G3:4 G3:3 C4:1 E4:4",
                            "C4:2 E4:2 D4:4 E4:2 G4:2 E4:4",
                            "G4:3 E4:1 C4:4 C4:2 A3:2 G3:4"
                    ),
                    List.of(
                            phrase(
                                    "E3:3 G3:1 C4:4 G3:2 C4:2 B3:4",
                                    "A3:2 F3:2 E3:4 E3:3 G3:1 C4:4",
                                    "G3:2 C4:2 B3:4 C4:2 E4:2 C4:4",
                                    "E4:3 C4:1 G3:4 G3:2 F3:2 E3:4"
                            ),
                            phrase(
                                    "C3:4 C3:4 G3:4 G3:4",
                                    "C3:4 F3:4 C3:4 C3:4",
                                    "C3:4 C3:4 G3:4 C3:4",
                                    "C3:4 C3:4 F3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 G4:4 F4:4",
                                    "E4:4 C4:4 R:8",
                                    "R:8 G4:4 A4:4",
                                    "G4:4 E4:4 C4:4 R:4"
                            ),
                            phrase(
                                    "R:8 C4:2 E4:2 D4:4",
                                    "R:8 A3:2 C4:2 E4:4",
                                    "R:8 C4:2 E4:2 G4:4",
                                    "R:8 E4:2 C4:2 G3:4"
                            )
                    )
            ),
            new SongSeed(
                    "Yankee Doodle",
                    "Folk",
                    phrase(
                            "C4:2 C4:2 D4:2 E4:2 C4:2 E4:2 D4:4",
                            "C4:2 C4:2 D4:2 E4:2 C4:4 B3:4",
                            "C4:2 C4:2 D4:2 E4:2 F4:2 E4:2 D4:2 C4:2",
                            "B3:2 G3:2 A3:2 B3:2 C4:4"
                    ),
                    List.of(
                            phrase(
                                    "E3:2 E3:2 F3:2 G3:2 E3:2 G3:2 F3:4",
                                    "E3:2 E3:2 F3:2 G3:2 E3:4 D3:4",
                                    "E3:2 E3:2 F3:2 G3:2 A3:2 G3:2 F3:2 E3:2",
                                    "D3:2 B2:2 C3:2 D3:2 E3:4"
                            ),
                            phrase(
                                    "C3:4 G3:4 C3:4 G3:4",
                                    "C3:4 G3:4 C3:4 C3:4",
                                    "C3:4 G3:4 F3:4 C3:4",
                                    "G3:4 G3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 G4:4 F4:4",
                                    "R:8 G4:4 E4:4",
                                    "R:8 A4:4 G4:4",
                                    "F4:4 D4:4 E4:4"
                            ),
                            phrase(
                                    "R:4 C4:2 D4:2 E4:4",
                                    "R:4 C4:2 D4:2 E4:4",
                                    "R:4 C4:2 E4:2 F4:4",
                                    "R:4 B3:2 D4:2 C4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Silent Night",
                    "Holiday",
                    phrase(
                            "G4:3 A4:1 G4:2 E4:6 G4:3 A4:1 G4:2 E4:6",
                            "D5:4 D5:2 B4:6 C5:4 C5:2 G4:6",
                            "A4:4 A4:2 C5:3 B4:1 A4:2 G4:3 A4:1 G4:2 E4:6"
                    ),
                    List.of(
                            phrase(
                                    "E4:3 F4:1 E4:2 C4:6 E4:3 F4:1 E4:2 C4:6",
                                    "B4:4 B4:2 G4:6 A4:4 A4:2 E4:6",
                                    "F4:4 F4:2 A4:3 G4:1 F4:2 E4:3 F4:1 E4:2 C4:6"
                            ),
                            phrase(
                                    "C3:6 G3:6 C3:6 G3:6",
                                    "G3:6 G3:6 C3:6 C3:6",
                                    "F3:6 F3:6 C3:6 G3:6 C3:6"
                            ),
                            phrase(
                                    "R:12 C5:6 R:6 C5:6",
                                    "G5:6 R:6 E5:6 R:6",
                                    "C5:6 R:6 E5:6 D5:6 C5:6"
                            ),
                            phrase(
                                    "R:6 G4:3 A4:1 G4:2 R:6",
                                    "R:6 G4:3 A4:1 G4:2 R:6",
                                    "R:6 A4:4 A4:2 R:6 G4:4 E4:2"
                            )
                    )
            ),
            new SongSeed(
                    "Deck the Halls",
                    "Holiday",
                    phrase(
                            "G4:2 F4:1 E4:1 D4:2 C4:2 D4:2 E4:2 C4:2",
                            "D4:1 E4:1 F4:2 D4:1 E4:1 F4:2 G4:2",
                            "G4:2 F4:1 E4:1 D4:2 C4:2 D4:2 E4:2 C4:2",
                            "A4:1 A4:1 G4:2 F4:1 E4:1 D4:2 C4:4"
                    ),
                    List.of(
                            phrase(
                                    "E4:2 D4:1 C4:1 B3:2 A3:2 B3:2 C4:2 A3:2",
                                    "B3:1 C4:1 D4:2 B3:1 C4:1 D4:2 E4:2",
                                    "E4:2 D4:1 C4:1 B3:2 A3:2 B3:2 C4:2 A3:2",
                                    "F4:1 F4:1 E4:2 D4:1 C4:1 B3:2 A3:4"
                            ),
                            phrase(
                                    "C3:4 G3:4 C3:4 F3:4",
                                    "G3:4 G3:4 C3:4 C3:4",
                                    "C3:4 G3:4 C3:4 F3:4",
                                    "F3:4 G3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 G4:4 E4:4",
                                    "F4:4 R:4 G4:4",
                                    "R:8 G4:4 E4:4",
                                    "C5:4 G4:4 E4:4"
                            ),
                            phrase(
                                    "R:4 G4:2 F4:2 E4:4",
                                    "R:4 D4:2 E4:2 F4:4",
                                    "R:4 G4:2 F4:2 E4:4",
                                    "R:4 A4:2 G4:2 C4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Joy to the World",
                    "Holiday",
                    phrase(
                            "C5:3 B4:1 A4:2 G4:4 F4:3 E4:1 D4:2 C4:4",
                            "G4:3 A4:1 A4:3 B4:1 B4:4",
                            "C5:3 C5:1 B4:2 A4:2 G4:3 F4:1 E4:2",
                            "C5:2 C5:1 B4:1 A4:2 G4:2 F4:2 E4:2 D4:2 C4:4"
                    ),
                    List.of(
                            phrase(
                                    "E4:3 D4:1 C4:2 B3:4 A3:3 G3:1 F3:2 E3:4",
                                    "E4:3 F4:1 F4:3 G4:1 G4:4",
                                    "E4:3 E4:1 D4:2 C4:2 B3:3 A3:1 G3:2",
                                    "E4:2 E4:1 D4:1 C4:2 B3:2 A3:2 G3:2 F3:2 E3:4"
                            ),
                            phrase(
                                    "C3:4 G3:4 F3:4 C3:4",
                                    "C3:4 F3:4 G3:4",
                                    "C3:4 G3:4 F3:4 C3:4",
                                    "C3:4 G3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 C5:4 A4:4",
                                    "R:4 C5:4 D5:4",
                                    "R:8 C5:4 B4:4",
                                    "A4:4 G4:4 E4:4"
                            ),
                            phrase(
                                    "R:4 C5:2 B4:2 A4:4",
                                    "R:4 G4:2 A4:2 B4:4",
                                    "R:4 C5:2 B4:2 A4:4",
                                    "R:4 G4:2 E4:2 C4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Auld Lang Syne",
                    "Folk",
                    phrase(
                            "G3:2 C4:3 C4:1 C4:2 E4:2 D4:3 C4:1 D4:2 E4:2",
                            "C4:3 C4:1 E4:2 G4:4 A4:3 G4:1 E4:2 E4:2",
                            "D4:3 C4:1 D4:2 E4:2 C4:3 A3:1 A3:2 G3:4"
                    ),
                    List.of(
                            phrase(
                                    "E3:2 G3:3 G3:1 G3:2 C4:2 B3:3 G3:1 B3:2 C4:2",
                                    "G3:3 G3:1 C4:2 E4:4 F4:3 E4:1 C4:2 C4:2",
                                    "B3:3 G3:1 B3:2 C4:2 G3:3 F3:1 F3:2 E3:4"
                            ),
                            phrase(
                                    "C3:4 C3:4 G3:4 C3:4",
                                    "C3:4 C3:4 F3:4 C3:4",
                                    "G3:4 C3:4 F3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 G4:4 E4:4",
                                    "R:4 G4:4 A4:4",
                                    "R:8 E4:4 C4:4"
                            ),
                            phrase(
                                    "R:6 C4:2 E4:4 D4:4",
                                    "R:6 E4:2 G4:4 A4:4",
                                    "R:6 D4:2 E4:4 G3:4"
                            )
                    )
            ),
            new SongSeed(
                    "Bingo",
                    "Folk",
                    phrase(
                            "G4:2 G4:2 G4:2 D4:2 E4:2 E4:2 D4:4",
                            "G4:2 G4:2 A4:2 A4:2 G4:4",
                            "D5:2 D5:2 C5:2 C5:2 B4:2 B4:2 A4:4",
                            "G4:2 G4:2 A4:2 A4:2 G4:4"
                    ),
                    List.of(
                            phrase(
                                    "E4:2 E4:2 E4:2 B3:2 C4:2 C4:2 B3:4",
                                    "E4:2 E4:2 F4:2 F4:2 E4:4",
                                    "B4:2 B4:2 A4:2 A4:2 G4:2 G4:2 F4:4",
                                    "E4:2 E4:2 F4:2 F4:2 E4:4"
                            ),
                            phrase(
                                    "C3:4 G3:4 C3:4 G3:4",
                                    "C3:4 F3:4 C3:4",
                                    "G3:4 F3:4 E3:4 D3:4",
                                    "C3:4 F3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 G4:4 E4:4",
                                    "R:4 G4:4 A4:4",
                                    "R:8 D5:4 B4:4",
                                    "R:4 G4:4 G4:4"
                            ),
                            phrase(
                                    "R:4 G4:2 G4:2 D4:4",
                                    "R:4 G4:2 A4:2 G4:4",
                                    "R:4 D5:2 C5:2 B4:4",
                                    "R:4 G4:2 A4:2 G4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Old MacDonald Had a Farm",
                    "Folk",
                    phrase(
                            "G4:2 G4:2 G4:2 D4:2 E4:2 E4:2 D4:4",
                            "B4:2 B4:2 A4:2 A4:2 G4:4",
                            "D4:2 D4:2 G4:2 G4:2 G4:2 D4:2 E4:2 E4:2 D4:4",
                            "B4:2 B4:2 A4:2 A4:2 G4:4"
                    ),
                    List.of(
                            phrase(
                                    "E4:2 E4:2 E4:2 B3:2 C4:2 C4:2 B3:4",
                                    "G4:2 G4:2 F4:2 F4:2 E4:4",
                                    "B3:2 B3:2 E4:2 E4:2 E4:2 B3:2 C4:2 C4:2 B3:4",
                                    "G4:2 G4:2 F4:2 F4:2 E4:4"
                            ),
                            phrase(
                                    "C3:4 G3:4 C3:4 G3:4",
                                    "G3:4 F3:4 C3:4",
                                    "G3:4 C3:4 C3:4 G3:4 C3:4",
                                    "G3:4 F3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 G4:4 E4:4",
                                    "R:4 B4:4 G4:4",
                                    "R:8 G4:4 E4:4",
                                    "R:4 B4:4 G4:4"
                            ),
                            phrase(
                                    "R:4 G4:2 D4:2 E4:4",
                                    "R:4 B4:2 A4:2 G4:4",
                                    "R:4 G4:2 D4:2 E4:4",
                                    "R:4 B4:2 A4:2 G4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Camptown Races",
                    "Folk",
                    phrase(
                            "G4:2 E4:2 G4:2 E4:2 G4:2 A4:2 G4:2 E4:2",
                            "D4:2 E4:2 D4:4",
                            "G4:2 E4:2 G4:2 E4:2 G4:2 A4:2 G4:2 E4:2",
                            "D4:2 E4:2 C4:4"
                    ),
                    List.of(
                            phrase(
                                    "E4:2 C4:2 E4:2 C4:2 E4:2 F4:2 E4:2 C4:2",
                                    "B3:2 C4:2 B3:4",
                                    "E4:2 C4:2 E4:2 C4:2 E4:2 F4:2 E4:2 C4:2",
                                    "B3:2 C4:2 G3:4"
                            ),
                            phrase(
                                    "C3:4 C3:4 C3:4 G3:4",
                                    "G3:4 C3:4",
                                    "C3:4 C3:4 C3:4 G3:4",
                                    "G3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 G4:4 A4:4",
                                    "R:4 E4:4",
                                    "R:8 G4:4 A4:4",
                                    "R:4 C4:4"
                            ),
                            phrase(
                                    "R:4 G4:2 E4:2 G4:4",
                                    "R:4 D4:2 E4:2 D4:4",
                                    "R:4 G4:2 E4:2 G4:4",
                                    "R:4 D4:2 E4:2 C4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Oh Susanna",
                    "Folk",
                    phrase(
                            "C4:2 D4:2 E4:2 G4:2 G4:2 A4:2 G4:4",
                            "E4:2 C4:2 D4:2 E4:2 E4:2 D4:2 C4:4",
                            "C4:2 D4:2 E4:2 G4:2 G4:2 A4:2 G4:4",
                            "E4:2 C4:2 D4:2 E4:2 D4:2 C4:2 D4:4"
                    ),
                    List.of(
                            phrase(
                                    "G3:2 B3:2 C4:2 E4:2 E4:2 F4:2 E4:4",
                                    "C4:2 G3:2 B3:2 C4:2 C4:2 B3:2 G3:4",
                                    "G3:2 B3:2 C4:2 E4:2 E4:2 F4:2 E4:4",
                                    "C4:2 G3:2 B3:2 C4:2 B3:2 G3:2 B3:4"
                            ),
                            phrase(
                                    "C3:4 C3:4 F3:4 C3:4",
                                    "C3:4 G3:4 C3:4 C3:4",
                                    "C3:4 C3:4 F3:4 C3:4",
                                    "C3:4 G3:4 C3:4 G3:4"
                            ),
                            phrase(
                                    "R:8 C5:4 G4:4",
                                    "R:8 E4:4 C4:4",
                                    "R:8 C5:4 G4:4",
                                    "R:8 E4:4 D4:4"
                            ),
                            phrase(
                                    "R:4 C4:2 E4:2 G4:4",
                                    "R:4 E4:2 D4:2 C4:4",
                                    "R:4 C4:2 E4:2 G4:4",
                                    "R:4 E4:2 C4:2 D4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Scarborough Fair",
                    "Folk",
                    phrase(
                            "D4:3 D4:1 A4:2 A4:2 E4:2 F4:2 E4:4",
                            "D4:2 C4:2 D4:2 E4:2 D4:4",
                            "A3:2 C4:2 D4:4 E4:2 F4:2 G4:2 A4:2 G4:4",
                            "F4:2 E4:2 D4:4"
                    ),
                    List.of(
                            phrase(
                                    "A3:3 A3:1 E4:2 E4:2 C4:2 D4:2 C4:4",
                                    "A3:2 G3:2 A3:2 C4:2 A3:4",
                                    "E3:2 G3:2 A3:4 C4:2 D4:2 E4:2 F4:2 E4:4",
                                    "D4:2 C4:2 A3:4"
                            ),
                            phrase(
                                    "D3:4 A3:4 C3:4 G3:4",
                                    "D3:4 A3:4 D3:4",
                                    "D3:4 A3:4 C3:4 G3:4",
                                    "D3:4 A3:4"
                            ),
                            phrase(
                                    "R:8 A4:4 E4:4",
                                    "R:4 D4:4 A3:4",
                                    "R:8 G4:4 A4:4",
                                    "R:4 F4:4 D4:4"
                            ),
                            phrase(
                                    "R:6 D4:2 A4:4 E4:4",
                                    "R:4 D4:2 E4:2 D4:4",
                                    "R:6 A3:2 D4:4 G4:4",
                                    "R:4 F4:2 E4:2 D4:4"
                            )
                    )
            ),
            new SongSeed(
                    "When the Saints Go Marching In",
                    "Folk",
                    phrase(
                            "C4:2 E4:2 F4:2 G4:4 C4:2 E4:2 F4:2 G4:4",
                            "C4:2 E4:2 F4:2 G4:2 E4:2 C4:2 E4:2 D4:4",
                            "E4:2 E4:2 D4:2 C4:4 C4:2 E4:2 G4:2 G4:2",
                            "F4:2 E4:2 F4:2 G4:4"
                    ),
                    List.of(
                            phrase(
                                    "G3:2 C4:2 D4:2 E4:4 G3:2 C4:2 D4:2 E4:4",
                                    "G3:2 C4:2 D4:2 E4:2 C4:2 G3:2 C4:2 B3:4",
                                    "C4:2 C4:2 B3:2 G3:4 G3:2 C4:2 E4:2 E4:2",
                                    "D4:2 C4:2 D4:2 E4:4"
                            ),
                            phrase(
                                    "C3:4 G3:4 C3:4 G3:4",
                                    "C3:4 G3:4 C3:4 G3:4",
                                    "C3:4 G3:4 C3:4 C3:4",
                                    "F3:4 G3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 C5:4 G4:4",
                                    "R:8 C5:4 G4:4",
                                    "R:8 E4:4 G4:4",
                                    "A4:4 G4:4 R:4"
                            ),
                            phrase(
                                    "R:4 C4:2 E4:2 G4:4",
                                    "R:4 C4:2 E4:2 G4:4",
                                    "R:4 E4:2 D4:2 C4:4",
                                    "R:4 F4:2 E4:2 G4:4"
                            )
                    )
            ),
            new SongSeed(
                    "This Old Man",
                    "Folk",
                    phrase(
                            "G4:2 E4:2 G4:2 G4:2 E4:2 G4:4",
                            "A4:2 G4:2 F4:2 E4:2 D4:4",
                            "E4:2 F4:2 G4:2 C4:2 C4:2 D4:2 E4:2 F4:2 G4:4",
                            "G4:2 D4:2 D4:2 F4:2 E4:2 D4:2 C4:4"
                    ),
                    List.of(
                            phrase(
                                    "E4:2 C4:2 E4:2 E4:2 C4:2 E4:4",
                                    "F4:2 E4:2 D4:2 C4:2 B3:4",
                                    "C4:2 D4:2 E4:2 G3:2 G3:2 B3:2 C4:2 D4:2 E4:4",
                                    "E4:2 B3:2 B3:2 D4:2 C4:2 B3:2 G3:4"
                            ),
                            phrase(
                                    "C3:4 C3:4 C3:4 G3:4",
                                    "F3:4 C3:4 G3:4",
                                    "C3:4 G3:4 C3:4 F3:4 C3:4",
                                    "G3:4 G3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 G4:4 R:4",
                                    "R:4 A4:4 E4:4",
                                    "R:8 G4:4 C4:4",
                                    "R:4 G4:4 C4:4"
                            ),
                            phrase(
                                    "R:4 G4:2 E4:2 G4:4",
                                    "R:4 A4:2 G4:2 D4:4",
                                    "R:4 E4:2 G4:2 C4:4",
                                    "R:4 D4:2 E4:2 C4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Pop Goes the Weasel",
                    "Folk",
                    phrase(
                            "C4:2 C4:2 D4:2 D4:2 E4:2 G4:2 E4:4",
                            "C4:2 C4:2 D4:2 D4:2 E4:4 C4:4",
                            "G4:2 C5:2 B4:2 D5:2 C5:2 E5:2 C5:4",
                            "A4:2 D5:2 C5:2 B4:2 A4:4 G4:4"
                    ),
                    List.of(
                            phrase(
                                    "G3:2 G3:2 B3:2 B3:2 C4:2 E4:2 C4:4",
                                    "G3:2 G3:2 B3:2 B3:2 C4:4 G3:4",
                                    "E4:2 G4:2 F4:2 A4:2 G4:2 B4:2 G4:4",
                                    "F4:2 A4:2 G4:2 F4:2 E4:4 D4:4"
                            ),
                            phrase(
                                    "C3:4 G3:4 C3:4 C3:4",
                                    "C3:4 G3:4 C3:4",
                                    "C3:4 G3:4 C3:4 C3:4",
                                    "F3:4 G3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 E4:4 C4:4",
                                    "R:8 E4:4 C4:4",
                                    "R:8 C5:4 E5:4",
                                    "R:8 A4:4 G4:4"
                            ),
                            phrase(
                                    "R:4 C4:2 D4:2 E4:4",
                                    "R:4 C4:2 D4:2 C4:4",
                                    "R:4 G4:2 C5:2 E5:4",
                                    "R:4 A4:2 B4:2 G4:4"
                            )
                    )
            ),
            new SongSeed(
                    "La Cucaracha",
                    "Folk",
                    phrase(
                            "C4:2 C4:2 C4:2 F4:2 A4:4 C4:2 C4:2 C4:2 F4:2 A4:4",
                            "F4:2 F4:2 E4:2 E4:2 D4:2 D4:2 C4:4",
                            "G4:2 G4:2 F4:2 E4:2 D4:2 E4:2 F4:4 E4:2 D4:2 C4:4"
                    ),
                    List.of(
                            phrase(
                                    "G3:2 G3:2 G3:2 C4:2 F4:4 G3:2 G3:2 G3:2 C4:2 F4:4",
                                    "C4:2 C4:2 B3:2 B3:2 A3:2 A3:2 G3:4",
                                    "E4:2 E4:2 D4:2 C4:2 B3:2 C4:2 D4:4 C4:2 B3:2 G3:4"
                            ),
                            phrase(
                                    "C3:4 C3:4 F3:4 C3:4 C3:4 F3:4",
                                    "F3:4 C3:4 G3:4 C3:4",
                                    "C3:4 F3:4 G3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 A4:4 R:4 A4:4",
                                    "R:8 F4:4 C4:4",
                                    "R:8 G4:4 F4:4"
                            ),
                            phrase(
                                    "R:4 C4:2 F4:2 A4:4",
                                    "R:4 C4:2 F4:2 A4:4",
                                    "R:4 F4:2 E4:2 C4:4",
                                    "R:4 G4:2 F4:2 C4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Hava Nagila",
                    "Folk",
                    phrase(
                            "E4:1 F4:1 G4:2 G4:2 F4:1 E4:1 F4:2 E4:2 D4:2",
                            "E4:1 F4:1 G4:2 G4:2 F4:1 E4:1 F4:2 E4:4",
                            "A4:1 G4:1 F4:2 E4:2 D4:2 E4:2 F4:4",
                            "G4:2 A4:2 G4:2 F4:2 E4:4"
                    ),
                    List.of(
                            phrase(
                                    "C4:1 D4:1 E4:2 E4:2 D4:1 C4:1 D4:2 C4:2 B3:2",
                                    "C4:1 D4:1 E4:2 E4:2 D4:1 C4:1 D4:2 C4:4",
                                    "F4:1 E4:1 D4:2 C4:2 B3:2 C4:2 D4:4",
                                    "E4:2 F4:2 E4:2 D4:2 C4:4"
                            ),
                            phrase(
                                    "E3:4 B3:4 E3:4 B3:4",
                                    "E3:4 B3:4 E3:4",
                                    "A3:4 E3:4 B3:4 E3:4",
                                    "E3:4 B3:4 E3:4"
                            ),
                            phrase(
                                    "R:8 G4:4 E4:4",
                                    "R:8 G4:4 E4:4",
                                    "R:8 A4:4 F4:4",
                                    "G4:4 A4:4 E4:4"
                            ),
                            phrase(
                                    "R:4 E4:2 F4:2 G4:4",
                                    "R:4 E4:2 F4:2 E4:4",
                                    "R:4 A4:2 G4:2 F4:4",
                                    "R:4 G4:2 F4:2 E4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Greensleeves",
                    "Folk",
                    phrase(
                            "A3:2 C4:4 D4:2 E4:3 F4:1 E4:2 D4:4",
                            "B3:2 G3:3 A3:1 B3:2 C4:4 A3:4",
                            "A3:2 C4:4 D4:2 E4:3 F4:1 E4:2 D4:4",
                            "B3:2 G3:3 A3:1 B3:2 C4:4 A3:4"
                    ),
                    List.of(
                            phrase(
                                    "E3:2 A3:4 B3:2 C4:3 D4:1 C4:2 B3:4",
                                    "G3:2 E3:3 F3:1 G3:2 A3:4 E3:4",
                                    "E3:2 A3:4 B3:2 C4:3 D4:1 C4:2 B3:4",
                                    "G3:2 E3:3 F3:1 G3:2 A3:4 E3:4"
                            ),
                            phrase(
                                    "A2:4 E3:4 G3:4 D3:4",
                                    "G2:4 D3:4 A2:4 A2:4",
                                    "A2:4 E3:4 G3:4 D3:4",
                                    "G2:4 D3:4 A2:4 A2:4"
                            ),
                            phrase(
                                    "R:8 E4:4 D4:4",
                                    "R:8 C4:4 A3:4",
                                    "R:8 E4:4 D4:4",
                                    "R:8 C4:4 A3:4"
                            ),
                            phrase(
                                    "R:4 A3:2 C4:2 E4:4",
                                    "R:4 B3:2 G3:2 A3:4",
                                    "R:4 A3:2 C4:2 E4:4",
                                    "R:4 B3:2 G3:2 A3:4"
                            )
                    )
            ),
            new SongSeed(
                    "Drunken Sailor",
                    "Folk",
                    phrase(
                            "D4:2 D4:2 D4:2 D4:2 D4:2 D4:2 A3:2 C4:2 D4:4",
                            "D4:2 D4:2 D4:2 D4:2 E4:2 F4:2 E4:2 D4:4",
                            "A4:2 A4:2 A4:2 A4:2 G4:2 F4:2 E4:2 D4:4",
                            "D4:2 D4:2 D4:2 D4:2 E4:2 C4:2 D4:4"
                    ),
                    List.of(
                            phrase(
                                    "A3:2 A3:2 A3:2 A3:2 A3:2 A3:2 E3:2 G3:2 A3:4",
                                    "A3:2 A3:2 A3:2 A3:2 C4:2 D4:2 C4:2 A3:4",
                                    "E4:2 E4:2 E4:2 E4:2 D4:2 C4:2 B3:2 A3:4",
                                    "A3:2 A3:2 A3:2 A3:2 C4:2 G3:2 A3:4"
                            ),
                            phrase(
                                    "D3:4 D3:4 D3:4 A3:4",
                                    "D3:4 D3:4 C3:4 D3:4",
                                    "A3:4 A3:4 G3:4 D3:4",
                                    "D3:4 D3:4 A3:4 D3:4"
                            ),
                            phrase(
                                    "R:8 D5:4 A4:4",
                                    "R:8 F4:4 D4:4",
                                    "R:8 A4:4 D4:4",
                                    "R:8 E4:4 D4:4"
                            ),
                            phrase(
                                    "R:4 D4:2 D4:2 A3:4",
                                    "R:4 D4:2 F4:2 D4:4",
                                    "R:4 A4:2 G4:2 D4:4",
                                    "R:4 D4:2 C4:2 D4:4"
                            )
                    )
            ),
            new SongSeed(
                    "Wellerman",
                    "Folk",
                    phrase(
                            "A3:2 C4:2 D4:2 E4:2 D4:2 C4:2 A3:4",
                            "A3:2 C4:2 D4:2 E4:2 G4:2 E4:2 D4:4",
                            "D4:2 E4:2 G4:2 A4:2 G4:2 E4:2 D4:4",
                            "C4:2 D4:2 E4:2 D4:2 C4:2 A3:4"
                    ),
                    List.of(
                            phrase(
                                    "E3:2 G3:2 A3:2 C4:2 A3:2 G3:2 E3:4",
                                    "E3:2 G3:2 A3:2 C4:2 E4:2 C4:2 A3:4",
                                    "A3:2 C4:2 E4:2 F4:2 E4:2 C4:2 A3:4",
                                    "G3:2 A3:2 C4:2 A3:2 G3:2 E3:4"
                            ),
                            phrase(
                                    "A2:4 E3:4 A2:4 A2:4",
                                    "A2:4 E3:4 G3:4 A2:4",
                                    "D3:4 A3:4 D3:4 D3:4",
                                    "A2:4 E3:4 A2:4"
                            ),
                            phrase(
                                    "R:8 D4:4 A3:4",
                                    "R:8 G4:4 D4:4",
                                    "R:8 A4:4 D4:4",
                                    "R:8 E4:4 A3:4"
                            ),
                            phrase(
                                    "R:4 A3:2 C4:2 D4:4",
                                    "R:4 A3:2 C4:2 D4:4",
                                    "R:4 D4:2 E4:2 G4:4",
                                    "R:4 C4:2 D4:2 A3:4"
                            )
                    )
            ),
            new SongSeed(
                    "O Christmas Tree",
                    "Holiday",
                    phrase(
                            "G4:3 C5:1 C5:2 C5:2 D5:3 E5:1 E5:2 E5:2",
                            "E5:2 D5:2 E5:2 F5:2 B4:4 D5:4 C5:4",
                            "G4:3 C5:1 C5:2 C5:2 D5:3 E5:1 E5:2 E5:2",
                            "E5:2 D5:2 E5:2 F5:2 B4:4 D5:4 C5:4"
                    ),
                    List.of(
                            phrase(
                                    "E4:3 G4:1 G4:2 G4:2 A4:3 C5:1 C5:2 C5:2",
                                    "C5:2 B4:2 C5:2 D5:2 G4:4 B4:4 G4:4",
                                    "E4:3 G4:1 G4:2 G4:2 A4:3 C5:1 C5:2 C5:2",
                                    "C5:2 B4:2 C5:2 D5:2 G4:4 B4:4 G4:4"
                            ),
                            phrase(
                                    "C3:4 C3:4 G3:4 C3:4",
                                    "C3:4 G3:4 C3:4",
                                    "C3:4 C3:4 G3:4 C3:4",
                                    "C3:4 G3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 C5:4 E5:4",
                                    "R:8 F5:4 C5:4",
                                    "R:8 C5:4 E5:4",
                                    "R:8 F5:4 C5:4"
                            ),
                            phrase(
                                    "R:4 G4:2 C5:2 D5:4",
                                    "R:4 E5:2 F5:2 C5:4",
                                    "R:4 G4:2 C5:2 D5:4",
                                    "R:4 E5:2 F5:2 C5:4"
                            )
                    )
            ),
            new SongSeed(
                    "America the Beautiful",
                    "March",
                    phrase(
                            "C4:2 C4:2 E4:2 E4:2 G4:2 G4:2 C5:4",
                            "B4:2 A4:2 G4:2 F4:2 E4:4",
                            "D4:2 E4:2 F4:2 G4:2 A4:2 G4:2 E4:4",
                            "C5:2 C5:2 B4:2 A4:2 G4:4"
                    ),
                    List.of(
                            phrase(
                                    "G3:2 G3:2 C4:2 C4:2 E4:2 E4:2 G4:4",
                                    "F4:2 E4:2 D4:2 C4:2 C4:4",
                                    "B3:2 C4:2 D4:2 E4:2 F4:2 E4:2 C4:4",
                                    "G4:2 G4:2 F4:2 E4:2 D4:4"
                            ),
                            phrase(
                                    "C3:4 C3:4 C3:4 G3:4",
                                    "G3:4 F3:4 C3:4",
                                    "G3:4 C3:4 F3:4 C3:4",
                                    "C3:4 G3:4 C3:4"
                            ),
                            phrase(
                                    "R:8 G4:4 C5:4",
                                    "R:8 B4:4 E4:4",
                                    "R:8 A4:4 E4:4",
                                    "R:8 C5:4 G4:4"
                            ),
                            phrase(
                                    "R:4 C4:2 E4:2 G4:4",
                                    "R:4 B4:2 A4:2 E4:4",
                                    "R:4 D4:2 F4:2 A4:4",
                                    "R:4 C5:2 A4:2 G4:4"
                            )
                    )
            )
            ,
            simpleSeed(
                    "Also Sprach Zarathustra",
                    "Theme",
                    "C3:8 G3:8 C4:8 E4:16 R:2 Eb4:8 C4:20"
            ),
            simpleSeed(
                    "In the Hall of the Mountain King",
                    "Classical",
                    phrase(
                            "B3:1 C#4:1 D4:1 E4:1 F#4:2 D4:2 F#4:2 F4:2",
                            "B3:1 C#4:1 D4:1 E4:1 F#4:2 D4:2 F#4:4",
                            "F#4:1 G4:1 A4:1 B4:1 C#5:2 A4:2 C#5:2 C5:2",
                            "F#4:1 G4:1 A4:1 B4:1 C#5:2 A4:2 C#5:4"
                    )
            ),
            simpleSeed(
                    "Toccata and Fugue",
                    "Theme",
                    phrase(
                            "D5:2 A4:2 D5:2 C5:2 Bb4:2 A4:2 G4:2 F4:2",
                            "E4:2 D4:4 R:2 D4:2 F4:2 A4:2 D5:4",
                            "C#5:2 D5:8 R:4"
                    )
            ),
            simpleSeed(
                    "Ride of the Valkyries",
                    "Theme",
                    phrase(
                            "B3:2 E4:2 G4:2 B4:4 G4:2 B4:2 E5:4",
                            "B4:2 E5:2 G5:2 B5:4 G5:2 B5:2 E6:4",
                            "B5:2 A5:2 G5:2 F#5:2 E5:6"
                    )
            ),
            simpleSeed(
                    "William Tell Overture",
                    "Theme",
                    phrase(
                            "E4:1 E4:1 E4:2 E4:1 E4:1 E4:2 E4:1 G4:1 C4:1 D4:1 E4:4",
                            "F4:1 F4:1 F4:1 F4:1 F4:1 E4:1 E4:1 E4:1 E4:1 D4:1 D4:1 E4:1 D4:4",
                            "G4:1 G4:1 G4:1 G4:1 G4:1 F4:1 E4:1 D4:1 C4:4"
                    )
            ),
            simpleSeed(
                    "Can Can",
                    "Theme",
                    phrase(
                            "G4:1 G4:1 A4:1 B4:1 C5:1 C5:1 A4:1 G4:1",
                            "E4:1 E4:1 F#4:1 G4:1 A4:1 A4:1 F#4:1 E4:1",
                            "G4:1 G4:1 A4:1 B4:1 C5:1 C5:1 A4:1 G4:1",
                            "E4:1 E4:1 F#4:1 G4:1 A4:2 G4:2"
                    )
            ),
            simpleSeed(
                    "Funeral March",
                    "Theme",
                    phrase(
                            "C4:3 C4:1 C4:2 C4:2 Eb4:3 D4:1 D4:4",
                            "C4:3 C4:1 C4:2 C4:2 F4:3 E4:1 E4:4",
                            "C4:3 C4:1 G4:2 F4:2 Eb4:2 D4:2 C4:4"
                    )
            ),
            simpleSeed(
                    "Wedding March",
                    "March",
                    phrase(
                            "C4:2 F4:4 F4:2 F4:4 C4:2 G4:4 E4:4",
                            "F4:2 A4:4 A4:2 A4:4 F4:2 C5:4 A4:4",
                            "Bb4:2 A4:2 G4:2 F4:2 E4:2 F4:8"
                    )
            ),
            simpleSeed(
                    "Bridal Chorus",
                    "March",
                    phrase(
                            "C4:4 F4:4 F4:4 F4:8 C4:4 G4:4 E4:8",
                            "F4:4 A4:4 A4:4 A4:8 F4:4 C5:4 A4:8",
                            "Bb4:4 A4:4 G4:4 F4:8"
                    )
            ),
            simpleSeed(
                    "Blue Danube",
                    "Classical",
                    phrase(
                            "D4:3 F#4:1 A4:3 R:1 A4:3 R:1 A4:2 B4:2 C#5:2 D5:4",
                            "D4:3 F#4:1 A4:3 R:1 A4:3 R:1 A4:2 B4:2 C#5:2 D5:4",
                            "C#5:2 B4:2 A4:4 A4:2 G4:2 F#4:4"
                    )
            ),
            simpleSeed(
                    "Nutcracker Dance of the Sugar Plum Fairy",
                    "Theme",
                    phrase(
                            "E5:1 D#5:1 E5:1 D#5:1 E5:2 B4:2 D5:2 C5:2 A4:4",
                            "C4:1 E4:1 A4:2 B4:4 E4:1 G#4:1 B4:2 C5:4",
                            "E5:1 D#5:1 E5:1 D#5:1 E5:2 B4:2 D5:2 C5:2 A4:4"
                    )
            ),
            simpleSeed(
                    "Swan Lake Theme",
                    "Theme",
                    phrase(
                            "B3:2 F#4:2 B4:2 C#5:2 D5:4 C#5:2 B4:2 A4:4",
                            "F#4:2 A4:2 B4:2 C#5:2 D5:4 C#5:2 B4:2 A4:4",
                            "G4:2 A4:2 B4:2 C#5:2 B4:4 A4:2 G4:2 F#4:4"
                    )
            ),
            simpleSeed(
                    "Habanera",
                    "Classical",
                    phrase(
                            "D4:2 C#4:2 C4:2 B3:2 Bb3:2 A3:2 G#3:2 A3:4",
                            "D4:2 C#4:2 C4:2 B3:2 Bb3:2 A3:2 G#3:2 A3:4",
                            "A3:2 Bb3:2 B3:2 C4:2 C#4:2 D4:4"
                    )
            ),
            simpleSeed(
                    "La Donna e Mobile",
                    "Classical",
                    phrase(
                            "C4:2 D4:2 E4:2 F4:2 G4:4 G4:4",
                            "A4:2 G4:2 F4:2 E4:2 D4:4 D4:4",
                            "E4:2 F4:2 G4:2 A4:2 B4:4 C5:8"
                    )
            ),
            simpleSeed(
                    "Dies Irae",
                    "Theme",
                    phrase(
                            "D4:2 C4:2 D4:2 E4:2 F4:2 E4:2 D4:2 C4:2",
                            "Bb3:2 C4:2 D4:4 D4:2 C4:2 D4:2 E4:2",
                            "F4:2 E4:2 D4:4"
                    )
            ),
            simpleSeed(
                    "Korobeiniki",
                    "Theme",
                    phrase(
                            "E4:2 B3:1 C4:1 D4:2 C4:1 B3:1 A3:2 A3:1 C4:1 E4:2 D4:1 C4:1 B3:4",
                            "C4:1 D4:1 E4:2 C4:2 A3:2 A3:4 B3:1 C4:1 D4:2 B3:2 G#3:2 G#3:4",
                            "E4:2 B3:1 C4:1 D4:2 C4:1 B3:1 A3:2 A3:1 C4:1 E4:2 D4:1 C4:1 B3:4"
                    )
            ),
            simpleSeed(
                    "Eine Kleine Nachtmusik",
                    "Classical",
                    phrase(
                            "G4:1 R:1 D4:1 R:1 G4:1 R:1 D4:1 R:1 G4:1 B4:1 D5:2 B4:1 G4:1 B4:1 D5:2",
                            "C5:1 A4:1 C5:1 A4:1 F#4:1 A4:1 D4:2 G4:1 R:1 D4:1 R:1 G4:1 R:1 D4:1 R:1 G4:4",
                            "B4:1 G4:1 B4:1 D5:2 C5:1 A4:1 C5:1 A4:1 G4:4"
                    )
            ),
            simpleSeed(
                    "Fur Elise",
                    "Classical",
                    phrase(
                            "E5:1 D#5:1 E5:1 D#5:1 E5:1 B4:1 D5:1 C5:1 A4:4",
                            "C4:1 E4:1 A4:1 B4:4 E4:1 G#4:1 B4:1 C5:4",
                            "E5:1 D#5:1 E5:1 D#5:1 E5:1 B4:1 D5:1 C5:1 A4:4"
                    )
            ),
            simpleSeed(
                    "Beethoven Fifth",
                    "Classical",
                    phrase(
                            "G4:1 G4:1 G4:1 Eb4:6 R:1 F4:1 F4:1 F4:1 D4:6 R:1",
                            "G4:1 G4:1 G4:1 Eb4:4 R:2 F4:1 F4:1 F4:1 D4:4 R:2",
                            "G4:1 G4:1 G4:1 Eb4:6"
                    )
            ),
            simpleSeed(
                    "Canon in D",
                    "Classical",
                    phrase(
                            "D4:4 A3:4 B3:4 F#3:4 G3:4 D3:4 G3:4 A3:4",
                            "D4:2 F#4:2 A4:2 G4:2 F#4:2 E4:2 D4:4",
                            "A3:4 B3:4 F#3:4 G3:4 A3:4"
                    )
            ),
            simpleSeed(
                    "Rondo Alla Turca",
                    "Classical",
                    phrase(
                            "B4:1 A4:1 G#4:1 A4:1 C5:2 R:1 D5:1 C5:1 B4:1 C5:1 E5:2",
                            "F5:1 E5:1 D#5:1 E5:1 B5:1 A5:1 G#5:1 A5:1 B5:1 A5:1 G#5:1 A5:1 C6:2",
                            "A5:1 C6:1 B5:1 A5:1 G#5:1 A5:1 B5:1 A5:1 G#5:1 A5:1 E5:2"
                    )
            ),
            simpleSeed(
                    "Spring",
                    "Classical",
                    phrase(
                            "E4:1 E4:1 E4:2 D4:1 C4:1 D4:2 E4:2 F4:2 G4:2 G4:2 F4:2 E4:2 D4:2 C4:4",
                            "E4:1 E4:1 E4:2 D4:1 C4:1 D4:2 E4:2 F4:2 G4:2 G4:2 F4:2 E4:2 D4:2 C4:4",
                            "G4:2 A4:2 B4:2 C5:4 B4:2 A4:2 G4:4"
                    )
            ),
            simpleSeed(
                    "Moonlight Sonata",
                    "Classical",
                    phrase(
                            "G#3:1 C#4:1 E4:1 G#3:1 C#4:1 E4:1 G#3:1 C#4:1 E4:1",
                            "A3:1 C#4:1 E4:1 A3:1 C#4:1 E4:1 A3:1 C#4:1 E4:1",
                            "B3:1 D#4:1 F#4:1 B3:1 D#4:1 F#4:1 B3:1 D#4:1 F#4:1",
                            "C#4:1 E4:1 G#4:1 C#4:1 E4:1 G#4:1 C#4:1 E4:1 G#4:1"
                    )
            ),
            simpleSeed(
                    "Maple Leaf Rag",
                    "Jazz",
                    phrase(
                            "Ab4:1 C5:1 Eb5:1 F5:1 Ab5:2 F5:1 Eb5:1 C5:2 Ab4:2",
                            "Bb4:1 C5:1 Eb5:1 F5:1 G5:2 F5:1 Eb5:1 C5:2 Bb4:2",
                            "Ab4:1 C5:1 Eb5:1 F5:1 Ab5:2 F5:1 Eb5:1 C5:2 Ab4:4"
                    )
            ),
            simpleSeed(
                    "The Entertainer",
                    "Jazz",
                    phrase(
                            "D4:1 D#4:1 E4:2 C5:1 E4:1 C5:1 E4:1 C5:4",
                            "C5:1 B4:1 A4:1 G4:1 F#4:1 A4:1 C5:1 E5:4",
                            "D4:1 D#4:1 E4:2 C5:1 E4:1 C5:1 E4:1 C5:4"
                    )
            ),
            simpleSeed(
                    "Entry of the Gladiators",
                    "Theme",
                    phrase(
                            "C5:1 B4:1 C5:1 G4:1 E4:1 G4:1 C5:1 E5:1",
                            "D5:1 C5:1 D5:1 A4:1 F4:1 A4:1 D5:1 F5:1",
                            "E5:1 D5:1 C5:1 B4:1 C5:2 G4:2 C5:4"
                    )
            ),
            simpleSeed(
                    "Dance of the Hours",
                    "Theme",
                    phrase(
                            "E4:2 F4:2 G4:2 C5:2 B4:2 A4:2 G4:4",
                            "E4:2 F4:2 G4:2 C5:2 B4:2 A4:2 G4:4",
                            "A4:2 B4:2 C5:2 D5:2 E5:4 C5:4"
                    )
            ),
            simpleSeed(
                    "Morning Mood",
                    "Classical",
                    phrase(
                            "E4:2 D4:2 C4:2 D4:2 E4:2 G4:2 D4:4",
                            "C4:2 D4:2 E4:2 G4:2 A4:2 E4:2 G4:4",
                            "E4:2 D4:2 C4:2 D4:2 E4:2 G4:2 D4:4"
                    )
            ),
            simpleSeed(
                    "Hungarian Dance No 5",
                    "Classical",
                    phrase(
                            "D4:1 F4:1 A4:2 A4:1 G4:1 F4:2 E4:1 F4:1 G4:2 F4:4",
                            "D4:1 F4:1 A4:2 A4:1 G4:1 F4:2 E4:1 F4:1 G4:2 A4:4",
                            "Bb4:2 A4:1 G4:1 F4:2 E4:1 D4:1 C#4:4"
                    )
            ),
            simpleSeed(
                    "Radetzky March",
                    "March",
                    phrase(
                            "D4:2 F#4:2 A4:2 D5:4 A4:2 F#4:2 D4:4",
                            "E4:2 G4:2 A4:2 C#5:4 A4:2 G4:2 E4:4",
                            "D4:2 F#4:2 A4:2 D5:4 C#5:2 B4:2 A4:4"
                    )
            ),
            simpleSeed(
                    "Pomp and Circumstance",
                    "March",
                    phrase(
                            "G4:2 C5:2 B4:2 C5:2 D5:4 C5:2 B4:2 A4:4",
                            "G4:2 A4:2 B4:2 C5:2 D5:4 E5:4",
                            "D5:2 C5:2 B4:2 C5:2 D5:8"
                    )
            ),
            simpleSeed(
                    "Hallelujah Chorus",
                    "Classical",
                    phrase(
                            "C4:2 E4:2 G4:2 C5:4 G4:2 E4:2 C4:4",
                            "F4:2 A4:2 C5:2 F5:4 C5:2 A4:2 F4:4",
                            "G4:2 B4:2 D5:2 G5:4 D5:2 B4:2 G4:4",
                            "C5:2 B4:2 A4:2 G4:2 C5:8"
                    )
            ),
            simpleSeed(
                    "Toreador Song",
                    "Classical",
                    phrase(
                            "C4:2 C4:2 C4:2 E4:2 G4:4 E4:2 C4:2 G3:4",
                            "C4:2 C4:2 D4:2 E4:2 F4:4 D4:2 B3:2 G3:4",
                            "E4:2 E4:2 F4:2 G4:2 A4:4 G4:2 F4:2 E4:4"
                    )
            ),
            simpleSeed(
                    "La Marseillaise",
                    "March",
                    phrase(
                            "G4:2 G4:2 G4:2 C5:4 C5:2 D5:2 E5:4",
                            "E5:2 D5:2 C5:2 B4:2 C5:4 G4:4",
                            "C5:2 C5:2 D5:2 E5:2 F5:4 E5:2 D5:2 C5:4"
                    )
            ),
            simpleSeed(
                    "1812 Overture",
                    "Classical",
                    phrase(
                            "E4:2 E4:2 F4:2 G4:2 G4:2 F4:2 E4:2 D4:2",
                            "C4:2 C4:2 D4:2 E4:2 E4:3 D4:1 D4:4",
                            "G4:2 G4:2 A4:2 B4:2 C5:4 B4:2 A4:2 G4:4"
                    )
            ),
            simpleSeed(
                    "Danse Macabre",
                    "Theme",
                    phrase(
                            "G4:2 F#4:2 G4:2 E4:2 F4:2 D#4:2 E4:2 C4:2",
                            "D4:2 B3:2 C4:2 A3:2 B3:2 G#3:2 A3:4",
                            "G4:2 F#4:2 G4:2 E4:2 F4:2 D#4:2 E4:4"
                    )
            ),
            simpleSeed(
                    "Happy Birthday",
                    "Theme",
                    phrase(
                            "C4:2 C4:1 D4:3 C4:3 F4:3 E4:6",
                            "C4:2 C4:1 D4:3 C4:3 G4:3 F4:6",
                            "C4:2 C4:1 C5:3 A4:3 F4:3 E4:3 D4:6",
                            "A#4:2 A#4:1 A4:3 F4:3 G4:3 F4:6"
                    )
            ),
            simpleSeed(
                    "Brahms Lullaby",
                    "Chill",
                    phrase(
                            "E4:2 E4:2 G4:4 E4:2 E4:2 G4:4",
                            "E4:2 G4:2 C5:4 B4:4 A4:4 A4:4 G4:8",
                            "D4:2 E4:2 F4:4 D4:2 D4:2 E4:4",
                            "F4:2 G4:2 A4:4 G4:4 F4:4 E4:8"
                    )
            ),
            simpleSeed(
                    "Take Me Out to the Ball Game",
                    "March",
                    phrase(
                            "C4:2 C5:3 A4:1 G4:2 E4:2 G4:4",
                            "D4:2 D5:3 B4:1 A4:2 F4:2 A4:4",
                            "G4:2 G4:2 F4:2 E4:2 D4:2 E4:2 F4:2 G4:4",
                            "C5:2 C5:2 A4:2 G4:2 E4:4"
                    )
            ),
            simpleSeed(
                    "God Save the King",
                    "March",
                    phrase(
                            "G4:2 G4:2 A4:2 F#4:3 G4:1 A4:2 B4:2 B4:2 C5:2 B4:3 A4:1",
                            "G4:2 A4:2 G4:2 F#4:2 G4:4 D5:4",
                            "C5:2 B4:2 A4:2 G4:2 B4:3 C5:1 D5:2 C5:2 B4:2 A4:2 G4:4"
                    )
            ),
            simpleSeed(
                    "Rule Britannia",
                    "March",
                    phrase(
                            "G4:2 C5:2 B4:2 A4:2 G4:4 D5:4",
                            "E5:2 D5:2 C5:2 B4:2 C5:4 G4:4",
                            "A4:2 B4:2 C5:2 D5:2 E5:4 D5:2 C5:2 B4:4"
                    )
            ),
            simpleSeed(
                    "Aloha Oe",
                    "Folk",
                    phrase(
                            "G4:2 E4:2 C4:2 E4:2 G4:4 A4:2 G4:2 E4:4",
                            "F4:2 E4:2 D4:2 C4:2 D4:4 G4:4",
                            "G4:2 E4:2 C4:2 E4:2 G4:4 C5:2 B4:2 A4:4",
                            "G4:2 E4:2 D4:2 C4:8"
                    )
            ),
            simpleSeed(
                    "Mexican Hat Dance",
                    "Folk",
                    phrase(
                            "C4:1 E4:1 G4:2 C4:1 E4:1 G4:2 C4:1 E4:1 G4:2 C5:4",
                            "B4:1 A4:1 G4:2 F4:1 E4:1 D4:2 C4:4",
                            "G4:1 A4:1 B4:2 G4:1 A4:1 B4:2 G4:1 A4:1 B4:2 C5:4"
                    )
            ),
            simpleSeed(
                    "Liberty Bell March",
                    "Theme",
                    phrase(
                            "G4:2 E4:2 C4:2 E4:2 G4:4 E4:4",
                            "A4:2 F4:2 D4:2 F4:2 A4:4 F4:4",
                            "G4:2 E4:2 C4:2 E4:2 G4:2 C5:2 B4:2 A4:2 G4:4"
                    )
            ),
            simpleSeed(
                    "Stars and Stripes Forever",
                    "March",
                    phrase(
                            "C5:2 G4:2 E4:2 G4:2 C5:4 G4:4",
                            "D5:2 G4:2 F4:2 G4:2 D5:4 G4:4",
                            "E5:2 D5:2 C5:2 B4:2 A4:2 G4:2 E4:4",
                            "C5:2 D5:2 E5:2 G5:2 C5:8"
                    )
            ),
            simpleSeed(
                    "Colonel Bogey March",
                    "March",
                    phrase(
                            "G4:2 E4:2 R:2 E4:2 F4:2 G4:2 E4:4",
                            "G4:2 E4:2 R:2 E4:2 F4:2 G4:2 E4:4",
                            "A4:2 G4:2 F4:2 E4:2 D4:4 B3:4",
                            "C4:2 D4:2 E4:2 F4:2 G4:8"
                    )
            ),
            simpleSeed(
                    "Flight of the Bumblebee",
                    "Classical",
                    phrase(
                            "A4:1 G#4:1 G4:1 F#4:1 F4:1 E4:1 D#4:1 D4:1",
                            "C#4:1 C4:1 B3:1 C4:1 C#4:1 D4:1 D#4:1 E4:1",
                            "F4:1 F#4:1 G4:1 G#4:1 A4:1 G#4:1 G4:1 F#4:1",
                            "F4:1 E4:1 D#4:1 D4:1 C#4:1 C4:1 B3:1 A3:1"
                    )
            ),
            simpleSeed(
                    "Largo from New World Symphony",
                    "Classical",
                    phrase(
                            "G4:3 E4:1 E4:2 D4:2 C4:4 D4:4",
                            "E4:3 G4:1 G4:2 E4:2 D4:8",
                            "G4:3 E4:1 E4:2 D4:2 C4:4 D4:4",
                            "E4:3 D4:1 C4:2 B3:2 C4:8"
                    )
            ),
            simpleSeed(
                    "Moldau",
                    "Classical",
                    phrase(
                            "E4:2 G4:2 A4:2 B4:2 C5:4 B4:2 A4:2 G4:4",
                            "E4:2 G4:2 A4:2 B4:2 C5:4 D5:2 C5:2 B4:4",
                            "A4:2 B4:2 C5:2 B4:2 A4:2 G4:2 E4:4"
                    )
            ),
            simpleSeed(
                    "Greensleeves What Child Is This",
                    "Holiday",
                    phrase(
                            "A3:2 C4:4 D4:2 E4:3 F4:1 E4:2 D4:4",
                            "B3:2 G3:3 A3:1 B3:2 C4:4 A3:4",
                            "A3:2 C4:4 D4:2 E4:3 F4:1 E4:2 D4:4",
                            "B3:2 G3:3 A3:1 B3:2 C4:4 A3:4"
                    )
            ),
            simpleSeed(
                    "Miserlou",
                    "Folk",
                    phrase(
                            "E4:1 F4:1 G4:1 A4:1 B4:1 C5:1 D5:1 E5:2",
                            "D5:1 C5:1 B4:1 A4:1 G4:1 F4:1 E4:2",
                            "E4:1 F4:1 G4:1 A4:1 B4:1 C5:1 D5:1 E5:2",
                            "F5:1 E5:1 D5:1 C5:1 B4:1 A4:1 G4:2"
                    )
            ),
            simpleSeed(
                    "When Irish Eyes Are Smiling",
                    "Folk",
                    phrase(
                            "C4:2 E4:2 G4:2 C5:4 B4:2 A4:2 G4:4",
                            "E4:2 G4:2 A4:2 G4:2 E4:4 D4:4",
                            "C4:2 E4:2 G4:2 C5:4 B4:2 A4:2 G4:4",
                            "F4:2 E4:2 D4:2 C4:8"
                    )
            ),
            simpleSeed(
                    "Daisy Bell",
                    "Pop",
                    phrase(
                            "C4:2 E4:2 G4:2 C5:4 G4:2 E4:2 C4:4",
                            "D4:2 F4:2 A4:2 D5:4 A4:2 F4:2 D4:4",
                            "E4:2 G4:2 C5:2 E5:4 D5:2 C5:2 B4:4",
                            "C5:2 G4:2 E4:2 C4:8"
                    )
            ),
            simpleSeed(
                    "Let Me Call You Sweetheart",
                    "Pop",
                    phrase(
                            "G3:2 C4:2 E4:2 G4:4 E4:2 C4:2 G3:4",
                            "A3:2 D4:2 F4:2 A4:4 F4:2 D4:2 A3:4",
                            "G3:2 C4:2 E4:2 G4:4 C5:2 B4:2 A4:4",
                            "G4:2 E4:2 D4:2 C4:8"
                    )
            ),
            simpleSeed(
                    "Love Story",
                    "Pop",
                    120,
                    phrase(
                            "R:0.25 D4:1 D4:0.5 D4:0.5 G4:1 F#4:1 D4:1 D4:0.5 E4:0.5 E4:0.5",
                            "D4:0.5 F#4:0.5 D4:0.5 E4:1 D4:1 E4:1 F#4:1 E4:1 D4:0.5 E4:0.5",
                            "E4:0.5 D4:0.5 F#4:0.5 D4:0.5 E4:1 D4:1 E4:0.5 D4:0.5 F#4:1 E4:1",
                            "D4:1 E4:0.5 D4:0.5 F#4:1 E4:1 D4:0.5 D4:0.5 E4:1 F#4:0.5 E4:0.5",
                            "F#4:1 F#4:0.5 E4:0.5 F#4:1 F#4:0.5 R:0.5 D4:1"
                    )
            ),
            simpleSeed(
                    "Fifteen",
                    "Pop",
                    96,
                    phrase(
                            "R:2.25 G3:0.25 R:0.25 B3:0.25 R:0.25 B3:0.25 D4:0.5 R:0.25 D4:1.25 R:0.25",
                            "B3:0.25 R:0.25 B3:0.25 R:0.25 C4:1.25 R:0.25 B3:0.75 G3:0.5 R:0.25 C4:1.25",
                            "R:0.25 B3:0.5 R:0.25 G3:0.5 R:0.25 A3:0.5 R:0.25 B3:0.5 R:0.25 B3:0.5",
                            "R:0.25 B3:0.5 R:0.25 B3:0.5 R:0.25 G3:0.5 R:0.25 A3:1.25 R:0.25 G3:1",
                            "R:3.75 G3:0.25 R:0.25 G3:0.75 B3:0.5 R:0.25 D4:0.5 R:0.25 D4:0.5 R:0.25",
                            "D4:1 R:0.25 G3:0.5 R:0.25 G3:0.75 R:0.5 B3:0.5 R:0.25 D4:0.5 R:0.25",
                            "D4:0.5 R:0.25 D4:1 R:0.25 B3:0.5 R:0.25 B3:0.5 R:0.25 B3:0.5 R:0.25",
                            "B3:0.5 R:0.25 B3:0.5 R:0.25 B3:0.5 R:0.25 A3:0.5 R:0.25 A3:0.5 R:0.25",
                            "G3:0.5 R:0.25 G3:1 R:5.75 B3:0.25 R:0.25 B3:0.25 R:0.25 G4:0.5 R:0.25",
                            "F#4:0.5 R:0.25 D4:1 R:0.25 D4:0.5 R:0.25 B3:0.5 E4:0.25 R:0.25 D4:0.5",
                            "R:0.25 D4:0.75 R:0.25 D4:1 R:0.25 B3:0.5 R:0.25 A3:0.5 R:0.25 B3:0.75",
                            "R:0.5 D4:1 R:0.25 G3:0.5 R:0.25 G3:0.5 R:0.25 D4:1.25 R:1.25 G3:2",
                            "R:0.5 G3:0.25 R:0.25 G3:0.25 R:0.25 G3:0.25 R:0.25 G3:0.25 R:0.25 G3:1",
                            "R:0.25 G4:0.5 R:0.25 F#4:0.5 R:0.25 D4:2 R:0.5 B3:0.5 R:0.25 E4:0.5",
                            "R:0.25 D4:0.25 R:0.25 D4:0.5 R:0.25 D4:0.25 R:0.25 D4:1 R:0.25 B3:0.25",
                            "R:0.25 B3:0.5 R:0.25 A3:0.5 R:0.25 B3:0.5 R:0.25 B3:0.5 R:0.25 D4:1",
                            "R:0.25 G3:0.5 R:0.25 G3:0.5 R:0.25 G3:2 R:3 C4:0.5 R:0.25 B3:3",
                            "R:0.75 D4:0.5 R:0.25 E4:0.5 R:0.25 G4:0.5"
                    )
            ),
            simpleSeed(
                    "Escape",
                    "Pop",
                    126,
                    phrase(
                            "R:0.5 B4:0.25 R:0.25 F#5:2 R:0.25 E5:0.25 R:0.75 Eb5:1 R:0.25 E5:0.5",
                            "R:2.5 C#5:0.5 R:0.25 Eb5:0.75 R:0.25 Eb5:0.25 R:0.25 E5:0.5 R:0.25 Eb5:0.25",
                            "R:0.25 B4:1 R:0.25 F#4:0.5 R:3.25 B4:0.25 R:0.25 F#5:1.75 R:0.5 E5:0.25",
                            "R:0.25 Eb5:0.25 R:0.25 Eb5:1 R:0.25 E5:0.5 R:2 F#5:0.25 R:0.25 F#5:0.25",
                            "R:0.25 F#5:0.5 R:0.5 E5:0.25 R:0.25 E5:0.5 R:0.25 E5:0.75 R:0.25 Eb5:0.25",
                            "R:0.25 Eb5:0.25 R:0.25 Eb5:0.5 R:0.5 C#5:0.25 R:0.25 C#5:0.5 R:0.5 C#5:0.75",
                            "R:0.25 B4:0.5"
                    )
            ),
            simpleSeed(
                    "These Words",
                    "Dance",
                    97,
                    phrase(
                            "R:0.25 G4:0.25 F4:0.5 D4:0.25 C5:2.25 R:0.25 A4:1.25 G4:0.5 G4:1 R:0.25",
                            "F4:1.25 R:0.5 C5:1 A4:1 G4:1 R:0.25 F4:2.25 R:2 G4:0.25 A4:0.25",
                            "A4:0.25 G4:0.25 A4:0.25 A4:0.25 G4:0.25 A4:0.25 A4:0.25 G4:0.25 A4:0.5 G4:0.5",
                            "R:1 C5:2.25 R:0.25 A4:1.25 G4:0.5 G4:1 R:0.25 F4:1.25 R:0.5 C5:1",
                            "A4:1 G4:1 R:0.25 F4:2.25 R:1.75 C4:0.25 Bb4:0.25 A4:2 R:1 A4:0.5",
                            "R:0.25 A4:0.25 G4:0.75"
                    )
            ),
            simpleSeed(
                    "Bad Day",
                    "Rock",
                    70,
                    phrase(
                            "R:0.25 C4:0.25 R:0.25 Eb4:0.5 R:0.25 F4:3.75 R:0.5 Bb3:0.25 R:0.25 Bb3:0.5",
                            "R:0.25 Bb3:0.25 R:0.25 G4:0.5 R:0.25 Bb4:0.25 R:0.25 F4:0.75 R:0.25 Eb4:0.75",
                            "R:0.75 Bb3:0.25 R:0.25 G4:0.5 R:0.25 Bb4:0.25 R:0.25 F4:0.75 R:0.25 Eb4:0.5",
                            "R:1 Bb3:0.25 R:0.25 G4:0.5 R:0.25 Bb4:0.25 R:0.25 F4:0.75 R:0.25 Eb4:0.75",
                            "R:0.25 C4:0.75 R:0.25 G4:0.5 R:0.25 Ab4:0.25 R:0.25 G4:0.5 R:0.25 G4:0.25",
                            "R:0.25 F4:0.5 R:1 C4:0.25 R:0.25 G4:0.5 R:0.25 Bb4:0.25 R:0.25 F4:0.75",
                            "R:0.25 Eb4:0.75 R:0.75 Bb3:0.25 R:0.25 G4:0.5 R:0.25 Bb4:0.25 R:0.25 F4:0.75",
                            "R:0.25 Eb4:0.5 R:1 Bb3:0.25 R:0.25 G4:0.5 R:0.25 Bb4:0.25 R:0.25 F4:0.75",
                            "R:0.25 Eb4:0.75 R:0.25 Eb4:0.5 R:0.25 Eb4:0.25 R:0.25 G4:0.5 R:0.25 Ab4:0.25",
                            "R:0.25 G4:0.5 R:0.25 G4:0.25 R:0.25 F4:0.5 R:1 Bb3:0.25 R:0.25 G4:0.5",
                            "R:0.25 Bb4:0.25 R:0.25 F4:0.75 R:0.25 Eb4:0.75 R:0.75 Bb3:0.25 R:0.25 G4:0.5",
                            "R:0.25 Bb4:0.25 R:0.25 F4:0.75 R:0.25 Eb4:0.5 R:1 Bb3:0.25 R:0.25 G4:0.5",
                            "R:0.25 Bb4:0.25 R:0.25 F4:0.75 R:0.25 Eb4:0.75 R:0.25 C4:0.5 R:0.25 Bb3:0.25",
                            "R:0.25 G4:0.5 R:0.25 Bb4:0.25 R:0.25 F4:0.75 R:0.25 Eb4:0.5 R:1 Bb3:0.25",
                            "R:0.25 G4:0.5 R:0.25 Bb4:0.25 R:0.25 F4:0.75 R:0.25 Eb4:0.5 R:0.25 C4:0.25",
                            "R:0.25 Bb3:1 R:3 G4:0.25 R:0.25 G4:0.5 R:0.25 Bb3:0.25 R:0.25 C4:0.75",
                            "R:0.25 Eb4:0.75"
                    )
            ),
            simpleSeed(
                    "Monkeys Spinning Monkeys",
                    "Theme",
                    phrase(
                            "C5:4 G#5:2 A5:4 G#5:4 F#5:2 G5:4 D#5:2 E5:8",
                            "E5:2 C5:4 G#5:2 A5:4 G#5:4 F#5:4 G5:8",
                            "B5:4 G5:2 E5:2 D5:2 C5:4 G#5:2 A5:4 G#5:4",
                            "E5:2 F#5:2 G5:2 D#5:2 E5:4 E5:2 C5:4 G#5:2 A5:4",
                            "G#5:4 G5:2 F5:2 E5:2 D5:2 C5:8"
                    )
            ),
            simpleSeed(
                    "Sneaky Snitch",
                    "Theme",
                    phrase(
                            "A5:1 G#5:1 A5:2 F5:2 E5:2 R:2 F5:1 G5:1 R:6",
                            "G#5:1 A5:2 E5:2 D5:2 A5:1 G#5:1 A5:2 F5:2 E5:2 R:2 F5:1 G5:1",
                            "A5:4 D5:2 A5:1 G#5:1 A5:2 F5:2 E5:2 R:2 F5:1 G5:1",
                            "G5:2 F5:2 E5:2 D5:2 C#5:4 A5:1 G#5:1 A5:2 F5:4",
                            "D5:2 Bb4:1 A4:1 Bb4:2 A4:2 G4:2 F4:2 E4:2 C#4:2 D4:8"
                    )
            ),
            simpleSeed(
                    "Scheming Weasel",
                    "Theme",
                    phrase(
                            "C5:2 D5:1 Eb5:1 F5:1 G5:1 Ab5:1 G5:1 F5:1 Eb5:1 D5:1 C5:4",
                            "F5:1 G5:1 Ab5:1 G5:1 F5:1 Eb5:1 D5:1 C5:2 G4:1 Ab4:4",
                            "C5:2 D5:1 Eb5:1 F5:1 G5:1 Ab5:1 G5:1 F5:1 G5:1 Ab5:1 G5:1 F5:1 Eb5:1 D5:1 C5:4",
                            "F5:2 E5:1 F5:1 G5:1 Ab5:1 F5:1 Ab5:1 C6:2 C6:2 C#6:2 C6:4",
                            "F5:2 E5:1 F5:1 G5:1 Ab5:1 F5:2 G5:4 D5:4 C5:8"
                    )
            )
    );

    private static final List<SongSeed> SONG_SEEDS = buildSongSeeds();

    private static SongSeed simpleSeed(String title, String style, String pattern) {
        return new SongSeed(title, style, pattern, List.of());
    }

    private static SongSeed simpleSeed(String title, String style, int bpm, String pattern) {
        return new SongSeed(title, style, pattern, List.of(), bpm);
    }

    private static int defaultBpm(String style) {
        return switch (normalizeStyle(style)) {
            case "Chill" -> 82;
            case "Jazz", "Pop" -> 96;
            case "Folk", "Holiday" -> 108;
            case "Classical" -> 116;
            case "March" -> 120;
            case "Dance", "Rock" -> 128;
            case "Theme" -> 132;
            default -> 112;
        };
    }

    private static int defaultBpm(String title, String style) {
        int offset = Math.floorMod(title.toLowerCase(Locale.ROOT).hashCode(), 17) - 8;
        return Math.max(60, defaultBpm(style) + offset);
    }


    private static List<SongSeed> buildSongSeeds() {
        List<SongSeed> seeds = new ArrayList<>(BASE_SONG_SEEDS);
        addMidiImportedSeeds(seeds);
        addRecentMidiDemoSeeds(seeds);
        Map<String, SongSeed> normalizedSeeds = new java.util.LinkedHashMap<>();
        for (SongSeed seed : seeds) {
            SongSeed normalizedSeed = seed.normalized();
            normalizedSeeds.putIfAbsent(canonicalSongKey(normalizedSeed), normalizedSeed);
        }
        return List.copyOf(normalizedSeeds.values());
    }

    private static String canonicalSongKey(SongSeed seed) {
        return seed.title().toLowerCase(Locale.ROOT);
    }

    private static String normalizeTitle(String title) {
        if (title.equalsIgnoreCase("Bridal Chorus")
                || title.equalsIgnoreCase("Hallelujah Chorus")
                || title.equalsIgnoreCase("London Bridge")) {
            return title.trim();
        }

        String normalized = title
                .replaceAll("(?i)\\b(solo)\\s+\\d+\\b", "")
                .replaceAll("(?i)\\b(pre-chorus|lead-out|chorus|verse|bridge|intro|instrumental|solo|outro|demo)\\b", "")
                .replaceAll("(?i)^\\s*(and|&)\\s+|\\s+(and|&)\\s*$", "")
                .replaceAll("\\s+", " ")
                .replaceAll("\\s+([?!.,])", "$1")
                .trim();
        return normalized.isEmpty() ? title.trim() : normalized;
    }

    private static String normalizeStyle(String style) {
        String compact = style.trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
        return switch (compact) {
            case "albumrock", "arenarock", "artrock", "classicrock", "countryrock", "folkrock",
                    "glamrock", "hardrock", "modernrock", "pianorock", "poprock", "postgrunge",
                    "rock", "sixtiesrock", "softrock" -> "Rock";
            case "alternative", "alternativerock", "alt", "grunge", "metal", "permanentwave" -> "Alt";
            case "dance", "dancepop", "disco", "dutchtrance", "italodance", "synthpop", "trance" -> "Dance";
            case "christmaspop", "electropop", "europop", "latinpop", "mellowgold", "mexicanpop",
                    "neomellow", "newwavepop", "pop", "retropop", "sunshinepop", "vintagepop" -> "Pop";
            case "anthem", "ceremony", "march", "patriotic", "sports" -> "March";
            case "adventure", "cartoon", "celebration", "cinematic", "circus", "comedy", "drama",
                    "fantasy", "gametheme", "horror", "musical", "scifi", "screentheme", "theme" -> "Theme";
            case "folk", "seashanty", "traditional" -> "Folk";
            case "holiday" -> "Holiday";
            case "classical" -> "Classical";
            case "jazz", "ragtime" -> "Jazz";
            case "funk", "motown", "rnb", "soul" -> "Soul";
            case "hiphop" -> "HipHop";
            case "country", "countrypop" -> "Country";
            case "chill", "lofi", "lullaby" -> "Chill";
            case "indie", "indierock", "lilith", "newwave" -> "Indie";
            case "original" -> "Original";
            default -> {
                yield compact.isEmpty() ? "Other" : Character.toUpperCase(compact.charAt(0)) + compact.substring(1);
            }
        };
    }

    private static void addMidiImportedSeeds(List<SongSeed> seeds) {

        // Imported from MulTTiPop aligned MIDI labels, distributed as CC-BY-4.0.

        seeds.add(simpleSeed(
                "Ain't Too Proud to Beg",
                "Soul",
                120,
                phrase(
                        "R:1 G4:1 R:1.5 A4:0.25 R:0.25 C5:0.25 R:0.25 E5:1 R:0.25 D5:0.25",
                        "R:0.25 D5:0.75 R:0.25 C5:0.25 A4:0.75 R:0.5 A4:0.25 R:0.25 D5:0.5 C5:0.5",
                        "R:0.25 A4:0.25 R:0.25 C5:0.5 A4:0.5 R:1 D5:0.25 R:0.25 E5:0.5 G5:1",
                        "R:0.5 D5:0.5 R:0.25 C5:0.5 R:0.5 C5:0.5 A4:0.75 R:0.75 G4:0.5 A4:0.25",
                        "R:0.25 C5:0.25 R:0.25 C5:0.5 R:0.25 A4:0.25 R:0.25 A5:0.75 R:0.25 E5:0.5",
                        "D5:0.5 R:0.25 C5:0.75 R:0.75 A4:0.25 R:0.25 E5:0.5 D5:0.75 R:0.25 C5:0.5",
                        "R:0.25 A4:0.25 R:0.25 C5:0.25 R:0.25 C5:0.25 R:0.25 C5:0.5 R:0.25 E5:0.5",
                        "R:0.5 A5:0.75 R:0.5 E5:0.5 D5:0.5 C5:0.25 R:0.25 C5:0.5 R:0.5 C5:0.25",
                        "R:0.25 A4:1 R:0.25 A4:0.5 C5:0.25 R:0.25 C5:0.5 R:0.25 A4:0.25 R:0.25",
                        "E5:1"
                )
        ));

        seeds.add(simpleSeed(
                "For Once In My Life",
                "Soul",
                108,
                phrase(
                        "R:0.75 G4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.75",
                        "R:0.75 G4:0.25 R:0.25 A4:0.5 R:0.25 A4:0.75 R:0.25 A4:0.5 R:0.25 G4:0.5",
                        "R:0.25 A4:0.5 R:0.25 D5:0.75 C5:1 R:0.75 Bb4:0.25 R:0.25 Bb4:0.25 G4:0.25",
                        "R:0.25 Bb4:0.75 R:0.25 C5:1 D5:0.5 R:0.25 C5:0.5 Bb4:2.25 R:0.75 C5:0.75",
                        "Bb4:0.5 R:0.25 Bb4:0.25 R:0.25 Bb4:0.25 R:0.25 Bb4:1 R:0.5 Bb4:0.5 R:0.25",
                        "G4:0.25 R:0.25 Bb4:0.75 R:0.25 Bb4:0.25 R:0.25 Bb4:0.25 R:0.25 Bb4:0.25 R:0.25",
                        "E5:0.75 D5:0.75 R:0.75 C5:0.25 R:0.25 C5:0.25 A4:0.25 R:0.25 C5:0.75 R:0.25",
                        "D5:1 E5:0.25 R:0.25 D5:0.5 C5:2.25 R:1 D5:0.25 R:0.25 F5:0.75 R:0.25",
                        "F5:0.25 R:0.25 F5:0.25 R:0.25 F5:1 R:0.5 F5:0.5 R:0.25 D5:0.25 R:0.25",
                        "F5:0.75 R:0.25 F5:0.25 R:0.25 F5:0.25 R:0.25 F5:0.75 A5:0.5 G5:0.25 F5:0.25",
                        "D5:0.5 R:0.5 F5:0.75 D5:0.75 R:0.25 A4:0.75 R:0.25 A4:0.75 G4:0.5 F4:1.5",
                        "R:1.75 E5:0.75 D5:0.25 R:0.25 C5:0.25 A4:0.25 C5:0.5 A4:0.25 C5:0.25 R:0.25",
                        "D5:2 A4:0.5 G4:0.5 R:0.25 F4:1.5 R:1.75 D5:0.5 R:0.5 A5:1 R:0.5",
                        "A5:0.5 R:0.25 G5:0.75 R:0.25 F5:0.75 R:0.25 A5:0.5 R:1 A5:0.5 R:0.5",
                        "F5:0.75 R:0.25 D5:0.5 C5:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Crying",
                "Rock",
                98,
                phrase(
                        "R:0.25 C#4:1 R:0.25 D4:0.5 R:0.25 A3:0.75 B3:0.75 F#3:0.5 R:0.25 A3:1.75",
                        "R:0.5 A3:0.75 F#4:1.25 C#4:2.5 R:0.5 D4:0.5 R:0.25 A3:0.5 B3:0.75 F#3:0.75",
                        "A3:2 R:0.5 A3:0.5 R:0.25 F#4:1 R:0.25 C#4:2.25 R:0.25 A3:0.5 R:0.25",
                        "G3:2.5 R:1 F#3:0.5 G3:0.5 F#3:2.75 R:1 E3:0.25 R:0.25 E3:0.25 R:0.25",
                        "E3:0.5 R:0.25 D3:3 R:0.5 C#3:0.25 R:0.25 D3:0.5 C#3:1.5 R:0.25 C#3:0.75",
                        "D3:1.25 E3:0.75 R:0.25 E3:0.5 D3:3 R:1.25 E3:0.75 D3:3 R:1.25 E3:0.75",
                        "D3:3.25 R:1 F#3:1.5 R:0.25 G3:0.25 R:0.25 F#3:0.5 E3:0.5 R:0.25 D3:0.25",
                        "R:0.25 D3:0.25 R:0.25 D3:0.5 R:0.25 A3:4 R:0.25 D3:0.25 R:0.25 D3:3",
                        "R:0.75 C#3:0.5 R:0.25 D3:0.25 R:0.25 A3:3.25 R:0.25 D3:0.25 R:0.25 D3:0.5",
                        "R:0.25 C#3:2.5 R:0.5 C#3:0.5 R:0.25 D3:0.5 R:0.25 E3:0.25 R:0.25 E3:0.5",
                        "R:0.25 D3:3.25"
                )
        ));

        seeds.add(simpleSeed(
                "Those Were The Days",
                "Pop",
                84,
                phrase(
                        "R:0.25 Ab4:0.75 R:0.25 Ab4:1.25 R:0.25 F#4:0.5 R:0.25 F#4:1.25 R:0.25 F#4:0.5",
                        "R:0.25 F#4:0.75 E4:0.5 R:0.25 E4:1.25 R:0.25 D4:0.5 R:0.25 D4:1.25 R:0.25",
                        "B3:0.75 R:0.25 C#4:0.5 D4:1 R:0.25 E4:1.75 R:0.25 F#4:0.75 R:0.25 Ab4:0.75",
                        "F#4:0.75 E4:0.75 D4:0.75 C#4:2.25 R:1.25 A3:0.75 B3:0.75 C#4:0.75 E4:1 R:0.25",
                        "D4:0.5 R:0.25 D4:1 R:0.5 D4:0.75 E4:0.75 R:0.25 F#4:0.75 B4:1.25 R:0.25",
                        "A4:0.5 R:0.25 A4:1.25 R:0.25 F#4:0.75 Ab4:0.25 A4:1 R:0.25 C#5:1.25 R:0.25",
                        "B4:0.75 A4:1.5 Ab4:0.75 F#4:0.5 R:0.25 F4:1 F#4:2.5 R:1.25 A4:0.5 R:0.25",
                        "A4:0.75 Ab4:0.5 R:0.25 Ab4:1.5 F#4:0.25 R:0.25 F#4:1 R:0.25 F#4:0.25 R:0.25",
                        "F#4:0.75 E4:0.5 R:0.25 E4:1.5 R:0.25 D4:0.25 R:0.25 D4:1.25 R:0.25 B3:0.75",
                        "C#4:0.75 D4:0.5 C#4:1.75 R:0.5 F4:0.5 R:0.25 A4:0.75 Ab4:0.75 F#4:0.25 F4:1",
                        "F#4:4.25"
                )
        ));

        seeds.add(simpleSeed(
                "Brown Eyed Girl",
                "Rock",
                147,
                phrase(
                        "R:1 E4:2.75 G3:0.5 C4:1.25 G3:0.5 R:1.25 A3:1.75 E4:0.5 F#4:0.75 R:0.5",
                        "B3:0.75 G4:0.5 R:0.25 A4:0.25 C4:0.5 R:0.25 G4:0.5 B3:0.5 R:0.25 G4:0.5",
                        "B3:0.5 F#4:0.75 A3:0.5 R:0.25 E4:2 G3:0.5 B3:1.5 E4:1 G3:0.5 R:0.25",
                        "G3:0.25 C4:0.5 R:0.25 D4:0.75 G3:0.5 R:0.25 E4:0.5 R:0.75 G4:0.5 A3:0.25",
                        "F#4:0.25 R:0.25 A3:1 F#4:1 R:0.25 E4:0.75 A3:0.5 F#4:0.25 R:0.25 F#4:0.25",
                        "R:0.5 G3:0.75 G4:0.5 R:0.25 G4:0.25 B3:0.5 R:0.25 A4:0.5 C4:0.5 R:0.25",
                        "A4:0.5 C4:0.5 B4:0.5 D4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.25 R:0.25 G4:0.25",
                        "R:0.5 F#4:0.5 A4:0.5 R:1.25 A3:0.25 R:1 D3:0.25 R:0.25 A3:0.25 R:0.25",
                        "D4:0.25 R:0.5 D3:0.25 R:0.25 A3:0.25 R:0.25 D4:0.25 R:0.75 A3:0.25 R:0.25",
                        "D4:0.25 R:0.75 B3:0.75"
                )
        ));

        seeds.add(simpleSeed(
                "Down On The Corner",
                "Rock",
                102,
                phrase(
                        "R:0.25 E3:1 F2:1 R:0.25 F2:0.75 R:0.25 C3:0.5 R:0.25 E3:0.25 R:0.5",
                        "C3:0.25 R:0.25 C3:0.5 E3:0.5 G2:1 R:0.25 G2:0.75 R:0.25 C3:0.5 E3:0.25",
                        "R:0.5 C3:0.25 R:0.25 C3:0.5 R:0.25 E3:0.5 F2:1 R:0.25 F2:0.75 R:0.25",
                        "C3:0.5 R:0.25 E3:0.25 R:0.5 C3:0.25 R:0.25 C3:0.5 E3:0.5 G2:1 R:0.25",
                        "G2:0.75 R:0.25 C3:0.5 E3:0.25 R:0.5 C3:0.25 R:0.25 C3:0.5 R:0.5 C3:0.75",
                        "R:0.25 A2:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "What Becomes Of The Brokenhearted",
                "Soul",
                98,
                phrase(
                        "R:0.25 C4:0.5 Bb3:0.5 R:1.25 C4:0.5 R:0.25 D4:0.5 R:0.25 C4:1 R:0.25",
                        "C4:0.5 R:0.25 D4:0.5 R:0.25 E4:1.5 R:0.25 G4:0.5 R:0.25 E4:1 R:0.25",
                        "D4:0.5 C4:0.5 R:1.25 A4:0.5 R:0.25 G4:0.5 R:0.25 E4:0.25 D4:0.25 C4:0.5",
                        "R:0.25 D4:0.5 C4:0.5 R:0.25 E4:1 R:0.25 E4:1 R:0.25 D4:0.75 C4:0.5",
                        "R:0.25 D4:0.5 R:0.75 E4:1 R:0.25 E4:0.5 R:0.25 E4:0.5 R:0.25 E4:0.5",
                        "R:0.25 D4:0.5 R:0.25 C4:0.5 R:0.75 E4:1 R:0.25 E4:0.5 R:0.25 G4:0.5",
                        "R:0.25 E4:0.5 R:0.25 D4:0.5 R:0.25 C4:0.5 R:0.75 D4:2 R:0.5 B3:1.75",
                        "D4:0.5 C4:1.25"
                )
        ));

        seeds.add(simpleSeed(
                "I Can't Help Myself (Sugar Pie Honey Bunch)",
                "Rock",
                127,
                phrase(
                        "R:0.25 A2:0.25 R:0.25 C3:0.25 R:0.25 E4:1 R:0.5 C4:1.25 E4:1.25 R:1",
                        "E4:0.75 R:0.75 C4:1 E4:1 R:1.5 B3:0.5 G4:0.5 R:0.75 B3:1.5 G4:1.5",
                        "R:0.75 B3:0.5 G4:0.25 R:1 B3:1.75 D4:1.75 G4:1.75 R:0.5 A3:0.5 F4:0.5",
                        "R:1 A3:1.5 F4:1.75 R:0.75 F4:0.25 R:1.25 D4:1.75 F4:1.75 R:0.5 A3:0.5",
                        "C4:0.25 F4:0.25 R:1 C4:1.25 F4:1.25 R:1 G4:0.25 R:0.75 B3:0.25 G4:0.25",
                        "R:0.5 B3:0.25 G4:0.25 R:0.25 A4:0.75 E4:0.75 R:0.75 E4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Never My Love",
                "Pop",
                94,
                phrase(
                        "R:3.75 F4:0.5 R:0.25 F#4:0.5 R:0.25 Ab4:0.5 R:0.25 Ab4:0.75 R:0.5 Eb5:1.75",
                        "R:0.25 C5:1 R:0.25 Ab4:0.5 R:0.25 F#4:2 R:1.25 Eb4:0.5 R:0.25 F4:0.5",
                        "R:0.25 F#4:0.25 R:0.25 F#4:1.25 R:0.25 C#4:0.5 R:0.25 C#4:0.25 R:0.25 C#4:2.75",
                        "R:3 C#4:0.25 R:0.25 Eb4:0.25 R:0.25 F4:0.75 R:0.5 F4:2 R:6.25 C#4:0.25",
                        "R:0.25 Eb4:0.25 R:0.25 F4:1 R:0.25 F4:1.75"
                )
        ));

        seeds.add(simpleSeed(
                "Wouldn't It Be Nice",
                "Pop",
                122,
                phrase(
                        "R:1.75 A3:0.25 R:0.25 A3:0.5 R:0.25 Bb3:0.25 R:0.25 C4:0.5 R:0.25 D4:0.25",
                        "R:0.25 E4:0.75 R:0.25 F4:0.25 R:0.25 G4:0.75 R:0.25 Bb3:0.5 R:0.25 D4:1",
                        "R:0.25 C4:1 R:1 A3:0.25 R:0.25 A3:0.75 R:0.25 Bb3:0.25 R:0.25 C4:0.5",
                        "R:0.25 D4:0.25 R:0.25 E4:0.75 R:0.25 F4:0.25 R:0.25 C5:2.75 R:0.25 F4:0.75",
                        "R:0.25 A4:1 R:0.25 G4:4.25 R:0.5 A4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Brown Eyed Girl",
                "Rock",
                148,
                phrase(
                        "R:0.75 G3:0.25 R:0.75 D4:0.25 R:0.25 D4:0.5 R:0.5 D4:0.75 R:0.25 E4:0.75",
                        "C4:0.5 R:0.25 D4:0.5 R:0.25 C4:0.5 R:0.25 C4:0.75 R:0.25 B3:0.5 R:0.25",
                        "A3:0.5 R:0.25 B3:0.5 R:0.25 G3:0.25 R:0.25 A3:0.5 R:1 D3:0.5 A3:0.25",
                        "B3:0.25 R:0.25 B3:0.5 R:0.25 A3:0.5 G3:0.5 R:0.5 D4:0.25 R:0.25 D4:0.5",
                        "R:0.5 D4:0.75 R:0.25 E4:0.75 C4:0.5 R:0.25 D4:0.5 R:0.25 C4:0.5 R:0.25",
                        "C4:0.75 R:0.25 B3:0.5 R:0.25 A3:0.5 R:0.25 B3:0.5 R:0.25 G3:0.25 R:0.25",
                        "A3:0.5 R:1.75 B3:0.25 R:0.25 B3:0.5 R:0.25 A3:0.5 G3:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "More Today Than Yesterday",
                "Rock",
                142,
                phrase(
                        "R:0.25 G4:0.5 R:0.25 G4:0.5 R:0.5 G4:0.5 R:0.5 G4:0.5 R:0.5 G4:0.5",
                        "R:0.5 E4:0.5 R:0.25 E4:0.5 R:0.25 E4:0.5 R:0.25 E4:0.5 R:0.5 F#4:0.5",
                        "R:0.5 F#4:0.5 R:0.25 F#4:0.5 R:0.25 F#4:0.5 R:0.25 F#4:0.5 R:0.5 F#4:0.5",
                        "R:0.25 F#4:0.5 R:0.25 F#4:0.5 R:0.5 G4:0.5 R:0.5 G4:0.5 R:0.5 G4:0.5",
                        "R:0.5 G4:0.5 R:0.5 E4:0.5 R:0.25 E4:0.5 R:0.25 E4:0.5 R:0.25 E4:0.5",
                        "R:0.5 F#4:0.5 R:0.5 F#4:0.5 R:0.25 F#4:0.5 R:0.25 F#4:0.5 R:0.25 F#4:0.5",
                        "R:0.5 F#4:0.5 R:0.25 F#4:0.5 R:0.25 F#4:0.5 R:0.5 G4:0.5 R:0.5 G4:0.5",
                        "R:0.5 G4:0.5 R:0.5 G4:0.5 R:0.5 E4:0.5 R:0.25 E4:0.5 R:0.25 E4:0.5",
                        "R:0.25 E4:0.5 R:0.5 F#4:0.5 R:0.5 F#4:0.5 R:0.25 F#4:0.5 R:0.25 F#4:0.5",
                        "R:0.25 F#4:0.5 R:0.5 F#4:0.5 R:0.25 F#4:0.5 R:0.25 F#4:0.5 R:0.5 B4:0.5",
                        "R:0.5 B4:0.5 R:0.5 B4:0.5 R:0.25 B4:0.25 R:0.5 E4:0.5 R:0.5 E4:0.5",
                        "R:0.25 E4:0.5 R:0.25 E4:0.5 R:0.5 G4:0.5 R:0.5 G4:0.5 R:0.25 G4:0.5",
                        "R:0.25 G4:0.5 R:0.25 G4:0.5 R:0.5 G4:0.5 R:0.25 G4:0.5 R:0.25 C4:0.25",
                        "G4:0.5 R:0.25 C4:0.25 R:0.25 F#4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Lightnin' Strikes",
                "Pop",
                133,
                phrase(
                        "R:0.25 C6:0.5 R:0.5 C6:1.25 R:0.5 C6:1.75 R:0.25 C6:1.5 R:0.25 C6:0.5",
                        "R:0.25 C6:0.5 R:0.25 C6:1.25 R:0.25 Bb5:2.25 R:3.5 C6:1.25 R:0.5 C6:1.5",
                        "R:0.25 C6:1.5 R:0.5 C6:0.5 R:0.25 C6:0.5 R:0.25 C6:1.25 R:0.25 Bb5:3.25",
                        "R:2.5 G4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Lightnin' Strikes",
                "Pop",
                128,
                phrase(
                        "R:0.25 Ab3:0.25 Ab3:0.75 Ab2:0.5 R:0.25 Bb3:0.5 Eb3:1.75 Bb3:1 G4:1.75 Eb4:1",
                        "Bb3:0.5 R:0.5 G3:0.75 Bb3:1.25 G3:0.75 Eb3:0.5 R:0.25 C#3:0.25 R:0.25 Ab3:0.5",
                        "R:0.25 Ab3:0.75 Eb3:0.5 R:0.25 Bb3:0.5 Eb3:1.75 Bb3:1 G4:1.5 Eb4:0.75 R:0.25",
                        "Bb3:1.5 Eb4:0.5 Bb2:0.75 Bb3:0.5 R:0.25 C#3:0.25 R:0.25 Ab3:0.5 R:0.25 Ab3:0.75",
                        "Ab2:0.5 R:0.25 Eb3:1.25 Eb4:1.5 Bb3:1 Eb3:0.5 R:1.25 Bb2:1.75 Bb3:0.5 Eb4:0.75",
                        "Bb3:0.5 R:0.25 C#3:0.25 R:0.25 Ab3:0.5 Eb3:0.75 Ab2:0.25 R:0.25 Eb3:1.25 Eb4:1.25",
                        "Bb3:1 Eb3:0.5 R:1.25 G3:0.75 Bb3:1.25 G3:0.75 Eb3:0.25 R:0.25 C#3:0.25 R:0.25",
                        "Ab3:0.5 R:0.25 Eb3:0.75 Ab2:0.5 R:0.25 G3:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Easy",
                "Soul",
                66,
                phrase(
                        "R:0.25 Bb4:1 R:0.5 F4:6 C#4:6 Ab3:5.75 R:0.25 Eb3:3.5 Ab2:3 Ab3:2.5",
                        "R:0.25 C3:2.5 G3:3.5 Bb3:3.5 Eb4:3 R:0.25 Bb2:2.5 Ab3:3.5 C#4:3.5 F4:3.25",
                        "R:0.25 F4:3.5 C#4:3 Ab3:2.5 R:0.25 C4:2 Eb4:1.75 Ab3:3 Ab4:1.25 Eb4:1.5",
                        "C4:0.75 R:0.25 G2:2 Eb3:2 Bb3:2 C4:1.75 R:1.5 F4:3.5 C#4:3.5 Bb4:3",
                        "Ab3:2.5 R:0.25 F4:3.5 C#4:3 Ab3:2.5 R:0.25 Ab3:2 C4:2 Eb3:3 C4:1.5",
                        "Ab3:0.75 R:0.25 C3:2.5 G3:3.5 Bb3:3.5 Eb4:3.25 R:0.25 Bb2:2 Ab3:2 C#4:2",
                        "F4:1.75 R:2 F4:3 C#4:2.75 Ab3:0.75 R:0.25 F#3:3.5 C#4:2.5 R:0.25 Ab3:1.5",
                        "C#4:1.25 F3:1.25 F4:1.25 R:0.5 C#4:1.5 F4:1.25 Ab3:1.25 R:0.5 Ab3:6.5 Eb3:6"
                )
        ));

        seeds.add(simpleSeed(
                "Sweet Talkin' Woman",
                "Rock",
                122,
                phrase(
                        "R:0.25 E5:0.25 R:0.25 G5:0.5 R:0.25 E5:0.5 R:0.25 A5:2 R:0.25 G5:2",
                        "R:0.25 E5:0.5 R:0.25 E5:0.5 R:0.25 E5:0.5 R:0.25 E5:1 R:0.25 B4:1.5",
                        "R:0.5 C5:0.5 R:0.25 C5:0.5 R:0.25 C5:0.5 R:0.25 G4:0.5 R:0.25 G4:1",
                        "R:1 C5:0.5 R:0.25 C5:0.5 R:0.25 C5:0.5 R:0.25 E5:0.5 F5:0.25 R:0.25",
                        "D5:1 R:0.25 A5:2 R:0.25 G5:2 R:0.25 E5:0.5 R:0.25 E5:0.5 R:0.25",
                        "E5:0.5 R:0.25 E5:1 R:0.25 B4:1.5 R:0.5 C5:0.5 R:0.25 A5:1 R:0.25",
                        "G5:1 R:0.25 E5:0.5 R:0.25 D5:1 R:0.25 C5:0.5 R:0.25 C5:0.5 R:0.25",
                        "A4:0.5 R:0.25 E5:1 R:0.25 D5:1 R:10.75 G5:0.5 E5:0.25 C5:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Rosalinda's Eyes",
                "Pop",
                163,
                phrase(
                        "R:2.5 D5:0.75 R:0.25 D5:0.25 D5:0.75 R:0.25 C5:0.75 R:0.25 C5:0.75 C5:0.75",
                        "R:0.25 C5:0.75 B4:0.75 R:0.25 A4:0.75 R:0.25 G4:0.75 R:0.25 G4:0.75 F#4:0.25",
                        "D4:0.25 R:0.25 E4:0.25 R:0.25 D4:0.75 R:2.25 D5:0.75 R:0.25 D5:0.25 R:0.25",
                        "D5:0.75 R:0.25 C5:0.75 R:0.25 C5:0.75 R:0.25 C5:0.75 R:0.25 C5:0.75 R:0.25",
                        "B4:0.75 R:0.25 A4:0.75 R:0.25 G4:0.75 R:0.25 G4:0.75 R:0.25 F#4:0.25 D4:0.25",
                        "R:0.25 E4:0.25 R:0.25 D4:0.75 R:2.25 D5:0.25 R:0.25 D5:0.75 D5:1 R:0.25",
                        "E5:0.25 R:0.25 G4:0.75 R:0.25 A4:1 R:0.75 D5:0.25 R:0.25 D5:1 R:0.25",
                        "D5:0.25 R:0.25 D5:0.75 R:0.25 E5:0.75 R:0.25 G4:0.75 A4:1 R:0.25 B4:0.75",
                        "R:0.25 B4:0.25 R:0.25 B4:0.75 R:0.25 C5:0.75 R:0.25 B4:0.75 R:0.25 B4:0.75",
                        "R:0.25 A4:0.75 B4:0.75 R:0.25 B4:1 R:1.5 A4:0.25 R:0.25 A4:1 G4:0.25",
                        "R:0.25 A4:0.75 R:0.25 B4:0.25 R:0.25 A4:0.75 R:0.25 G4:2.5"
                )
        ));

        seeds.add(simpleSeed(
                "Turn to Stone",
                "Rock",
                141,
                phrase(
                        "R:4 Ab3:0.5 R:0.25 C#4:0.5 R:0.25 B3:0.5 B3:0.75 R:1.25 Ab3:0.5 R:0.25",
                        "C#4:0.5 R:0.25 B3:0.5 R:0.25 B3:0.75 R:0.75 C#4:0.5 R:0.25 Ab3:0.75 R:0.25",
                        "F#3:0.5 R:0.25 F#3:0.75 R:5.25 C#4:0.5 R:0.25 B3:0.5 R:0.25 B3:0.75 R:0.5",
                        "Ab3:0.5 R:0.25 B3:0.5 R:0.25 E4:0.75 R:0.25 Eb4:0.5 R:0.25 B3:0.75 R:0.75",
                        "C#4:0.5 R:0.25 Ab3:0.75 F#3:0.5 R:0.25 F#3:0.75 R:5 C#4:0.5 B3:0.5 R:0.25",
                        "B3:0.75 R:1.25 Ab3:0.5 R:0.25 C#4:0.5 R:0.25 B3:0.5 B3:0.75 R:0.75 B3:0.5",
                        "R:0.25 Ab4:0.75 R:0.25 F#4:0.75 R:0.25 E4:2 R:10 Ab3:0.5 R:0.25 C#4:0.5",
                        "R:0.25 B3:0.5 B3:0.75 R:1.25 Ab3:0.5 R:0.25 C#4:0.5 R:0.25 B3:0.5 B3:0.75",
                        "R:0.75 C#4:0.5 R:0.25 Ab3:0.75 F#3:0.5 R:0.25 F#3:0.75 R:5.25 C#4:0.5 R:0.25",
                        "B3:0.5 R:0.25 B3:0.75 R:0.5 Ab3:0.5 B3:0.5 R:0.25 E4:0.75 R:0.25 Eb4:0.5",
                        "B3:0.75 R:0.75 C#4:0.5 Ab3:0.75 R:0.25 F#3:0.5 R:0.25 F#3:0.75 R:5.25 C#4:0.5",
                        "R:0.25 B3:0.5 R:0.25 B3:0.75 R:1.25 B3:0.5 R:0.25 E4:0.5 F#4:0.75 Ab4:0.75",
                        "R:0.5 E4:0.5 R:0.25 A4:0.75 R:0.25 Ab4:0.75 R:0.25 E4:2"
                )
        ));

        seeds.add(simpleSeed(
                "Bang-A-Boomerang",
                "Pop",
                132,
                phrase(
                        "R:0.25 F4:0.25 G4:1 R:0.25 A4:1 R:1.75 A4:0.5 R:0.25 A4:1 G4:0.5",
                        "R:0.25 G4:0.5 R:0.25 A4:0.5 R:0.25 G4:1 R:0.5 G4:1 R:0.25 G4:0.5",
                        "R:0.25 A4:1 Bb4:1 F4:0.5 R:0.25 E4:1 R:0.25 E4:0.5 F4:1 G4:1",
                        "C4:1 A4:1 R:1.75 A4:0.5 R:0.25 A4:1 R:0.25 G4:0.5 R:0.25 G4:0.5",
                        "R:0.25 A4:0.5 R:0.25 G4:1 R:0.5 G4:1 R:0.25 G4:0.5 R:0.25 A4:0.75",
                        "Bb4:1 R:0.25 F4:0.5 R:0.25 E4:1 R:0.25 E4:0.5 R:0.25 F4:1 R:0.25",
                        "G4:1 R:0.5 C4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.5 A4:1.75",
                        "R:0.25 A4:0.5 R:0.25 C5:1 R:0.25 C5:0.5 R:0.25 Bb4:1 R:0.25 A4:1.25",
                        "R:0.25 Bb4:1 R:0.25 Bb4:1 R:0.25 A4:0.5 R:0.25 Bb4:0.5 R:0.25 A4:0.5",
                        "R:0.25 A4:1 R:0.25 G4:2.25 F4:1 R:0.25 A4:1 R:1.75 A4:0.5 R:0.25",
                        "A4:1 R:0.25 G4:0.5 R:0.25 G4:0.5 R:0.25 A4:0.5 G4:1 R:0.25 G4:0.5",
                        "R:0.25 G4:1 R:0.25 F4:2.25 R:0.5 A4:0.5 R:0.25 A4:0.5 G4:0.5 R:0.25",
                        "G4:0.5 A4:0.5 R:0.25 G4:1 R:0.25 F4:0.5 R:0.25 F4:1.5"
                )
        ));

        seeds.add(simpleSeed(
                "Rock And Roll All Nite",
                "Rock",
                148,
                phrase(
                        "R:1.5 E5:0.25 R:0.25 E5:0.25 R:0.25 E5:0.25 R:0.25 E5:0.75 R:0.25 Eb5:0.25",
                        "R:0.25 F#5:0.5 R:0.25 F#5:0.5 R:0.25 Eb5:0.25 R:0.25 C#5:0.25 R:0.25 F#5:0.75",
                        "R:0.25 Eb5:0.5 R:0.25 C#5:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "So Lonely",
                "Alt",
                160,
                phrase(
                        "R:0.25 F1:0.5 R:0.25 F1:0.25 R:0.25 C2:0.25 R:0.25 C2:0.25 R:0.25 C2:0.25",
                        "R:0.25 C2:0.25 R:0.25 C2:0.25 R:0.25 C2:0.25 R:0.25 C2:0.25 R:0.25 C2:0.25",
                        "R:0.25 G1:0.25 R:0.25 G1:0.25 R:0.25 G1:0.25 R:0.25 G1:0.25 R:0.25 G1:0.25",
                        "R:0.25 G1:0.25 R:0.25 G1:0.25 R:0.25 G1:0.25 R:0.25 A1:0.25 R:0.25 A1:0.25",
                        "R:0.25 A1:0.25 R:0.25 A1:0.25 R:0.25 A1:0.25 R:0.25 A1:0.25 R:0.25 A1:0.25",
                        "R:0.25 A1:0.25 R:0.5 F1:1 R:0.5 F1:0.75 R:0.25 F1:0.25 R:0.25 C2:0.25",
                        "R:0.25 C2:0.25 R:0.25 C2:0.25 R:0.25 C2:0.25 R:0.25 C2:0.25 R:0.25 C2:0.25",
                        "R:0.25 C2:0.25 R:0.25 F#1:0.25 R:0.25 G1:0.25 R:0.25 G1:0.25 R:0.25 G1:0.25",
                        "R:0.25 G1:0.25 R:0.25 G1:0.25 R:0.25 G1:0.25 R:0.25 G1:0.25 R:0.25 Ab1:0.25",
                        "R:0.25 A1:0.25 R:0.25 A1:0.25 R:0.25 A1:0.25 R:0.25 A1:0.25 R:0.25 A1:0.25",
                        "R:0.25 A1:0.25 R:0.25 A1:0.25 R:0.25 A1:0.25 R:0.5 F1:1.25 R:0.5 F1:0.75",
                        "R:0.25 F1:0.25 R:0.25 C2:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Rosalinda's Eyes",
                "Pop",
                161,
                phrase(
                        "R:2.75 G4:0.25 A4:1 R:0.25 C5:1.5 R:0.25 G4:0.75 R:0.25 G4:0.25 R:0.25",
                        "G4:0.75 R:0.25 F4:0.75 R:0.25 G4:1 R:0.25 A4:0.25 R:0.25 G4:0.75 F4:1",
                        "R:3 F4:0.25 R:0.25 G4:1 F4:0.75 R:0.25 Eb4:0.25 R:0.25 Eb4:0.75 R:0.25",
                        "F4:1 R:0.25 D4:3"
                )
        ));

        seeds.add(simpleSeed(
                "Last Train To London",
                "Rock",
                122,
                phrase(
                        "R:3 D5:0.5 R:0.25 E5:0.5 R:0.25 G5:0.5 R:0.25 G5:0.5 R:0.25 E5:0.5",
                        "R:0.25 E5:1.5 R:4 D5:0.5 R:0.25 E5:0.5 R:0.25 G5:0.5 R:0.25 G5:0.5",
                        "E5:0.5 R:0.25 E5:0.5 R:0.25 E5:0.5 R:0.25 E5:0.5 R:0.25 E5:0.5 R:2.5",
                        "D5:0.5 R:0.25 E5:0.5 R:0.25 G5:0.5 G5:0.5 R:0.25 E5:0.5 R:0.25 E5:1.5",
                        "R:4 D5:0.5 R:0.25 E5:0.5 R:0.25 G5:0.5 R:0.25 G5:0.5 E5:0.5 R:0.25",
                        "E5:0.5 R:0.25 E5:0.5 R:0.25 E5:0.5 R:0.25 D5:0.5 R:0.25 D5:0.5 R:0.25",
                        "B5:0.5 R:0.25 B5:0.5 R:0.25 B5:0.5 R:0.25 B5:0.5 R:0.25 B5:1 R:0.25",
                        "A5:0.5 R:0.25 C6:0.5 R:1 B5:0.5 R:0.25 G5:1 A5:1 R:0.25 D5:0.5",
                        "R:0.25 B5:0.5 R:0.25 B5:0.5 R:0.25 B5:0.5 R:0.25 B5:0.5 R:0.25 B5:1",
                        "R:0.25 A5:0.5 R:0.25 B5:1 R:3.5 B5:0.5 R:0.25 B5:0.5 R:0.25 B5:0.5",
                        "R:0.25 B5:0.5 R:0.25 B5:0.5 R:0.25 A5:1 R:0.25 C6:1 R:0.25 B5:1",
                        "R:0.25 G5:1.5 R:0.25 E5:0.5 R:0.25 G5:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "I Was Made for Lovin' You",
                "Rock",
                129,
                phrase(
                        "R:0.25 E2:0.75 C#2:0.25 Bb1:0.25 R:0.25 E1:0.5 R:0.25 E2:0.5 R:0.25 E1:0.5",
                        "R:0.25 E2:0.5 R:0.25 E1:0.5 R:0.25 E1:0.5 R:0.25 E2:0.5 R:0.25 E1:0.5",
                        "R:0.25 E1:0.5 R:0.25 E2:0.5 R:0.25 E1:0.5 R:0.25 E2:0.5 R:0.25 E1:0.5",
                        "R:0.25 E1:0.5 R:0.25 E2:0.5 R:0.25 E1:0.5 R:0.25 A1:0.5 R:0.25 A2:0.5",
                        "R:0.25 A1:0.5 R:0.25 A2:0.5 R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 A2:0.5",
                        "R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 A2:0.5 R:0.25 A1:0.5 R:0.25 A2:0.5",
                        "R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 D2:1 R:0.25 E1:0.5 R:0.25 E2:0.5",
                        "R:0.25 E1:0.5 R:0.25 E2:0.5 R:0.25 E1:0.5 R:0.25 E1:0.5 R:0.25 E2:0.5",
                        "R:0.25 E1:0.5 R:0.25 E1:0.5 R:0.25 E2:0.5 R:0.25 E1:0.5 R:0.25 E2:0.5",
                        "R:0.25 E1:0.5 R:0.25 E1:0.5 R:0.25 E2:0.5 R:0.25 E1:0.5 R:0.25 A1:0.5",
                        "R:0.25 A2:0.5 R:0.25 A1:0.5 R:0.25 A2:0.5 R:0.25 A1:0.75 R:0.25 A1:0.25",
                        "R:0.25 E2:0.5 R:0.25 E2:0.5 R:0.25 D2:0.5 R:0.25 D3:0.5 R:0.25 D2:0.5",
                        "R:0.25 D2:0.5 R:0.25 D3:0.5 R:0.25 D2:0.5 R:0.25 E1:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Evil Woman",
                "Rock",
                121,
                phrase(
                        "R:2 E3:1 G3:0.5 A3:1 C4:0.5 A3:1 R:4 E3:1 G3:0.5 A3:1",
                        "C4:0.5 A3:1.5 R:3.5 E3:1 G3:0.5 A3:1 C4:1 A3:1.5 R:3 A4:0.5",
                        "G4:1 E4:1 D4:0.5 C4:1 A3:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Rock And Roll All Nite",
                "Rock",
                145,
                phrase(
                        "R:4 C5:0.75 Bb4:0.5 Ab4:1.5 R:0.25 Eb5:0.25 R:0.25 Eb5:0.25 R:0.25 F5:0.25",
                        "R:0.25 F5:0.5 R:0.25 Eb5:0.5 R:0.25 Ab5:0.5 R:0.5 F5:0.75 R:0.25 Eb5:0.25",
                        "R:0.25 C#5:1.75 R:1 Eb5:0.25 R:0.25 F5:0.25 R:0.25 Eb5:0.25 R:0.25 Ab5:0.5",
                        "Eb5:0.25 R:0.25 F5:0.5 R:0.25 Eb5:0.5 C5:0.5 Bb4:0.5 Ab4:1.5 R:0.25 Eb5:0.25",
                        "R:0.25 Eb5:0.25 R:0.25 F5:0.25 R:0.25 F5:0.5 R:0.25 Eb5:0.5 R:0.25 Ab5:0.5",
                        "R:0.5 F5:0.75 R:0.25 Eb5:0.25 R:0.25 C#5:1.75 R:1 Eb5:0.25 R:0.25 F5:0.25",
                        "R:0.25 Eb5:0.25 R:0.25 Ab5:0.5 Eb5:0.25 R:0.25 F5:0.25 R:0.25 Eb5:1 R:6.75",
                        "F5:0.5 R:0.5 Eb5:0.5 C5:0.25 R:0.25 Eb5:0.5 R:0.25 C5:0.25 R:0.25 Eb5:0.25",
                        "R:0.25 Eb5:0.5 R:0.5 F5:1.25 R:1.5 F5:0.25 R:0.5 Eb5:0.5 R:0.25 C5:0.25",
                        "R:0.25 Eb5:0.5 R:0.25 C5:0.25 R:0.25 Eb5:0.25 R:0.25 Eb5:0.5 R:0.25 Eb5:0.75",
                        "R:0.25 F5:0.25 R:0.25 Eb5:0.25 R:1.25 F5:0.5 R:0.5 Eb5:0.75 R:0.25 C5:0.25",
                        "R:0.25 F5:1 R:0.25 Eb5:0.5 R:0.5 C5:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "The Stranger",
                "Pop",
                180,
                phrase(
                        "R:0.25 E4:0.25 R:0.25 G4:0.25 R:0.25 B4:1.25 R:0.5 A4:0.5 R:0.25 G4:0.25",
                        "R:0.25 B4:1.25 R:0.5 E4:0.5 R:0.25 G4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.25",
                        "R:0.25 A4:0.75 R:0.25 G4:0.25 R:0.25 B4:0.5 R:0.25 E4:0.75 R:0.25 E4:0.5",
                        "R:0.25 G4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.25 R:0.25 A4:0.75 R:0.25 G4:0.5",
                        "R:0.25 B4:0.25 R:0.25 A4:0.5 R:0.25 A4:1 R:0.25 G4:0.25 R:0.25 A4:0.5",
                        "R:0.25 A4:0.25 R:0.25 B4:0.5 R:0.25 D5:0.5 R:0.25 B4:0.25 A4:1 R:0.5",
                        "E4:0.25 R:0.25 G4:0.75 R:0.25 B4:0.25 R:0.25 A4:0.75 R:0.25 A4:0.25 R:0.25",
                        "G4:0.75 R:0.25 B4:0.75 R:0.75 E4:0.25 R:0.25 G4:0.5 R:0.25 A4:0.5 R:0.25",
                        "A4:0.5 R:0.25 A4:0.25 R:0.25 G4:0.75 R:0.25 B4:0.25 R:0.25 E4:0.75 R:0.25",
                        "F#4:0.5 R:0.25 G4:0.25 R:0.25 A4:0.75 R:0.25 A4:0.5 R:0.25 A4:0.5 R:0.25",
                        "G4:0.25 R:0.25 B4:0.75 R:0.25 A4:0.5 R:0.25 G4:0.5 R:0.25 E4:0.25 R:0.25",
                        "A4:0.75 R:0.25 G4:0.25 R:0.25 E4:0.5 R:0.25 D4:0.25 E4:0.5 R:0.25 E4:2.25"
                )
        ));

        seeds.add(simpleSeed(
                "The Name Of The Game",
                "Pop",
                77,
                phrase(
                        "R:0.25 B1:0.75 R:0.25 F#2:0.75 Ab2:0.75 A2:0.75 R:0.25 B2:0.5 R:0.25 B2:0.25",
                        "R:0.25 B1:0.75 R:0.25 C#2:0.75 E2:0.75 F#2:1.75 Ab2:0.75 A2:0.75 B2:0.25 R:0.25",
                        "B2:0.25 R:0.25 B1:0.75 C#2:0.75 E2:0.75 F#2:1.75 Ab2:0.75 R:0.25 A2:0.75 B2:0.25",
                        "R:0.25 B2:0.25 R:0.25 B1:0.75 R:0.25 C#2:0.75 E2:0.75 F#2:1.75 Ab2:0.75 R:0.25",
                        "A2:0.75 R:0.25 B1:4 R:0.25 F#2:0.75 Ab2:0.75 A2:0.75 B2:0.25 R:0.25 B2:0.25",
                        "R:0.25 B1:0.75 R:0.25 C#2:0.75 E2:0.75 F#2:1.75 Ab2:0.75 R:0.25 A2:0.75 B2:0.25",
                        "R:0.25 B2:0.25 R:0.25 B1:0.75 R:0.25 C#2:0.75 E2:0.75 F#2:1.75 Ab2:0.75 R:0.25",
                        "A2:0.75 B2:0.25 R:0.25 B2:0.25 R:0.25 B1:0.75 R:0.25 C#2:1 E2:0.75 R:0.25",
                        "F#2:1.75 Ab2:0.75 A2:0.75 R:0.25 B1:3.5"
                )
        ));

        seeds.add(simpleSeed(
                "Ring Ring",
                "Pop",
                138,
                phrase(
                        "R:1 A2:1.25 R:0.25 C#3:1 D3:0.5 Eb3:0.5 E3:0.25 R:0.25 A2:1.25 R:0.25",
                        "C#3:0.75 D3:0.5 Eb3:0.5 E3:0.5 R:0.25 E2:1.25 R:0.25 Ab2:0.75 A2:0.5 Bb2:0.5",
                        "B2:0.5 R:0.25 E2:1.25 Ab2:0.75 A2:0.5 Bb2:0.5 B2:0.25 R:0.25 E2:1.25 R:0.25",
                        "Ab2:0.75 A2:0.5 Bb2:0.5 B2:0.5 R:0.25 E2:1.25 Ab2:0.75 A2:0.5 Bb2:0.5 B2:0.25",
                        "R:0.25 A2:1.25 R:0.25 C#3:1 D3:0.5 Eb3:0.5 E3:0.5 R:0.25 A2:1.25 R:0.25",
                        "C#3:0.75 D3:0.5 Eb3:0.5 E3:0.5 R:0.25 A2:1.25 R:0.25 C#3:1 D3:0.5 Eb3:0.5",
                        "E3:0.5 R:0.25 A2:1.25 R:0.25 C#3:0.75 D3:0.5 Eb3:0.5 E3:0.5 R:0.25 E2:1.25",
                        "R:0.25 Ab2:0.75 A2:0.5 Bb2:0.5 B2:0.5 R:0.25 E2:1.25 Ab2:0.75 A2:0.5 Bb2:0.5",
                        "B2:0.25 R:0.25 B2:2.5 R:0.25 D3:0.5 C#3:0.75 R:0.25 D3:0.25 R:0.25 B2:2.25",
                        "R:0.25 B2:2.5 R:0.25 D3:0.5 R:0.25 C#3:0.75 R:0.25 D3:0.5 R:0.25 B2:0.75",
                        "R:0.25 B2:0.75 R:0.25 A2:0.5 R:0.25 E2:1.25 R:0.25 Ab2:0.75 A2:0.5 Bb2:0.5",
                        "B2:0.5 R:0.25 E2:1.25 Ab2:0.75 A2:0.5 Bb2:0.5 B2:0.25 R:0.25 A2:1.25 R:0.25",
                        "C#3:1 D3:0.5 Eb3:0.5 E3:0.5 R:0.25 A2:1.25 R:0.25 C#3:0.75 D3:0.5 Eb3:0.5",
                        "E3:0.5 R:0.25 E2:1.25 R:0.25 Ab2:0.75 A2:0.5 Bb2:0.5 B2:0.5 R:0.25 E2:1.25",
                        "Ab2:0.75 A2:0.5 Bb2:0.5 B2:0.25 R:0.25 A2:1.25 R:0.25 C#3:1 D3:0.5 Eb3:0.5",
                        "E3:0.5 R:0.25 A2:1.25 R:0.25 C#3:0.75 D3:0.5 Eb3:0.5 E3:0.5 R:0.25 A2:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Bang-A-Boomerang",
                "Pop",
                132,
                phrase(
                        "R:1 F5:1 R:0.25 G5:0.5 R:0.25 E5:0.5 R:0.25 F5:0.5 R:0.25 G5:0.5",
                        "E5:0.5 R:0.25 F5:2.25 C5:1.75 R:0.25 F5:0.5 G5:0.5 R:0.25 A5:0.5 R:0.25",
                        "A5:1.25 R:0.25 G5:1.75 R:0.25 D5:0.5 R:0.25 E5:4 R:0.25 F5:1 R:0.25",
                        "G5:0.5 E5:0.5 R:0.25 F5:0.5 G5:0.5 R:0.25 E5:0.5 F5:2.25 R:0.25 A5:0.5",
                        "R:0.25 Bb5:0.5 R:0.25 C6:0.5 R:0.25 C6:6.25 G5:2.75 R:0.25 F4:0.5 R:0.25",
                        "F4:0.5 D4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "The Stranger",
                "Pop",
                153,
                phrase(
                        "R:2.5 B4:0.25 R:0.25 E5:0.5 R:0.25 F#5:0.5 R:0.25 G5:1.5 R:0.75 B5:0.5",
                        "R:0.25 B5:1.75 R:0.25 C6:0.25 R:0.25 A5:0.25 R:0.25 G5:0.25 R:0.25 A5:4.25",
                        "R:1 B4:0.25 R:0.25 F#5:0.25 R:0.25 G5:0.25 R:0.25 A5:0.5 R:0.25 C6:0.5",
                        "A5:0.5 B5:2.25 R:0.25 A5:0.25 R:0.25 G5:0.25 R:0.25 F#5:0.25 R:0.25 E5:2.75",
                        "R:2 E5:0.5 R:0.25 E6:3.5 R:0.25 B5:0.25 R:0.25 C6:0.25 R:0.25 D6:0.75",
                        "R:0.25 C6:0.25 R:0.25 B5:0.25 R:0.25 C6:3.25 R:0.5 B5:0.25 R:0.25 G5:0.25",
                        "R:0.25 A5:0.25 R:0.25 B5:0.25 R:0.25 A5:0.25 R:0.25 G5:0.25 R:0.25 A5:0.25",
                        "R:0.25 G5:0.25 R:0.25 A5:3.25 R:0.5 G5:0.25 R:0.25 E5:0.25 R:0.25 F#5:0.25",
                        "R:0.25 G5:0.25 R:0.25 F#5:0.25 R:0.25 E5:0.25 R:0.25 F#5:3.5 R:0.5 A3:0.25",
                        "R:0.25 B3:0.25 R:0.25 Eb4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Strange Magic",
                "Rock",
                92,
                phrase(
                        "R:1.25 B4:1.25 R:2.5 G4:0.75 R:0.25 A4:0.75 R:0.25 B4:0.75 R:0.25 B4:0.75",
                        "R:0.25 A4:0.75 R:0.25 G4:0.75 R:0.25 G4:1.25 R:0.25 A4:0.75 R:0.25 B4:2",
                        "R:2.5 G4:0.75 R:0.25 A4:0.75 R:0.25 B4:0.75 R:0.25 B4:0.75 R:0.25 A4:0.75",
                        "R:0.25 A4:0.75 R:0.25 G4:0.75 R:0.25 B4:1.25 R:0.25 B4:2 R:2.75 B4:0.75",
                        "R:0.25 A4:2 R:2.5 B4:0.75 R:0.25 A4:0.25 R:0.25 G4:0.25 R:0.25 E4:1.25",
                        "R:2 E4:0.5 R:0.25 G4:0.5 R:0.25 E4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Does Your Mother Know",
                "Pop",
                138,
                phrase(
                        "R:3.25 D4:0.25 R:0.25 D4:0.25 R:0.25 D4:0.5 R:0.25 E4:0.5 R:0.25 E4:0.5",
                        "R:0.25 D4:0.5 R:0.25 F4:0.25 R:0.25 F4:0.5 R:0.5 E4:0.75 R:0.25 E4:0.25",
                        "R:0.25 E4:0.25 R:0.25 D4:0.5 R:0.25 F4:0.25 R:0.25 F4:0.5 R:0.5 E4:0.75",
                        "R:0.25 E4:0.5 R:0.25 D4:0.5 R:0.25 C4:0.5 R:0.25 E4:0.5 R:0.25 D4:0.25",
                        "R:0.25 D4:0.25 R:0.25 D4:0.75 R:2 D4:0.25 R:0.25 D4:0.25 R:0.25 D4:0.25",
                        "R:0.25 E4:0.75 R:0.25 E4:0.5 R:0.25 D4:0.5 R:0.25 F4:0.25 R:0.25 F4:0.5",
                        "R:0.25 E4:0.75 R:0.25 E4:0.25 R:0.25 E4:0.5 R:0.25 D4:0.5 R:0.25 F4:0.5",
                        "R:0.25 F4:0.5 R:0.5 E4:0.75 R:0.25 E4:0.5 R:0.25 D4:0.5 R:0.25 C4:0.5",
                        "R:0.25 E4:0.5 R:0.25 D4:0.25 R:0.25 D4:0.25 R:0.25 D4:0.75 R:2.5 G3:0.5",
                        "R:0.25 F#3:0.5 R:0.25 G3:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Summer Night City",
                "Pop",
                131,
                phrase(
                        "R:0.75 D5:0.5 Bb5:0.5 R:0.5 Bb5:0.5 R:0.25 D5:0.5 R:0.25 Eb5:0.5 R:0.5",
                        "Bb5:0.5 R:0.5 A5:0.5 R:0.5 G5:0.5 R:1 A5:0.5 G5:0.5 R:0.5 Bb5:0.5",
                        "R:0.5 A5:0.5 R:0.5 A5:1.25 G5:0.25 R:0.25 G5:1.5 R:0.75 D5:0.5 R:0.25",
                        "Bb5:0.5 R:0.5 Bb5:0.5 R:0.25 D5:0.5 R:0.25 Eb5:0.5 R:0.5 Bb5:0.5 R:0.5",
                        "A5:0.5 R:0.5 G5:0.5 R:1 A5:0.5 G5:0.5 R:0.5 Bb5:0.5 R:0.5 A5:0.5",
                        "R:0.5 A5:1.25 R:0.25 G5:0.25 R:0.25 G5:1.5 R:0.75 A5:0.5 R:0.25 G5:0.5",
                        "R:0.5 A5:0.75 R:0.25 A5:0.5 R:0.25 A5:0.75 R:0.25 A5:0.5 R:0.25 Bb5:1",
                        "R:0.75 A5:1.25 R:0.25 G5:0.25 R:0.25 A5:0.75 R:0.25 A5:0.75 R:0.25 G5:1",
                        "R:0.25 G5:0.5 R:0.25 F5:6.25"
                )
        ));

        seeds.add(simpleSeed(
                "Rock And Roll All Nite",
                "Rock",
                146,
                phrase(
                        "R:1.75 F5:0.5 R:0.5 Eb5:0.5 C5:0.25 R:0.25 Eb5:0.5 R:0.25 C5:0.25 R:0.25",
                        "Eb5:0.25 R:0.25 Eb5:0.5 R:0.5 F5:1.5 R:1.5 F5:0.25 R:0.5 Eb5:0.5 R:0.25",
                        "C5:0.25 R:0.25 Eb5:0.5 R:0.25 C5:0.25 R:0.25 Eb5:0.25 R:0.25 Eb5:0.5 R:0.5",
                        "Eb5:0.75 R:0.25 F5:0.25 R:0.25 Eb5:0.25 R:1.25 F5:0.5 R:0.5 Eb5:0.75 R:0.25",
                        "C5:0.25 R:0.25 F5:1 R:0.25 Eb5:0.5 R:0.5 C5:0.75 R:0.25 Bb4:0.25 R:0.25",
                        "C5:1.25 R:0.25 Ab4:3.5"
                )
        ));

        seeds.add(simpleSeed(
                "Confusion",
                "Rock",
                112,
                phrase(
                        "R:2 G4:0.5 R:0.25 G4:0.5 R:0.25 F4:0.5 F4:0.5 R:0.25 E4:0.5 R:0.25",
                        "E4:0.5 R:0.25 D4:0.5 R:0.25 C4:1 R:0.25 C4:0.5 R:0.25 D4:1 R:0.25",
                        "E4:1.5 R:1.25 F4:0.5 R:0.25 F4:1 R:0.25 E4:0.5 R:0.25 E4:0.5 R:0.25",
                        "D4:1.5 R:0.25 B3:0.5 R:0.25 C4:1 R:0.25 D4:1.5 R:1.75 E4:0.5 R:0.25",
                        "F4:0.5 R:0.25 G4:1 R:0.25 A4:2 R:0.25 C4:0.5 R:0.25 D4:0.5 R:0.25",
                        "E4:1 R:0.25 E4:1 R:0.25 F4:0.25 R:0.25 E4:0.25 R:0.25 D4:1.5 R:0.25",
                        "A3:0.5 R:0.25 C4:0.5 R:0.25 B3:2.5 R:2 C5:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Beth",
                "Rock",
                115,
                phrase(
                        "R:0.25 D5:0.75 R:0.25 C5:1.5 R:0.25 C3:0.5 R:0.25 C3:0.75 R:0.75 C3:0.5",
                        "R:0.25 D5:1.5 R:0.25 C3:0.5 R:0.25 C3:0.75 R:0.75 C3:0.5 R:0.25 E5:0.75",
                        "R:0.75 C3:0.5 R:0.25 C3:0.75 R:0.25 B2:1 R:0.25 E5:0.75 R:0.75 A2:0.5",
                        "R:0.25 D5:0.75 R:0.75 G2:0.5 R:0.25 C5:1 R:0.75 F3:0.5 R:0.25 F3:0.75",
                        "R:0.75 F3:0.5 R:0.25 D5:1.5 R:0.25 F3:0.5 R:0.25 F3:0.75 R:0.75 F3:0.5",
                        "R:0.25 E5:1.5 R:0.25 E3:0.5 R:0.25 E5:0.5 R:0.25 D5:0.5 R:0.25 C5:0.5",
                        "R:0.25 E3:0.5 R:0.25 E5:1 R:0.75 E3:0.5 R:0.25 E5:0.75 R:0.25 E2:0.75"
                )
        ));

        seeds.add(simpleSeed(
                "Mr Blue Sky",
                "Rock",
                174,
                phrase(
                        "R:1 C3:0.25 R:0.5 F2:0.25 R:0.5 F2:0.25 R:0.25 F2:0.25 R:0.25 F2:0.25",
                        "R:0.25 F2:0.25 R:0.25 F2:0.25 R:0.5 F2:0.25 R:0.5 F2:0.25 R:0.5 F2:0.25",
                        "R:0.5 F2:0.25 R:0.25 F2:0.25 R:0.25 F2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25",
                        "R:0.5 A2:0.25 R:0.5 A2:0.25 R:0.5 D2:0.25 R:0.5 D2:0.25 R:0.25 D2:0.25",
                        "R:0.25 D2:0.25 R:0.25 G2:0.25 R:0.25 G2:0.25 R:0.5 G2:0.25 R:0.5 G2:0.25",
                        "R:0.5 E2:0.25 R:0.5 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 A1:0.25",
                        "R:0.25 A1:0.25 R:0.5 A1:0.25 R:0.5 A1:0.25 R:0.5 Bb1:0.25 R:0.25 Bb1:0.25",
                        "R:0.25 Bb1:0.25 R:0.25 Bb1:0.25 R:0.25 C2:0.25 R:0.25 C2:0.25 R:0.5 C2:0.25",
                        "R:0.5 C2:0.25 R:0.5 F2:0.25 R:0.25 F2:0.25 R:0.25 F2:0.25 R:0.25 F2:0.25",
                        "R:0.25 C2:0.25 R:0.25 C2:0.25 R:0.5 C2:0.25 R:0.5 C2:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Living On A Prayer",
                "Rock",
                124,
                phrase(
                        "R:0.25 D2:0.75 R:0.25 E1:0.5 F#1:0.5 R:0.25 G1:0.5 C2:1 R:0.25 B1:0.5",
                        "C2:0.25 R:0.25 C2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25",
                        "D2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.5 F#2:0.25 R:0.25 F#2:0.25 R:0.25",
                        "G2:0.5 R:0.25 D2:0.5 R:0.25 B1:0.5 R:0.25 C2:0.75 R:0.25 B1:0.5 R:0.25",
                        "C2:0.5 D2:0.5 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25",
                        "R:0.25 D2:0.25 R:0.25 E1:0.5 R:0.25 G1:0.5 F#1:0.5 E1:0.5 R:0.25 F#1:0.5",
                        "G1:0.5 R:0.25 C2:1 R:0.25 B1:0.5 R:0.25 C2:0.25 R:0.25 C2:0.25 R:0.25",
                        "D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25",
                        "D2:0.25 R:0.25 E2:0.5 R:0.25 F#2:0.5 R:0.25 G2:0.5 R:0.25 D2:0.5 B1:0.5",
                        "R:0.25 C2:1 R:0.25 B1:0.5 R:0.25 C2:0.25 R:0.25 C2:0.5 D2:0.25 R:0.25",
                        "D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25",
                        "D2:0.25 R:0.25 D2:0.25 R:0.25 E1:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "True Colors",
                "Indie",
                86,
                phrase(
                        "R:0.25 G3:0.75 C4:0.5 A3:1 F4:1 F3:0.75 A3:0.25 R:0.5 E4:1.5 R:0.25",
                        "G3:0.5 R:0.25 C4:0.5 R:0.25 E4:0.25 R:0.5 G4:0.75 C4:0.5 R:0.25 D4:0.5",
                        "R:0.25 G4:1.25 R:0.25 C4:0.5 R:0.25 D4:0.5 R:0.75 F4:1 F3:0.75 A3:0.25",
                        "R:0.5 E4:0.75 G3:1.25 C4:0.5 R:0.25 E4:0.5 R:0.25 G3:0.25 R:0.25 F3:1.75",
                        "F4:1 A3:0.25 R:0.5 G4:1.25 R:0.25 C4:0.25 R:0.5 G4:0.75 B3:0.25 R:0.5",
                        "F4:0.75 R:0.25 A3:0.5 R:0.25 C4:0.5 R:0.25 E4:0.75 R:0.25 G3:0.5 R:0.25",
                        "C4:0.5 R:0.25 E4:0.75 C3:0.25 R:0.5 F4:0.75 A3:0.5 R:0.25 F4:0.25 R:0.25",
                        "E4:1.25 R:0.25 A3:0.5 R:0.25 C4:0.5 R:0.25 A3:0.25 R:0.25 A3:1 F4:1",
                        "F3:0.75 A3:0.25 R:0.5 E4:1.5 R:0.25 G3:0.5 R:0.25 C4:0.5 R:0.25 E4:0.25",
                        "R:0.25 A3:1 F4:1 F3:0.75 R:0.25 A3:0.25 R:0.5 E4:1.25 R:0.25 G3:1",
                        "C4:0.5 R:0.25 E4:0.25 R:0.25 C4:5.5 G4:5.25 R:0.75 G4:1.25 B3:2.25 C4:1.5",
                        "G4:0.25 G4:0.75 B3:1.75 C4:1 G4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Carrie",
                "Rock",
                68,
                phrase(
                        "R:0.25 D4:1 R:0.25 G3:3.5 D4:3.5 G4:3.25 F#3:3.5 D4:3.5 F#4:3.25 E3:3.5",
                        "B3:3.5 E4:3.5 R:0.25 D3:3.5 A3:3.5 D4:3.25 R:0.25 C3:1.75 G3:1.75 C4:1.75",
                        "C3:0.25 G3:0.75 C4:0.75 D4:0.75 R:0.25 E4:3.25 R:0.25 D3:7 A3:7 D4:7",
                        "R:0.25 G3:3.5 D4:3.5 G4:3.25 F#3:3.5 D4:3.5 F#4:3.25 E3:3.5 B3:3.5 E4:3.5",
                        "R:0.25 D3:3.5 A3:3.5 D4:3.25 R:0.25 C3:1.75 G3:1.75 C4:1.75 R:0.25 C3:0.75",
                        "G3:1 C4:0.75 D4:0.75 R:0.25 E4:3.25 R:0.25 D3:7 A3:7 D4:7 R:0.25",
                        "D4:1 C4:1 C4:0.75 B3:0.75 B3:1 G3:1 G3:0.75 R:0.25 D4:0.75 D4:0.75",
                        "C4:1 C4:1 B3:0.75 B3:1 G3:1 G3:1 A3:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Wanna Be Startin' Somethin'",
                "Soul",
                120,
                phrase(
                        "R:0.75 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25",
                        "R:0.25 B1:0.25 R:0.25 D2:0.75 R:0.25 E2:0.25 R:0.5 D2:0.25 R:0.25 D2:0.25",
                        "R:0.25 D2:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 B1:0.25 R:0.25 D2:0.75 E2:0.25",
                        "R:0.5 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25",
                        "B1:0.25 R:0.25 D2:0.75 E2:0.25 R:0.5 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25",
                        "E2:0.25 R:0.25 E2:0.25 R:0.25 B1:0.25 R:0.25 D2:0.75 E2:0.25 R:0.5 D2:0.25",
                        "R:0.25 D2:0.25 R:0.25 D2:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 B1:0.25 R:0.25",
                        "D2:0.75 E2:0.25 R:0.5 D2:0.25 R:0.25 D2:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Overjoyed",
                "Soul",
                82,
                phrase(
                        "R:0.25 D5:0.25 Eb5:0.25 R:0.25 F5:0.5 R:0.25 G5:1 R:0.25 F5:0.75 R:0.25",
                        "C5:0.75 R:0.25 F5:3.25 R:0.25 F5:1 R:0.25 Eb5:0.75 R:0.25 Bb4:0.75 R:0.25",
                        "Eb5:2 R:1.25 Eb5:1 D5:0.75 R:0.25 C5:0.75 R:0.25 D5:1.25 R:1 G4:0.25",
                        "R:0.25 Bb4:0.25 R:0.25 C5:0.25 R:0.25 C#5:1 R:0.25 C5:0.75 Bb4:1 R:0.25",
                        "C5:1 R:0.25 C5:0.25 R:0.25 D5:0.25 R:0.25 E5:0.25 R:0.25 F5:0.25 R:0.25",
                        "G5:0.25 A5:1 R:0.25 G5:0.75 R:0.25 D5:0.75 G5:2.75 R:0.5 G5:1 R:0.25",
                        "F5:0.75 C5:0.75 R:0.25 F5:1 R:0.5 D5:0.5 R:0.25 Bb4:0.75 R:0.25 Eb5:4",
                        "R:0.5 D5:0.75 Bb4:0.75 R:0.25 Eb5:2.5 R:0.25 D5:1 C#5:0.25 R:0.25 C5:0.75",
                        "B4:0.75 R:0.25 Bb4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "So Emotional",
                "Pop",
                120,
                phrase(
                        "R:1.25 C2:0.5 R:0.25 B1:0.5 R:0.25 C2:0.5 R:0.25 D2:0.25 R:0.75 D2:1",
                        "R:0.25 C2:0.5 R:0.25 B1:0.5 R:0.25 D2:0.5 R:0.25 F#2:0.5 R:0.25 E2:0.5",
                        "R:0.5 E2:1 R:0.25 D2:0.5 R:0.25 A1:0.25 R:0.25 A1:0.25 R:0.25 C2:0.25",
                        "R:0.25 E2:0.25 R:0.25 D2:0.75 R:0.25 D2:1 R:0.25 Eb2:0.5 R:0.25 E2:0.25",
                        "R:0.25 E2:0.25 R:0.25 B1:0.25 R:0.25 E1:0.25 R:0.25 E2:0.5 R:0.25 E2:0.25",
                        "R:0.25 E1:0.25 R:0.25 E1:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E1:0.25",
                        "R:0.25 E1:0.25 R:0.25 C2:0.5 R:0.25 B1:0.5 R:0.25 C2:0.5 R:0.25 D2:0.25",
                        "R:0.75 D2:1 R:0.25 C2:0.25 R:0.25 B1:0.5 R:0.25 D2:0.5 R:0.25 F#2:0.5",
                        "R:0.25 E2:0.25 R:0.75 E2:1 R:0.25 D2:0.5 R:0.25 A1:0.25 R:0.25 A1:0.25",
                        "R:0.25 B1:0.75 R:0.5 B1:0.25 R:0.25 A1:0.25 R:0.25 B1:0.25 R:0.25 D2:0.25",
                        "R:0.25 A1:0.25 R:0.25 D2:2.75 R:1.75 E2:0.75 R:0.25 E2:0.25 R:0.25 E2:0.25",
                        "R:0.25 E2:0.25 R:0.25 D2:0.5 R:0.25 E2:0.5 R:0.25 D2:0.5 R:0.25 E2:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Our Last Summer",
                "Pop",
                98,
                phrase(
                        "R:0.25 E5:2.25 R:1.25 E5:0.5 R:0.25 F#5:0.5 R:0.25 G5:0.5 R:0.25 E5:0.5",
                        "R:0.25 F#5:2.5 R:0.25 C#5:1.25 R:0.25 A4:1.25 R:0.25 B4:1 D5:1.5 E5:0.5",
                        "R:0.25 F#5:0.5 R:0.25 G5:0.5 R:0.25 E5:0.25 F#5:6 R:1.5 E5:0.5 R:0.25",
                        "F#5:0.5 R:0.25 G5:0.5 R:0.25 E5:0.5 R:0.25 F#5:2.5 R:0.25 C#5:0.5 R:0.25",
                        "D5:0.5 R:0.25 E5:0.5 R:0.25 C#5:0.5 R:0.25 D5:2.5 R:0.25 C#5:1.25 R:0.25",
                        "A4:1.25 R:0.25 B4:1.25 R:0.25 D5:1.25 R:0.25 C#5:0.5 R:0.25 D5:0.5 R:0.25",
                        "E5:0.5 R:0.25 C#5:0.5 R:0.25 D5:3.75"
                )
        ));

        seeds.add(simpleSeed(
                "I Still Haven't Found What I'm Looking For",
                "Alt",
                102,
                phrase(
                        "R:3.75 C#5:0.5 R:0.25 C#5:0.5 R:0.25 Ab5:2.5 R:1 F#5:0.75 F5:0.5 R:0.25",
                        "C#5:2.25 R:1.25 Bb4:0.5 R:0.25 Bb4:0.5 R:0.25 Bb4:0.75 R:0.25 C#5:0.5 R:0.25",
                        "C#5:2.75 R:4 C#5:0.5 R:0.25 C#5:0.5 R:0.25 Ab5:2.25 R:1.25 F#5:0.5 R:0.25",
                        "F5:0.5 R:0.25 C#5:2.5 R:1 Bb4:0.5 R:0.25 Bb4:0.5 R:0.25 Bb4:0.75 C#5:0.5",
                        "R:0.25 C#5:3.25"
                )
        ));

        seeds.add(simpleSeed(
                "One Moment In Time",
                "Pop",
                77,
                phrase(
                        "R:0.25 D5:0.25 F4:0.75 A4:0.75 D5:0.75 R:0.25 C5:0.75 R:0.25 G4:0.75 G5:0.75",
                        "R:0.25 E5:0.75 A4:0.75 F5:0.75 B4:0.75 F5:0.75 R:0.25 E5:0.75 R:0.25 E4:0.75",
                        "R:0.25 G4:0.75 E5:0.75 R:0.25 D5:0.75 E4:0.75 G4:0.75 D5:0.75 R:0.25 C5:0.75",
                        "R:0.25 E4:0.75 A4:0.75 R:0.25 B4:0.75 C5:0.75 D5:0.75 E5:0.75 C5:0.75 R:0.25",
                        "C5:0.75 R:0.25 F4:0.75 A4:0.75 R:0.25 C5:0.75 D5:0.75 F4:0.75 R:0.25 Ab4:0.75",
                        "D5:0.75 R:0.25 D5:6 R:0.25 E5:0.75 R:0.25 E4:0.75 R:0.25 G4:0.75 E5:0.75",
                        "R:0.25 D5:0.75 E4:0.75 G4:0.75 D5:0.75 R:0.25 C5:0.75 R:0.25 E4:0.75 A4:0.75",
                        "R:0.25 B4:0.75 C5:0.75 D5:0.75 E5:0.75 C5:0.75 R:0.25 C5:0.75 R:0.25 F4:0.75",
                        "A4:0.75 R:0.25 C5:0.75 D5:0.75 F4:0.75 R:0.25 Ab4:0.75 D5:0.75 R:0.75 E4:0.75",
                        "D5:0.75 R:0.25 B4:0.75 C#5:0.5 D5:0.5 C#5:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Overjoyed",
                "Soul",
                82,
                phrase(
                        "R:0.75 B4:0.25 D5:1 R:0.25 C5:0.75 R:0.25 B4:1 A4:2 R:0.25 G4:0.75",
                        "R:0.25 A4:1 R:0.25 B4:0.75 A4:0.25 R:0.25 B4:2.25 R:1 G4:0.25 D5:1",
                        "R:0.25 C5:0.75 R:0.25 Bb4:1 A4:1.75 R:0.5 Bb4:0.75 C5:1 R:0.25 D5:1",
                        "R:0.25 C5:0.5 R:0.25 D5:0.75 R:0.25 Bb4:0.25 C5:0.5 D5:0.5 Eb5:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Take It on the Run",
                "Rock",
                77,
                phrase(
                        "R:2.25 G5:0.5 G5:0.5 G5:0.5 G5:0.5 G5:0.5 G5:1.5 F#5:0.75 E5:0.75 R:1.25",
                        "G5:0.5 G5:0.5 G5:0.5 G5:0.5 G5:0.5 G5:0.75 G5:0.5 F#5:1.25 E5:0.5 R:2",
                        "D5:0.5 D5:0.75 D5:1.25 D5:0.75 E5:0.5 D5:0.75 D5:1.25 G4:1.25 R:5.75 G5:0.5",
                        "G5:0.75 G5:0.5 G5:1.5 F#5:0.75 E5:0.75 R:1.5 G5:0.5 G5:0.75 G5:0.5 G5:1.5",
                        "F#5:0.75 E5:0.75 R:1.25 E5:0.5 E5:0.5 F#5:0.5 G5:0.75 A5:0.75 E5:0.25 E5:0.25",
                        "E5:0.5 F#5:0.5 G5:0.5 A5:0.5 A5:3.25"
                )
        ));

        seeds.add(simpleSeed(
                "With or Without You",
                "Alt",
                111,
                phrase(
                        "R:0.25 G1:0.5 R:0.25 G1:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5",
                        "R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5",
                        "R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 A1:0.5",
                        "R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 B1:0.5 R:0.25 B1:0.5",
                        "R:0.25 B1:0.5 R:0.25 B1:0.5 R:0.25 B1:0.5 R:0.25 B1:0.5 R:0.25 B1:0.5",
                        "R:0.25 B1:0.5 R:0.25 G1:0.5 R:0.25 G1:0.5 R:0.25 G1:0.5 R:0.25 G1:0.5",
                        "R:0.25 G1:0.5 R:0.25 G1:0.5 R:0.25 G1:0.5 R:0.25 G1:0.5 R:0.25 D2:0.5",
                        "R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5",
                        "R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 A1:0.5",
                        "R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 A1:0.5",
                        "R:0.25 B1:0.5 R:0.25 B1:0.5 R:0.25 B1:0.5 R:0.25 B1:0.5 R:0.25 B1:0.5",
                        "R:0.25 B1:0.5 R:0.25 B1:0.5 R:0.25 B1:0.5 R:0.25 G1:0.5 R:0.25 G1:0.5",
                        "R:0.25 G1:0.5 R:0.25 G1:0.5 R:0.25 G1:0.5 R:0.25 G1:0.5 R:0.25 G1:0.5",
                        "R:0.25 G1:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Dude (Looks Like a Lady)",
                "Rock",
                127,
                phrase(
                        "R:0.5 G4:0.25 R:3.25 C5:0.75 R:0.25 C5:0.5 B4:0.5 A4:0.5 R:0.25 C5:1",
                        "A4:0.25 G4:0.75 R:3.25 C5:0.75 R:0.25 C5:0.5 B4:0.5 A4:0.5 R:0.25 C5:1",
                        "A4:0.25 G4:0.75 R:3.25 C5:0.75 R:0.25 C5:0.75 B4:0.5 A4:0.5 C5:0.25 R:0.25",
                        "C5:1 E4:0.5 R:0.25 G4:0.5 R:0.25 G4:0.75 F#4:0.5 R:0.25 E4:0.25 R:0.25",
                        "E4:0.25 R:0.25 E4:0.5 D4:0.5 R:0.25 E4:0.5 R:0.25 C4:1 R:2.5 E4:0.5",
                        "G4:0.25 R:0.25 G4:0.5 F#4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Every Breath You Take",
                "Alt",
                118,
                phrase(
                        "R:2 C5:0.5 C#5:0.5 C5:1 Bb4:0.5 Ab4:1 R:0.25 Ab4:1.5 R:3 C5:0.5",
                        "C#5:0.5 C5:1 R:0.25 Bb4:0.5 Ab4:1 F4:1 R:3.25 Ab4:0.5 R:0.25 Ab4:0.5",
                        "C5:1 C#5:1 Ab4:0.75 R:0.5 Ab4:0.5 R:0.25 Ab4:0.5 C#5:1 C5:1 Bb4:0.75",
                        "R:0.25 Bb4:0.5 Ab4:0.5 C5:0.5 Ab4:1 F4:0.25 Eb4:0.5 R:5.5 C5:0.5 C#5:0.5",
                        "C5:1 Bb4:0.5 Ab4:1 C5:3 R:1.5 C5:0.5 C#5:0.5 C5:1 Bb4:0.5 Ab4:0.5",
                        "Ab4:0.5 F4:2 R:2 Ab4:0.5 R:0.25 Ab4:0.5 R:0.25 C5:1 R:0.25 C#5:1",
                        "R:0.25 Ab4:0.75 R:0.25 Ab4:0.5 R:0.25 Ab4:0.5 R:0.25 C#5:1 R:0.25 C5:1",
                        "R:0.25 Bb4:0.75 R:0.5 Bb4:0.5 Ab4:0.5 C5:0.5 Ab4:1 R:0.25 F4:0.25 Eb4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Last Christmas",
                "Indie",
                108,
                phrase(
                        "R:0.25 E2:0.5 R:0.25 E2:0.25 R:0.5 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25",
                        "R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25",
                        "R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 C#2:0.5",
                        "R:0.25 D2:0.5 R:0.25 C#2:0.5 R:0.25 D2:0.5 R:0.25 B1:0.25 R:0.25 B1:0.25",
                        "R:0.25 B1:0.25 R:0.25 B1:0.25 R:0.25 B1:0.25 R:0.25 B1:0.25 R:0.25 B1:0.25",
                        "R:0.25 B1:0.25 R:0.25 B1:0.5 R:0.25 B1:0.25 R:0.5 F#2:0.5 R:0.25 F#2:0.25",
                        "R:0.5 B1:0.25 R:0.25 B1:0.5 R:0.25 C#2:0.5 R:0.25 D2:0.25 R:0.25 E2:0.25",
                        "R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25",
                        "R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25",
                        "R:0.25 E2:0.25 R:0.25 F#2:0.5 R:0.25 G2:0.5 R:0.25 F#2:0.5 R:0.25 G2:0.5",
                        "R:0.25 A2:1 R:0.5 A1:0.5 R:0.25 A1:1 R:0.5 A1:0.5 R:0.25 A1:0.5",
                        "R:0.25 A1:0.25 R:0.5 B1:0.5 R:0.25 B1:0.25 R:0.5 C#2:0.5 R:0.25 C#2:0.25",
                        "R:0.5 E2:0.5 R:0.25 E2:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Wake Me Up Before You Go-Go",
                "Indie",
                163,
                phrase(
                        "R:3.25 G3:0.25 R:0.25 A3:0.5 R:0.25 G3:0.25 R:0.25 C3:0.5 R:0.25 C3:0.5",
                        "R:0.25 E3:0.5 R:0.25 G3:0.25 R:0.25 A3:0.5 R:0.25 C4:1 R:1.25 E4:0.25",
                        "R:0.25 E4:0.5 R:0.25 G4:0.25 R:0.25 F4:0.5 R:0.25 E4:0.5 R:0.25 D4:0.5",
                        "R:0.25 C4:0.5 R:0.25 A3:0.25 R:0.25 C4:0.5 R:0.25 C4:0.5 R:0.25 G3:1",
                        "R:0.5 C3:0.25 R:0.25 C3:0.25 R:0.25 C3:0.25 R:0.25 E3:0.25 R:0.25 G3:0.25",
                        "R:0.25 A3:0.5 R:0.25 D4:0.5 R:0.25 C4:1.25 R:0.25 E4:0.25 R:0.25 E4:0.5",
                        "R:0.25 G4:0.25 R:0.25 F4:0.5 R:0.25 E4:0.5 R:0.25 C4:0.5 R:0.25 C4:0.5",
                        "R:0.25 A3:0.25 R:0.25 C4:0.25 R:0.25 A3:0.25 R:0.25 C4:0.5 R:0.25 G3:1",
                        "R:0.5 A3:0.25 R:0.25 D4:0.5 R:0.25 D4:0.5 R:0.25 D4:0.75 R:0.25 C4:0.25",
                        "A3:0.5 R:0.25 E4:0.5 R:0.25 E4:0.5 R:0.25 D4:0.5 R:0.25 E4:0.75 R:0.25",
                        "C4:0.25 R:0.25 F4:0.25 R:0.25 F4:0.5 R:0.25 E4:0.5 R:0.25 F4:0.25 R:0.25",
                        "F4:0.5 R:0.25 D4:0.25 R:0.25 E4:0.25 R:0.25 E4:0.25 R:0.25 E4:0.5 R:0.25",
                        "G4:0.5 R:0.25 E4:0.5 R:0.25 A3:0.25 R:0.25 D4:0.5 R:0.25 D4:0.25 R:0.25",
                        "C4:0.5 R:0.25 D4:1 R:0.25 E4:0.25 R:0.25 E4:0.5 R:0.25 B3:1 R:0.75",
                        "F4:0.25 R:0.25 F4:0.5 R:0.25 E4:0.5 R:0.25 F4:0.25 R:0.25 F4:0.5 R:0.25",
                        "F4:0.25 R:0.25 A4:0.5 R:0.25 G4:0.25 R:0.25 G4:0.5 R:0.25 E4:0.25 R:0.25",
                        "G4:0.5 R:0.25 E4:0.5 R:0.25 A4:1.25 R:0.5 E4:0.5 R:0.25 G4:0.5 R:0.25",
                        "A4:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Super Trouper",
                "Pop",
                118,
                phrase(
                        "R:0.25 E2:0.5 R:0.25 D2:0.5 R:0.25 C2:0.25 R:0.25 C2:0.5 R:0.25 A1:0.75",
                        "R:0.25 G1:0.25 R:0.25 G1:0.5 R:0.25 A1:0.5 R:0.25 G1:0.5 R:0.25 E2:0.25",
                        "R:0.25 E2:0.5 R:0.25 E2:0.75 R:0.25 E2:0.25 R:0.25 E2:0.5 R:0.25 E2:1",
                        "R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.75 R:0.25 A2:0.25 R:0.25 A2:0.5",
                        "R:0.25 F2:1 R:0.25 G2:0.25 R:0.25 G2:0.5 R:0.25 E2:0.75 R:0.25 D2:0.25",
                        "R:0.25 D2:0.5 R:0.25 G2:1 R:0.25 C2:0.25 R:0.25 C2:0.25 R:0.25 A1:0.75",
                        "R:0.25 G1:0.75 R:0.25 A1:0.25 R:0.25 G1:0.5 R:0.25 E1:0.5 R:0.25 E1:0.5",
                        "R:0.25 E1:0.75 R:0.25 E1:0.25 R:0.25 E1:0.5 R:0.25 E1:1 R:0.25 D2:0.5",
                        "R:0.25 D2:0.5 R:0.25 E2:0.75 R:0.25 F2:0.5 R:0.25 F2:0.5 R:0.25 A2:1",
                        "R:0.25 G2:0.25 R:0.25 G2:0.25 R:0.25 E2:0.75 R:0.25 D2:0.25 R:0.25 D2:0.5",
                        "R:0.25 E2:1"
                )
        ));

        seeds.add(simpleSeed(
                "Our Last Summer",
                "Pop",
                98,
                phrase(
                        "R:1.75 F#5:0.5 R:0.25 F#5:0.5 R:0.25 F#5:0.25 F#5:1 F#5:0.5 R:0.25 F#5:0.5",
                        "R:0.25 F#5:0.25 F#5:1 D5:0.5 R:0.25 D5:0.5 R:0.25 D5:0.25 D5:1 A4:0.5",
                        "R:0.25 D5:0.5 R:0.25 F#5:0.5 R:0.25 G5:2.5 R:0.25 G5:0.5 R:0.25 F#5:0.5",
                        "R:0.25 F#5:0.5 R:0.25 D5:0.5 R:0.25 D5:0.75 R:0.25 E5:3 R:1.75 E5:0.5",
                        "R:0.25 E5:0.5 R:0.25 E5:0.5 R:0.25 E5:0.75 R:0.25 E5:0.5 R:0.25 E5:0.5",
                        "R:0.25 E5:0.5 R:0.25 E5:1.25 R:4.25 E5:0.5 R:0.25 E5:0.5 R:0.25 E5:0.25",
                        "E5:1 E5:0.5 R:0.25 E5:0.5 R:0.25 E5:0.25 F#5:2.75 G5:2.5 R:0.25 F#5:1.25",
                        "R:4.25 F#5:0.5 R:0.25 F#5:0.5 R:0.25 D5:0.5 R:0.25 D5:0.75 R:0.25 A4:0.5",
                        "R:0.25 D5:0.5 R:0.25 F#5:0.25 G5:2.75 G5:0.5 R:0.25 F#5:0.5 R:0.25 F#5:0.5",
                        "R:0.25 D5:0.5 R:0.25 D5:0.5 R:0.25 E5:3 R:1.75 E5:0.5 R:0.25 E5:0.5",
                        "R:0.25 E5:0.75 R:0.25 E5:2.5 R:0.5 E5:0.5 R:0.25 E5:0.75 R:0.25 E5:0.25",
                        "F#5:1 E5:0.5 R:0.25 E5:0.5 R:0.25 D5:0.5 R:0.5 E5:0.5 R:0.25 E5:0.5",
                        "R:0.25 E5:0.25 E5:2.5 R:1 E5:0.5 R:0.25 E5:0.5 R:0.25 E5:0.25 F#5:1",
                        "E5:0.5 R:0.25 E5:0.5 R:0.25 D5:0.5 R:0.75 E5:0.5 R:0.25 E5:0.75 R:0.25",
                        "E5:0.25 E5:2.5 R:1 E5:0.5 R:0.25 E5:0.5 R:0.25 E5:0.25 F#5:1 E5:0.75",
                        "R:0.25 E5:0.5 R:0.25 D5:1 E5:6.25 R:1 E5:0.5 R:0.25 F#5:0.5 R:0.25",
                        "G5:0.5 R:0.25 E5:0.25 F#5:1.25"
                )
        ));

        seeds.add(simpleSeed(
                "Super Trouper",
                "Pop",
                118,
                phrase(
                        "R:0.25 C5:0.5 R:0.25 E5:0.5 R:0.25 G5:0.75 R:0.25 G5:0.75 R:0.25 F5:0.75",
                        "R:0.25 F5:0.75 R:0.25 E5:0.5 R:0.25 D5:0.5 R:0.25 E5:0.5 R:0.25 F5:0.5",
                        "R:0.25 E5:1 R:0.25 D5:1 R:0.25 F5:0.75 R:0.25 F5:0.75 R:0.25 E5:0.75",
                        "R:0.25 E5:0.75 R:0.25 D5:3.25 R:0.75 F5:1 R:0.25 F5:1 R:0.25 D5:1",
                        "R:0.25 E5:1 R:0.25 D5:2.75 R:1.25 D5:0.5 R:0.25 C5:0.5 R:0.25 D5:0.5",
                        "R:0.25 E5:0.5 R:0.25 D5:1 R:0.25 C5:0.75 R:0.25 C5:2.25 R:0.5 G4:0.5",
                        "R:0.25 C5:0.5 R:0.25 E5:0.5 R:0.25 G5:0.75 R:0.25 G5:0.75 R:0.25 F5:0.75",
                        "R:0.25 F5:0.75 R:0.25 E5:0.5 R:0.25 D5:0.5 R:0.25 E5:0.5 R:0.25 F5:0.5",
                        "R:0.25 E5:1 R:0.25 D5:1 R:0.25 F5:0.75 R:0.25 F5:0.75 R:0.25 E5:0.75",
                        "R:0.25 E5:0.75 R:0.25 D5:3.25 R:0.75 F5:1 R:0.25 F5:1 R:0.25 D5:1",
                        "R:0.25 E5:1 R:0.25 D5:2.75 R:0.75 D5:0.25 R:0.25 D5:0.5 R:0.25 C5:0.5",
                        "R:0.25 D5:0.5 R:0.25 E5:0.5 R:0.25 D5:1 R:0.25 C5:0.75 R:0.25 C5:3.25"
                )
        ));

        seeds.add(simpleSeed(
                "Total Eclipse of the Heart",
                "Pop",
                65,
                phrase(
                        "R:0.25 B3:0.25 R:0.25 E4:1.75 E4:1.25 F#4:0.5 Ab4:2.25 R:0.25 Ab4:1.75 R:0.25",
                        "E4:2 Ab4:0.25 R:0.25 F#4:0.5 Ab4:0.5 F#4:0.5 R:0.25 Ab4:0.5 F#4:0.5 R:0.25",
                        "E4:0.5 F#4:0.5 E4:0.5 E4:0.5 F#4:0.25 R:0.25 Ab4:1.25 F#4:0.5 Ab4:0.5 R:1.75",
                        "Ab4:1.75 R:0.25 E4:2 Ab4:0.5 R:0.25 F#4:0.5 Ab4:0.5 F#4:0.5 Ab4:0.5 F#4:0.5",
                        "R:0.25 E4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Super Trouper",
                "Pop",
                118,
                phrase(
                        "R:1.25 G5:0.5 R:0.25 C6:0.5 R:0.25 E6:0.5 R:0.25 G6:0.5 R:0.25 D6:1",
                        "R:0.25 D6:0.5 R:0.25 C6:0.5 R:0.25 G5:0.5 R:0.25 C6:0.5 R:0.25 E6:0.5",
                        "R:0.25 G6:0.5 R:0.25 D6:1 R:0.25 D6:0.5 R:0.25 C6:0.5 R:0.25 E5:0.5",
                        "R:0.25 A5:0.5 R:0.25 C6:0.5 R:0.25 E6:0.5 R:0.25 B5:1 R:0.25 B5:0.5",
                        "R:0.25 A5:0.5 R:0.25 E5:0.5 R:0.25 A5:0.5 R:0.25 C6:0.5 R:0.25 E6:0.5",
                        "R:0.25 B5:1 R:0.25 B5:0.5 R:0.25 A5:0.5 R:0.25 A5:0.5 R:0.25 D6:0.5",
                        "R:0.25 F6:0.5 R:0.25 A6:0.5 R:0.25 E6:1 R:0.25 E6:0.5 R:0.25 D6:0.5",
                        "R:0.25 A5:0.5 R:0.25 D6:0.5 R:0.25 F6:0.5 R:0.25 A6:0.5 R:0.25 E6:1",
                        "R:0.25 E6:0.5 R:0.25 D6:0.5 R:0.25 D5:0.5 R:0.25 G5:0.5 R:0.25 B5:0.5",
                        "R:0.25 D6:0.5 R:0.25 A5:1 R:0.25 A5:0.5 R:0.25 G5:0.5 R:0.25 D5:0.5",
                        "R:0.25 G5:0.5 R:0.25 B5:0.5 R:0.25 D6:0.5 R:0.25 A5:1 R:0.25 A5:0.5",
                        "R:0.25 G5:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Thriller",
                "Soul",
                118,
                phrase(
                        "R:1.25 C#2:0.25 R:0.25 B1:0.5 C#2:0.5 R:0.25 E2:0.5 R:0.25 F#2:0.5 R:0.25",
                        "C#2:0.25 R:1 C#2:0.25 C2:0.25 B1:0.25 A2:0.5 R:1 F#2:0.5 R:1 B1:0.5",
                        "R:1 C#3:0.25 R:0.25 C#3:0.25 B2:0.25 R:0.25 B1:0.5 C#2:0.5 R:0.25 E2:0.25",
                        "R:0.25 F#2:4.75 C#2:0.25 F#1:1.75 R:0.25 F#1:0.75 R:0.25 Ab1:0.75 R:0.25 A1:4.25",
                        "E2:0.25 R:0.25 A2:0.25 R:0.25 A1:1.75 R:0.25 A1:0.75 R:0.25 B1:0.5 R:0.25",
                        "C#2:2.25 R:0.25 C#2:0.5 Eb2:0.5 R:0.25 E2:0.5 C#2:0.5 R:0.25 B1:1.75 R:0.25",
                        "B1:0.25 R:0.25 B1:1.5 R:0.25 B1:0.25 R:0.25 Bb1:1.75 R:0.25 Bb1:2 R:0.25",
                        "A1:1.75 E2:0.25 R:0.25 A2:2 Ab2:1.75 R:0.25 Eb2:0.25 R:0.25 Ab1:4 Eb2:0.25",
                        "R:0.25 Ab2:0.25 R:0.25 Ab2:0.5 R:0.25 F#2:0.25 E2:0.25 R:0.25 C#2:0.25 R:0.25",
                        "B1:0.5 C#2:0.5 R:0.25 E2:0.5 R:0.25 F#2:0.5 R:0.25 C#2:0.5 R:1.5 C#2:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Last Christmas",
                "Indie",
                108,
                phrase(
                        "R:0.25 E2:0.5 R:0.25 E2:0.25 R:0.5 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25",
                        "R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25",
                        "R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25 R:0.25 C#2:0.5",
                        "R:0.25 D2:0.5 R:0.25 C#2:0.5 R:0.25 D2:0.5 R:0.25 B1:0.25 R:0.25 B1:0.25",
                        "R:0.25 B1:0.25 R:0.25 B1:0.25 R:0.25 B1:0.25 R:0.25 B1:0.25 R:0.25 B1:0.25",
                        "R:0.25 B1:0.25 R:0.25 B1:0.5 R:0.25 B1:0.25 R:0.5 F#2:0.5 R:0.25 F#2:0.25",
                        "R:0.5 B1:0.25 R:0.25 B1:0.5 R:0.25 C#2:0.5 R:0.25 D2:0.25 R:0.25 E2:0.25",
                        "R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25",
                        "R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25 R:0.25 E2:0.25",
                        "R:0.25 E2:0.25 R:0.25 F#2:0.5 R:0.25 G2:0.5 R:0.25 F#2:0.5 R:0.25 G2:0.5",
                        "R:0.25 A2:1 R:0.5 A1:0.5 R:0.25 A1:1 R:0.5 A1:0.5 R:0.25 A2:0.5",
                        "R:0.25 G2:0.25 R:0.25 F#2:0.5 R:0.25 G2:0.25 R:0.25 F#2:0.25 R:0.25 E2:0.25",
                        "R:0.25 D2:0.25 R:0.25 C#2:0.25 R:0.25 D2:0.25 R:0.25 D2:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Runaway",
                "Rock",
                153,
                phrase(
                        "R:2 E5:0.25 R:0.25 E5:0.25 R:0.25 E5:0.5 R:0.25 E5:0.25 R:0.25 E5:0.25",
                        "R:0.25 E5:0.5 R:0.25 E5:0.75 R:0.25 E5:0.25 R:0.25 E5:0.5 E5:0.5 E5:0.75",
                        "E5:5.5 D5:0.25 D5:0.5 E5:0.75 E5:0.5 E5:1 E5:1 E5:1 E5:1 E5:0.5",
                        "E5:11.25 F5:0.25 C5:0.25 C5:0.25 B4:0.25 B4:0.25 C5:0.25 B4:0.25 A4:0.25 A4:0.25",
                        "E5:0.75 E5:1 E5:1.25 E5:0.75 E5:0.75 E5:1 E5:0.75 E5:0.75 E5:0.75 E5:0.5",
                        "E5:0.5 E5:0.75 E5:5.5 D5:0.25 D5:0.5 E5:1 E5:0.75 E5:0.5 E5:0.5 E5:0.5",
                        "E5:0.75 E5:11.75 D5:0.5 C5:0.5 D5:0.5 D5:0.25 D5:0.5 C5:0.25 D5:0.25 C5:0.25",
                        "C5:0.25 E5:1"
                )
        ));

        seeds.add(simpleSeed(
                "I Still Haven't Found What I'm Looking For",
                "Alt",
                101,
                phrase(
                        "R:0.25 F5:0.25 R:0.25 Ab5:0.75 R:0.25 Ab5:0.75 R:0.25 F5:0.75 R:1.25 Bb5:1",
                        "R:0.25 Ab5:0.75 R:0.25 Ab5:1 R:0.25 F5:0.75 R:1.25 F5:0.75 R:0.25 Ab5:0.75",
                        "R:0.25 Ab5:0.75 R:0.25 F5:0.75 R:1.5 Bb5:0.75 R:0.25 Ab5:0.75 R:0.25 F5:0.75",
                        "R:1.5 F5:0.75 R:0.25 Eb5:0.75 R:0.25 C#5:0.75 R:0.25 Eb5:1.75 R:0.25 C#5:0.5",
                        "R:0.25 C#5:2 R:2.5 F5:0.75 R:0.25 Eb5:0.75 R:0.25 C#5:0.75 R:0.25 Eb5:1.75",
                        "R:0.25 C#5:0.5 R:0.25 C#5:2 R:3.5 F5:0.75 R:0.25 Ab5:0.75"
                )
        ));

        seeds.add(simpleSeed(
                "Super Trouper",
                "Pop",
                118,
                phrase(
                        "R:0.25 D5:1 R:0.25 C5:4 A4:4 R:0.25 C5:2 G4:2 R:0.25 C6:1",
                        "R:0.25 G5:1 E5:1 C5:1 R:0.25 C4:3 A4:3 R:0.25 C5:1 A5:1",
                        "R:0.25 E5:1.5 G5:1.5 R:0.25 E5:0.5 C6:0.5 R:0.25 G5:1 C6:1 R:0.25",
                        "G5:1 E5:1 R:0.25 C5:3 F5:3 R:0.25 G5:1 R:0.25 G4:3 G5:3",
                        "R:0.25 C5:1 R:0.25 C5:2 G4:2 D5:2 R:0.25 C5:1 G4:1 R:0.25",
                        "G4:1 C5:1 R:0.25 G4:1 B4:1 R:0.25 D4:1 B4:1 G4:1 R:0.25",
                        "G4:1 B4:1 R:0.25 D4:0.75 B4:0.75"
                )
        ));

        seeds.add(simpleSeed(
                "What's Up",
                "Rock",
                134,
                phrase(
                        "E5:0.75 R:0.25 C#6:1.75 R:0.25 A5:1.75 R:0.25 E5:1.25 R:0.25 D5:1 R:0.25",
                        "C#5:1.25 R:0.25 C#6:1.75 R:0.25 A5:1.75 R:0.25 D5:1.75 R:0.5 B5:0.5 R:0.25",
                        "C#6:0.5 A5:2.75 R:2.25 B5:0.5 R:0.5 B5:0.5 C#6:0.75 R:0.25 A5:4.5 R:0.5",
                        "E5:0.5 F#5:0.5 R:0.5 E5:1 R:0.25 C#6:1.75 R:0.25 A5:1.75 R:0.25 E5:1.25",
                        "R:0.25 D5:0.75 R:0.25 C#5:1 R:0.25 C#6:1.75 R:0.25 A5:1.75 R:0.25 D5:1.75",
                        "R:0.25 B5:0.25 R:0.25 C#6:0.25 R:0.25 A5:2.75 R:2.25 B5:0.5 R:0.5 B5:0.5",
                        "R:0.25 C#6:0.75 R:0.25 A5:4"
                )
        ));

        seeds.add(simpleSeed(
                "Half The World Away",
                "Alt",
                116,
                phrase(
                        "R:2.5 A4:0.5 E5:0.5 D5:1 C5:2.5 R:0.5 A4:0.5 C5:1 E5:0.5 R:0.5",
                        "E5:0.5 E5:0.5 E5:1 D5:0.5 C5:0.5 D5:1 E5:1 C5:1 A4:1 A4:0.5",
                        "E5:0.5 D5:0.5 C5:0.5 D5:1 D5:0.5 C5:0.5 D5:2 C5:0.5 D5:0.5 C5:1",
                        "D5:5.75 B4:0.5 A4:0.5 G4:1.5"
                )
        ));

        seeds.add(simpleSeed(
                "Don't Want You Back",
                "Alt",
                102,
                phrase(
                        "R:0.25 F#1:0.5 R:0.25 F#1:0.5 R:0.25 F#1:0.5 R:0.75 C#1:0.5 R:0.25 D1:0.5",
                        "R:0.75 F#1:0.5 R:0.5 E1:1.75 B0:0.5 R:0.25 C#1:1 R:0.25 E1:1 R:0.25",
                        "D1:1 R:2.5 D1:0.75 R:0.5 C1:0.25 R:0.5 C#1:1.25 R:0.25 C#1:0.25 R:0.5",
                        "C#1:0.5 R:0.5 F#1:0.5 R:0.25 F#1:0.5 R:0.25 F#1:0.5 R:0.25 F#1:0.75 R:0.5",
                        "C#1:0.5 R:0.25 D1:0.75 R:0.5 F#1:0.5 R:0.5 E1:1.75 B0:0.5 R:0.25 C#1:0.75",
                        "R:0.5 E1:0.75 R:0.25 D1:1.75 A0:0.5 R:0.25 B0:0.5 R:0.75 D1:0.75 R:0.5",
                        "C#1:2 R:0.5 C#1:1 R:0.25 F#1:0.5 R:0.25 F#1:0.5 R:0.25 F#1:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Born to make you happy",
                "Dance",
                169,
                phrase(
                        "R:0.25 F#4:0.5 R:0.25 E4:1.5 D4:0.25 R:0.25 F#4:0.25 D4:1.25 F#4:0.25 F#4:0.25",
                        "F#4:0.25 R:0.25 F#4:0.5 R:0.25 E4:0.25 R:0.25 E4:0.25 R:0.25 D4:0.25 R:0.25",
                        "F#4:0.5 R:0.5 D4:0.25 R:0.25 D4:0.25 R:0.25 A4:0.5 R:0.25 F#4:0.25 R:0.25",
                        "G4:0.25 R:0.5 F#4:0.25 R:0.25 E4:0.5 R:0.25 D4:0.5 R:1.5 D4:0.25 R:0.25",
                        "F#4:0.25 R:0.25 F#4:0.25 R:0.25 F#4:0.25 R:0.25 F#4:0.25 R:0.25 G4:0.5 R:0.25",
                        "F#4:0.25 R:0.25 E4:0.25 R:0.25 D4:0.25 R:0.25 D4:0.5 R:0.5 D4:0.25 R:0.25",
                        "D4:0.25 R:0.25 A4:0.5 R:0.25 F#4:0.25 R:0.25 G4:0.25 R:0.5 F#4:0.25 R:0.25",
                        "E4:0.5 R:0.25 D4:0.5 R:2 A4:0.25 R:0.25 A4:0.25 R:0.25 A4:0.25 R:0.25",
                        "F#4:0.25 R:0.25 G4:0.75 R:0.25 F#4:0.25 R:0.25 E4:0.25 R:0.5 D4:0.25 R:0.25",
                        "B3:0.25 D4:0.5 R:1.5 D4:0.25 R:0.25 D4:0.25 R:0.25 E4:0.25 R:0.25 F#4:0.5",
                        "R:0.25 G4:0.5 R:0.25 F#4:0.5 R:0.25 F#4:0.5 R:1 D4:0.25 R:0.25 F#4:0.25",
                        "R:0.25 F#4:0.25 R:0.25 F#4:0.25 R:0.25 F#4:0.25 R:0.25 F#4:0.5 R:0.25 E4:0.25",
                        "R:0.25 E4:0.25 R:0.25 D4:0.25 R:0.25 F#4:0.5 R:0.5 D4:0.25 R:0.25 D4:0.25",
                        "R:0.25 A4:0.5 R:0.25 F#4:0.25 R:0.25 G4:0.25 R:0.5 F#4:0.25 R:0.25 E4:0.5",
                        "R:0.25 D4:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Say It Ain't So",
                "Rock",
                78,
                phrase(
                        "R:7.25 G5:0.75 F5:0.5 R:0.25 F5:0.5 R:0.5 F5:1.25 R:0.25 G5:0.5 F5:0.5",
                        "C5:0.5 R:0.25 Eb5:1.25 R:2 Eb5:0.75 D5:2.5 C5:0.75 Bb4:0.5 R:0.25 D5:1.75",
                        "R:0.25 C5:0.5 R:0.25 G4:3.5 R:6.75 G5:1 F5:0.5 R:0.25 F5:0.5 R:0.25",
                        "F5:1.25 R:0.25 G5:0.5 F5:0.5 C5:0.25 R:0.25 Eb5:1.25 R:2 Eb5:0.75 D5:2.5",
                        "C5:0.75 Bb4:0.25 R:0.25 D5:1.75 R:0.25 Eb5:0.5 R:0.5 Eb5:3.5"
                )
        ));

        seeds.add(simpleSeed(
                "What's Up",
                "Rock",
                134,
                phrase(
                        "R:2 C#5:0.25 R:0.25 C#5:0.25 R:0.25 C#5:0.75 R:0.25 C#5:0.5 E4:0.5 C#5:0.25",
                        "R:0.25 C#5:0.5 R:0.25 E5:1.5 R:0.75 B4:0.5 R:0.25 B4:0.25 R:0.25 B4:0.25",
                        "R:0.25 B4:0.25 R:0.25 A4:1 R:0.5 E5:0.25 R:0.25 E5:1 B4:0.5 R:0.5",
                        "B4:0.5 R:0.25 C#5:0.5 A4:4.5 R:0.5 A4:0.5 R:0.25 F#4:0.5 R:0.25 A4:0.25",
                        "R:0.25 A4:0.25 R:0.25 A4:0.5 C#5:1 A4:5.25 R:1 E4:0.5 R:0.25 C#5:0.25",
                        "R:0.25 C#5:0.5 R:0.25 C#5:0.5 R:0.25 C#5:0.25 R:0.25 C#5:0.5 E4:0.25 R:0.25",
                        "C#5:0.25 R:0.25 C#5:0.5 R:0.5 E5:0.5 R:0.25 E5:0.25 R:0.25 E5:0.25 R:0.25",
                        "B4:0.5 R:0.25 B4:0.5 A4:0.5 R:0.25 B4:0.5 R:0.5 A4:0.5 R:0.75 E5:0.25",
                        "R:0.25 E5:1 R:0.25 B4:0.5 R:0.5 B4:0.5 R:0.25 C#5:0.5 R:0.25 A4:5",
                        "R:0.5 F#4:0.25 R:0.25 A4:0.25 R:0.25 A4:0.25 R:0.25 A4:0.25 R:0.25 C#5:0.5",
                        "A4:5.25 R:0.5 E5:0.25 R:0.25 E5:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "If I Let You Go",
                "Alt",
                93,
                phrase(
                        "R:1 E4:1 G3:0.75 C4:0.25 R:0.25 E4:1.25 R:0.25 G3:0.25 R:0.25 D4:1",
                        "G3:0.25 R:0.25 E4:1 A3:0.75 C4:0.25 R:0.25 E4:0.75 R:0.75 A3:0.25 R:0.25",
                        "D4:1 G3:0.25 R:0.25 C4:1.25 F3:1 A3:0.25 R:0.25 C4:1.25 R:0.25 F3:0.25",
                        "R:0.25 C4:0.75 F3:0.25 R:0.25 D4:1 G3:0.75 B3:0.25 R:0.25 D4:1.75 G3:0.75",
                        "D4:0.25 R:0.25 G3:0.25 R:0.25 E4:1 A3:0.75 C4:0.25 R:0.25 E4:1.25 R:0.25",
                        "A3:0.25 R:0.25 D4:1 G3:0.25 R:0.25 C4:0.75 F3:0.75 A3:0.25 R:0.25 F4:1.25",
                        "A3:0.25 R:0.25 E4:0.75 G3:0.25 R:0.25 F4:1.25 A3:0.75 D4:0.25 R:0.25 F4:1.5",
                        "A3:0.25 R:0.25 F4:0.75 A3:0.5 R:0.25 D4:1.25 Ab3:0.25 C4:0.75 F4:2 D4:1",
                        "C4:0.75 Ab3:0.75 R:0.25 E4:1.25 G3:1 B3:0.25 R:0.25 E4:2"
                )
        ));

        seeds.add(simpleSeed(
                "Run To You",
                "Pop",
                74,
                phrase(
                        "R:1 Eb4:0.25 C4:0.75 Bb3:0.75 C4:0.75 C#4:0.75 Eb4:0.75 F4:1.5 G4:0.75 F4:0.5",
                        "G4:0.5 Ab4:2.25 R:0.25 C#4:0.75 F4:2 R:0.25 C5:1.5 Bb4:2.25 R:2 G4:0.75",
                        "F4:0.5 G4:0.5 Bb4:2.25 R:0.25 Eb4:0.75 Ab4:2 R:0.25 Eb5:0.75 C#5:2.25 R:0.25",
                        "Eb5:0.75 C5:1.75 R:0.75 C4:0.5 C#4:0.5 Eb4:0.75 C#4:0.75 C#4:0.75 Bb4:0.75 Ab4:2",
                        "R:0.25 C#4:0.75 Bb3:0.75 C4:0.75 C#4:0.75 Eb4:0.75 F4:1.75 G4:0.75 F4:0.5 G4:0.5",
                        "Ab4:2 R:0.25 C#4:0.75 F4:2.25 R:0.25 C5:1.5 Bb4:2.25 R:2 G4:1 F4:0.5",
                        "G4:0.5 Bb4:2.25 R:0.25 C5:1 C5:2.25 R:0.25 C#5:0.5 R:0.25 Eb5:0.5 C#5:2.25",
                        "R:0.25 Eb5:0.75 C5:1.75 F4:0.75 Ab4:0.75 R:0.75 C5:0.75 C5:0.75 Bb4:0.5 Ab4:1.75",
                        "R:1 Eb4:0.75 B4:0.5 Bb4:0.75 Bb4:0.5 Ab4:3 R:1.25 Ab4:0.5 G4:0.5 Ab4:7"
                )
        ));

        seeds.add(simpleSeed(
                "Larger Than Life",
                "Alt",
                108,
                phrase(
                        "R:1.25 C5:1 Eb4:1 R:0.25 Eb4:0.25 C5:0.25 R:0.25 Eb4:0.25 C5:0.25 R:0.25",
                        "C5:0.25 R:0.25 Eb4:0.25 C5:0.25 R:0.25 Eb4:0.5 C5:0.5 R:0.25 C5:1 Eb4:1",
                        "R:0.75 Bb4:1.75 D4:0.25 D4:0.5 R:0.25 A4:2.75 C4:2.75 R:0.25 C5:1 Eb4:1",
                        "R:0.25 Eb4:0.25 C5:0.25 R:0.25 Eb4:0.25 C5:0.25 R:0.25 C5:0.25 Eb4:0.25 R:0.25",
                        "Eb4:0.25 C5:0.25 R:0.25 Eb4:0.5 C5:0.5 R:0.25 C5:1 Eb4:1 R:0.75 Bb4:1.75",
                        "D4:0.25 D4:0.5 R:0.25 A4:2.5 R:0.25 C5:1 Eb4:1 R:0.25 Eb4:0.25 C5:0.25",
                        "R:0.25 Eb4:0.25 C5:0.25 R:0.25 C5:0.25 Eb4:0.25 R:0.25 Eb4:0.25 C5:0.25 R:0.25",
                        "Eb4:0.5 C5:0.5 R:0.25 C5:1 Eb4:1 R:0.75 Bb4:1.75 D4:0.25 D4:0.5 R:0.25",
                        "A4:2.75"
                )
        ));

        seeds.add(simpleSeed(
                "The One",
                "Alt",
                110,
                phrase(
                        "R:1 C#4:0.25 R:0.5 C#4:0.25 R:0.5 C#4:0.25 R:0.5 C#4:0.25 R:0.25 C#4:0.25",
                        "R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.5 C#4:0.25 R:0.5 C#4:0.25",
                        "R:0.5 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25",
                        "R:0.25 Bb3:0.5 R:0.25 Bb3:0.25 R:0.5 Bb3:0.25 R:0.5 Bb3:0.25 R:0.25 Bb3:0.25",
                        "R:0.25 Bb3:0.25 R:0.25 Bb3:0.25 R:0.25 C4:0.25 R:0.5 C4:0.25 R:0.5 C4:0.25",
                        "R:0.75 C4:0.25 R:0.25 C#4:0.25 R:0.25 Eb4:0.25 R:0.25 C#4:0.25 R:0.5 C#4:0.25",
                        "R:0.5 C#4:0.25 R:0.5 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25",
                        "R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.5 C#4:0.25 R:0.5 C#4:0.25 R:0.5 C#4:0.25",
                        "R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.5 C#4:0.25",
                        "R:0.5 C#4:0.25 R:0.5 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25",
                        "R:0.25 C4:3.75"
                )
        ));

        seeds.add(simpleSeed(
                "El Scorcho",
                "Rock",
                151,
                phrase(
                        "R:1.5 Ab4:0.25 R:0.25 Ab4:0.25 R:0.25 G4:0.25 R:0.25 F4:1 Eb4:0.25 R:0.25",
                        "Eb4:0.5 R:0.25 F4:0.25 R:0.25 F4:0.25 R:0.25 Eb4:0.25 R:0.25 C4:1 Ab3:0.25",
                        "R:0.25 Ab3:0.25 R:0.25 Ab3:0.25 R:0.25 Bb3:0.25 R:0.25 Bb3:0.25 R:0.25 C4:0.25",
                        "R:0.25 Bb3:1 Bb3:0.25 R:0.25 Bb3:0.25 R:0.25 Bb3:0.25 R:0.25 Eb4:0.25 R:0.25",
                        "F4:0.25 R:0.25 Eb4:1 R:1 Ab4:0.25 R:0.25 Ab4:0.25 R:0.25 Ab4:0.25 R:0.25",
                        "G4:0.25 R:0.25 F4:1 R:0.25 Eb4:0.25 R:0.25 Eb4:0.25 R:0.25 Eb4:0.25 R:0.25",
                        "C4:0.25 R:0.25 C4:0.25 R:0.25 C#4:0.25 R:0.25 C4:0.5 R:0.75 C4:0.5 R:0.25",
                        "Bb3:0.25 R:0.25 Bb3:0.25 R:0.25 Bb3:0.25 R:0.25 Bb3:1 R:0.25 Bb3:0.5 R:0.25",
                        "Eb4:0.25 R:0.25 F4:0.25 R:0.25 Eb4:1 Ab4:1 R:0.5 Ab4:0.25 R:0.25 Ab4:0.25",
                        "R:0.25 G4:0.25 R:0.25 F4:1 R:0.25 Eb4:0.25 R:0.25 F4:1 R:0.25 F4:0.25",
                        "R:0.25 Ab4:1 C4:0.25 R:0.25 C4:0.25 R:0.25 C4:1 Bb3:0.25 R:0.25 Bb3:1",
                        "R:1 Bb3:0.25 R:0.25 Eb4:0.25 R:0.25 Eb4:0.25 R:0.25 Eb4:0.25 R:0.25 F4:0.5",
                        "R:0.25 Eb4:0.5 R:0.25 C#4:0.25 R:0.25 C4:0.25 R:0.25 C4:0.25 R:0.25 C4:1",
                        "R:2.5 Ab3:0.25 R:0.25 C4:0.25 R:0.25 C4:0.25 R:0.25 Ab3:0.25 R:0.25 C4:1",
                        "R:0.25 Ab3:0.5 C#4:1 R:1.75 Ab3:0.5 R:0.25 C4:0.25 R:0.25 Ab3:0.25 R:0.25",
                        "Ab3:0.25 R:0.25 Ab3:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Born to make you happy",
                "Dance",
                169,
                phrase(
                        "R:1 G1:0.25 R:0.25 G1:0.25 R:0.5 G1:0.25 R:0.25 G1:0.25 R:0.25 G1:0.25",
                        "R:0.25 G1:0.25 R:0.25 G1:0.25 R:0.25 G1:0.25 R:0.5 G1:0.25 R:0.25 G1:0.25",
                        "R:0.25 G1:0.25 R:0.5 G1:0.25 R:0.25 E1:0.25 R:0.25 E1:0.25 R:0.5 E1:0.25",
                        "R:0.25 E1:0.25 R:0.25 E1:0.25 R:0.5 E1:0.25 R:0.25 A1:0.25 R:0.25 A1:0.25",
                        "R:0.5 A1:0.25 R:0.25 A1:0.25 R:0.25 A1:0.25 R:0.5 A1:0.25 R:0.25 B1:0.25",
                        "R:0.25 B1:0.25 R:0.5 B1:0.25 R:0.25 B1:0.25 R:0.25 B1:0.25 R:0.5 B1:0.25",
                        "R:0.25 E1:0.25 R:0.25 E1:0.25 R:0.5 E1:0.25 R:0.25 E1:0.25 R:0.25 E1:0.25",
                        "R:0.5 E1:0.25 R:0.25 G1:0.25 R:0.25 G1:0.25 R:0.5 G1:0.25 R:0.25 G1:0.25",
                        "R:0.25 G1:0.25 R:0.5 G1:0.25 R:0.25 F#1:0.25 R:0.25 F#1:0.25 R:0.5 F#1:0.25",
                        "R:0.25 F#1:0.25 R:0.25 F#1:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Wonderwall",
                "Alt",
                87,
                phrase(
                        "R:1.25 F#4:1.25 R:0.25 D4:0.75 C#4:0.75 D4:0.75 A4:0.75 F#4:1.5 R:1.25 D4:0.75",
                        "C#4:0.75 R:0.25 D4:0.75 R:0.25 A4:0.75 R:0.25 F#4:1.25 R:1.5 D4:0.75 C#4:0.75",
                        "R:0.25 D4:0.75 R:0.25 A4:0.75 F#4:0.75 R:2 D4:0.75 C#4:0.75 R:0.25 D4:0.75",
                        "R:0.25 A4:0.75 F#4:1.75 R:1.25 D4:0.75 C#4:0.75 R:0.25 D4:0.75 A4:0.75 F#4:2",
                        "R:0.5 D4:0.75 C#4:0.5 R:0.25 D4:0.75 R:0.25 A4:0.5 R:0.25 F#4:2.25 R:0.5",
                        "D4:0.75 C#4:0.5 R:0.25 D4:0.75 R:0.25 A4:0.75 R:0.25 F#4:1.5"
                )
        ));

        seeds.add(simpleSeed(
                "She's Electric",
                "Alt",
                126,
                phrase(
                        "R:0.25 F#4:0.25 R:0.75 Ab4:2 R:0.5 Ab4:0.75 R:0.25 F#4:0.5 R:0.5 E4:0.5",
                        "R:1.5 E4:0.25 R:0.25 F#4:0.5 R:0.25 E4:0.25 R:0.25 Ab4:0.5 R:0.25 B4:0.25",
                        "R:0.25 Ab4:0.25 R:0.25 C#5:0.25 R:0.5 B4:0.5 R:0.5 Ab4:0.5 R:0.25 E4:0.5",
                        "B4:0.25 B4:0.25 R:0.5 B4:0.25 R:0.75 A4:0.25 E4:0.25 R:0.25 F#4:0.5 R:0.25",
                        "E4:0.25 Ab4:0.25 Ab4:0.75 R:0.25 Ab4:0.5 R:0.5 Ab4:0.25 R:0.25 A4:0.25 R:0.25",
                        "Ab4:0.25 R:0.25 F#4:0.25 R:0.5 E4:0.5 R:1.5 E4:0.25 R:0.25 F#4:0.5 R:0.25",
                        "E4:0.25 R:0.25 C3:1.5 G4:0.75 E4:1 C4:0.75 F#4:0.5 E4:0.25 G3:0.25 R:0.25",
                        "F#4:0.25 F#4:0.5 R:0.25 G4:0.25 D4:0.5 F#4:0.5 A3:0.25 R:0.25 E4:0.25 E4:0.75",
                        "E3:3.5 Ab4:1 B3:0.25 A4:0.5 Ab4:1 E4:0.25 R:5.75 Ab4:1 R:0.25 F#4:0.25",
                        "R:0.25 E4:0.25 R:0.25 F#4:0.25 R:0.25 E4:1.75 R:3.25 B4:0.25 R:0.25 B4:0.25",
                        "R:0.25 B4:0.25 R:0.25 C#4:0.75 A4:15.25 F#4:0.75 Ab4:0.25 B3:0.25 C#4:0.25 E4:2",
                        "C#3:0.75 E3:0.25 C#3:0.75 B2:0.25 G3:1 G4:0.75 C4:1 G4:0.25 F#4:0.25 E4:0.25",
                        "F#4:0.75 E4:0.25 Ab4:1 F#4:0.75 E4:0.25 G4:0.25 Ab4:0.25 B4:0.25 R:0.25 C#5:0.25",
                        "R:0.25 B4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Spiderwebs",
                "Rock",
                143,
                phrase(
                        "R:0.25 Bb3:0.25 R:13.5 F3:0.5 D4:0.5 R:0.25 D4:0.25 R:0.25 D4:0.75 R:0.25",
                        "Bb3:0.25 R:0.5 Bb3:0.25 C4:2.5 R:1 F3:0.75 D4:0.25 R:0.25 D4:0.5 R:0.25",
                        "D4:0.5 R:0.25 Bb3:0.25 R:0.5 Bb3:0.25 C4:2.25 R:1 F3:0.75 D4:0.25 R:0.25",
                        "D4:0.25 R:0.5 D4:0.5 R:0.5 Bb3:0.25 R:0.5 C4:2.25 Bb3:0.25 R:0.25 F3:0.5",
                        "G3:0.5 F3:0.75 D4:0.25 R:0.25 D4:0.25 R:0.5 D4:0.5 R:0.25 Bb3:0.25 R:0.5",
                        "C4:1.5 R:0.25 D4:0.25 C#4:0.25 C4:0.25 Bb3:1.25"
                )
        ));

        seeds.add(simpleSeed(
                "All I Want For Christmas Is You",
                "Dance",
                149,
                phrase(
                        "R:1.25 G3:0.75 B3:0.75 D4:0.75 F#4:0.5 G4:0.75 F#4:1.25 E4:0.5 D4:1.25 A4:0.75",
                        "G4:0.75 G4:0.5 F#4:0.75 G4:0.75 F#4:0.75 E4:0.5 D4:1.5 R:0.75 E4:0.75 G4:0.75",
                        "A4:0.5 B4:0.75 A4:0.75 G4:0.75 E4:1.25 C4:0.75 Eb4:0.5 G4:1.25 A4:0.5 Bb4:0.75",
                        "A4:0.75 F4:0.75 Eb4:1.25 G3:0.75 B3:0.75 D4:0.75 F#4:0.5 G4:0.75 F#4:1.25 E4:0.5",
                        "D4:1.25 A4:0.75 G4:0.75 G4:0.5 F#4:0.75 G4:0.75 F#4:0.75 E4:0.5 D4:1.5 R:0.75",
                        "E4:0.75 G4:0.75 A4:0.5 B4:0.75 A4:0.75 G4:0.75 E4:1.25 C4:0.75 Eb4:0.5 G4:1.25",
                        "A4:0.5 Bb4:0.75 A4:0.75 F4:0.75 Eb4:1.25 G4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Born to make you happy",
                "Dance",
                169,
                phrase(
                        "R:2.25 D4:0.25 R:0.25 F#4:0.25 R:0.25 F#4:0.25 R:0.25 F#4:0.25 R:0.25 F#4:0.25",
                        "R:0.25 F#4:0.5 R:0.25 E4:0.25 R:0.25 E4:0.25 R:0.5 E4:0.25 R:0.25 D4:0.25",
                        "R:0.25 F#4:0.5 R:6.75 D4:0.25 R:0.25 F#4:0.25 R:0.25 F#4:0.25 R:0.25 F#4:0.25",
                        "R:0.25 F#4:0.25 R:0.25 F#4:0.5 R:0.25 E4:0.25 R:0.25 E4:0.25 R:0.25 E4:0.25",
                        "R:0.25 D4:0.25 R:0.25 E4:0.25 R:0.25 D4:0.25 R:2.25 D4:0.25 R:0.5 D4:0.25",
                        "R:0.5 D4:0.5 R:2.5 D4:0.25 R:0.25 A4:0.25 R:0.25 A4:0.25 R:0.25 A4:0.25",
                        "R:0.25 F#4:0.25 R:0.25 G4:0.5 R:0.25 F#4:0.25 R:0.25 E4:0.5 R:0.25 D4:0.25",
                        "R:0.25 B3:0.25 R:0.25 D4:0.5 R:1.25 D4:0.25 R:0.25 D4:0.25 R:0.25 E4:0.25",
                        "R:0.25 F#4:0.25 R:0.5 G4:0.25 R:0.25 F#4:0.25 R:0.25 F#4:0.5 R:1 D4:0.25",
                        "R:0.25 A4:0.25 R:0.25 A4:0.25 R:0.25 A4:0.25 R:0.25 F#4:0.25 R:0.25 G4:0.5",
                        "R:0.5 E4:0.25 R:0.25 D4:0.25 R:0.25 D4:0.5 R:1.5 D4:0.25 R:0.25 D4:0.25",
                        "R:0.25 B4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.5 R:0.25 F#4:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "What's Up",
                "Rock",
                134,
                phrase(
                        "R:1.75 A3:4 R:0.5 A3:0.25 R:0.25 A3:1.25 R:0.25 F#3:1.75 R:0.25 E3:1.25",
                        "R:0.25 A3:0.75 R:0.25 F#3:0.75 R:0.25 E3:1.25 R:0.25 A3:0.5 R:0.25 F#3:0.5",
                        "R:0.25 A3:5.25 A3:0.25 R:0.25 D4:0.5 A3:0.5 R:0.25 E4:1 R:0.25 A3:4.5",
                        "R:0.25 Eb3:0.5 R:0.25 E3:0.5 R:0.25 Eb3:0.5 R:0.25 E3:0.75 R:0.25 A3:5",
                        "R:0.5 A3:0.25 R:0.25 A3:1.25 R:0.25 F#3:1.75 R:0.25 E3:1.25 R:0.25 A3:0.75",
                        "R:0.25 F#3:0.75 R:0.25 E3:1.25 R:0.25 A3:0.5 R:0.25 F#3:0.5 R:0.25 A3:5.25",
                        "A3:0.25 R:0.25 D4:0.5 A3:0.5 R:0.25 E4:0.75 R:0.25 A3:4.75 R:0.25 E3:0.75",
                        "R:0.25 Eb3:0.5 R:0.25 E3:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "All I Want For Christmas Is You",
                "Dance",
                149,
                phrase(
                        "R:1.25 B4:2 D5:0.5 B4:0.75 B4:1.25 A4:1.25 D5:0.75 B4:0.75 R:0.75 B4:0.75",
                        "B4:0.5 C5:0.75 B4:0.75 A4:0.75 G4:0.75 G4:0.5 E4:0.75 F#4:0.5 G4:0.75 A4:0.75",
                        "A4:1.25 G4:2.5 R:1.5 A4:0.5 B4:0.75 E5:1.25 D5:0.5 A4:0.75 G4:1.25 B4:0.75",
                        "D5:0.5 A4:0.75 G4:0.75 B4:3.5 R:1.25 D4:0.5 G4:0.5 A4:0.75 A4:0.75 A4:0.75",
                        "G4:0.75 G4:1.25 R:1.25 D4:0.5 G4:0.5 A4:0.75 E5:0.75 D5:0.75 A4:0.75 G4:1.25",
                        "B4:0.5 B4:0.5 B4:0.5 B4:0.5 B4:0.75 B4:0.5 B4:0.5 B4:0.5 B4:0.5 B4:0.5",
                        "B4:0.5 B4:0.75 B4:0.5 B4:0.5 E5:0.75 D5:0.5 B4:0.5 D5:0.5 B4:0.5 D5:0.5",
                        "E5:0.75 D5:0.75 B4:0.5 A4:0.25 G4:0.25 E4:0.25 G4:0.5 A4:0.5 G3:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Wanted",
                "Rock",
                111,
                phrase(
                        "R:1 D2:1 R:0.25 D2:0.5 R:0.25 Bb1:1 R:0.25 Bb1:0.5 R:0.25 Bb1:0.75",
                        "R:0.25 F1:0.5 R:0.25 F1:0.5 R:0.25 F1:0.5 R:0.25 C2:1 R:0.25 C2:0.5",
                        "R:0.25 C2:1 R:0.25 D2:0.75 R:0.25 D2:0.5 R:0.25 Bb1:1 R:0.25 Bb1:0.5",
                        "R:0.25 Bb1:0.75 R:0.25 F1:0.5 R:0.25 F1:0.5 R:0.25 F1:0.5 R:0.25 C2:1",
                        "R:0.25 D1:0.5 R:0.25 F1:0.5 R:0.25 G1:0.5 R:0.25 D2:0.75 R:0.25 D2:0.5",
                        "R:0.25 Bb1:1 R:0.25 Bb1:0.5 R:0.25 Bb1:0.75 R:0.25 F1:0.5 R:0.25 F1:0.5",
                        "R:0.25 F1:0.5 R:0.25 C2:1 R:0.25 C2:0.5 R:0.25 C2:1 R:0.25 D2:0.75",
                        "R:0.25 D2:0.5 R:0.25 Bb1:1 R:0.25 Bb1:0.5 R:0.25 Bb1:0.75 R:0.25 F1:0.5",
                        "R:0.25 F1:0.5 R:0.25 F1:0.5 R:0.25 C2:1 R:0.25 C2:0.5 R:0.25 C2:0.75",
                        "R:0.25 D2:1 R:0.25 D2:0.5 R:0.25 Bb1:1 R:0.25 Bb1:0.5 R:0.25 Bb1:0.75",
                        "R:0.25 F1:0.5 R:0.25 F1:0.5 R:0.25 F1:0.5 R:0.25 C2:1 C2:0.5 R:0.25",
                        "C2:1 R:0.25 D2:1 R:0.25 D2:0.5 R:0.25 Bb1:1 R:0.25 Bb1:0.5 R:0.25",
                        "Bb1:0.75 R:0.25 F1:0.5 R:0.25 F1:0.5 R:0.25 F1:0.5 R:0.25 C2:1 R:0.25",
                        "D1:0.5 R:0.25 F1:0.5 R:0.25 G1:0.5 R:0.25 D2:1 R:0.25 D2:0.5 R:0.25",
                        "Bb1:1 R:0.25 Bb1:0.5 R:0.25 Bb1:0.75 R:0.25 F1:0.5 R:0.25 F1:0.5 R:0.25",
                        "F1:0.5 R:0.25 C2:1 R:0.25 C2:0.5 R:0.25 C2:0.75 R:0.25 D2:1 R:0.25",
                        "D2:0.5 R:0.25 Bb1:1 R:0.25 Bb1:0.5 R:0.25 Bb1:0.75 R:0.25 F1:0.5 R:0.25",
                        "F1:0.5 R:0.25 F1:0.5 R:0.25 C2:1 R:0.25 C2:0.5 R:0.25 C2:0.75 R:0.25",
                        "D2:0.75"
                )
        ));

        seeds.add(simpleSeed(
                "Spiderwebs",
                "Rock",
                144,
                phrase(
                        "R:1.75 Bb4:0.75 R:0.75 Bb4:0.75 R:0.75 A4:0.75 R:0.75 A4:0.75 R:0.75 Bb4:0.75",
                        "R:0.75 Bb4:0.75 R:0.75 G4:0.75 R:0.75 G4:0.75 R:0.75 Bb4:0.75 R:0.75 Bb4:0.75",
                        "R:0.75 A4:0.75 R:0.75 A4:0.75 R:1 Bb4:0.75 R:1 Bb4:0.75 Eb4:0.5 Eb4:0.5",
                        "D4:0.5 D4:0.5 C4:0.5 C4:0.5 Bb3:0.5 A3:0.5 Bb2:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "No Surprises",
                "Alt",
                75,
                phrase(
                        "Bb2:0.25 G2:0.75 F2:3.25 F2:0.5 R:0.25 F2:2.5 F2:3.25 Bb2:1.25 Bb2:0.5 Bb2:0.75",
                        "A2:0.5 G2:0.5 F2:3.25 F2:0.5 R:0.5 F2:1.25 R:0.5 F2:0.5 F2:0.5 F2:2.5",
                        "F2:0.5 F2:0.5 Bb2:1.25 Bb2:0.75 Bb2:0.5 A2:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "U Can't Touch This",
                "Alt",
                134,
                phrase(
                        "R:1.25 D3:0.75 R:0.25 C3:0.5 R:0.25 B2:0.5 R:0.25 A2:0.5 R:1 E2:0.5",
                        "R:0.25 G2:0.5 R:1 B2:0.5 R:0.25 A2:0.5 R:1.5 D3:0.75 R:0.25 C3:0.5",
                        "R:0.25 B2:0.5 R:0.25 A2:0.5 R:1 E2:0.5 R:0.25 G2:0.5 R:1 B2:0.5",
                        "R:0.25 A2:0.5 R:1.5 D3:0.75 R:0.25 C3:0.5 R:0.25 B2:0.5 R:0.25 A2:0.5",
                        "R:1 E2:0.5 R:0.25 G2:0.5 R:1 B2:0.5 R:0.25 A2:0.5 R:1.5 D3:0.75",
                        "R:0.25 C3:0.5 R:0.25 B2:0.5 R:0.25 A2:0.5 R:1 E2:0.5 R:0.25 G2:0.5",
                        "R:1 B2:0.5 R:0.25 A2:0.5 R:1.25 D3:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Experiencia Religiosa",
                "Pop",
                112,
                phrase(
                        "R:0.25 C4:0.75 G2:0.5 E4:1.5 R:0.5 E4:1.75 R:0.5 E4:1.75 R:0.5 E4:1.75",
                        "R:0.25 C2:0.25 R:0.25 F4:1.5 R:0.5 F4:1.5 R:0.5 F4:0.5 A3:1.25 C4:0.5",
                        "A4:0.75 G4:0.5 C4:5.5 C5:0.5 A4:2 A4:1.5 R:0.5 C4:1.75 R:0.5 C4:2",
                        "R:0.25 E4:1.5 R:0.5 E4:2 R:1.25 F4:1.25 C3:7.75 F4:1 G2:0.5 E4:1.5",
                        "E4:1.75 E4:1.75 D3:0.25 R:0.25 E4:1.75 R:0.25 F4:1.75 R:0.5 F4:1.75 R:0.5",
                        "F4:0.5 C4:1.5 F4:1.5 C5:1.75 E3:2 C4:1.25 E4:0.75 E4:0.25 A4:1.5 R:0.5",
                        "A4:1.5 F#2:0.5 F4:1.75 R:0.5 F4:1.5 R:0.5 E4:1.75 R:0.25 E4:1.75 R:0.5",
                        "E4:0.5 G3:0.5 C4:0.75 E4:0.25 R:0.25 E4:1"
                )
        ));

        seeds.add(simpleSeed(
                "Fearless",
                "Pop",
                100,
                phrase(
                        "R:0.25 A4:0.25 R:0.25 G4:0.25 R:1 C4:0.25 R:0.25 C4:0.25 R:0.25 G4:0.25",
                        "R:0.25 G4:0.5 R:0.5 F4:1 R:0.25 C4:0.5 R:0.75 A4:0.5 R:0.5 G4:0.25",
                        "R:0.25 G4:0.25 R:0.25 A4:0.5 R:0.5 D4:0.25 R:0.25 D4:0.25 R:0.25 Bb4:0.25",
                        "R:0.25 A4:0.25 R:0.25 G4:0.25 R:0.25 F4:0.25 R:0.25 F4:0.25 R:0.25 D4:0.25",
                        "R:0.25 F4:1.25 R:0.25 C4:0.25 R:1 G4:0.5 R:0.25 F4:0.25 R:0.25 F4:1.25",
                        "R:0.5 C4:0.25 R:0.25 C4:0.25 R:0.25 G4:0.25 R:0.25 G4:0.5 R:0.75 F4:1",
                        "R:0.25 C4:0.5 R:0.5 A4:1 R:0.25 G4:0.25 R:0.25 G4:0.5 R:0.25 A4:0.25",
                        "R:0.25 D4:1 R:1 Bb4:0.25 R:0.25 A4:0.25 R:0.25 G4:0.5 R:0.25 F4:0.25",
                        "R:0.25 F4:0.25 R:0.25 D4:0.25 R:0.25 F4:1 R:0.25 C4:1 R:0.25 G4:0.5",
                        "R:0.25 F4:0.25 R:0.25 F4:1"
                )
        ));

        seeds.add(simpleSeed(
                "Drops Of Jupiter",
                "Pop",
                80,
                phrase(
                        "R:1.5 G4:1 R:0.25 G4:1.5 D4:0.25 R:0.25 G4:0.75 R:0.25 G4:0.75 R:0.25",
                        "G4:0.5 R:0.25 G4:1 R:0.25 F#4:1.5 R:0.25 F#4:1.5 E3:0.25 F#4:0.5 R:0.25",
                        "F#4:1 E3:0.5 E3:0.25 F#4:0.5 R:0.25 F#4:1 R:0.25 F4:1.5 R:0.25 F4:1.25",
                        "R:0.25 F4:0.75 R:0.25 F4:0.75 R:0.25 F4:0.25 R:0.25 F4:0.5 R:0.25 F4:0.25",
                        "R:0.25 F4:0.5 R:0.25 E4:1.5 G3:0.25 R:0.25 E4:1.5 G3:0.25 R:0.25 E4:0.5",
                        "R:0.25 E4:0.25 R:0.25 E4:0.75 R:0.25 E4:0.5 R:0.25 E4:1 R:0.25 G4:1.5",
                        "R:0.25 G4:1.5 D4:0.25 R:0.25 G4:0.75 R:0.25 G4:0.75 R:0.25 G4:0.5 R:0.25",
                        "G4:1 R:0.25 F#4:1.5 R:0.25 F#4:1.5 E3:0.25 F#4:0.5 R:0.25 F#4:1 E3:0.5",
                        "E3:0.25 F#4:0.5 R:0.25 F#4:1 R:0.25 F4:5.75 R:0.25 F4:5.5"
                )
        ));

        seeds.add(simpleSeed(
                "Square One",
                "Alt",
                62,
                phrase(
                        "R:3 Eb4:0.5 Eb4:0.5 D4:1.5 D4:0.5 C4:0.5 D4:1 C4:3 R:8.25 C4:0.5",
                        "C4:0.5 Bb3:1.5 Bb3:0.5 C3:0.5 Bb3:1 A3:2 R:9.25 Eb4:0.5 Eb4:0.5 D4:1.5",
                        "D4:0.5 C4:0.5 D4:1 C4:3 R:3.25 C3:0.5 C4:0.5 Bb3:2.5 R:1.5 D4:0.5",
                        "D4:0.5 D4:0.5 D4:0.5 C4:0.5 D4:0.5 C4:0.5 Eb4:0.5 D4:0.5 C4:3 R:2.5",
                        "Eb4:0.5 Eb4:0.5 Eb4:0.5 Eb4:0.5 D4:0.5 D4:0.5 C4:0.5 C4:3.75 G4:3.75 C5:7.75"
                )
        ));

        seeds.add(simpleSeed(
                "World Of Our Own",
                "Pop",
                98,
                phrase(
                        "R:0.25 C#4:0.25 R:0.25 Ab3:0.5 R:0.25 Ab3:0.25 R:0.25 Ab3:0.5 R:0.25 C#4:0.25",
                        "R:0.5 Ab3:0.5 R:0.25 Ab3:0.5 R:0.25 Ab3:0.5 R:0.25 C#4:0.5 R:0.25 Ab3:0.5",
                        "R:0.25 Ab3:0.25 R:0.25 Ab3:0.5 R:0.25 C#4:0.25 R:0.5 Ab3:0.5 R:0.25 Ab3:0.5",
                        "R:0.25 Ab3:0.5 R:0.25 C#4:0.5 R:0.25 Ab3:0.5 R:0.25 Ab3:0.25 R:0.25 Ab3:0.5",
                        "R:0.25 C#4:0.25 R:0.5 Ab3:0.5 R:0.25 Ab3:0.5 R:0.25 Ab3:0.5 R:0.25 C#4:0.5",
                        "R:0.25 Ab3:0.5 R:0.25 F#3:0.25 R:0.25 F#3:0.5 R:0.25 C#4:0.25 R:0.5 F#3:0.5",
                        "R:0.25 F#3:0.5 R:0.25 F#3:0.5 R:0.25 C#4:0.5 R:0.25 F#3:0.5 R:0.25 Ab3:0.25",
                        "R:0.25 Ab3:0.5 R:0.25 C#4:0.25 R:0.5 Ab3:0.5 R:0.25 Ab3:0.5 R:0.25 Ab3:0.5",
                        "R:0.25 C#4:0.5 R:0.25 Ab3:0.5 R:0.25 Ab3:0.25 R:0.25 Ab3:0.5 R:0.25 C#4:0.25",
                        "R:0.5 Ab3:0.5 R:0.25 Ab3:0.5 R:0.25 Ab3:0.5 R:0.25 C#4:0.5 R:0.25 Ab3:0.5",
                        "R:0.25 Ab3:0.25 R:0.25 Ab3:0.5 R:0.25 C#4:0.25 R:0.5 Ab3:0.5 R:0.25 Ab3:0.5",
                        "R:0.25 Ab3:0.5 R:0.25 C#4:0.5 R:0.25 Ab3:0.5 R:0.25 F#3:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Island in the Sun",
                "Rock",
                114,
                phrase(
                        "R:1.25 E4:1 G4:1 E4:1 G4:1 E4:1 G4:1 E4:1 G4:0.5 D4:1",
                        "F#4:1 D4:1 F#4:1 D4:1 G4:1 D4:1 G4:1 E4:1 G4:1 E4:1",
                        "G4:1 E4:1 G4:1 E4:1 G4:0.5 D4:1 F#4:1 D4:1 F#4:1 D4:1",
                        "G4:1 D4:1 G4:1 E4:1 G4:1 E4:1 G4:1 E4:1 G4:1 E4:1",
                        "G4:0.5 D4:1 F#4:1 D4:1 F#4:1 D4:1 G4:1 D4:1 G4:1 E4:1",
                        "G4:1 E4:1 G4:1 E4:1 G4:1 E4:1 G4:0.5 D4:1 F#4:1 D4:1",
                        "F#4:1 D4:1 G4:1 D4:1 G4:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Fifteen",
                "Pop",
                95,
                phrase(
                        "R:0.25 E4:0.25 R:0.25 G4:1.75 R:0.75 B4:0.75 R:0.5 A4:1.25 R:0.25 G4:0.5",
                        "R:0.75 A4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.5",
                        "R:0.25 G4:0.5 R:0.25 A4:1.25 R:0.25 D4:0.75 R:0.75 D4:0.25 R:0.25 A4:0.25",
                        "R:0.25 A4:0.5 R:0.25 B4:1 R:0.25 A4:1.25 R:0.25 G4:2 R:0.5 D4:0.5",
                        "R:0.25 E4:0.5 R:0.25 G4:1.75 R:0.75 B4:1 R:0.25 G4:1 R:0.25 D4:1",
                        "R:0.25 A4:1.25 R:0.25 G4:1 R:0.25 F#4:0.75 R:0.5 F#4:1 R:0.25 F#4:1.25",
                        "R:0.25 G4:1 R:0.25 B3:1 R:0.25 C4:1.25 R:0.25 D4:3 R:0.25 C4:1.25",
                        "R:0.75 D4:0.5 R:0.25 B4:0.5 R:0.25 A4:1 R:0.25 G4:0.5 R:0.25 B4:0.5",
                        "R:0.25 A4:0.75 R:0.5 G4:0.5 R:0.25 B4:0.5 R:0.25 A4:1 R:0.25 G4:0.5",
                        "R:0.25 G4:0.5 R:0.25 A4:0.5 R:0.25 B4:0.5 R:0.25 C5:0.5 R:0.25 B4:0.5",
                        "R:0.25 A4:1 R:0.25 G4:1 R:0.25 C4:1.5 R:1.75 D4:0.25 R:0.25 D4:0.5",
                        "R:0.25 D4:0.5 R:0.25 D4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.75",
                        "R:0.5 A4:1.25 R:0.25 G4:1"
                )
        ));

        seeds.add(simpleSeed(
                "Love Story",
                "Pop",
                119,
                phrase(
                        "R:0.75 D4:0.5 C#4:0.5 D4:0.5 D4:0.5 D4:0.5 D4:1 D4:0.5 D4:0.5 C#4:0.5",
                        "D4:1 E4:1 D4:0.5 D4:0.5 D4:0.5 C#4:0.5 D4:0.5 D4:0.5 R:0.5 D4:0.5",
                        "D4:0.5 D4:0.5 D4:0.5 A4:0.5 G4:1 F#4:0.5 R:0.5 E4:0.5 F#4:0.5 E4:0.5",
                        "F#4:0.5 E4:0.5 F#4:0.5 F#4:0.5 F#4:1 D4:0.5 R:0.5 E4:0.5 E4:0.5 E4:0.5",
                        "E4:1 D4:1 F#4:0.5 G4:0.5 F#4:3 R:1.5 F#4:0.5 F#4:1 D4:1.5"
                )
        ));

        seeds.add(simpleSeed(
                "Clocks",
                "Alt",
                132,
                phrase(
                        "R:0.25 F2:0.25 R:0.25 F2:0.5 R:0.25 F2:0.5 R:0.25 Eb2:0.5 R:0.25 Eb2:0.5",
                        "R:0.25 Eb2:0.5 R:0.25 Eb2:0.5 Eb2:0.5 R:0.25 Eb2:0.5 R:0.25 Eb2:0.5 R:0.25",
                        "Eb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25",
                        "Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25",
                        "Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5",
                        "R:0.25 Bb2:0.5 R:0.25 F2:0.5 R:0.25 F2:0.5 R:0.25 F2:0.5 R:0.25 F2:0.5",
                        "R:0.25 F2:0.5 R:0.25 F2:0.5 R:0.25 F2:0.5 R:0.25 F2:0.5 R:0.25 Eb2:0.5",
                        "R:0.25 Eb2:0.5 R:0.25 Eb2:0.5 R:0.25 Eb2:0.5 R:0.25 Eb2:0.5 R:0.25 Eb2:0.5",
                        "R:0.25 Eb2:0.5 R:0.25 Eb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5",
                        "Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25",
                        "Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 Bb2:0.5",
                        "R:0.25 Bb2:0.5 R:0.25 Bb2:0.5 R:0.25 F2:0.5 R:0.25 F2:0.5 R:0.25 F2:0.5",
                        "F2:0.5 F2:0.5 R:0.25 F2:0.5 R:0.25 F2:0.5 R:0.25 F2:0.5 R:0.25 Eb2:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Bad Day",
                "Pop",
                70,
                phrase(
                        "R:2.75 Bb3:0.5 R:0.25 C4:0.25 R:0.25 Eb4:0.5 R:0.25 Eb4:0.75 R:0.25 C4:0.25",
                        "R:0.25 C4:0.5 R:0.25 C4:0.75 R:0.25 D4:0.25 R:0.25 Eb4:0.5 R:0.25 Eb4:0.75",
                        "R:0.25 D4:0.5 R:7.25 Bb3:0.25 R:0.25 Bb3:0.5 R:0.25 C4:0.25 R:0.25 Eb4:0.5",
                        "R:0.25 Eb4:0.75 R:0.25 C4:0.25 R:0.25 C4:0.5 R:0.25 C4:0.75 R:0.25 D4:0.25",
                        "R:0.25 Eb4:0.5 R:0.25 Eb4:0.75 R:0.25 D4:0.5 R:7.25 D4:0.25 R:0.25 D4:0.5",
                        "R:0.25 Eb4:0.25 R:0.25 Eb4:0.5 R:0.25 D4:0.75 R:0.25 Bb3:0.75 R:0.25 G3:0.75",
                        "R:0.25 Bb3:0.25 R:0.25 D4:0.5 R:0.25 D4:0.75 R:0.25 C4:0.5 R:0.25 C4:0.25",
                        "R:0.25 C4:0.5 R:0.25 Bb3:0.25 R:0.25 C4:0.5 R:0.25 Bb3:0.75 R:0.25 Eb3:0.75",
                        "R:0.25 Eb3:0.75 R:0.25 F3:0.25 R:0.25 G3:0.5 R:0.25 G3:0.5 R:0.25 F3:0.75",
                        "R:0.25 F3:0.25 R:0.25 F3:0.5 R:0.25 G3:0.75 R:0.25 Ab3:0.75 R:0.25 Bb3:0.75",
                        "R:0.25 C4:1 R:0.25 Eb4:0.5 R:0.25 F4:2.25"
                )
        ));

        seeds.add(simpleSeed(
                "Who says you can't go home",
                "Rock",
                132,
                phrase(
                        "R:2 B3:0.25 R:0.5 B3:0.5 R:0.75 B3:0.25 R:0.25 B3:0.25 R:0.25 B3:0.25",
                        "R:0.25 B3:0.25 R:0.5 C4:0.75 R:0.75 C4:0.25 R:0.25 C4:0.25 R:0.25 B3:0.25",
                        "R:0.25 B3:0.25 R:0.5 B3:0.75 R:0.25 G3:0.25 R:0.25 B3:0.25 R:0.25 G3:0.25",
                        "R:0.5 B3:0.25 R:0.25 G3:0.25 R:0.25 B3:0.5 R:0.25 G3:0.5 R:1.75 G3:0.25",
                        "R:0.25 B3:0.25 R:0.25 B3:0.25 R:0.25 B3:0.5 R:1.25 C4:0.5 R:0.5 B3:0.5",
                        "R:0.5 A3:0.5 R:0.25 G3:0.5 R:0.25 F#3:0.25 R:0.25 F#3:1.5 R:7 B3:0.25",
                        "R:0.5 B3:0.5 R:0.75 B3:0.25 R:0.25 B3:0.25 R:0.25 B3:0.25 R:0.25 B3:0.5",
                        "R:0.5 C4:0.75 R:0.75 B3:0.25 R:0.25 C4:0.25 R:0.25 B3:0.25 R:0.25 B3:0.5",
                        "R:0.25 G3:0.25 R:0.25 B3:0.75 R:0.25 G3:0.25 R:0.25 B3:0.5 R:0.25 G3:0.25",
                        "R:0.25 B3:0.25 R:0.25 G3:0.25 R:0.25 B3:0.5 R:0.25 G3:0.5 R:1.25 G3:0.25",
                        "R:0.25 B3:0.5 R:0.5 B3:0.5 R:0.5 B3:0.5 R:0.5 C4:0.5 R:0.25 B3:0.75",
                        "R:0.25 A3:0.5 R:0.25 G3:0.25 R:0.25 F#3:0.25 R:0.25 F#3:1.5 R:3 G3:0.25",
                        "R:0.25 G3:0.25 R:0.25 G3:0.25 R:0.25 G3:0.75 R:0.25 E3:0.25 R:0.25 D3:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Sk8er Boi",
                "Pop",
                150,
                phrase(
                        "R:0.75 D4:0.25 R:1.5 A4:0.5 A4:0.5 A4:0.5 Bb4:0.5 A4:0.5 F4:0.75 A4:0.5",
                        "A4:0.5 A4:0.5 A4:0.5 Bb4:0.5 A4:0.5 F4:1.25 F4:0.5 F4:0.5 F4:0.5 E4:1.25",
                        "E4:0.5 G4:0.75 E4:0.75 F4:1.25 R:0.75 A4:0.5 A4:0.5 A4:0.5 Bb4:0.5 A4:0.5",
                        "F4:0.75 A4:0.5 A4:0.5 A4:0.5 A4:0.5 Bb4:0.5 A4:0.5 F4:1.25 F4:0.5 F4:0.5",
                        "F4:0.5 E4:1.25 E4:0.5 G4:0.75 E4:0.75 G4:0.75 F4:0.5 E4:0.5 F4:0.75"
                )
        ));

        seeds.add(simpleSeed(
                "Fireflies",
                "Pop",
                90,
                phrase(
                        "R:1.75 Bb3:0.25 F4:0.75 F4:0.75 Eb4:0.25 F4:0.75 Eb4:0.75 Bb3:0.75 R:1.25 Bb3:0.25",
                        "C4:0.75 C4:0.75 Bb3:0.25 C4:0.75 Eb4:0.75 F4:1.25 R:0.75 G4:0.75 F4:0.75 Eb4:0.25",
                        "Bb3:0.75 Bb3:0.25 Bb3:0.75 F4:0.25 Eb4:0.75 C4:3 R:3.25 Bb3:0.25 F4:0.75 F4:0.75",
                        "Eb4:0.25 F4:0.75 Eb4:0.75 Bb3:0.75 R:1.25 C4:0.75 C4:0.75 Bb3:0.75 C4:0.25 Eb4:0.75",
                        "F4:1 Bb3:0.25 Bb3:0.75 G4:1 F4:0.75 Eb4:0.25 Bb3:0.75 Bb3:0.25 G4:0.75 F4:0.75",
                        "C4:0.75 R:1.25 Eb4:2.75"
                )
        ));

        seeds.add(simpleSeed(
                "Yellow",
                "Alt",
                87,
                phrase(
                        "R:0.25 Eb4:0.25 R:0.25 Eb4:0.75 R:0.25 Ab4:0.75 R:0.25 Ab4:0.75 R:0.25 Ab4:0.75",
                        "R:0.25 Ab4:0.75 R:0.25 Ab4:0.75 R:0.25 Ab4:0.75 R:0.25 Ab4:0.75 R:0.25 Ab4:0.75",
                        "R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75",
                        "R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 Ab4:0.75 R:0.25 Ab4:0.75",
                        "R:0.25 Ab4:0.75 R:0.25 Ab4:0.75 R:0.25 Ab4:0.75 R:0.25 Ab4:0.75 R:0.25 Ab4:0.75",
                        "R:0.25 Ab4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75",
                        "R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 Ab4:0.75",
                        "R:0.25 Ab4:0.75 R:0.25 Ab4:0.75 R:0.25 Ab4:0.75 R:0.25 Ab4:0.75 R:0.25 Ab4:0.75",
                        "R:0.25 Ab4:0.75 R:0.25 Ab4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75",
                        "R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75 R:0.25 F#4:0.75",
                        "R:0.25 E3:1 R:0.25 Eb4:1.25 R:0.25 E3:0.75 R:0.25 Eb4:0.75 R:0.25 Eb4:0.75",
                        "R:0.25 E3:0.25 R:0.25 Eb4:0.25 R:0.25 Eb4:5.25"
                )
        ));

        seeds.add(simpleSeed(
                "We Belong Together",
                "Dance",
                140,
                phrase(
                        "R:1 F1:0.5 R:2 G1:0.5 R:2 G1:0.5 R:0.25 D2:0.5 R:0.25 G1:0.5",
                        "F1:0.5 R:0.25 E1:0.5 R:2 F1:0.5 R:1.5 F1:0.5 R:0.25 C1:0.5 R:0.5",
                        "C1:0.5 R:0.5 F1:0.5 R:2 G1:0.5 R:2 G1:0.5 R:0.25 D2:0.5 R:0.25",
                        "G1:0.5 F1:0.5 R:0.25 E1:0.5 R:2 F1:0.5 R:1.5 F1:0.5 R:0.25 C1:0.5",
                        "R:0.5 C1:0.5 R:0.5 F1:0.5 R:2 G1:0.5 R:2 G1:0.5 R:0.25 D2:0.5",
                        "G1:0.5 R:0.25 F1:0.5 R:0.25 E1:0.5 R:2 F1:0.5 R:1.5 F1:0.5 C1:0.5",
                        "R:0.5 C1:0.5 R:0.5 F1:0.5 R:2 G1:0.5 R:2 G1:0.5 R:0.25 D2:0.5",
                        "G1:0.5 R:0.25 F1:0.5 R:0.25 E1:0.5 R:2 F1:0.5 R:1.5 F1:0.5 R:0.25",
                        "C1:0.5 R:0.5 C1:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "You And Me",
                "Rock",
                96,
                phrase(
                        "R:1.25 Eb3:1.75 D4:0.5 Bb3:0.5 G3:0.25 R:0.25 F4:0.5 R:0.25 D4:0.25 R:0.25",
                        "Bb3:0.5 R:0.25 F4:0.5 R:0.25 Bb3:0.5 R:0.25 C3:2 Eb4:0.5 Bb3:0.5 G3:0.25",
                        "R:0.25 D4:0.5 R:0.25 Bb3:0.25 R:0.25 G3:0.5 R:0.25 D4:0.5 R:0.25 Bb3:0.5",
                        "R:0.25 Eb3:2 D4:0.5 Bb3:0.5 G3:0.25 R:0.25 F4:0.5 R:0.25 D4:0.25 R:0.25",
                        "Bb3:0.5 R:0.25 F4:0.5 R:0.25 Bb3:0.5 R:0.25 C3:2 Eb4:0.5 Bb3:0.5 G3:0.25",
                        "R:0.25 D4:0.5 R:0.25 Bb3:0.25 R:0.25 G3:0.5 R:0.25 D4:0.25 R:0.25 Eb4:0.25",
                        "R:0.25 D4:0.5 R:0.25 Eb3:2 D4:0.5 Bb3:0.5 G3:0.25 R:0.25 F4:0.5 R:0.25",
                        "D4:0.25 R:0.25 Bb3:0.5 R:0.25 F4:0.5 R:0.25 Bb3:0.5 R:0.25 C3:2 Eb4:0.5",
                        "Bb3:0.5 G3:0.25 R:0.25 D4:0.5 R:0.25 Bb3:0.25 R:0.25 G3:0.5 R:0.25 D4:0.5",
                        "R:0.25 Bb3:0.5 R:0.25 Eb3:2 D4:0.5 Bb3:0.5 G3:0.25 R:0.25 F4:0.5 R:0.25",
                        "D4:0.25 R:0.25 Bb3:0.5 R:0.25 F4:0.5 R:0.25 Bb3:0.5 R:0.25 C3:2 Eb4:0.5",
                        "Bb3:0.5 G3:0.25 R:0.25 D4:0.5 R:0.25 Bb3:0.25 R:0.25 G3:0.5 R:0.25 D4:0.25",
                        "R:0.25 Eb4:0.25 R:0.25 D4:0.5 R:0.25 Eb3:1 D4:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Square One",
                "Alt",
                62,
                phrase(
                        "R:1 Bb3:0.5 Bb3:0.5 Bb3:0.5 Bb3:0.5 Bb3:0.5 Bb3:0.5 C3:0.5 A3:0.5 A3:1",
                        "A3:2.5 R:9.25 Bb3:0.5 Bb3:0.5 Bb3:0.5 Bb3:0.5 C3:0.5 A3:1 A3:0.5 A3:2.5",
                        "R:8.25 G4:0.5 G4:0.5 G4:0.5 G4:1 Eb4:1 R:0.5 F4:0.5 F4:0.5 F4:0.5",
                        "F4:1 R:8.25 G4:0.5 G4:1 Eb4:0.5 Eb4:0.5 Eb4:1.5 F4:0.5 F4:1.5 D4:0.5",
                        "D4:2"
                )
        ));

        seeds.add(simpleSeed(
                "Fireflies",
                "Pop",
                90,
                phrase(
                        "R:1.75 G4:0.75 F4:0.75 Eb4:0.25 Bb4:0.75 G4:1 F4:0.75 Eb4:0.25 F4:3 R:0.75",
                        "Bb3:0.75 Bb4:0.75 Bb4:0.25 Ab4:3 G4:2.75 Bb4:2.75 Ab4:2.75 C4:0.75 C4:0.25 Eb4:1",
                        "G4:0.75 F4:0.25 Eb4:1 Bb3:0.75 Bb3:0.25 C4:1 C4:0.75 Eb4:0.75 F4:0.25 C4:2.75",
                        "Bb3:0.25 C4:0.75 Eb4:0.25 Eb4:1 G4:0.75 F4:0.25 Eb4:1 Bb3:0.75 Eb4:0.25 F4:3",
                        "R:3.25 G4:0.75 F4:0.75 Eb4:0.25 Bb4:0.75 G4:1 F4:0.75 Eb4:0.25 F4:3 R:0.75",
                        "Bb3:0.75 Bb4:0.75 Bb4:0.25 Ab4:3 G4:2.75 Bb4:2.75 Ab4:2.75 C4:0.75 C4:0.25 Eb4:1",
                        "G4:0.75 F4:0.25 Eb4:1 Bb3:0.75 Bb3:0.25 C4:1 C4:0.75 Eb4:0.75 F4:0.25 C4:2.75",
                        "Bb3:0.25 C4:0.75 Eb4:0.25 Eb4:1 G4:0.75 F4:0.25 Eb4:1 Bb3:0.75 Eb4:0.25 F4:4.25",
                        "R:2 G3:0.75 F3:0.75 Eb3:0.25 Bb3:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Jenny Was A Friend Of Mine",
                "Rock",
                115,
                phrase(
                        "R:0.25 Ab4:1 R:0.25 Eb5:0.5 R:0.25 C#5:1 B4:1 Bb4:1 Bb4:1 Ab4:1",
                        "F#4:1 F#4:0.5 Ab4:0.5 Bb4:1 R:0.25 F#4:0.5 Ab4:0.5 Bb4:1 F#4:0.5 Ab4:0.5",
                        "Bb4:1.5 R:2.5 Eb5:0.5 R:0.25 Eb5:0.5 C#5:1 B4:1 Bb4:1 R:0.25 Bb4:1",
                        "B4:1 Ab4:2.5 R:0.25 Eb5:0.5 R:0.25 C#5:1 B4:1 Bb4:1 Bb4:1 Ab4:1",
                        "F#4:1 F#4:0.5 Ab4:0.5 Bb4:1 R:0.25 F#4:0.5 Ab4:0.5 Bb4:1 F#4:0.5 Ab4:0.5",
                        "Bb4:1.75 R:1 Bb5:0.75 Bb5:0.75 Bb5:0.75 R:0.25 Eb5:1"
                )
        ));

        seeds.add(simpleSeed(
                "Circus",
                "Dance",
                115,
                phrase(
                        "R:1 F#2:0.5 R:0.25 F#2:0.5 R:0.25 F#2:0.5 R:0.25 F#2:0.5 R:0.25 F#2:0.5",
                        "R:0.25 F#2:0.5 R:0.25 F#2:0.5 R:0.25 F#2:0.5 R:0.25 E2:0.5 R:0.25 E2:0.5",
                        "R:0.25 E2:0.5 R:0.25 E2:0.5 R:0.25 E2:0.5 R:0.25 E2:0.5 R:0.25 E2:0.5",
                        "R:0.25 E2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5",
                        "R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 A1:0.5",
                        "R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 Ab1:0.5 R:0.25 Ab1:0.5 R:0.25 Ab1:0.5",
                        "R:0.25 Ab1:0.5 R:0.25 Ab1:0.5 R:0.25 F#2:0.5 R:0.25 F#2:0.5 R:0.25 F#2:0.5",
                        "R:0.25 F#2:0.5 R:0.25 F#2:0.5 R:0.25 F#2:0.5 R:0.25 F#2:0.5 R:0.25 F#2:0.5",
                        "R:0.25 E2:0.5 R:0.25 E2:0.5 R:0.25 E2:0.5 R:0.25 E2:0.5 R:0.25 E2:0.5",
                        "R:0.25 E2:0.5 R:0.25 E2:0.5 R:0.25 E2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5",
                        "R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5 R:0.25 D2:0.5",
                        "R:0.25 D2:0.5 R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 A1:0.5 R:0.25 Ab1:0.5",
                        "R:0.25 Ab1:0.5 R:0.25 Ab1:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Sk8er Boi",
                "Pop",
                150,
                phrase(
                        "R:1 D4:0.5 D4:0.5 D4:0.5 D4:0.5 D4:0.5 D4:0.5 D4:0.5 D4:0.5 A3:0.5",
                        "A3:0.5 A3:0.5 A3:0.5 A3:0.5 A3:0.5 A3:0.5 A3:0.5 B3:0.5 B3:0.5 B3:0.5",
                        "B3:0.5 B3:0.5 B3:0.5 B3:0.5 B3:0.5 Bb3:0.5 Bb3:0.5 Bb3:0.5 Bb3:0.5 Bb3:0.5",
                        "A3:0.5 A3:0.5 A3:0.5 D4:0.5 D4:0.5 D4:0.5 D4:0.5 D4:0.5 D4:0.5 D4:0.5",
                        "D4:0.5 A3:0.5 A3:0.5 A3:0.5 A3:0.5 A3:0.5 A3:0.5 A3:0.5 A3:0.5 B3:0.5",
                        "B3:0.5 B3:0.5 B3:0.5 B3:0.5 B3:0.5 B3:0.5 B3:0.5 Bb3:0.5 Bb3:0.5 Bb3:0.5",
                        "Bb3:0.5 Bb3:0.5 A3:0.5 A3:0.5 A3:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Celebration",
                "Dance",
                126,
                phrase(
                        "R:0.25 D4:0.25 R:0.25 C#4:0.25 R:0.25 B3:0.25 R:0.25 A3:0.25 R:0.5 B3:0.25",
                        "R:0.5 B3:0.25 R:0.5 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25",
                        "R:0.25 D4:0.25 R:0.5 D4:0.25 R:0.25 B3:0.25 R:0.25 D4:0.25 R:0.25 B3:0.25",
                        "R:0.25 D4:0.25 R:0.25 B3:0.25 R:0.25 D4:0.25 R:0.25 C#4:0.25 R:0.25 B3:0.25",
                        "R:0.25 A3:0.25 R:0.5 G3:0.25 R:0.5 G3:0.25 R:0.5 C#4:0.25 R:0.25 C#4:0.25",
                        "R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.25 D4:0.25 R:0.5 D4:0.25 R:0.25 B3:0.25",
                        "R:0.25 D4:0.25 R:0.25 B3:0.25 R:0.25 D4:0.25 R:0.25 B3:0.25 R:0.25 D4:0.25",
                        "R:0.25 C#4:0.25 R:0.25 B3:0.25 R:0.25 A3:0.25 R:0.5 B3:0.25 R:0.5 B3:0.25",
                        "R:0.5 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25 R:0.25 D4:0.25",
                        "R:0.5 D4:0.25 R:0.25 B3:0.25 R:0.25 D4:0.25 R:0.25 B3:0.25 R:0.25 D4:0.25",
                        "R:0.25 B3:0.25 R:0.25 D4:0.25 R:0.25 C#4:0.25 R:0.25 B3:0.25 R:0.25 A3:0.25",
                        "R:0.5 G3:0.25 R:0.5 G3:0.25 R:0.5 C#4:0.25 R:0.25 C#4:0.25 R:0.25 C#4:0.25",
                        "R:0.25 C#4:0.25 R:0.25 D4:0.25 R:0.5 D4:0.25 R:0.25 B3:0.25 R:0.25 D4:0.25",
                        "R:0.25 B3:0.25 R:0.25 D4:0.25 R:0.25 B3:0.25 R:0.25 D4:0.25 R:0.25 C#4:0.25",
                        "R:0.25 B3:0.25 R:0.25 A3:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Fifteen",
                "Pop",
                95,
                phrase(
                        "R:3.25 D4:0.5 R:0.25 G4:0.5 R:0.25 G4:0.5 R:0.25 F#4:0.5 R:0.25 F#4:1",
                        "R:0.25 G4:1 R:0.25 D4:0.5 R:0.25 G4:0.5 R:0.25 G4:0.5 R:0.25 F#4:0.5",
                        "R:0.25 F#4:0.75 R:0.5 G4:0.25 R:0.5 G4:0.25 R:0.25 G4:0.5 R:0.25 E4:0.25",
                        "R:0.25 G4:0.75 R:0.25 E4:0.75 R:0.25 D4:0.5 R:0.25 B4:0.5 R:0.25 C5:0.5",
                        "R:0.25 B4:0.5 R:0.25 A4:0.5 R:0.25 A4:0.5 R:0.25 G4:1 R:0.25 C4:1.75",
                        "R:9.75 D4:0.5 R:0.25 D4:0.5 R:0.25 D4:0.5 R:0.25 D4:0.75 B3:0.25 R:0.25",
                        "B3:0.25 R:0.25 B3:0.25 R:0.25 B3:0.5 R:0.25 C4:0.5 R:0.25 B3:0.25 R:0.25",
                        "B3:1 R:0.25 C4:0.75 R:0.25 B3:0.75 R:0.25 A3:0.25 R:0.25 G3:0.75 R:0.25",
                        "A3:0.25 R:0.25 B3:0.25 R:0.25 B3:1.25 R:0.25 A3:0.5 R:0.25 A3:0.25 R:0.25",
                        "G3:0.5 R:0.25 B3:0.75 R:0.25 G3:0.25 R:0.25 G3:1 R:4 G3:0.25 R:0.25",
                        "B3:0.25 R:0.25 A3:0.5 R:0.25 B3:0.75 R:0.25 B3:1.25 R:0.25 A3:0.5 R:0.25",
                        "G3:0.5 R:0.25 C4:1 R:0.25 B3:1 R:0.25 B3:1.25 R:0.25 A3:0.5 R:0.25",
                        "G3:0.5 R:0.25 A3:1.25 R:1.25 G3:0.5 R:0.25 B3:0.75 R:0.25 A3:1 R:0.25",
                        "G3:1.75 R:1.75 E4:0.25 R:0.25 G4:0.5 R:0.25 G4:1 R:0.25 E4:1.75 R:1.5",
                        "D4:0.5 R:0.25 E4:0.5 R:0.25 G4:0.25"
                )
        ));

    }

    private static void addRecentMidiDemoSeeds(List<SongSeed> seeds) {

        // Imported from MIDIdb free demo MIDI files; MIDIdb states copyright-owner permission for demo downloads.

        seeds.add(simpleSeed(
                "Uptown Funk",
                "Soul",
                100,
                phrase(
                        "A4:8 C3:0.25 F#2:0.25 C3:0.25 F#2:0.25 D4:0.25 C3:0.25 C4:0.25 F#2:0.25 C3:0.25",
                        "D3:1 F6:1.5 C4:0.25 C3:0.25 C4:0.25 G3:1.5 D4:8 F#2:0.25 F3:0.75 D4:0.5",
                        "F#2:0.25 D4:0.25 D4:0.25 D3:0.5 G3:1.5 F4:0.25 C4:0.25 F#2:0.25 F3:0.75 C4:0.5",
                        "F#2:0.25 F4:0.25 F4:0.25 D3:0.25 A4:8 F#2:0.25 C3:0.25 C3:0.25 F#2:0.25 C3:0.25",
                        "D4:0.25 C4:0.25 C3:0.25 F#2:0.25 C3:0.25 F6:1.5 D3:1 D4:0.25 C3:0.25 D4:0.25",
                        "G3:1.5 D4:8 C4:0.25 D4:0.25 F6:0.25 F6:0.25 F6:0.25 D3:0.5 D4:0.25 G3:1.5",
                        "F6:0.25 F6:0.25 F3:0.75 F6:0.25 F6:0.25 C3:0.5 F6:0.25 F6:0.25 D3:0.5 A4:8"
                )
        ));

        seeds.add(simpleSeed(
                "Happy",
                "Pop",
                100,
                phrase(
                        "F#2:0.5 R:1.5 F#2:0.5 R:1.5 F#2:0.5 R:1.5 F#2:0.5 R:1.5 B1:1.25 F4:1",
                        "Eb4:1 A3:1 C4:0.75 R:0.75 B1:1 Eb4:1 F4:1 A3:1 C4:1 R:1",
                        "B1:1 F4:1 Eb4:1 A3:1 C4:0.75 R:1 Eb4:1 F4:1 B1:1 A3:1",
                        "C4:1 R:1 F4:1 Eb4:1 C4:1 A3:1 F#2:0.5 R:1 F#2:0.5 R:0.25",
                        "C4:0.75 B1:1 F4:0.5 Bb2:0.5 R:0.5 F4:1 B1:0.75 R:0.25 F#2:0.5 R:0.5",
                        "F4:1.75 B1:0.5 F#2:0.5 R:0.25 C4:0.5 R:0.5 C4:0.5 F#2:0.5 R:0.5 C4:1.25",
                        "B1:1 Bb2:0.25 Eb4:1 R:0.25 B1:0.75 F4:0.5 R:0.25 F4:1.25 B1:0.75 R:0.25",
                        "F4:2.25 C4:2.25 F3:2 Ab3:1.5 B1:1 Bb3:2.25 F4:2.25 D4:2.25 Bb3:1.5 B1:0.75",
                        "Ab3:0.75 R:0.25 C4:1.5 G4:3 E4:2.75 C4:2.75 F#2:0.5 Bb3:1.5 F#2:0.5 R:0.25",
                        "D4:3.25"
                )
        ));

        seeds.add(simpleSeed(
                "Thinking Out Loud",
                "Pop",
                100,
                phrase(
                        "A4:3 F#3:2.75 A3:2.75 D4:1.75 F#2:0.25 D4:0.5 D4:5 D3:2.5 A3:2.75 F#2:0.25",
                        "F#2:0.25 A3:0.25 D4:0.25 A3:1.75 E4:0.25 F#4:0.75 D4:3 D3:1.75 G3:1.75 B3:1.75",
                        "F#2:0.25 D3:1 G3:1 B3:1 E4:1.75 E3:1.75 A3:2.25 C#4:2.5 F#2:0.25 E4:0.75",
                        "R:0.25 G4:0.25 A3:1.75 C#4:1.75 F#4:0.25 D4:1.75 A4:3 F#3:1.75 A3:2.75 D4:1.75",
                        "F#2:0.25 D4:0.5 D4:5 D3:1.75 A3:2.75 F#2:0.25 D3:3 A3:2 A3:1.75 F#2:0.25",
                        "D4:3 D3:1.75 G3:1.75 B3:1.75 F#2:0.25 D3:1 G3:1 B3:1 E4:5 E3:2.25",
                        "A3:2.25 C#4:2.5 F#2:0.25 A2:1.75 A3:0.25 D4:0.25 C#4:1.75 C#4:1.75 E4:0.25 F#4:0.75",
                        "A4:3"
                )
        ));

        seeds.add(simpleSeed(
                "Get Lucky",
                "Dance",
                116,
                phrase(
                        "D4:4 R:1 C#4:1 D4:1 F#4:4.5 R:1.5 F#4:1.25 Ab4:1.25 A4:4.5 R:1.5",
                        "A4:1 B4:1 Ab4:5 R:3 F#3:1 D4:4 R:1 C#4:1 R:0.25 D4:1",
                        "F#4:4.75 R:1.25 F#4:1 Ab4:1.25 A4:4.5 R:1.5 F#4:1 C#5:1 R:0.25 B4:6",
                        "R:4 D4:0.25 R:0.25 D4:0.5 R:0.5 D4:0.5 R:0.5 D4:0.5 R:0.5 D4:0.25",
                        "R:0.25 D4:0.5 R:0.5 E4:1.5 R:1.5 D4:0.25 R:0.25 D4:0.5 R:0.5 D4:0.5",
                        "R:0.5 D4:0.5 R:0.5 D4:0.25 R:0.25 D4:0.5 R:0.5 E4:1.75 R:1.25 C#4:0.25",
                        "R:0.25 C#4:0.5 R:0.5 C#4:0.5 R:0.5 C#4:0.5 R:0.5 C#4:0.25 R:0.25 C#4:0.5",
                        "R:0.5 E4:1.75 R:1.25 B3:0.25 R:0.25 B3:0.5 R:0.5 B3:0.5 R:0.5 B3:0.5",
                        "R:0.5 B3:0.25 R:0.25 C#4:1 B3:1 R:0.25 A3:0.75 R:1.25 D4:0.25 R:0.25",
                        "D4:0.5 R:0.5 D4:0.5 R:0.5 D4:0.5 R:0.5 D4:0.25 R:0.25 D4:0.5 R:0.5",
                        "E4:1.5 R:1.5 D4:0.25 R:0.25 D4:0.5 R:0.5 D4:0.5 R:0.5 D4:0.5 R:0.5",
                        "D4:0.25 R:0.25 D4:0.5 R:0.5 E4:1.75 R:1.25 C#4:0.25 R:0.25 C#4:0.5 R:0.5",
                        "C#4:0.5 R:0.5 C#4:0.5 R:0.5 C#4:0.25 R:0.25 C#4:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "All Of Me",
                "Pop",
                63,
                phrase(
                        "C4:0.5 R:0.25 Ab2:7.25 Eb5:0.5 Eb4:2.75 C5:0.5 Ab4:0.5 Eb5:1 Eb4:0.75 Ab4:0.5",
                        "Eb5:0.5 Eb4:0.5 Eb4:0.5 Eb5:0.5 F4:1 C5:0.5 Ab4:0.5 Eb5:1 C5:1 Ab4:0.75",
                        "Eb4:0.25 R:0.75 C5:0.75 C5:8 F3:3.75 C4:0.5 F4:0.5 C5:1 G4:1 Ab4:0.5",
                        "C4:1 Ab3:0.5 F4:0.5 Bb3:0.5 C4:1 C4:0.5 F4:0.5 G4:0.5 C5:1 C4:0.25",
                        "Ab4:0.5 C4:0.5 C5:0.25 R:0.25 C#4:2 F4:3 Bb4:1 Bb3:0.75 Bb4:2 Bb3:1",
                        "C#4:0.25 C#4:1.25 Ab3:0.5 F4:1 C#5:0.5 Ab3:0.75 C#4:1.25 F4:1.25 C5:1.5 C#4:0.25",
                        "C4:0.25 C#4:1 Ab4:0.5 Bb3:0.25 Bb4:0.25 R:0.25 Ab4:1 F4:1 R:0.25 C#4:1.5",
                        "Bb3:0.75 F4:1 Ab4:1 Bb3:1 Eb3:5.25"
                )
        ));

        seeds.add(simpleSeed(
                "All About That Bass",
                "Pop",
                100,
                phrase(
                        "E4:16 A3:0.5 A3:0.5 A3:0.5 A3:0.5 A3:0.5 A3:0.5 A3:0.5 B1:0.25 C#4:0.5",
                        "B3:0.5 A3:0.5 E4:1 E2:1.5 E4:0.75 D4:1.75 F#4:16 C#4:0.5 B3:0.25 C#4:0.25",
                        "F#4:0.5 F#4:0.25 F#4:0.25 F#4:0.5 B1:0.25 B3:0.25 B3:0.25 B3:0.25 B3:0.25 F#3:0.25",
                        "F#3:0.5 D2:1 B3:16 C#4:0.25 B3:0.25 B3:0.25 B3:0.25 B1:0.25 B3:0.25 B3:0.5",
                        "E2:0.25 B3:0.25 A3:0.25 A3:0.25 A3:0.25 E2:0.25 A3:0.5 B1:0.25 E2:0.25 C#4:0.25",
                        "E4:16 C#4:0.25 E4:0.75 C2:1 C#4:0.5 E2:1.25 C#4:0.5 C#4:0.5 E4:0.5 E4:0.5",
                        "E4:0.5 C2:1.25 C#4:0.5 C#4:0.75 E4:16 C#4:0.25 C#4:0.25 C#4:0.25 C#4:0.25 C#4:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Shut Up And Dance",
                "Rock",
                100,
                phrase(
                        "C#5:82.25 F4:2.75 C#3:0.25 C#3:0.25 Ab4:2.75 C#3:0.25 C#3:0.25 C#5:2.75 C#3:0.25 F#4:2.75",
                        "F#4:4.75 C#3:0.25 Ab4:2.75 C#3:0.25 C#3:0.25 C#3:0.25 C#5:2.25 C#3:0.25 F#4:2 C#3:0.25",
                        "C#3:0.25 Ab4:1.75 C#3:0.25 F4:3.25 F4:2.75 C#3:0.25 Ab4:2.75 C#3:0.25 C#5:2.5 C#3:0.25",
                        "C#3:0.25 Eb4:2.75 Eb4:4.75 C#3:0.25 C#3:0.25 Ab4:2.75 C#3:0.25 C#3:0.25 C#5:3 C#3:0.25",
                        "C#4:0.75 Eb4:1.75 C#3:0.25 Ab4:1 C#4:0.75 C#3:0.25 F4:3.25 F4:2.75 C#3:0.25 Ab4:2.75",
                        "C#3:0.25 C#4:1 C#5:2.75 C#3:0.25 C#3:0.25 F#4:2.75 F#4:4.75 C#3:0.25 C#3:0.25 Ab4:2.75",
                        "C#3:0.25 C#4:0.5 C#5:2.25 C#3:0.25 C#4:0.5 F#4:2 C#3:0.25 C#4:0.5 Ab4:1.75 C#3:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Can't Stop The Feeling",
                "Dance",
                113,
                phrase(
                        "E4:1.25 F4:1 E4:1 F4:1 E4:1.25 D4:0.5 C4:3.25 R:1.25 Bb3:0.75 R:0.25",
                        "C4:0.75 R:0.25 C4:1 R:0.25 A4:2 G4:1 E4:1 G4:1.25 E4:1 G4:1",
                        "E4:1.25 D4:0.5 C4:3 R:1.5 Bb3:1 R:0.25 C4:1 R:0.25 G4:2 F4:0.75",
                        "R:0.25 F4:1 Eb4:1 R:0.25 F4:1 Eb4:1 R:0.25 F4:1.25 D4:1 C4:0.75",
                        "Bb3:3.25 R:1.75 C4:0.5 R:0.25 Eb4:0.75 R:0.25 Eb4:0.25 R:0.25 Eb4:1 R:0.25",
                        "C4:13.5 G4:0.5 G4:0.5 G4:1 Eb4:9.75 Bb4:0.5 Bb4:0.5 Bb4:1 G4:5.75 R:1.75",
                        "C4:0.75 D4:0.5 R:0.5 D4:0.5 R:0.5 D4:0.25 R:0.75 D4:1 R:0.25 C4:1",
                        "D4:1 R:0.25 C4:0.75 D4:0.5 R:0.5 D4:1.25 R:0.75 D4:1.5 R:0.5 D4:1.5",
                        "R:2 D4:0.25 R:0.75 D4:0.5 R:0.5 D4:0.5 R:0.5 D4:0.5 R:0.5 D4:1",
                        "R:0.25 C4:1 R:0.25 D4:1 R:0.25 C4:0.5 D4:0.75 R:0.25 D4:1.5 R:0.5",
                        "D4:1.5 R:0.5 D4:1.25 R:0.25 C4:0.5 A3:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Rolling In The Deep",
                "Pop",
                105,
                phrase(
                        "F#2:0.75 R:1.25 F#2:0.75 R:1.25 F#2:0.5 R:1.5 F#2:0.5 R:1.5 C4:0.5 R:0.5",
                        "C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75",
                        "C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75",
                        "C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.5 R:0.5 C4:0.25 R:0.75",
                        "C4:0.5 R:0.5 C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75",
                        "C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75",
                        "C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.5 R:0.5",
                        "C5:0.5 R:0.5 G4:1 C4:0.25 R:0.25 G4:2.25 C4:0.5 C4:0.25 R:0.25 F4:0.5",
                        "Eb4:0.5 R:0.25 C4:0.25 R:0.75 C4:0.25 R:0.75 C4:0.25 R:0.75 G4:0.5 R:0.5",
                        "G4:0.5 R:0.5 F4:0.5 R:0.5 Eb4:0.5 R:0.5 C4:1 G3:0.25 R:0.75 G3:0.5",
                        "R:0.5 G3:0.25 R:0.75 G4:0.75 R:0.25 Bb3:0.25 R:0.25 Bb4:0.75 Bb3:0.5 R:0.5",
                        "G4:0.5 R:0.5 F4:0.5 R:0.5 Eb4:0.25 R:0.75 C4:0.5 R:0.5 C4:0.25 R:0.75",
                        "Eb4:0.25 R:0.25 Eb4:0.25 R:0.25 Eb4:0.25 R:0.75 D4:0.25 R:0.75 C4:0.25 R:0.25",
                        "C4:1.5 G3:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Shape Of You",
                "Dance",
                100,
                phrase(
                        "C#5:0.5 R:1 E5:0.5 R:1 C#5:0.25 R:0.75 C#5:0.5 R:1 E5:0.5 R:1",
                        "C#5:0.5 R:0.5 C#5:0.5 R:1 E5:0.5 R:1 C#5:0.25 R:0.75 Eb5:0.5 R:1",
                        "C#5:0.5 R:1 B4:0.5 R:0.5 F6:0.5 Ab4:1 F6:0.5 C#4:0.25 C#4:0.25 R:0.25",
                        "E5:0.5 C#4:0.25 C#4:0.5 R:0.25 G4:0.5 R:0.25 C#5:0.25 R:0.75 F6:0.5 C#4:1",
                        "F6:0.5 C#4:0.25 C#4:0.25 C#4:0.25 E5:0.5 C#4:0.25 R:0.25 C#4:0.5 R:0.25 G4:0.25",
                        "R:0.25 C#5:0.5 R:0.5 F6:0.5 E4:1 F6:0.5 C#4:0.25 R:0.25 C#4:0.25 C#4:0.25",
                        "E5:0.5 C#4:0.25 Eb3:0.5 G4:0.5 R:0.25 C#5:0.25 R:0.75 F6:0.5 F#4:1 F6:0.5",
                        "C#4:0.25 R:0.25 C#4:0.25 C#4:0.25 C#5:0.5 C#4:0.25 Eb3:0.5 G4:0.25 R:0.25 B4:0.5",
                        "R:0.25 E3:0.25 R:0.25 E3:0.75 F6:0.75 Ab4:1 F6:0.75 E3:0.25 C#4:0.25 C#4:0.25",
                        "R:0.25 E5:0.5 E3:0.25 C#4:0.25 C#4:0.5 E3:0.25 R:0.25 G4:0.5 E3:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Flowers",
                "Pop",
                118,
                phrase(
                        "D4:0.5 R:0.5 D4:1 R:0.25 C4:0.5 R:0.25 D4:1.5 R:0.25 A3:1 R:1",
                        "D4:1.5 R:0.5 D4:0.5 R:0.5 D4:1 R:0.25 C4:0.5 R:0.25 D4:1.5 R:2",
                        "B3:2 R:0.25 B3:0.5 R:0.5 A3:0.5 R:0.5 B3:2 R:0.25 A3:1 R:0.25",
                        "B3:1 R:0.25 A3:1 R:0.25 B3:1.5 R:0.25 C4:1.5 R:0.25 D4:4 R:3",
                        "E4:0.5 R:0.5 E4:0.5 R:0.5 E4:1 R:0.25 D4:1 R:0.25 C4:1 R:0.25",
                        "E4:1 R:0.25 F4:5.5 R:4.5 D4:0.5 R:0.5 D4:0.5 R:0.5 D4:1.5 R:0.25",
                        "C4:0.5 R:0.25 A3:1 R:0.25 E4:6 R:4 E4:0.5 R:0.5 E4:0.5 R:0.5",
                        "E4:0.5 R:0.5 E4:1 R:0.25 D4:1 R:0.25 C4:1 R:0.25 E4:1 R:0.25",
                        "F4:6 R:0.25 E4:1 R:0.25 D4:1 R:1 C4:0.5 R:0.5 D4:1 R:0.25",
                        "C4:1 R:0.25 E4:2 R:0.25 D4:0.5 R:0.25 C4:0.5 R:0.5 C4:0.5 R:0.25",
                        "C4:0.5 R:0.25 A3:0.5 R:0.25 C4:3 R:6 G4:1 R:0.25 E4:1 R:0.25",
                        "G4:1 R:0.25 E4:0.5 R:0.5 G4:0.5 R:0.5 A4:1.5 R:0.5 E4:0.5 R:0.25",
                        "D4:0.5 R:0.25 C4:3 R:1 E4:1 R:0.25 D4:1 R:1 D4:0.5 R:0.5",
                        "D4:0.5 R:0.5 D4:1 R:0.25 G4:2.5 R:0.25 F4:0.5 R:0.25 E4:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Cold Heart",
                "Dance",
                116,
                phrase(
                        "Bb6:8 F1:0.5 Eb3:0.25 Eb3:0.5 Bb5:0.25 Bb5:0.25 E2:0.25 F#2:0.25 Bb5:0.25 C4:1.5",
                        "F2:0.5 Eb3:0.25 B1:0.25 Bb5:0.25 C3:0.5 Bb5:0.25 C2:0.5 C3:0.5 C2:0.5 E2:0.25",
                        "Bb5:0.25 C3:0.25 C3:0.25 C#4:6 Bb5:0.25 Ab2:0.5 F1:0.5 Bb5:0.25 F6:16 Bb2:0.25",
                        "Bb2:0.5 Bb1:0.5 Ab6:13 F#2:0.25 Bb5:0.25 Bb5:0.25 C2:0.25 C#7:11.75 Ab7:11 Bb5:0.25",
                        "Bb3:0.25 Bb5:0.25 Bb5:0.25 F3:0.25 F3:0.25 Bb5:0.25 E2:0.25 Bb5:0.25 F#2:0.25 C#3:0.5",
                        "C#2:0.5 Bb5:0.25 C#4:7.75 Eb3:0.25 Eb3:0.5 Eb1:0.5 Bb5:0.25 Bb3:0.5 Bb5:0.25 Eb3:0.5",
                        "Bb5:0.25 C#4:1.75 Eb3:0.5 Eb2:0.5 C2:0.25 Bb5:0.25 F4:2 Bb5:0.25 F#3:0.5 Bb5:0.25"
                )
        ));

        seeds.add(simpleSeed(
                "Bad Habits",
                "Dance",
                150,
                phrase(
                        "B3:1 R:0.25 E4:2 B4:5 F#4:0.75 E4:0.5 E4:1 D4:0.5 R:0.25 Bb4:0.25",
                        "R:1 Bb4:0.25 R:0.5 Bb4:0.25 R:0.5 Bb4:0.25 R:1 Bb4:0.25 B1:0.75 Bb4:0.25",
                        "R:0.25 Bb4:0.25 R:1 Bb4:0.25 R:0.5 Bb4:0.25 R:0.5 Bb4:0.25 R:1 Bb4:0.25",
                        "R:0.5 Bb4:0.25 R:0.5 C#5:4.5 D1:0.75 Bb4:0.25 D2:0.75 Bb4:0.25 Bb4:0.25 D1:0.75",
                        "Bb4:0.25 D2:0.75 Bb4:0.25 Bb4:0.25 R:0.5 Bb4:0.25 R:0.5 Bb4:0.25 R:0.5 Bb4:0.25",
                        "R:1 Bb4:0.25 R:0.5 Bb4:0.25 R:0.5 D5:12.75 Bb4:0.25 B3:0.5 Bb4:0.25 Bb4:0.25",
                        "G1:0.75 Bb4:0.25 Bb4:0.25 Bb4:0.25 D4:0.75 G0:0.75 Bb4:0.25 D4:0.5 G1:0.75 Bb4:0.25",
                        "Bb4:0.25 Bb4:0.25 Bb4:0.25 Bb4:0.25 Bb4:0.25 G1:0.75 Bb4:0.25 F#4:0.75 Bb4:0.25 Bb4:0.25",
                        "Bb4:0.25 Bb4:0.25 R:0.25 E5:3 Bb4:0.25 Bb4:0.25 Bb4:0.25 Bb4:0.25 R:0.25 Bb4:0.25",
                        "R:0.5 B3:1.5"
                )
        ));

        seeds.add(simpleSeed(
                "Leave The Door Open",
                "Soul",
                74,
                phrase(
                        "G4:0.5 F4:0.5 R:1 G4:0.25 R:0.25 G4:0.5 R:1 Bb4:1 R:0.25 G4:0.5",
                        "F4:0.75 G4:3 R:1 E4:0.5 D4:0.5 E4:1 F4:1.25 G4:1 R:0.25 C5:2.25",
                        "A4:2 R:1 A4:0.25 R:0.25 A4:0.25 R:0.25 A4:0.25 R:0.25 A4:0.25 R:0.25",
                        "A4:1 R:0.25 A4:2 R:0.25 G4:1.75 R:0.25 E4:0.5 D4:0.5 E4:1.25 F4:1.25",
                        "G4:1 R:0.25 D5:2 C5:0.5 B4:1.25 A4:0.5 G4:1 B4:0.25 R:0.25 B4:0.25",
                        "R:0.25 B4:0.25 R:0.25 B4:0.5 R:0.25 B4:1 R:0.25 C5:2 R:0.25 A4:1.25",
                        "R:0.75 C5:1.75 R:0.25 A4:1 R:0.25 G4:0.5 A4:0.75 R:0.25 C5:1 A4:0.5",
                        "R:0.25 C5:0.5 A4:0.5 R:0.25 C5:0.75 R:0.25 C5:0.5 A4:0.5 R:0.25 C5:0.75",
                        "A4:0.5 C5:0.5 A4:0.5 C5:0.75 A4:0.5 G4:0.75 A4:1.5 R:0.25 G4:0.5 A4:0.5",
                        "R:2 B4:0.5 C5:0.5 B4:0.5 C5:0.5 B4:1 G4:1 D5:6.5 R:0.25 C5:0.75",
                        "D5:1 E5:5.75"
                )
        ));

        seeds.add(simpleSeed(
                "Easy On Me",
                "Pop",
                71,
                phrase(
                        "C4:0.5 D4:0.5 R:0.25 F4:0.5 R:0.5 F4:1.5 R:0.25 C4:0.5 R:0.5 D4:0.5",
                        "R:0.25 F4:1.5 R:1.5 C4:0.5 R:0.25 D4:0.25 R:0.25 F4:0.5 R:0.5 F4:1.5",
                        "R:0.25 D4:1.25 R:3.25 C4:0.5 R:0.25 D4:0.5 R:0.25 F4:0.5 R:0.5 F4:1.25",
                        "R:0.25 E4:1 R:0.25 F4:1.25 R:0.25 E4:1.25 R:0.25 F4:0.75 R:0.25 E4:0.5",
                        "R:0.75 F4:1.25 R:0.25 D4:3 R:1.25 C4:0.5 D4:0.75 F4:0.5 R:0.5 F4:1.25",
                        "R:0.25 A4:1.5 R:3 C4:0.5 D4:0.5 R:0.25 F4:0.5 R:0.5 F4:1.5 D4:2",
                        "R:2.75 C4:0.5 D4:0.5 R:0.25 F4:0.75 R:0.5 F4:1.25 R:0.25 E4:1 R:0.5",
                        "F4:1.25 R:0.25 E4:1.25 R:0.25 F4:0.75 R:0.25 E4:0.75 R:0.25 F4:1 R:0.25",
                        "E4:1 R:0.5 F4:1.25 R:0.25 E4:1.25 R:0.25 F4:0.75 R:0.25 G4:1 R:0.25",
                        "A4:1.25 R:0.25 G4:0.75 R:0.5 A4:1.5 G4:1.25 R:0.5 A4:1 R:0.25 G4:1",
                        "R:0.25 Bb4:3.25 A4:2.25 R:0.75 F4:2 R:0.25 C5:3 R:0.25 Bb4:0.5 A4:0.5",
                        "Bb4:1 A4:0.5 G4:0.75 A4:0.75 R:0.25 A4:0.25 R:0.25 A4:1.25"
                )
        ));

        seeds.add(simpleSeed(
                "As It Was",
                "Rock",
                174,
                phrase(
                        "Ab3:1.25 R:0.25 E3:0.75 R:0.5 E3:0.75 R:0.5 E3:2 R:6 B3:1.25 R:0.25",
                        "A3:1.25 R:0.25 B3:0.75 R:0.75 B3:1.25 R:0.25 A3:1.25 R:0.25 B3:0.75 R:0.75",
                        "B3:1 R:0.25 A3:1 R:2 C#4:3 R:0.25 B3:3 R:0.25 A3:2 R:8",
                        "D4:3 R:0.25 C#4:3 R:0.25 B3:4 R:6 D4:3 R:0.25 C#4:3 R:0.25",
                        "B3:4 R:12 Ab4:1 R:0.25 A4:3 R:0.25 Ab4:2 R:0.25 F#4:2 R:0.25",
                        "E4:2 R:0.25 D4:3 R:0.25 C#4:3 R:0.25 B3:1.5 R:0.5 B3:3.5 R:4.5",
                        "D4:3 R:0.25 C#4:3 R:0.25 B3:4 R:6 D4:3 R:0.25 C#4:3 R:0.25",
                        "Ab3:4 R:12 Ab4:1 R:0.25 A4:3 R:0.25 Ab4:2 R:0.25 F#4:2 R:0.25",
                        "E4:2 R:0.25 D4:3 R:0.25 E4:3 R:0.25 B3:1.5 R:0.5 B3:3.5 R:7.5",
                        "E4:3 R:0.25 B3:1.5 R:0.5 B3:3.5 R:7.5 E4:3 R:0.25 B3:1.5 R:0.5",
                        "B3:2 R:0.25 A3:3.5 R:8.5 Ab4:1 R:0.25 A4:3 R:0.25 Ab4:2 R:0.25",
                        "F#4:2 R:0.25 E4:2 R:0.25 D4:3 R:5 C#4:1.25 R:0.25 B3:1.25 R:0.25",
                        "C#4:1.25 R:0.25 C#4:1 R:0.25 B3:1 R:6 C#4:1.25 R:0.25 B3:1.25 R:0.25",
                        "C#4:1.25 R:0.25 C#4:1.25 R:0.25 B3:1.25 R:0.25 C#4:1.25 R:0.25 C#4:1"
                )
        ));

        seeds.add(simpleSeed(
                "Shivers",
                "Dance",
                141,
                phrase(
                        "B4:0.5 R:2.5 D5:0.5 R:3.5 E5:0.5 R:3.5 F#5:0.5 R:4.5 B4:0.5 R:2.5",
                        "D5:0.5 R:3.5 E5:0.5 R:3.5 F#5:0.5 R:4.5 A4:0.5 R:2.5 D5:0.5 R:3.5",
                        "E5:0.5 R:3.5 F#5:0.5 R:4.5 A4:0.5 R:2.5 C#5:0.5 R:3.5 D5:0.5 R:8.5",
                        "B4:0.5 R:2.5 D5:0.5 R:3.5 E5:0.5 R:3.5 F#5:0.5 R:4.5 B4:0.5 R:2.5",
                        "D5:0.5 R:3.5 E5:0.5 R:3.5 F#5:0.5 R:4.5 A4:0.5 R:2.5 D5:0.5 R:3.5",
                        "E5:0.5 R:3.5 F#5:0.5 R:4.5 A4:0.5 R:2.5 C#5:0.5 R:3.5 D5:0.5 R:3.5",
                        "E5:0.5 R:4.5 B4:0.5 R:2.5 D5:0.5 R:3.5 E5:0.5 R:3.5 F#5:0.5 R:4.5",
                        "B4:0.5 R:2.5 D5:0.5 R:3.5 E5:0.5 R:3.5 F#5:0.5 R:4.5 A4:0.5 R:2.5",
                        "D5:0.5 R:3.5 E5:0.5 R:3.5 F#5:0.5 R:4.5 A4:0.5 R:2.5 C#5:0.5 R:3.5",
                        "D5:0.5 R:8.5 B4:0.5 R:2.5 D5:0.5 R:3.5 E5:0.5 R:3.5 F#5:0.5 R:4.5",
                        "B4:0.5 R:2.5 D5:0.5 R:3.5 E5:0.5 R:3.5 F#5:0.5 R:4.5 A4:0.5 R:2.5",
                        "D5:0.5 R:3.5 E5:0.5 R:3.5 F#5:0.5 R:4.5 A4:0.5 R:2.5 C#5:0.5 R:3.5",
                        "D5:0.5 R:3.5 E5:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "We Don't Talk About Bruno",
                "Theme",
                103,
                phrase(
                        "Eb4:0.5 R:0.25 C4:0.5 R:0.25 Eb4:0.5 R:0.5 Eb4:1 R:0.25 D4:0.5 R:0.5",
                        "D4:1 R:13 G3:0.5 R:0.5 Eb4:0.5 R:0.5 G3:0.5 R:0.5 F3:2 R:0.25",
                        "C4:0.5 R:0.5 B3:0.5 R:0.5 C4:1.5 R:12.5 Ab3:1 R:1 Ab3:2 R:8",
                        "G3:0.5 R:0.5 G3:0.25 R:0.25 G3:0.25 R:0.25 G3:0.25 R:0.25 G3:0.25 R:0.25",
                        "G3:0.25 R:0.25 G3:0.25 R:0.25 D4:4 R:0.25 C4:4 R:4 G3:1 R:0.5",
                        "G3:0.5 R:0.25 G3:0.5 R:0.5 Eb4:1 R:0.25 B3:2 R:0.25 C4:2 R:7.5",
                        "F3:0.5 R:0.25 G3:0.5 R:0.25 F3:0.5 R:0.25 G3:0.5 R:0.25 F3:0.5 R:0.25",
                        "G3:1 R:0.25 D4:1 R:0.25 Eb4:1 R:0.25 D4:1 R:0.25 C4:2 R:8",
                        "G3:0.5 R:0.25 B3:0.5 R:0.25 D4:0.5 R:0.5 Ab3:0.5 R:0.5 F3:0.5 R:0.5",
                        "F3:0.5 R:0.5 G3:0.5 R:0.5 F4:0.5 R:0.5 Eb4:1 R:1 Eb4:0.5 R:0.5",
                        "Ab3:0.5 R:0.5 Eb4:0.5 R:0.5 Ab3:0.5 R:0.5 Eb4:0.5 R:0.5 D4:1 R:1",
                        "B3:1 R:1 G3:1 R:1 F3:1 R:1 G3:1 R:2 Eb4:0.5 R:0.5",
                        "C4:0.5 R:0.5 Eb4:0.5 R:0.5 C4:0.5 R:0.5 Eb4:0.5 R:0.5 F4:1 R:0.25",
                        "C4:1 R:0.25 D4:5"
                )
        ));

        seeds.add(simpleSeed(
                "Dance The Night",
                "Dance",
                150,
                phrase(
                        "A3:0.25 E5:0.5 R:0.25 F5:0.5 R:0.25 F#5:0.5 R:0.5 F#2:0.25 F#5:0.5 R:0.5",
                        "B3:4.75 F#4:5 F#5:0.5 B5:0.5 A5:0.5 F#5:0.5 F5:0.5 F4:0.5 E5:0.5 F#5:0.5",
                        "A5:0.5 F#5:0.5 F#4:0.5 B2:3.5 F#3:1 D4:16 B5:1 D4:1 F#4:0.75 Bb4:0.25",
                        "F#3:1.5 A3:0.5 Bb4:0.25 F#4:0.5 F#4:0.5 Bb4:0.25 A3:0.5 E2:0.25 Bb4:0.25 F#4:0.5",
                        "Bb4:0.25 A3:0.5 F#3:1 A3:0.5 Bb4:0.25 F#4:0.5 F#4:0.5 Bb4:0.25 A3:0.5 B2:3.5",
                        "F#3:1 F#4:2.25 Bb4:0.25 D4:1 F#4:0.75 Bb4:0.25 F#3:1 A3:0.5 Bb4:0.25 F#4:0.5",
                        "F#4:0.5 D4:0.5 Bb4:0.25 A3:0.5 F#3:1 A3:0.5 D4:0.5 Bb4:0.25 F#4:0.5 F#4:0.5",
                        "Bb4:0.25 A3:0.5 F#3:1 A3:0.5"
                )
        ));

        seeds.add(simpleSeed(
                "Save Your Tears",
                "Dance",
                118,
                phrase(
                        "G4:1 G4:2 E4:1 D4:1.5 R:0.5 C4:1 A3:1 C4:1 D4:0.5 R:0.5",
                        "D4:2 C4:4 G4:1 A4:2 E4:1 D4:1.5 R:0.5 C4:1 A3:1 C4:1",
                        "D4:3 E4:4 C4:2 R:2 G4:2 E4:1 D4:1 C4:1 D4:1 B3:2",
                        "R:16 E4:4 C4:2 R:2 G4:1.5 R:0.5 E4:1 D4:1 C4:1 D4:1",
                        "B3:2 R:14 D3:2 R:3 C3:1 C4:1 C4:2 C4:2 A3:1 C4:1",
                        "A3:1 C4:1 D4:1 B3:4 R:1 G3:1 B3:1 B3:2 B3:2 G3:1",
                        "G3:1 G3:1 B3:1 C4:1 C4:2 R:3 C4:1 E4:1 G4:1 G4:1",
                        "A4:2 E4:1 E4:1 D4:1 E4:1 G4:1 E4:2 R:3 G3:1 B3:1",
                        "B3:1 B3:1"
                )
        ));

    }

    private static final List<Song> ALL_SONGS;
    private static final Map<String, Song> SONGS_BY_NAME;
    private static final List<String> SONG_NAMES;

    static {
        List<Song> songs = new ArrayList<>();
        Map<String, Song> songsByName = new HashMap<>();
        List<String> songNames = new ArrayList<>();

        for (SongSeed seed : SONG_SEEDS) {
            Song song = new Song(
                    seed.title(),
                    seed.style(),
                    seed.bpm(),
                    seed.pattern(),
                    seed.layerPatterns(),
                    Song.shouldPreserveMelody(seed.style()),
                    true
            );
            songs.add(song);
            String lookupName = song.lookupName();
            if (songsByName.put(lookupName, song) != null) {
                throw new IllegalStateException("Duplicate song id: " + lookupName);
            }
            songNames.add(lookupName);
        }

        ALL_SONGS = List.copyOf(songs);
        SONGS_BY_NAME = Map.copyOf(songsByName);
        SONG_NAMES = List.copyOf(songNames);
    }

    private AllSongs() {
    }

    public static Song getSongByName(String name) {
        if (name == null) return null;
        return SONGS_BY_NAME.get(name.toLowerCase(Locale.ROOT));
    }

    public static Song getSongFromItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() != SONG_MATERIAL) return null;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return null;
        if (!hasSongCustomModelData(meta)) return null;

        String value = meta.getPersistentDataContainer().get(BSInstruments.NSKEY, PersistentDataType.STRING);
        if (value == null || !value.startsWith(SONG_KEY_PREFIX)) return null;
        return getSongByName(value.substring(SONG_KEY_PREFIX.length()));
    }

    public static List<Song> getAllSongs() {
        return ALL_SONGS;
    }

    public static List<String> getAllSongNames() {
        return SONG_NAMES;
    }

    public static void giveAllSongs(Player player) {
        int given = 0;
        for (Song song : ALL_SONGS) {
            if (!ItemDelivery.giveToInventory(player, song.getItem())) break;
            given++;
        }
        player.sendMessage("§aAdded §e" + given + "§a sheet music items to your inventory.");
        if (given < ALL_SONGS.size()) player.sendMessage("§cInventory full. Some sheet music was not added.");
    }

    public static Song getRandomSong() {
        return ALL_SONGS.get(ThreadLocalRandom.current().nextInt(ALL_SONGS.size()));
    }

    public static boolean isSameSong(ItemStack itemStack, Song song) {
        Song itemSong = getSongFromItem(itemStack);
        return itemSong == song;
    }

    private static boolean hasSongCustomModelData(ItemMeta meta) {
        return meta.hasCustomModelDataComponent()
                && meta.getCustomModelDataComponent().getFloats().contains(SONG_CUSTOM_MODEL_DATA);
    }

    private static String phrase(String... parts) {
        return String.join(" ", parts);
    }

    private record SongSeed(String title, String style, String pattern, List<String> layerPatterns, int bpm) {
        private SongSeed(String title, String style, String pattern, List<String> layerPatterns) {
            this(title, style, pattern, layerPatterns, defaultBpm(title, style));
        }

        private SongSeed normalized() {
            return new SongSeed(
                    normalizeTitle(title),
                    normalizeStyle(style),
                    pattern,
                    layerPatterns,
                    bpm
            );
        }
    }
}
