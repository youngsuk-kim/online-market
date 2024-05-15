package me.bread.product

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import me.bread.product.domain.entity.Product
import me.bread.product.domain.entity.ProductItem
import me.bread.product.domain.enums.ProductOption
import java.math.BigDecimal

class StockManageUseCaseTest : FeatureSpec(
    {
        feature("재고 관리") {
            scenario("상품을 구매시 재고 감소") {
                // Given
                val product = Product(
                    1,
                    "나이키 1번 모델",
                    BigDecimal(1000),
                    mutableListOf(
                        ProductItem(
                            id = 1,
                            optionKey = ProductOption.COLOR,
                            optionValue = "빨간색",
                            stock = 1,
                        ),
                    ),
                )

                // When
                product.decreaseStock(1L)

                // Then
                product.stock(1L) shouldBe 0
            }
        }
    },
)
