package me.bread.order.presentation.rest.request

import me.bread.order.domain.entity.OrderItem
import java.math.BigDecimal

data class PreorderRequest(
    val token: String,
    val orderItems: List<OrderItemRequest>,
) {

    data class OrderItemRequest(
        val productItemId: Long,
        val productName: String,
        val productPrice: BigDecimal,
        val quantity: Long,
    ) {
        fun toOrderItem(): OrderItem {
            return OrderItem(
                productItemId = this.productItemId,
                productName = this.productName,
                productPrice = this.productPrice,
                quantity = this.quantity,
            )
        }
    }
}
