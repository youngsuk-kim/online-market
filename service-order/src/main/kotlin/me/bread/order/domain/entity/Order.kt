package me.bread.order.domain.entity

import me.bread.order.domain.enums.OrderStatus
import me.bread.order.domain.enums.OrderStatus.PRE_ORDER

class Order(
    val orderItems: List<OrderItem>,
    val status: OrderStatus,
) {

    companion object {
        fun preorder(orderItems: List<OrderItem>) = Order(orderItems, PRE_ORDER)
    }

    fun totalQuantity() = this.orderItems.sumOf { orderItem -> orderItem.quantity }

    fun charge() = this.orderItems.sumOf { orderItem -> orderItem.productPrice }
}
