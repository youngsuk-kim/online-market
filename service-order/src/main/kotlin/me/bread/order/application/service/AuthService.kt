package me.bread.order.application.service

import me.bread.order.application.external.AuthApi
import org.springframework.stereotype.Component

@Component
class AuthService(
    private val authApi: AuthApi,
) {
    suspend fun getCustomerId(token: String) = authApi.fetchCustomerIdBy(token)
}
