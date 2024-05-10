package me.bread.order.presentation.rest.request

import me.bread.order.domain.entity.OrderItem
import java.math.BigDecimal

data class PreorderRequest(
    val token: String,
    val orderItems: List<OrderItemRequest>,
) {

    data class OrderItemRequest(
        val productId: Long,
        val productName: String,
        val productPrice: Long,
        val quantity: Long,
    ) {
        fun toOrderItem(): OrderItem {
            return OrderItem(
                productId = this.productId,
                productName = this.productName,
                productPrice = BigDecimal(this.productPrice),
                quantity = this.quantity,
            )
        }
    }
}
