package biraw.online.bSInstruments;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import biraw.online.bSInstruments.Obtaining.ItemDelivery;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class AllSongs {
    private static final List<SongSeed> SONG_SEEDS = List.of(
            new SongSeed("Ode to Joy", "Classical", "E4:2 E4:2 F4:2 G4:2 G4:2 F4:2 E4:2 D4:2 C4:2 C4:2 D4:2 E4:2 E4:3 D4:1 D4:4"),
            new SongSeed("Eine Kleine Nachtmusik", "Classical", "G4:2 D4:2 G4:2 D4:2 G4:2 B4:2 D5:4 C5:2 A4:2 C5:2 A4:2 C5:2 F#4:2 A4:4"),
            new SongSeed("Canon in D", "Classical", "D4:2 A3:2 B3:2 F#3:2 G3:2 D3:2 G3:2 A3:2 D4:2 F#4:2 A4:2 G4:2 F#4:2 E4:2 D4:4"),
            new SongSeed("Spring", "Classical", "E4:2 E4:2 E4:2 D4:1 C4:1 D4:2 E4:2 F4:2 G4:2 G4:2 F4:2 E4:2 D4:2 C4:4"),
            new SongSeed("Blue Danube", "Classical", "D4:3 F#4:1 A4:3 R:1 A4:3 R:1 A4:2 B4:2 C#5:2 D5:4 D4:3 F#4:1 A4:3 R:1"),
            new SongSeed("William Tell", "Classical", "E4:1 E4:1 E4:2 E4:1 E4:1 E4:2 E4:1 G4:1 C4:1 D4:1 E4:4 F4:1 F4:1 F4:1 F4:1 F4:1 E4:1 E4:1 E4:1"),
            new SongSeed("Swan Lake", "Classical", "B3:2 F#4:2 B4:2 A4:2 G4:2 F#4:2 E4:2 D4:2 C#4:2 D4:2 E4:2 F#4:2 B3:4"),
            new SongSeed("Habanera", "Classical", "D4:2 C#4:2 C4:2 B3:2 A#3:2 A3:2 G#3:2 A3:4 R:2 A3:2 B3:2 C4:2 D4:4"),
            new SongSeed("Greensleeves", "Traditional", "A3:2 C4:4 D4:2 E4:3 F4:1 E4:2 D4:4 B3:2 G3:3 A3:1 B3:2 C4:4 A3:4"),
            new SongSeed("Scarborough Fair", "Traditional", "D4:3 D4:1 A4:2 A4:2 E4:2 F4:2 E4:4 D4:2 C4:2 D4:2 E4:2 D4:4"),
            new SongSeed("Auld Lang Syne", "Traditional", "G3:2 C4:3 C4:1 C4:2 E4:2 D4:3 C4:1 D4:2 E4:2 C4:3 C4:1 E4:2 G4:4"),
            new SongSeed("Amazing Grace", "Traditional", "G3:3 C4:1 E4:4 C4:2 E4:2 D4:4 C4:2 A3:2 G3:4 G3:3 C4:1 E4:4"),
            new SongSeed("House of the Rising Sun", "Traditional", "A3:2 C4:2 D4:2 F4:2 A4:2 C5:2 E4:2 A4:2 D4:2 F4:2 A4:2 D5:2 F4:4"),
            new SongSeed("When the Saints", "Traditional", "C4:2 E4:2 F4:2 G4:4 C4:2 E4:2 F4:2 G4:4 C4:2 E4:2 F4:2 G4:2 E4:2 C4:2 E4:2 D4:4"),
            new SongSeed("Yankee Doodle", "Traditional", "C4:2 C4:2 D4:2 E4:2 C4:2 E4:2 D4:4 C4:2 C4:2 D4:2 E4:2 C4:4 B3:4"),
            new SongSeed("Camptown Races", "Traditional", "G4:2 E4:2 G4:2 E4:2 G4:2 A4:2 G4:2 E4:2 D4:2 E4:2 D4:4"),
            new SongSeed("Oh Susanna", "Traditional", "C4:2 D4:2 E4:2 G4:2 G4:2 A4:2 G4:4 E4:2 C4:2 D4:2 E4:2 E4:2 D4:2 C4:4"),
            new SongSeed("Molly Malone", "Traditional", "G3:2 C4:2 C4:2 C4:2 D4:2 E4:2 G4:4 E4:2 C4:2 D4:2 E4:2 F4:2 E4:2 D4:4"),
            new SongSeed("Drunken Sailor", "Sea Shanty", "D4:2 D4:2 D4:2 D4:2 D4:2 D4:2 A3:2 C4:2 D4:4 D4:2 D4:2 D4:2 D4:2 E4:2 F4:2 E4:2 D4:4"),
            new SongSeed("Wellerman", "Sea Shanty", "A3:2 C4:2 D4:2 E4:2 D4:2 C4:2 A3:4 A3:2 C4:2 D4:2 E4:2 G4:2 E4:2 D4:4"),
            new SongSeed("Twinkle Twinkle", "Folk", "C4:2 C4:2 G4:2 G4:2 A4:2 A4:2 G4:4 F4:2 F4:2 E4:2 E4:2 D4:2 D4:2 C4:4"),
            new SongSeed("Mary Had a Little Lamb", "Folk", "E4:2 D4:2 C4:2 D4:2 E4:2 E4:2 E4:4 D4:2 D4:2 D4:4 E4:2 G4:2 G4:4"),
            new SongSeed("Frere Jacques", "Folk", "C4:2 D4:2 E4:2 C4:2 C4:2 D4:2 E4:2 C4:2 E4:2 F4:2 G4:4 E4:2 F4:2 G4:4"),
            new SongSeed("Row Your Boat", "Folk", "C4:3 C4:1 C4:2 D4:1 E4:3 E4:1 D4:1 E4:1 F4:1 G4:4 C5:1 C5:1 C5:1 G4:1 G4:1 G4:1 E4:1 E4:1 E4:1 C4:1 C4:1 C4:1 G4:2 F4:1 E4:1 D4:1 C4:4"),
            new SongSeed("London Bridge", "Folk", "G4:2 A4:2 G4:2 F4:2 E4:2 F4:2 G4:4 D4:2 E4:2 F4:4 E4:2 F4:2 G4:4"),
            new SongSeed("Bingo", "Folk", "G4:2 G4:2 G4:2 D4:2 E4:2 E4:2 D4:4 G4:2 G4:2 A4:2 A4:2 G4:4"),
            new SongSeed("Happy Birthday", "Celebration", "C4:2 C4:1 D4:3 C4:3 F4:3 E4:6 C4:2 C4:1 D4:3 C4:3 G4:3 F4:6"),
            new SongSeed("Jingle Bells", "Holiday", "E4:2 E4:2 E4:4 E4:2 E4:2 E4:4 E4:2 G4:2 C4:3 D4:1 E4:8"),
            new SongSeed("We Wish You a Merry Christmas", "Holiday", "C4:2 F4:2 F4:1 G4:1 F4:1 E4:1 D4:2 D4:2 D4:2 G4:2 G4:1 A4:1 G4:1 F4:1 E4:2 C4:2"),
            new SongSeed("Silent Night", "Holiday", "G4:3 A4:1 G4:2 E4:6 G4:3 A4:1 G4:2 E4:6 D5:4 D5:2 B4:6 C5:4 C5:2 G4:6"),
            new SongSeed("Deck the Halls", "Holiday", "G4:2 F4:1 E4:1 D4:2 C4:2 D4:2 E4:2 C4:2 D4:1 E4:1 F4:2 D4:1 E4:1 F4:2 G4:2"),
            new SongSeed("God Rest Ye Merry Gentlemen", "Holiday", "E4:2 E4:2 B4:2 B4:2 A4:2 G4:2 F#4:2 E4:2 D4:2 E4:2 F#4:2 G4:2 A4:2 B4:4"),
            new SongSeed("Minuet in G", "Classical", "D5:2 G4:2 A4:2 B4:2 C5:2 D5:2 G4:2 G4:2 E5:2 C5:2 D5:2 E5:2 F#5:2 G5:2 G4:2 G4:2"),
            new SongSeed("Fur Elise", "Classical", "E5:1 D#5:1 E5:1 D#5:1 E5:1 B4:1 D5:1 C5:1 A4:4 C4:1 E4:1 A4:1 B4:4 E4:1 G#4:1 B4:1 C5:4"),
            new SongSeed("Toreador", "Classical", "C4:2 C4:2 C4:2 G3:2 E4:2 E4:2 E4:2 C4:2 G4:2 G4:2 G4:2 E4:2 C5:4 G4:4"),
            new SongSeed("New World", "Classical", "E4:3 G4:1 G4:2 E4:2 D4:3 C4:1 D4:2 E4:4 G4:3 A4:1 G4:2 E4:2 D4:4"),
            new SongSeed("Bridal Chorus", "Classical", "C4:4 F4:4 F4:3 F4:1 C4:4 G4:4 E4:4 F4:8 C4:4 F4:4 A4:4 C5:4"),
            new SongSeed("Can Can", "Classical", "G4:1 G4:1 A4:1 B4:1 C5:1 C5:1 A4:1 G4:1 E4:1 E4:1 F#4:1 G4:1 A4:1 A4:1 F#4:1 E4:1"),
            new SongSeed("La Cucaracha", "Traditional", "C4:2 C4:2 C4:2 F4:2 A4:4 C4:2 C4:2 C4:2 F4:2 A4:4 F4:2 F4:2 E4:2 E4:2 D4:2 D4:2 C4:4"),
            new SongSeed("Sakura", "Traditional", "A4:2 A4:2 B4:2 A4:2 A4:2 B4:2 A4:2 B4:2 C5:2 B4:2 A4:2 B4:1 A4:1 F4:4"),
            new SongSeed("Korobeiniki", "Traditional", "E4:2 B3:1 C4:1 D4:2 C4:1 B3:1 A3:2 A3:1 C4:1 E4:2 D4:1 C4:1 B3:4 C4:1 D4:1 E4:2 C4:2 A3:2 A3:4"),
            new SongSeed("Hava Nagila", "Traditional", "E4:1 F4:1 G4:2 G4:2 F4:1 E4:1 F4:2 E4:2 D4:2 E4:1 F4:1 G4:2 G4:2 F4:1 E4:1 F4:2 E4:4"),
            new SongSeed("Aura Lee", "Traditional", "G4:3 F4:1 E4:2 D4:2 C4:4 D4:2 E4:2 F4:2 G4:2 E4:4 D4:4"),
            new SongSeed("Danny Boy", "Traditional", "D4:2 F4:2 G4:4 F4:2 E4:2 D4:4 C4:2 D4:2 E4:2 F4:2 G4:4"),
            new SongSeed("Shenandoah", "Traditional", "C4:2 E4:2 G4:4 E4:2 C4:2 D4:4 E4:2 G4:2 A4:4 G4:2 E4:2 C4:4"),
            new SongSeed("Loch Lomond", "Traditional", "G3:2 C4:2 D4:2 E4:4 D4:2 C4:2 D4:4 E4:2 G4:2 A4:2 G4:2 E4:4"),
            new SongSeed("The Entertainer", "Ragtime", "D4:1 D#4:1 E4:2 C5:1 E4:1 C5:1 E4:1 C5:4 C5:1 B4:1 A4:1 G4:1 F#4:1 A4:1 C5:1 E5:4"),
            new SongSeed("Maple Leaf Rag", "Ragtime", "G4:1 A4:1 C5:1 E5:2 C5:1 A4:1 G4:1 E4:1 G4:1 A4:1 C5:2 A4:1 G4:1 E4:1"),
            new SongSeed("Beethoven Fifth", "Classical", "G4:1 G4:1 G4:1 Eb4:6 R:1 F4:1 F4:1 F4:1 D4:6 R:1 G4:1 G4:1 G4:1 Eb4:4"),
            new SongSeed("Moonlight Sonata", "Classical", "G#3:2 C#4:2 E4:2 G#3:2 C#4:2 E4:2 A3:2 C#4:2 E4:2 A3:2 C#4:2 E4:2 B3:2 D#4:2 F#4:2 B3:2"),
            new SongSeed("Turkish March", "Classical", "B4:1 A4:1 G#4:1 A4:1 C5:2 R:1 D5:1 C5:1 B4:1 C5:1 E5:2 R:1 F5:1 E5:1 D#5:1 E5:1 B5:2"),
            new SongSeed("Toccata in D Minor", "Classical", "A4:2 G4:2 A4:2 R:2 G4:2 F4:2 E4:2 D4:4 A4:2 C5:2 A4:2 G4:2 F4:4"),
            new SongSeed("Air on the G String", "Classical", "D4:4 F#4:2 A4:2 D5:4 C#5:2 B4:2 A4:4 G4:2 F#4:2 E4:4 D4:4"),
            new SongSeed("Jesu Joy of Man's Desiring", "Classical", "G4:1 A4:1 B4:1 D5:1 C5:1 B4:1 A4:1 G4:1 F#4:1 G4:1 A4:1 C5:1 B4:1 A4:1 G4:2"),
            new SongSeed("Hallelujah Chorus", "Classical", "G4:2 G4:2 G4:2 G4:2 A4:2 B4:2 C5:4 B4:2 A4:2 G4:2 A4:2 B4:4"),
            new SongSeed("Ride of the Valkyries", "Classical", "B4:2 E5:2 B4:2 E5:2 B4:2 E5:2 G5:4 E5:2 G5:2 E5:2 G5:2 E5:2 G5:2 B5:4"),
            new SongSeed("Wedding March", "Classical", "C4:2 F4:2 A4:4 C5:2 A4:2 F4:4 G4:2 E4:2 C4:4 F4:2 A4:2 C5:4"),
            new SongSeed("Funeral March", "Classical", "C4:2 C4:2 C4:2 C4:2 Eb4:2 D4:2 D4:2 C4:4 C4:2 C4:2 C4:2 C4:2 F4:2 Eb4:2 Eb4:2 D4:4"),
            new SongSeed("Largo", "Classical", "F4:4 A4:4 C5:4 Bb4:2 A4:2 G4:4 F4:4 G4:2 A4:2 Bb4:4 A4:4"),
            new SongSeed("Gymnopedie", "Classical", "G4:4 B4:4 D5:4 B4:4 A4:4 C5:4 E5:4 C5:4 G4:4 B4:4 D5:4"),
            new SongSeed("Clair de Lune", "Classical", "F4:3 F4:1 F4:2 Ab4:2 C5:4 Bb4:2 Ab4:2 G4:4 F4:4 Eb4:2 F4:2 G4:4"),
            new SongSeed("Morning Mood", "Classical", "E4:2 D4:2 C4:2 D4:2 E4:2 G4:2 E4:4 D4:2 B3:2 D4:4 C4:2 E4:2 C4:4"),
            new SongSeed("In the Hall of the Mountain King", "Classical", "B3:1 C#4:1 D4:1 E4:1 F#4:1 D4:1 F#4:2 F4:1 C#4:1 F4:2 E4:1 C4:1 E4:2"),
            new SongSeed("Brahms Lullaby", "Classical", "G4:2 G4:2 Bb4:4 G4:2 G4:2 Bb4:4 G4:2 Bb4:2 Eb5:4 D5:4 C5:4"),
            new SongSeed("Nocturne", "Classical", "C4:2 G4:2 E5:4 D5:2 C5:2 B4:4 A4:2 G4:2 F4:4 E4:2 D4:2 C4:4"),
            new SongSeed("Pathetique", "Classical", "C4:2 Eb4:2 G4:2 C5:4 Bb4:2 Ab4:2 G4:4 F4:2 Eb4:2 D4:4 C4:4"),
            new SongSeed("Radetzky March", "Classical", "G4:2 G4:2 G4:2 A4:2 B4:2 C5:2 D5:4 D5:2 C5:2 B4:2 A4:2 G4:4"),
            new SongSeed("Bolero", "Classical", "C4:1 D4:1 E4:1 C4:1 D4:1 E4:1 C4:1 D4:1 E4:1 G4:1 E4:1 C4:1 E4:1 G4:1 E4:1 C4:1"),
            new SongSeed("O Christmas Tree", "Holiday", "G4:3 C5:1 C5:2 C5:2 D5:3 E5:1 E5:2 E5:2 E5:2 D5:2 E5:2 F5:2 B4:4 D5:4 C5:4"),
            new SongSeed("The First Noel", "Holiday", "E4:2 D4:2 C4:2 D4:2 E4:2 F4:2 G4:4 A4:2 B4:2 C5:2 B4:2 A4:2 G4:4"),
            new SongSeed("Hark the Herald Angels Sing", "Holiday", "G4:2 G4:2 A4:2 G4:2 C5:2 B4:4 G4:2 G4:2 A4:2 G4:2 D5:2 C5:4"),
            new SongSeed("Joy to the World", "Holiday", "C5:3 B4:1 A4:2 G4:4 F4:3 E4:1 D4:2 C4:4 G4:3 A4:1 A4:3 B4:1 B4:4"),
            new SongSeed("Angels We Have Heard", "Holiday", "C5:2 B4:2 C5:2 G4:2 A4:2 G4:2 E4:4 C5:2 B4:2 C5:2 G4:2 A4:2 G4:2 E4:4"),
            new SongSeed("Away in a Manger", "Holiday", "G4:2 G4:2 F4:2 E4:2 D4:4 C4:2 C4:2 B3:2 C4:2 D4:4 G4:2 G4:2 F4:2 E4:2 D4:4"),
            new SongSeed("O Come All Ye Faithful", "Holiday", "G4:2 G4:2 D4:2 G4:2 A4:2 D4:2 B4:4 A4:2 B4:2 C5:2 B4:2 A4:2 G4:4"),
            new SongSeed("Good King Wenceslas", "Holiday", "G4:2 G4:2 G4:2 A4:2 G4:2 G4:2 D4:4 E4:2 D4:2 E4:2 F#4:2 G4:4"),
            new SongSeed("Twelve Days of Christmas", "Holiday", "G4:2 G4:2 G4:2 C5:2 B4:2 C5:2 D5:4 E5:2 D5:2 C5:2 B4:2 A4:2 G4:4"),
            new SongSeed("Here We Come A-Wassailing", "Holiday", "G4:2 C5:2 C5:2 D5:2 E5:2 D5:2 C5:4 B4:2 A4:2 G4:2 A4:2 B4:2 C5:4"),
            new SongSeed("Coventry Carol", "Holiday", "E4:2 G4:2 F4:2 E4:2 D4:4 E4:2 G4:2 A4:2 B4:2 A4:4 G4:2 F4:2 E4:4"),
            new SongSeed("Bring a Torch Jeanette Isabella", "Holiday", "G4:2 A4:2 B4:2 C5:2 D5:4 C5:2 B4:2 A4:2 G4:2 A4:4 B4:2 C5:2 D5:4"),
            new SongSeed("Red River Valley", "Traditional", "G4:2 A4:2 C5:4 C5:2 B4:2 A4:4 G4:2 A4:2 C5:2 A4:2 G4:4"),
            new SongSeed("This Old Man", "Folk", "G4:2 E4:2 G4:2 G4:2 E4:2 G4:4 A4:2 G4:2 F4:2 E4:2 D4:4"),
            new SongSeed("Pop Goes the Weasel", "Folk", "C4:2 C4:2 D4:2 D4:2 E4:2 G4:2 E4:4 C4:2 C4:2 D4:2 D4:2 E4:4 C4:4"),
            new SongSeed("Itsy Bitsy Spider", "Folk", "G4:2 C5:2 C5:2 C5:2 D5:2 E5:2 E5:4 E5:2 D5:2 C5:2 D5:2 E5:2 C5:4"),
            new SongSeed("Old MacDonald", "Folk", "G4:2 G4:2 G4:2 D4:2 E4:2 E4:2 D4:4 B4:2 B4:2 A4:2 A4:2 G4:4"),
            new SongSeed("She'll Be Coming Round the Mountain", "Folk", "G4:2 G4:2 G4:2 B4:2 D5:4 B4:2 G4:2 A4:2 B4:2 A4:2 G4:4"),
            new SongSeed("Home on the Range", "Folk", "G3:2 C4:2 E4:4 D4:2 C4:2 D4:4 E4:2 G4:2 C5:4 B4:2 A4:2 G4:4"),
            new SongSeed("Oh My Darling Clementine", "Folk", "G4:2 G4:2 G4:2 D4:2 B4:2 B4:2 B4:4 G4:2 B4:2 D5:2 D5:2 C5:2 B4:2 A4:4"),
            new SongSeed("Skip to My Lou", "Folk", "C4:2 E4:2 G4:2 G4:2 A4:2 G4:2 E4:4 C4:2 E4:2 G4:2 G4:2 A4:2 G4:2 C5:4"),
            new SongSeed("Swing Low Sweet Chariot", "Spiritual", "C4:2 E4:2 G4:4 E4:2 C4:2 D4:4 E4:2 G4:2 A4:4 G4:2 E4:2 C4:4"),
            new SongSeed("Michael Row the Boat Ashore", "Spiritual", "C4:2 E4:2 G4:4 G4:2 A4:2 G4:4 E4:2 D4:2 C4:4 E4:2 G4:2 C5:4"),
            new SongSeed("Down by the Riverside", "Spiritual", "C4:2 E4:2 G4:2 A4:2 G4:4 E4:2 C4:2 D4:2 E4:2 D4:2 C4:4"),
            new SongSeed("Battle Hymn of the Republic", "Traditional", "G4:2 G4:2 G4:2 E4:2 F4:2 G4:2 A4:4 G4:2 E4:2 C4:2 D4:2 E4:4"),
            new SongSeed("John Brown's Body", "Traditional", "G4:2 G4:2 G4:2 E4:2 F4:2 G4:2 A4:4 G4:2 E4:2 C4:2 D4:2 C4:4"),
            new SongSeed("Irish Washerwoman", "Traditional", "G4:1 B4:1 D5:1 G5:1 D5:1 B4:1 G4:1 B4:1 A4:1 C5:1 E5:1 A5:1 E5:1 C5:1 A4:1 C5:1"),
            new SongSeed("Sailor's Hornpipe", "Traditional", "G4:1 B4:1 D5:1 G5:1 F#5:1 E5:1 D5:1 C5:1 B4:1 G4:1 A4:1 B4:1 C5:1 A4:1 D5:2"),
            new SongSeed("Mexican Hat Dance", "Traditional", "C5:1 G4:1 C5:1 G4:1 C5:1 G4:1 E4:2 D4:1 G4:1 D4:1 G4:1 D4:1 G4:1 C4:2"),
            new SongSeed("Tarantella", "Traditional", "A4:1 E4:1 A4:1 E4:1 A4:1 B4:1 C5:1 B4:1 A4:1 E4:1 A4:1 E4:1 A4:1 B4:1 C5:2"),
            new SongSeed("Funiculi Funicula", "Traditional", "C4:2 E4:2 G4:2 C5:4 B4:2 A4:2 G4:4 E4:2 G4:2 C5:2 E5:4 D5:2 C5:4"),
            new SongSeed("Simple Gifts", "Traditional", "G4:2 C5:2 D5:2 E5:4 D5:2 C5:2 A4:4 G4:2 A4:2 C5:2 D5:2 C5:4"),
            new SongSeed("For He's a Jolly Good Fellow", "Traditional", "G4:2 G4:2 A4:2 G4:2 C5:4 B4:4 G4:2 G4:2 A4:2 G4:2 D5:4 C5:4"),
            new SongSeed("Take Me Out to the Ball Game", "Traditional", "C4:2 C5:2 A4:2 G4:2 E4:2 G4:4 D4:2 D5:2 B4:2 A4:2 F4:2 A4:4"),
            new SongSeed("The Ash Grove", "Traditional", "G4:2 C5:2 B4:2 A4:2 G4:2 E4:2 C4:4 D4:2 E4:2 F4:2 G4:2 A4:4"),
            new SongSeed("The Minstrel Boy", "Traditional", "G4:2 C5:2 D5:2 E5:4 D5:2 C5:2 A4:4 G4:2 A4:2 C5:2 D5:2 E5:4"),
            new SongSeed("The Wearing of the Green", "Traditional", "E4:2 G4:2 A4:2 B4:4 A4:2 G4:2 E4:4 G4:2 A4:2 B4:2 C5:2 B4:4"),
            new SongSeed("Lo Yisa Goy", "Traditional", "E4:2 F#4:2 G4:2 A4:4 G4:2 F#4:2 E4:4 D4:2 E4:2 F#4:2 G4:2 E4:4"),
            new SongSeed("Be Thou My Vision", "Hymn", "D4:2 G4:2 A4:2 B4:4 A4:2 G4:2 E4:4 D4:2 E4:2 G4:2 A4:2 G4:4"),
            new SongSeed("Come Thou Fount", "Hymn", "C4:2 E4:2 G4:4 G4:2 A4:2 G4:4 E4:2 D4:2 C4:4 E4:2 G4:2 C5:4"),
            new SongSeed("Doxology", "Hymn", "G4:2 F#4:2 E4:2 D4:2 G4:2 A4:2 B4:4 C5:2 B4:2 A4:2 G4:2 A4:4"),
            new SongSeed("Old Hundredth", "Hymn", "G4:2 F#4:2 E4:2 D4:2 G4:2 A4:2 B4:4 C5:2 B4:2 A4:2 G4:4"),
            new SongSeed("Star Spangled Banner", "Patriotic", "G4:2 E4:2 C4:4 E4:4 G4:4 C5:8 E5:2 D5:2 C5:4 E4:4 F#4:4 G4:8"),
            new SongSeed("America the Beautiful", "Patriotic", "C4:2 C4:2 E4:2 E4:2 G4:2 G4:2 C5:4 B4:2 A4:2 G4:2 F4:2 E4:4"),
            new SongSeed("My Country Tis of Thee", "Patriotic", "G4:2 G4:2 A4:2 F#4:2 G4:2 A4:2 B4:4 B4:2 B4:2 C5:2 B4:2 A4:2 G4:4"),
            new SongSeed("La Marseillaise", "Patriotic", "G4:2 G4:2 G4:2 C5:4 C5:2 D5:2 E5:4 E5:2 D5:2 C5:2 E5:2 D5:4"),
            new SongSeed("Rule Britannia", "Patriotic", "G4:2 C5:2 B4:2 A4:2 G4:4 A4:2 B4:2 C5:2 D5:2 C5:4"),
            new SongSeed("British Grenadiers", "Patriotic", "G4:1 A4:1 B4:1 C5:1 D5:2 B4:2 C5:1 D5:1 E5:1 F#5:1 G5:4 D5:4"),
            new SongSeed("Amazing Grace Reprise", "Traditional", "D4:3 G4:1 B4:4 G4:2 B4:2 A4:4 G4:2 E4:2 D4:4 D4:3 G4:1 B4:4"),
            new SongSeed("Simple Blues", "Original", "C4:2 E4:2 F4:2 F#4:1 G4:1 C5:2 A4:2 G4:2 E4:2 C4:4 R:2 G3:2 A#3:2 C4:4"),
            new SongSeed("Miner's March", "Original", "C4:2 E4:2 G4:2 C5:2 B4:2 G4:2 E4:2 C4:2 D4:2 F4:2 A4:2 D5:2 C5:4"),
            new SongSeed("Neon Skyline", "Modern Pop", "C4:2 E4:2 G4:2 A4:2 G4:4 E4:2 D4:2 C4:4 R:1 C4:2 E4:2 G4:2 A4:2 C5:4 B4:2 A4:2 G4:4"),
            new SongSeed("Midnight Radio", "Modern Pop", "A3:2 C4:2 E4:2 G4:2 E4:4 C4:2 D4:2 E4:4 R:1 A3:2 C4:2 E4:2 A4:2 G4:4 E4:4"),
            new SongSeed("City Lights", "Modern Pop", "F4:2 A4:2 C5:2 A4:2 G4:4 F4:2 E4:2 F4:4 R:1 F4:2 A4:2 C5:2 D5:2 C5:4 A4:2 G4:4"),
            new SongSeed("Electric Heart", "Modern Pop", "D4:2 F#4:2 A4:2 B4:2 A4:4 F#4:2 E4:2 D4:4 R:1 D4:2 F#4:2 A4:2 D5:2 C#5:4 A4:4"),
            new SongSeed("Summer Drive", "Modern Pop", "G4:2 B4:2 D5:2 E5:2 D5:4 B4:2 A4:2 G4:4 R:1 G4:2 A4:2 B4:2 D5:2 B4:4 G4:4"),
            new SongSeed("Afterglow", "Modern Pop", "E4:2 G4:2 B4:2 C5:2 B4:4 G4:2 F#4:2 E4:4 R:1 E4:2 G4:2 B4:2 E5:2 D5:4 B4:4"),
            new SongSeed("Starlit Avenue", "Modern Pop", "C4:1 D4:1 E4:2 G4:2 A4:2 G4:2 E4:4 D4:2 E4:2 G4:2 C5:2 B4:4 A4:4"),
            new SongSeed("Golden Hour", "Modern Pop", "A3:2 E4:2 F#4:2 A4:2 B4:4 A4:2 F#4:2 E4:4 R:1 A3:2 C#4:2 E4:2 A4:2 G#4:4 E4:4"),
            new SongSeed("Lost in the Beat", "Modern Pop", "D4:1 D4:1 F4:2 A4:2 C5:2 A4:2 F4:4 E4:2 F4:2 A4:2 D5:2 C5:4 A4:4"),
            new SongSeed("Satellite", "Modern Pop", "G3:2 D4:2 G4:2 B4:2 A4:4 G4:2 E4:2 D4:4 R:1 G3:2 D4:2 G4:2 C5:2 B4:4 G4:4"),
            new SongSeed("Velvet Chorus", "Modern Pop", "F4:2 G4:2 A4:2 C5:2 A4:4 G4:2 F4:2 E4:4 R:1 F4:2 A4:2 C5:2 F5:2 E5:4 C5:4"),
            new SongSeed("Crystal Pulse", "Modern Pop", "C4:1 E4:1 G4:2 C5:2 B4:2 G4:2 E4:4 D4:1 F4:1 A4:2 D5:2 C5:2 A4:2 F4:4"),
            new SongSeed("Dreamwave", "Modern Pop", "E4:2 B4:2 C5:2 B4:2 A4:4 E4:2 G4:2 A4:4 R:1 E4:2 B4:2 C5:2 E5:2 D5:4 B4:4"),
            new SongSeed("Rain on Glass", "Modern Pop", "A3:2 D4:2 F4:2 A4:2 G4:4 F4:2 D4:2 C4:4 R:1 A3:2 D4:2 F4:2 C5:2 A4:4 F4:4"),
            new SongSeed("Open Road", "Modern Pop", "G4:2 A4:2 B4:2 D5:2 B4:4 A4:2 G4:2 E4:4 R:1 G4:2 B4:2 D5:2 E5:2 D5:4 B4:4"),
            new SongSeed("Firefly Dance", "Modern Pop", "C4:1 C4:1 E4:1 G4:1 A4:2 G4:2 E4:4 D4:1 D4:1 F4:1 A4:1 B4:2 A4:2 F4:4"),
            new SongSeed("Echo Bloom", "Modern Pop", "D4:2 A4:2 B4:2 A4:2 G4:4 D4:2 F#4:2 G4:4 R:1 D4:2 A4:2 B4:2 D5:2 C5:4 A4:4"),
            new SongSeed("Highrise", "Modern Pop", "F4:2 C5:2 D5:2 C5:2 A4:4 F4:2 G4:2 A4:4 R:1 F4:2 A4:2 C5:2 D5:2 C5:4 A4:4"),
            new SongSeed("Lunar Arcade", "Modern Pop", "E4:1 G4:1 B4:2 D5:2 B4:2 G4:2 E4:4 F#4:1 A4:1 C5:2 E5:2 C5:2 A4:2 F#4:4"),
            new SongSeed("Paper Lanterns", "Modern Pop", "C4:2 G4:2 A4:2 G4:2 E4:4 C4:2 D4:2 E4:4 R:1 C4:2 E4:2 G4:2 C5:2 A4:4 G4:4"),
            new SongSeed("Dopamine Rush", "Modern Pop", "C4:1 E4:1 G4:2 A4:2 C5:2 A4:2 G4:4 E4:2 G4:2 A4:2 C5:2 D5:4 C5:4"),
            new SongSeed("Chrome Sunset", "Modern Pop", "A3:2 C#4:2 E4:2 G#4:2 A4:4 E4:2 F#4:2 G#4:4 R:1 A3:2 C#4:2 E4:2 B4:2 A4:4 G#4:4"),
            new SongSeed("Glass Elevator", "Modern Pop", "F4:1 A4:1 C5:2 E5:2 D5:2 C5:2 A4:4 G4:1 Bb4:1 D5:2 F5:2 E5:2 D5:2 Bb4:4"),
            new SongSeed("Weekend Signal", "Modern Pop", "D4:2 F#4:2 A4:2 C#5:2 B4:4 A4:2 F#4:2 E4:4 R:1 D4:2 A4:2 B4:2 C#5:2 D5:4 A4:4"),
            new SongSeed("Pink Horizon", "Modern Pop", "G4:2 D5:2 E5:2 D5:2 B4:4 G4:2 A4:2 B4:4 R:1 G4:2 B4:2 D5:2 G5:2 E5:4 D5:4"),
            new SongSeed("Static Kiss", "Modern Pop", "E4:1 E4:1 G4:2 B4:2 C5:2 B4:2 G4:4 F#4:2 G4:2 B4:2 E5:2 D5:4 B4:4"),
            new SongSeed("Blue Neon", "Modern Pop", "C4:2 D4:2 E4:2 G4:2 A4:4 G4:2 E4:2 D4:4 R:1 C4:2 E4:2 A4:2 G4:2 E4:4"),
            new SongSeed("Velcro Heart", "Modern Pop", "A3:1 C4:1 E4:2 A4:2 G4:2 E4:2 C4:4 D4:1 F4:1 A4:2 C5:2 A4:2 F4:2 D4:4"),
            new SongSeed("Rooftop Stereo", "Modern Pop", "F4:2 G4:2 A4:2 C5:2 D5:4 C5:2 A4:2 G4:4 R:1 F4:2 A4:2 D5:2 C5:2 A4:4"),
            new SongSeed("Soft Focus", "Modern Pop", "D4:2 E4:2 F#4:2 A4:2 B4:4 A4:2 F#4:2 D4:4 R:1 E4:2 F#4:2 A4:2 C#5:2 B4:4"),
            new SongSeed("Comet Trail", "Modern Pop", "G3:2 B3:2 D4:2 G4:2 A4:4 B4:2 A4:2 G4:4 R:1 D4:2 G4:2 B4:2 D5:2 C5:4 B4:4"),
            new SongSeed("Bright Side", "Modern Pop", "C4:2 E4:2 F4:2 G4:2 A4:4 G4:2 F4:2 E4:4 R:1 C4:2 E4:2 G4:2 C5:2 B4:4 G4:4"),
            new SongSeed("Night Bus", "Modern Pop", "A3:2 E4:2 G4:2 A4:2 C5:4 A4:2 G4:2 E4:4 R:1 A3:2 C4:2 E4:2 G4:2 A4:4"),
            new SongSeed("Mirrorball", "Modern Pop", "F4:1 G4:1 A4:2 C5:2 A4:2 G4:2 F4:4 A4:1 C5:1 D5:2 F5:2 D5:2 C5:2 A4:4"),
            new SongSeed("Arcade Romance", "Modern Pop", "E4:2 G#4:2 B4:2 C#5:2 B4:4 G#4:2 F#4:2 E4:4 R:1 E4:2 B4:2 C#5:2 E5:2 D#5:4 B4:4"),
            new SongSeed("Polaroid Summer", "Modern Pop", "D4:2 F4:2 A4:2 D5:2 C5:4 A4:2 F4:2 D4:4 R:1 F4:2 A4:2 C5:2 D5:2 A4:4"),
            new SongSeed("Late Night Glitter", "Modern Pop", "G4:1 A4:1 B4:2 D5:2 E5:2 D5:2 B4:4 A4:1 B4:1 D5:2 G5:2 E5:2 D5:2 B4:4"),
            new SongSeed("Coffee Shop Chorus", "Modern Pop", "C4:2 G4:2 E4:2 G4:2 A4:4 G4:2 E4:2 C4:4 R:1 D4:2 A4:2 F4:2 A4:2 G4:4"),
            new SongSeed("Silver Jacket", "Modern Pop", "F#4:2 A4:2 C#5:2 E5:2 C#5:4 A4:2 G#4:2 F#4:4 R:1 F#4:2 A4:2 C#5:2 F#5:2 E5:4 C#5:4"),
            new SongSeed("Heatwave", "Modern Pop", "E4:2 A4:2 B4:2 C#5:2 B4:4 A4:2 E4:2 F#4:4 R:1 E4:2 A4:2 C#5:2 E5:2 C#5:4 B4:4"),
            new SongSeed("Skyline Fever", "Modern Pop", "C4:1 D4:1 E4:2 G4:2 C5:2 B4:2 G4:4 E4:1 F4:1 G4:2 B4:2 D5:2 C5:2 G4:4"),
            new SongSeed("Cassette Dreams", "Modern Pop", "A3:2 C4:2 D4:2 E4:2 G4:4 E4:2 D4:2 C4:4 R:1 A3:2 E4:2 G4:2 A4:2 G4:4 E4:4"),
            new SongSeed("Prism", "Modern Pop", "D4:1 F#4:1 A4:2 C#5:2 D5:2 C#5:2 A4:4 F#4:1 A4:1 B4:2 D5:2 C#5:2 B4:2 A4:4"),
            new SongSeed("Downtown Echo", "Modern Pop", "G3:2 D4:2 F4:2 G4:2 Bb4:4 G4:2 F4:2 D4:4 R:1 G3:2 D4:2 G4:2 Bb4:2 C5:4 Bb4:4"),
            new SongSeed("Bubblegum Moon", "Modern Pop", "C4:2 E4:2 G4:2 B4:2 C5:4 B4:2 G4:2 E4:4 R:1 C4:2 F4:2 A4:2 C5:2 A4:4 G4:4"),
            new SongSeed("Kinetic Love", "Modern Pop", "E4:1 F#4:1 G#4:2 B4:2 C#5:2 B4:2 G#4:4 F#4:1 G#4:1 B4:2 E5:2 C#5:2 B4:2 G#4:4"),
            new SongSeed("Daydream Metro", "Modern Pop", "F4:2 A4:2 Bb4:2 C5:2 D5:4 C5:2 Bb4:2 A4:4 R:1 F4:2 C5:2 D5:2 F5:2 D5:4 C5:4"),
            new SongSeed("Plastic Stars", "Modern Pop", "D4:2 G4:2 A4:2 B4:2 A4:4 G4:2 D4:2 E4:4 R:1 D4:2 G4:2 B4:2 D5:2 B4:4 A4:4"),
            new SongSeed("Sunroof Anthem", "Modern Pop", "G4:2 B4:2 C5:2 D5:2 E5:4 D5:2 C5:2 B4:4 R:1 G4:2 C5:2 E5:2 G5:2 E5:4 D5:4"),
            new SongSeed("Afterparty Lights", "Modern Pop", "A3:1 E4:1 A4:2 C5:2 B4:2 A4:2 E4:4 G3:1 D4:1 G4:2 B4:2 A4:2 G4:2 D4:4")
    );

    public static final List<Song> AllSongs;
    private static final Map<String, Song> SONGS_BY_NAME;
    private static final List<String> SONG_NAMES;

    static {
        List<Song> songs = new ArrayList<>();
        Map<String, Song> songsByName = new HashMap<>();
        List<String> songNames = new ArrayList<>();

        for (SongSeed seed : SONG_SEEDS) {
            Song song = new Song(seed.title(), seed.style(), 4, seed.pattern());
            songs.add(song);
            String lookupName = song.lookupName();
            songsByName.put(lookupName, song);
            songNames.add(lookupName);
        }

        AllSongs = List.copyOf(songs);
        SONGS_BY_NAME = Map.copyOf(songsByName);
        SONG_NAMES = List.copyOf(songNames);
    }

    public static Song getSongByName(String name) {
        if (name == null) return null;
        return SONGS_BY_NAME.get(name.toLowerCase(Locale.ROOT));
    }

    public static Song getSongFromItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() != Material.PAPER) return null;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return null;
        if (!meta.hasCustomModelData() || meta.getCustomModelData() != Song.CUSTOM_MODEL_DATA) return null;

        String value = meta.getPersistentDataContainer().get(BSInstruments.NSKEY, PersistentDataType.STRING);
        if (value == null || !value.startsWith("song_")) return null;
        return getSongByName(value.substring("song_".length()));
    }

    public static boolean isSong(ItemStack itemStack) {
        return getSongFromItem(itemStack) != null;
    }

    public static List<String> getAllSongNames() {
        return SONG_NAMES;
    }

    public static void giveAllSongs(Player player) {
        int given = 0;
        for (Song song : AllSongs) {
            if (!ItemDelivery.giveToInventory(player, song.getItem())) break;
            given++;
        }
        player.sendMessage("§aAdded §e" + given + "§a sheet music items to your inventory.");
        if (given < AllSongs.size()) player.sendMessage("§cInventory full. Some sheet music was not added.");
    }

    public static Song getRandomSong() {
        return AllSongs.get(ThreadLocalRandom.current().nextInt(AllSongs.size()));
    }

    public static boolean isSameSong(ItemStack itemStack, Song song) {
        Song itemSong = getSongFromItem(itemStack);
        return itemSong != null && Objects.equals(itemSong.id(), song.id());
    }

    private record SongSeed(String title, String style, String pattern) {
    }
}
