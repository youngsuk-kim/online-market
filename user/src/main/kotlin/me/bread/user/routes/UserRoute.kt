package me.bread.user.routes

import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import me.bread.user.entity.User
import me.bread.user.service.UserService
import me.bread.user.usecase.SignInRequest
import me.bread.user.usecase.SignInUseCase

fun Route.userRouting() = route("/api/users") {
    post {
        with(call.receive<SignInRequest>()) { SignInUseCase.register(this) }
    }

    get {
        call.request.queryParameters["email"]?.let { email ->
            call.respond<User>(UserService.findByEmail(email))
        }
    }
}
