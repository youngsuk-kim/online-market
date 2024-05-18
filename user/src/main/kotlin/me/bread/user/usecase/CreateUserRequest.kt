package me.bread.user.usecase

import me.bread.user.service.UserResult

data class CreateUserRequest(val email: String, val password: String) {
    fun toDomain() = UserResult(this.email, this.password)
}
