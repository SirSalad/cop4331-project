package edu.ucf.cop4331project.config

import edu.ucf.cop4331project.game.Horse
import edu.ucf.cop4331project.game.Track
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class GameConfig(
    var horses: Set<Horse> = mutableSetOf(
        Horse("horse1", "track1", "mane1", "race1", 0, 1.0),
        Horse("horse2", "track2", "mane2", "race2", 0, 1.0),
        Horse("horse3", "track3", "mane3", "race3", 0, 1.0),
        Horse("horse4", "track4", "mane4", "race4", 0, 1.0),
        Horse("horse5", "track5", "mane5", "race5", 0, 1.0),
        Horse("horse6", "track6", "mane6", "race6", 0, 1.0),
        Horse("horse7", "track7", "mane7", "race7", 0, 1.0),
        Horse("horse8", "track8", "mane8", "race8", 0, 1.0),
        Horse("horse9", "track9", "mane9", "race9", 0, 1.0),
    ),
    var tracks: Set<Track> = mutableSetOf(
        Track("track1", "wet"),
        Track("track2", "dry"),
    )
)