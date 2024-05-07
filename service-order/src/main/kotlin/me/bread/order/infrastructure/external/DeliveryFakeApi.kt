package me.bread.order.infrastructure.external

import me.bread.order.application.annotation.Local
import me.bread.order.application.external.DeliveryApi
import org.springframework.stereotype.Component

@Local
@Component
class DeliveryFakeApi : DeliveryApi {
    override fun fetchSurChargeArea(): List<String> {
        return listOf("363")
    }
}
