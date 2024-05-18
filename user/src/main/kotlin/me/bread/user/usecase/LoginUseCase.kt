package me.bread.user.usecase

import me.bread.user.service.AuthService

object LoginUseCase {

    fun login(request: LoginRequest) = AuthService.authenticate(request.toDomain())
}
