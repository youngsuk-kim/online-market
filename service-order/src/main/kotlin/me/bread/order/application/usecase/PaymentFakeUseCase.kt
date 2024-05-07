package me.bread.order.application.usecase

import me.bread.order.application.annotation.Local
import me.bread.order.infrastructure.external.MockPaymentApi
import org.springframework.stereotype.Component

@Local
@Component
class PaymentFakeUseCase {
    fun execute() {
        MockPaymentApi.sendPaymentRequest(
            orderId = "test-order-id",
            amount = "1.0",
            paymentKey = "test-key",
            successUrl = "http://localhost:8080/api/payments/confirm",
        )
    }
}
