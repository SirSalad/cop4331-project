package edu.ucf.cop4331project.config

import edu.ucf.cop4331project.game.Horse
import edu.ucf.cop4331project.game.Track
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class GameConfig(
    var horses: Set<Horse> = mutableSetOf(
        Horse("Horsune Miku", "Urban Street", "curly", "Thoroughbred", 0, 2.5),
        Horse("Thimble", "E. Colonial", "braided", "Quarter Horse", 0, 1.0),
        Horse("Valkyrie", "Plains", "straight", "Stockhorse", 0, 3.0),
        Horse("Fasty McFast", "Plains", "short", "Spanish Mustang", 0, 1.0),
        Horse("Freddy Fazhorse", "Desert", "straight", "Spanish Mustang", 0, 1.0),
        Horse("HorseJuan", "Desert", "braided", "Thoroughbred", 0, 1.0),
        Horse("Mississippi Queen", "E. Colonial", "curly", "Mississippi Queen", 0, 2.1),
        Horse("Guppie Dan", "Desert", "short", "Stockhorse", 0, 1.0),
        Horse("Saul Hoofman", "Urban Street", "straight", "Quarter Horse", 1, 1.0)
    ),
    var tracks: Set<Track> = mutableSetOf(
        Track("Plains", "raining"),
        Track("Plains", "sunny"),
        Track("Desert", "raining"),
        Track("Desert", "sunny"),
        Track("Urban Street", "raining"),
        Track("Urban Street", "sunny"),
        Track("E. Colonial", "raining"),
        Track("E. Colonial", "sunny")
    )
)