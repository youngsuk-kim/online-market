package me.bread.order.application.external

import me.bread.order.application.model.TossPayment
import java.math.BigDecimal

interface PaymentApi {
    fun execute(orderId: Long, paymentId: String, amount: BigDecimal): TossPayment
}
