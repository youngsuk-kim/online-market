package me.bread.order.application.usecase

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import me.bread.order.domain.entity.Order
import me.bread.order.domain.enums.OrderStatus
import me.bread.order.infrastructure.external.test.FakePaymentApi
import me.bread.order.orderItems
import org.junit.jupiter.api.assertDoesNotThrow
import java.math.BigDecimal

class PaymentUseCaseTest : FeatureSpec(
    {
        feature("결제") {

            scenario("토스 페이먼츠에 결제 승인을 요청한다") {
                // Given When
                val tossPayment = FakePaymentApi.execute(1L, "test-id", 10_000)

                // Then
                tossPayment.amount shouldBe BigDecimal.valueOf(10000)
                tossPayment.orderId shouldBe 1
            }

            scenario("결제 정보를 주문 정보를 통해 검증한다") {
                // Given
                val order = Order.preorder(orderItems = orderItems(), 1L)
                val tossPayment = FakePaymentApi.execute(1L, "test-id", 10_000)

                // When Then
                assertDoesNotThrow {
                    order.validatePayment(
                        tossPayment.orderId,
                        tossPayment.amount,
                    )
                }
            }

            scenario("주문을 결제 완료로 변경한다") {
                // Given
                val order = Order.preorder(orderItems = orderItems(), customerId = 1L)

                // When
                order.payed()

                // Then
                order.status shouldBe OrderStatus.PAYED
            }
        }
    },
)
