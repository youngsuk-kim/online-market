package me.bread.user.usecase

import me.bread.user.service.UserResult

data class LoginRequest(val username: String, val password: String) {
    fun toDomain() = UserResult(username, password)
}
