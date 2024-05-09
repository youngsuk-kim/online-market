package me.bread.order.infrastructure.external

import me.bread.order.application.annotation.Local
import me.bread.order.application.external.PaymentApi
import me.bread.order.application.model.TossPayment
import me.bread.order.domain.entity.Order.Companion.TEST_ORDER_ID
import me.bread.order.domain.entity.Order.Companion.testOrderDateTime
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Local
@Component
object FakePaymentApi : PaymentApi {
    override fun execute(): TossPayment {
        return TossPayment(
            orderId = TEST_ORDER_ID,
            amount = BigDecimal(34_000),
            requestedAt = testOrderDateTime(),
        )
    }
}
