package me.bread.order.domain.entity

class Payment(
    val id: Long? = null,
    val orderId: Long,
    val customerId: Long,
    val paymentItems: List<PaymentItem> = emptyList(),
) {
    companion object {
        fun create(orderId: Long, customerId: Long) = Payment(
            orderId = orderId,
            customerId = customerId,
        )
    }
}
