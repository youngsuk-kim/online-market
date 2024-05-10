package me.bread.order.application.service

import me.bread.order.application.external.DeliveryApi
import org.springframework.stereotype.Component

private const val DELIVERY_SURCHARGE_AREA_FEE = 3000L
private const val NO_CHARGE = 0L

@Component
class DeliveryService(private val deliveryApi: DeliveryApi) {

    fun calculateSurcharge(postNum: String): Long {
        if (deliveryApi.fetchSurChargeArea().any { postNum == it }) {
            return DELIVERY_SURCHARGE_AREA_FEE
        }

        return NO_CHARGE
    }
}
