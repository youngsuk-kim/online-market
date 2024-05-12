package me.bread.product

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import me.bread.product.domain.entity.Product

class StockManageUseCaseTest : FeatureSpec(
    {
        feature("재고 관리") {
            scenario("제품 1번의 재고를 감소시킨다") {
                // Given
                val product = Product(1, 1)

                // When
                product.decrease()

                // Then
                product.stock shouldBe 0
            }
        }
    },
)
