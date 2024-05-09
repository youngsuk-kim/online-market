package me.bread.order.infrastructure.external

import me.bread.order.application.annotation.Local
import me.bread.order.application.external.PaymentApi
import me.bread.order.application.model.TossPayment
import me.bread.order.domain.entity.Order.Companion.testOrderDateTime
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Local
@Component
object FakePaymentApi : PaymentApi {
    override fun execute(orderId: Long, paymentId: String, amount: BigDecimal): TossPayment {
        return TossPayment(
            orderId = orderId,
            amount = amount,
            requestedAt = testOrderDateTime(),
        )
    }
}
