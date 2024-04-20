package edu.ucf.cop4331project.controller

import com.google.inject.Inject
import edu.ucf.cop4331project.common.container.Container
import edu.ucf.cop4331project.container.RouteMapping
import edu.ucf.cop4331project.container.Router
import edu.ucf.cop4331project.storage.UserRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

@Container
@Suppress("unused")
class LeaderboardController @Inject constructor(private val userRepository: UserRepository) : Router {

    @RouteMapping
    fun Route.leaderboard() = get("/leaderboard") {
        val users = userRepository.all()
            .sortedByDescending { it.coins }
            .map { LeaderboardUser(it.username, it.coins, if (it.isPremium) "Yes" else "No") }
            .toList()

        call.respond(status = HttpStatusCode.OK, users)
    }

    data class LeaderboardUser(val username: String, val coins: Int, val isPremium: String)
}