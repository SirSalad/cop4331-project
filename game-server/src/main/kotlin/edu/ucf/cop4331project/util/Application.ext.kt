package edu.ucf.cop4331project.util

import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import edu.ucf.cop4331project.common.storage.User
import io.ktor.server.application.Application
import java.time.Instant
import java.time.temporal.ChronoUnit

// region config extensions
val Application.jwtSecret
    get() = environment.config.property("jwt.secret").getString()

val Application.jwtIssuer
    get() = environment.config.property("jwt.issuer").getString()

val Application.validTransactionSources
    get() = environment.config.property("valid-transaction-sources").getList()

val Application.dataSourceJdbcUrl
    get() = environment.config.property("datasource.jdbc-url").getString()

val Application.dataSourceUsername
    get() = environment.config.property("datasource.username").getString()

val Application.dataSourcePassword
    get() = environment.config.property("datasource.password").getString()
// endregion

// region jwt extensions
fun Application.JWT(): JWTCreator.Builder =
    com.auth0.jwt.JWT.create()
        .withIssuer(jwtIssuer)
        .withExpiresAt(Instant.now().plus(10, ChronoUnit.MINUTES))

fun JWTCreator.Builder.withUserContext(user: User): JWTCreator.Builder =
    withClaim("username", user.username)
        .withClaim("coins", user.coins)
        .withClaim("premium", user.isPremium)

fun JWTCreator.Builder.sign(secret: String): String =
    sign(Algorithm.HMAC256(secret))
// endregion