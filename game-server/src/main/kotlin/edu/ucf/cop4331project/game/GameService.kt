package edu.ucf.cop4331project.game

import com.google.inject.Inject
import com.google.inject.Singleton
import edu.ucf.cop4331project.common.config.ConfigManager
import edu.ucf.cop4331project.common.container.Container
import edu.ucf.cop4331project.config.GameConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Closeable
import java.util.TreeMap
import kotlin.time.Duration.Companion.minutes

@Singleton
@Container
class GameService @Inject constructor(private val configmanager: ConfigManager) : Closeable {

    lateinit var currentGame: Game
    var time: Long = 0
    val previousGames: MutableMap<Long, Game> = TreeMap()

    private val gameJob: Job = CoroutineScope(Dispatchers.Default).launch {
        while (true) {
            val horses = configmanager.getConfig(GameConfig::class.java).horses.shuffled().take(8)
            val track = configmanager.getConfig(GameConfig::class.java).tracks.random()
            val game = Game(horses.toSet(), track)

            time = 10.minutes.inWholeMilliseconds
            currentGame = game

            previousGames[System.currentTimeMillis()] = game
            delay(10.minutes)
        }
    }
    private val timeTickerJob: Job = CoroutineScope(Dispatchers.Default).launch {
        while (true) {
            delay(1000)
            time -= 1000
        }
    }

    override fun close() {
        gameJob.cancel()
        timeTickerJob.cancel()
    }
}