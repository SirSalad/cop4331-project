package edu.ucf.cop4331project.controller

import com.google.inject.Inject
import edu.ucf.cop4331project.storage.User
import edu.ucf.cop4331project.common.container.Container
import edu.ucf.cop4331project.container.RouteMapping
import edu.ucf.cop4331project.container.Router
import edu.ucf.cop4331project.storage.UserService
import edu.ucf.cop4331project.util.JWT
import edu.ucf.cop4331project.util.jwtSecret
import edu.ucf.cop4331project.util.sign
import edu.ucf.cop4331project.util.withUserContext
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

@Container
@Suppress("unused")
class AuthenticationController @Inject constructor(private val application: Application, private val userService: UserService) : Router {

    override val routeMapping = "/auth"

    @RouteMapping
    fun Route.login() = post("/login") {
        val request = call.receive<User>()

        val user = userService.loadByUsernameAndPassword(request.username, request.password) ?: run {
            call.respond(status = HttpStatusCode.Unauthorized, "Invalid credentials")
            return@post
        }

        call.respond(status = HttpStatusCode.OK, application.JWT().withUserContext(user).sign(application.jwtSecret))
    }

    @RouteMapping
    fun Route.signup() = post("/signup") {
        val request = call.receive<User>()

        if (userService.loadByUsername(request.username) != null) {
            call.respond(status = HttpStatusCode.BadRequest, "User already exists")
            return@post
        }

        // Clone and re-set defaults in case of tampering
        val newUser = request.copy(coins = 0)
        userService.update(newUser)

        call.respond(status = HttpStatusCode.Created, "User created")
    }

    @RouteMapping
    fun Route.refresh() = authenticate {
        post("/refresh") {
            val principal = call.principal<JWTPrincipal>() ?: run {
                call.respond(status = HttpStatusCode.Unauthorized, "Invalid token")
                return@post
            }

            val user = userService.loadByUsername(principal.payload.getClaim("username").asString()) ?: run {
                call.respond(status = HttpStatusCode.Unauthorized, "Invalid token")
                return@post
            }

            val token = application.JWT()
                .withUserContext(user)
                .sign(application.jwtSecret)

            call.respond(status = HttpStatusCode.OK, token)
        }
    }
}