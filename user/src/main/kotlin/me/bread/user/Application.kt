package me.bread.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import me.bread.user.database.config.DBConnectionPool
import me.bread.user.database.tables.UserTable
import me.bread.user.routes.authRouting
import me.bread.user.routes.userRouting
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    DBConnectionPool.init()

    transaction {
        SchemaUtils.create(UserTable)
    }

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureStatusPages()
    configureSecurity()
    configureRouting()

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            setDefaultPrettyPrinter(
                DefaultPrettyPrinter().apply {
                    indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                    indentObjectsWith(DefaultIndenter("  ", "\n"))
                },
            )
        }
    }
}

fun Application.configureSecurity() {
    install(Authentication) {
        jwt("auth-jwt") { // Name your configuration
            realm = "Access to 'private' routes"
            verifier(
                JWT
                    .require(Algorithm.HMAC256("your-secret")) // Use your secret
                    .withAudience("your-audience") // Specify your audience if applicable
                    .withIssuer("your-issuer") // Specify your issuer if applicable
                    .build(),
            )
            validate { credential ->
                if (credential.payload.audience.contains("your-audience") &&
                    credential.issuer == "your-issuer"
                ) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to (cause.localizedMessage ?: "Unknown error")),
            )
        }
        exception<IllegalArgumentException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to cause.localizedMessage))
        }
    }
}

fun Application.configureRouting() {
    routing {
        userRouting()
        authRouting()
    }
}
