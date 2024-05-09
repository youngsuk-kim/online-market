package me.bread.order.domain.entity

import me.bread.order.domain.enums.OrderStatus
import me.bread.order.domain.enums.OrderStatus.PAYED
import me.bread.order.domain.enums.OrderStatus.PRE_ORDER
import java.math.BigDecimal

class Order(
    private val id: Long? = null,
    var orderItems: List<OrderItem>,
    var status: OrderStatus,
) {

    companion object {
        fun preorder(orderItems: List<OrderItem>) = Order(
            orderItems = orderItems,
            status = PRE_ORDER,
        )

        private const val TEST_ORDER_ID: Long = 1

        fun testPreorder(orderId: Long = TEST_ORDER_ID, orderItems: List<OrderItem>) = Order(
            id = orderId,
            orderItems = orderItems,
            status = PRE_ORDER,
        )
    }

    fun totalQuantity() = this.orderItems.sumOf { orderItem -> orderItem.quantity }

    fun charge() = this.orderItems.sumOf { orderItem -> orderItem.productPrice }

    fun validatePayment(orderId: Long, charge: BigDecimal) {
        require(orderId == id) { "Order id should be $id" }
        require(charge == charge()) { "Charge should be ${charge()}" }
    }

    fun payed() {
        this.status = PAYED
    }
}
