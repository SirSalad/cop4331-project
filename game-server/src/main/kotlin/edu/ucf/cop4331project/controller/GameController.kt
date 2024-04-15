package edu.ucf.cop4331project.controller

import com.google.inject.Inject
import edu.ucf.cop4331project.common.container.Container
import edu.ucf.cop4331project.container.RouteMapping
import edu.ucf.cop4331project.container.Router
import edu.ucf.cop4331project.game.GameService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse.BodyHandlers

@Container
class GameController @Inject constructor(private val gameService: GameService) : Router {

    override val routeMapping = "/game"
    private val httpClient = java.net.http.HttpClient.newHttpClient()

    @RouteMapping
    fun Route.timeRemaining() = get("/time") {
        call.respond(status = HttpStatusCode.OK, gameService.time)
    }

    @RouteMapping
    fun Route.currentGame() = get("/currentgame") {
        call.respond(status = HttpStatusCode.OK, gameService.currentGame)
    }

    @RouteMapping
    fun Route.previousGames() = get("/previousgames") {
        call.respond(status = HttpStatusCode.OK, gameService.previousGames)
    }

    @RouteMapping
    fun Route.bet() = authenticate {
        post("/bet") {
            val user = call.principal<JWTPrincipal>()
            val amount = call.request.queryParameters["amount"]?.toIntOrNull() ?: run {
                call.respond(status = HttpStatusCode.BadRequest, "Invalid amount")
                return@post
            }

            val request = java.net.http.HttpRequest.newBuilder(URI.create("http://localhost:8080/transaction"))
                .header("amount", amount.toString())
                .header("user", user?.payload?.getClaim("username")?.asString())
                .POST(BodyPublishers.noBody())
                .build()

            val response = httpClient.send(request, BodyHandlers.ofString())

            call.respond(status = HttpStatusCode.OK, response)
        }
    }
}