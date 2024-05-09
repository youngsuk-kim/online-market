package me.bread.order.application.usecase

import me.bread.order.application.external.PaymentApi
import me.bread.order.infrastructure.external.MockPaymentApi
import org.springframework.stereotype.Component

@Component
class PaymentUseCase(
    private val paymentApi: PaymentApi,
) {
    fun execute() {
        MockPaymentApi.execute(
            orderId = "test-order-id",
            amount = "1.0",
            paymentKey = "test-key",
            successUrl = "http://localhost:8080/api/payments/confirm",
        )
    }
}
