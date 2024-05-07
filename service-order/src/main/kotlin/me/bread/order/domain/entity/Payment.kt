package me.bread.order.domain.entity

class Payment(
    val id: Long? = null,
    val orderId: Long,
    val customerEmail: String,
    val customerName: String,
) {
    companion object {
        fun create(orderId: Long, customerEmail: String, customerName: String) = Payment(
            orderId = orderId,
            customerEmail = customerEmail,
            customerName = customerName,
        )
    }
}
