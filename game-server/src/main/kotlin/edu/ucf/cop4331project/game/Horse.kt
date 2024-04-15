package edu.ucf.cop4331project.game

import kotlinx.serialization.Serializable
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@Serializable
@ConfigSerializable
data class Horse(
    val name: String,
    val advTrack: String,
    val maneStyle: String,
    val race: String,
    val injuries: Int,
    val tailLength: Double
)
