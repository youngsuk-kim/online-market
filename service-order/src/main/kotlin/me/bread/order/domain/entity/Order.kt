package me.bread.order.domain.entity

class Order(
    private val orderItems: List<OrderItem>,
) {

    companion object {
        fun request(orderItems: List<OrderItem>) = Order(orderItems)
    }

    fun totalQuantity() = this.orderItems.sumOf { orderItem -> orderItem.quantity }

    fun charge() = this.orderItems.sumOf { orderItem -> orderItem.productPrice }
}
