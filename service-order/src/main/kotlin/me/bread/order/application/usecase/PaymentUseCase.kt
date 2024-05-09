package me.bread.order.application.usecase

import me.bread.order.application.external.PaymentApi
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PaymentUseCase(
    private val paymentApi: PaymentApi,
) {
    fun execute(orderId: Long, paymentKey: String, amount: BigDecimal) {
        val tossPayment = paymentApi.execute(orderId, paymentKey, amount)
    }
}
