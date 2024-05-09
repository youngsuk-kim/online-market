package me.bread.order.domain.entity

class Payment(
    val id: Long? = null,
    val orderId: Long,
    val buyerEmail: String,
    val buyerName: String,
    val paymentItems: List<PaymentItem> = emptyList(),
) {
    companion object {
        fun create(orderId: Long, customerEmail: String, customerName: String) = Payment(
            orderId = orderId,
            buyerEmail = customerEmail,
            buyerName = customerName,
        )
    }
}
