package me.bread.order.application.usecase

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import me.bread.order.application.service.AuthService
import me.bread.order.application.service.DeliveryService
import me.bread.order.application.service.ProductService
import me.bread.order.domain.entity.Order
import me.bread.order.domain.entity.Payment
import me.bread.order.domain.vo.PhoneNumber
import me.bread.order.infrastructure.external.test.AuthFakeApi
import me.bread.order.infrastructure.external.test.DeliveryFakeApi
import me.bread.order.infrastructure.external.test.ProductFakeApi
import me.bread.order.orderItems
import org.junit.jupiter.api.assertDoesNotThrow
import java.math.BigDecimal

class PreorderUseCaseTest : FeatureSpec(
    {

        feature("주문 준비") {
            scenario("점원이 관리자에게 주문을 요청한다") {
                // Given
                val orderItems = orderItems()

                // When
                val order = Order.preorder(orderItems = orderItems, 1L)

                // Then
                order.totalQuantity() shouldBe 4
            }

            scenario("관리자는 토큰 정보를 통해 손님의 신원을 파악한다") {
                // Given
                val token = "customer-token"

                // When
                val customerId = AuthService(
                    AuthFakeApi(),
                )
                    .getCustomerId(token)

                // Then
                customerId shouldBe 1L
            }

            scenario("관리자는 주문한 물품의 재고가 충분한지 확인한다") {
                // Given When Then
                assertDoesNotThrow {
                    ProductService(ProductFakeApi()).verifyStock(orderItems())
                }
            }

            scenario("관리자는 손님의 핸드폰 번호의 유효성을 검사한다") {
                // Given When
                val phoneNumber = PhoneNumber("01030202322")

                // Then
                phoneNumber.number shouldBe "01030202322"
            }

            scenario("관리자는 손님의 배송지가 도서 산간 지역인지에 따라 배송비가 달라진다") {
                // Given
                val postNum = "363"

                // When
                val isSurChargeArea = DeliveryService(DeliveryFakeApi()).calculateSurcharge(postNum)

                // Then
                isSurChargeArea shouldBe 3000
            }

            scenario("관리자는 주문 금액을 산출한다") {
                // Given
                val orderItems = orderItems()

                // When
                val charge = Order.preorder(orderItems = orderItems, 1L).charge()

                // Then
                charge shouldBe BigDecimal(34_000)
            }

            scenario("관리자는 결제 정보를 받아서 저장한다") {
                // Given
                val orderId = 1L
                val customerId = 1L

                // When
                val payment = Payment.create(orderId, customerId)

                // Then
                payment.orderId shouldBe 1L
                payment.customerId shouldBe 1L
            }
        }
    },
)
