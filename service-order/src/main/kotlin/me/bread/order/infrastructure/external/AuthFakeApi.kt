package me.bread.order.infrastructure.external

import me.bread.order.application.annotation.Local
import me.bread.order.application.external.AuthApi
import org.springframework.stereotype.Component

@Local
@Component
class AuthFakeApi : AuthApi {
    override fun fetchCustomerIdBy(token: String): Long = 1
}
