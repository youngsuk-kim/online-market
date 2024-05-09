package me.bread.order.domain.entity

import me.bread.order.domain.enums.OrderStatus
import me.bread.order.domain.enums.OrderStatus.PAYED
import me.bread.order.domain.enums.OrderStatus.PRE_ORDER
import java.math.BigDecimal
import java.time.LocalDateTime

class Order(
    private val id: Long? = null,
    private val createdAt: LocalDateTime = LocalDateTime.now(),
    val orderItems: List<OrderItem>,
    var status: OrderStatus,
) {

    companion object {
        fun preorder(orderItems: List<OrderItem>) = Order(
            orderItems = orderItems,
            status = PRE_ORDER,
        )

        const val TEST_ORDER_ID: Long = 1

        fun testPreorder(
            orderId: Long = TEST_ORDER_ID,
            orderItems: List<OrderItem>,
            createdAt: LocalDateTime = testOrderDateTime(),
        ) = Order(
            id = orderId,
            orderItems = orderItems,
            status = PRE_ORDER,
            createdAt = createdAt,
        )

        fun testOrderDateTime(): LocalDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 0)
    }

    fun totalQuantity() = this.orderItems.sumOf { orderItem -> orderItem.quantity }

    fun charge() = this.orderItems.sumOf { orderItem -> orderItem.productPrice }

    fun validatePayment(orderId: Long, charge: BigDecimal, requestAt: LocalDateTime) {
        require(orderId == id) { "Order id should be $id" }
        require(charge == charge()) { "Charge should be ${charge()}" }
        require(createdAt == requestAt) { "CreatedAt should be $requestAt" }
    }

    fun payed() {
        this.status = PAYED
    }
}
