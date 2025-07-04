package me.bread.order.infrastructure.external.test

import me.bread.order.application.annotation.Local
import me.bread.order.application.annotation.LocalDev
import me.bread.order.application.external.PaymentApi
import me.bread.order.application.model.TossPayment
import org.springframework.stereotype.Component

@Local
@LocalDev
@Component
object FakePaymentApi : PaymentApi {
    override fun execute(orderId: Long, paymentId: String, amount: Long): TossPayment {
        return TossPayment(
            orderId = orderId,
            amount = amount,
        )
    }
}
