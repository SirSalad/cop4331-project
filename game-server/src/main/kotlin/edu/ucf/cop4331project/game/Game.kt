package edu.ucf.cop4331project.game

import kotlinx.serialization.Serializable
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@Serializable
@ConfigSerializable
data class Track(val track: String, val weather: String)

@Serializable
class Game(private val horses: Set<Horse>, private val track: Track) {
    val winner = determineWinner()

    fun determineWinner(): Horse {
        val scores = horses.associateWith {
            var score = 0.0

            if (track.track == it.advTrack) score += 1
            score -= it.injuries
            if (it.tailLength > 2.0) score += 5

            if (track.weather == "rain") {
                // add scores accoridngly
            }
            if (track.weather == "dry") {
                // add scores accordingly
            }

            score
        }

        return getWeighedRandomArbitrary(scores)
    }

    private fun <T> getWeighedRandomArbitrary(entries: Map<T, Double>): T {
        val target = Math.random() * entries.values.stream().mapToDouble { obj: Double -> obj }
            .sum()
        var value = 0.0

        for ((key, value1) in entries) {
            value += value1
            if (value >= target) {
                return key
            }
        }

        return entries.keys.first()
    }
}