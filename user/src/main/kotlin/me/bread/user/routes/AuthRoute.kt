package me.bread.user.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.header
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import me.bread.user.usecase.LoginRequest
import me.bread.user.usecase.LoginUseCase

fun Route.authRouting() = route("/api/auth") {
    authenticate("auth-jwt") {
        get("/private") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal?.payload?.getClaim("username")?.asString()
            call.respondText("Hello, $username! This is a protected route.")
        }
    }

    post("/login") {
        val token = with(call.receive<LoginRequest>()) {
            LoginUseCase.login(this)
        }

        call.response.header("Authorization", token)
        call.respondText("ok", status = HttpStatusCode.OK)
    }
}
