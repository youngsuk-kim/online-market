package me.bread.order.application.usecase

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import me.bread.order.application.service.AuthService
import me.bread.order.application.service.DeliveryService
import me.bread.order.application.service.ProductService
import me.bread.order.domain.entity.Order
import me.bread.order.domain.entity.Payment
import me.bread.order.domain.vo.PhoneNumber
import me.bread.order.infrastructure.external.AuthFakeApi
import me.bread.order.infrastructure.external.DeliveryFakeApi
import me.bread.order.infrastructure.external.ProductFakeApi
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
                val order = Order.request(orderItems)

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

            scenario("관리자는 손님의 배송지가 도서 산간 지역인지 확인한다") {
                // Given
                val postNum = "363"

                // When
                val isSurChargeArea = DeliveryService(DeliveryFakeApi()).isSurChargeArea(postNum)

                // Then
                isSurChargeArea shouldBe true
            }

            scenario("관리자는 주문 금액을 산출한다") {
                // Given
                val orderItems = orderItems()

                // When
                val charge = Order.request(orderItems).charge()

                // Then
                charge shouldBe BigDecimal(34_000)
            }

            scenario("관리자는 결제 정보를 받아서 저장한다") {
                // Given
                val orderId = 1L
                val customerName = "김영석"
                val customerEmail = "example@gmail.com"

                // When
                val payment = Payment.create(orderId, customerEmail, customerName)

                // Then
                orderId shouldBe 1L
                customerName shouldBe "김영석"
                customerEmail shouldBe "example@gmail.com"
            }
        }
    },
)
