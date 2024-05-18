package me.bread.user.usecase

import me.bread.user.service.UserService

object UserRegisterUseCase {

    fun register(request: CreateUserRequest) {
        UserService.save(request.toDomain())
    }
}
