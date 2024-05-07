package me.bread.order.application.service

import me.bread.order.application.external.DeliveryApi
import me.bread.order.domain.entity.Order
import me.bread.order.domain.entity.OrderItem
import org.springframework.stereotype.Component

@Component
class OrderService(
    private val deliveryApi: DeliveryApi,
) {

    fun preorder(orderItem: List<OrderItem>) {
        Order.request(orderItem)
    }

    fun calculateTotalCharge() {
        deliveryApi.fetchSurChargeArea()
    }
}
