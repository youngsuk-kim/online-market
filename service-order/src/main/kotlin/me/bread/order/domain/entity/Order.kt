package me.bread.order.domain.entity

import me.bread.order.domain.enums.OrderStatus
import me.bread.order.domain.enums.OrderStatus.PAYED
import me.bread.order.domain.enums.OrderStatus.PRE_ORDER
import java.math.BigDecimal

class Order(
    val id: Long? = null,
    var customerId: Long,
    var orderItems: List<OrderItem>,
    var status: OrderStatus,
) {

    companion object {
        fun preorder(orderItems: List<OrderItem>, customerId: Long) = Order(
            orderItems = orderItems,
            status = PRE_ORDER,
            customerId = customerId,
        )
    }

    fun totalQuantity() = this.orderItems.sumOf { orderItem -> orderItem.quantity }

    fun charge() = this.orderItems.sumOf { orderItem -> orderItem.productPrice }

    fun validatePayment(orderId: Long, amount: Long) {
        require(orderId == id) { "Order id should be $id" }
        require(BigDecimal(amount) == charge()) { "Charge should be ${charge()} but got $amount" }
    }

    fun payed() {
        this.status = PAYED
    }
}
