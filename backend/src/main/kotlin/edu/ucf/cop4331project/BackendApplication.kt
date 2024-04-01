package edu.ucf.cop4331project

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Key
import com.google.inject.TypeLiteral
import com.zaxxer.hikari.HikariDataSource
import edu.ucf.cop4331project.common.container.ContainerModule
import edu.ucf.cop4331project.container.RouteMapping
import edu.ucf.cop4331project.container.Router
import edu.ucf.cop4331project.util.jwtIssuer
import edu.ucf.cop4331project.util.jwtSecret
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Closeable
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.hasAnnotation

object BackendApplication {

    // Initialization is delayed until we have an Application context
    private lateinit var injector: Injector

    @JvmStatic
    fun main(args: Array<String>): Unit = EngineMain.main(args)

    @Suppress("unused") // Referenced in application.yaml
    fun Application.main() {
        injector = Guice.createInjector(ContainerModule(), BackendApplicationModule(this))

        install(ContentNegotiation) {
            json()
        }

        install(Authentication) {
            jwt {
                verifier {
                    JWT.require(Algorithm.HMAC256(this@main.jwtSecret))
                        .withIssuer(this@main.jwtIssuer)
                        .build()
                }

                validate { credential ->
                    if (credential.payload.getClaim("username").asString().isNotEmpty()) {
                        JWTPrincipal(credential.payload)
                    } else {
                        null
                    }
                }

                challenge { _, _ ->
                    call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                }
            }
        }

        // Container registrar - enable
        val containers = injector.getInstance(Key.get(object : TypeLiteral<Set<Any>>() {}))
        val database = injector.getInstance(Database::class.java)
        containers.forEach {
            if (it is Table) {
                transaction(database) {
                    SchemaUtils.createMissingTablesAndColumns(it)
                }
            }

            if (it is Router) {
                routing {
                    route(it.routeMapping) {
                        it::class.declaredFunctions
                            .filter { func -> func.hasAnnotation<RouteMapping>() }
                            .forEach { func -> func.call(it, this) }
                    }
                }
            }
        }

        // Container registrar - Disable
        Runtime.getRuntime().addShutdownHook(Thread {
            containers
                .filterIsInstance<Closeable>()
                .forEach(Closeable::close)
            injector.getInstance(HikariDataSource::class.java).close()
        })
    }
}
