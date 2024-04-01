package edu.ucf.cop4331project.controller

import com.google.inject.Inject
import edu.ucf.cop4331project.common.container.Container
import edu.ucf.cop4331project.container.RouteMapping
import edu.ucf.cop4331project.container.Router
import edu.ucf.cop4331project.storage.UserRepository
import edu.ucf.cop4331project.util.validTransactionSources
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.plugins.origin
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

@Container
@Suppress("unused")
class TransactionController @Inject constructor(private val userRepository: UserRepository) : Router {

    @RouteMapping
    fun Route.transact() = post("/transaction") {
        if (call.request.origin.remoteHost !in application.validTransactionSources) {
            call.respond(status = HttpStatusCode.Unauthorized, "Invalid transaction source")
            return@post
        }

        val amount = call.request.queryParameters["amount"]?.toIntOrNull() ?: run {
            call.respond(status = HttpStatusCode.BadRequest, "Invalid amount")
            return@post
        }

        val context = call.request.queryParameters["user"] ?: run {
            call.respond(status = HttpStatusCode.BadRequest, "No user context provided")
            return@post
        }

        val user = userRepository.findByUsername(context) ?: run {
            call.respond(status = HttpStatusCode.BadRequest, "No such user for $context")
            return@post
        }

        if (user.coins + amount < 0) {
            call.respond(status = HttpStatusCode.BadRequest, "Insufficient funds")
            return@post
        }

        user.coins += amount
        userRepository.update(user)

        call.respond(status = HttpStatusCode.OK, "Transaction successful")
    }
}