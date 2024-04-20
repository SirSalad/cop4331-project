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
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.util.TreeMap
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.minutes

@Singleton
@Container
class GameService @Inject constructor(private val configmanager: ConfigManager) : Closeable {

    lateinit var currentGame: Game
    var time: Long = 0
    val previousGames: MutableMap<Long, Game> = TreeMap()
    val bets = ConcurrentHashMap<String, Map<String, Int>>()

    private val gameJob: Job = CoroutineScope(Dispatchers.Default).launch {
        while (true) {
            val horses = configmanager.getConfig(GameConfig::class.java).horses.shuffled().take(8)
            val track = configmanager.getConfig(GameConfig::class.java).tracks.random()
            val game = Game(horses.toSet(), track)

            bets.forEach { (user, bets) ->
                if (game.winner.name in bets.keys) {
                    val amount = bets[game.winner.name]!!

                    val request = HttpRequest.newBuilder(URI.create("http://localhost:8080/transaction"))
                        .header("amount", amount.toString())
                        .header("user", user)
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .build()
                    launch {
                        HttpClient.newHttpClient().send(request, BodyHandlers.discarding())
                    }
                }
            }

            time = 10.minutes.inWholeMilliseconds
            currentGame = game

            previousGames[System.currentTimeMillis()] = game
            bets.clear()
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