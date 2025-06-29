package me.bread.product.domain.entity

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import me.bread.product.infrastructure.jpa.builder.ProductBuilder
import me.bread.product.infrastructure.jpa.builder.ProductItemBuilder

class ProductTest : StringSpec({
    "decreaseStock 메서드는 지정된 아이템의 재고를 감소시켜야 한다" {
        // Given
        val itemId = "TEST ITEM ID"
        val initialStock = 10
        val productItem = ProductItemBuilder.anItem()
            .id(itemId)
            .stock(initialStock)
            .build()

        val product = ProductBuilder.aProduct()
            .id("TEST ITEM ID")
            .addProductItem(productItem)
            .build()

        // When
        product.decreaseStock(itemId)

        // Then
        val updatedStock = product.stock(itemId)
        updatedStock shouldBe (initialStock - 1)
    }

    "decreaseStock 메서드는 존재하지 않는 아이템 ID가 주어지면 아무 작업도 수행하지 않아야 한다" {
        // Given
        val existingItemId = "TEST ITEM ID"
        val nonExistingItemId = "NONEXISTENT ITEM ID"
        val initialStock = 10
        val productItem = ProductItemBuilder.anItem()
            .id(existingItemId)
            .stock(initialStock)
            .build()

        val product = ProductBuilder.aProduct()
            .id("TEST ITEM ID 1")
            .addProductItem(productItem)
            .build()

        // When
        product.decreaseStock(nonExistingItemId)

        // Then
        val stockAfterDecrease = product.stock(existingItemId)
        stockAfterDecrease shouldBe initialStock // 재고가 변경되지 않아야 함
    }

    "stock 메서드는 지정된 아이템의 재고를 반환해야 한다" {
        // Given
        val itemId = "TEST ITEM ID"
        val initialStock = 10
        val productItem = ProductItemBuilder.anItem()
            .id(itemId)
            .stock(initialStock)
            .build()

        val product = ProductBuilder.aProduct()
            .id("TEST ITEM ID 1")
            .addProductItem(productItem)
            .build()

        // When
        val result = product.stock(itemId)

        // Then
        result shouldBe initialStock
    }

    "stock 메서드는 존재하지 않는 아이템 ID가 주어지면 null을 반환해야 한다" {
        // Given
        val existingItemId = "TEST ITEM ID"
        val nonExistingItemId = "NONEXISTENT ITEM ID"
        val productItem = ProductItemBuilder.anItem()
            .id(existingItemId)
            .build()

        val product = ProductBuilder.aProduct()
            .id("TEST ITEM ID 1")
            .addProductItem(productItem)
            .build()

        // When
        val result = product.stock(nonExistingItemId)

        // Then
        result shouldBe null
    }
})
