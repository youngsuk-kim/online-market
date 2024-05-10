package me.bread.order.infrastructure.external

import me.bread.order.application.annotation.Local
import me.bread.order.application.annotation.LocalDev
import me.bread.order.application.external.AuthApi
import org.springframework.stereotype.Component

@Local
@LocalDev
@Component
class AuthFakeApi : AuthApi {
    override suspend fun fetchCustomerIdBy(token: String): Long = 1
}
