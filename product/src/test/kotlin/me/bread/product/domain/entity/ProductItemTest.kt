package me.bread.product.domain.entity

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import me.bread.product.infrastructure.jpa.builder.ProductItemBuilder

class ProductItemTest : StringSpec({
    "decrease 메서드는 재고를 1 감소시켜야 한다" {
        // Given
        val initialStock = 10
        val productItem = ProductItemBuilder.anItem()
            .stock(initialStock)
            .build()

        // When
        productItem.decrease()

        // Then
        productItem.stock shouldBe (initialStock - 1)
    }

    "decrease 메서드는 재고가 0일 때도 감소시킬 수 있어야 한다" {
        // Given
        val initialStock = 0
        val productItem = ProductItemBuilder.anItem()
            .stock(initialStock)
            .build()

        // When
        productItem.decrease()

        // Then
        productItem.stock shouldBe -1 // 재고가 음수가 될 수 있음 (비즈니스 요구사항에 따라 다를 수 있음)
    }

    "decrease 메서드는 여러 번 호출하면 그만큼 재고를 감소시켜야 한다" {
        // Given
        val initialStock = 10
        val decreaseCount = 3
        val productItem = ProductItemBuilder.anItem()
            .stock(initialStock)
            .build()

        // When
        repeat(decreaseCount) {
            productItem.decrease()
        }

        // Then
        productItem.stock shouldBe (initialStock - decreaseCount)
    }
})
