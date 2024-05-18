package me.bread.user.usecase

import kotlinx.serialization.Serializable
import me.bread.user.service.UserResult

@Serializable
data class CreateUserRequest(val email: String, val password: String) {
    fun toDomain() = UserResult(this.email, this.password)
}
