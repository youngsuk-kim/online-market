package me.bread.product.application.service

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.application.service.manager.CustomTransactionManager
import me.bread.product.application.service.manager.LockManager
import me.bread.product.domain.entity.Product
import me.bread.product.domain.enums.ProductOption
import me.bread.product.infrastructure.jpa.builder.ProductBuilder
import me.bread.product.infrastructure.jpa.builder.ProductItemBuilder

class StockServiceTest : StringSpec({
    // 목 의존성
    val lockManager = mockk<LockManager>(relaxed = true)
    val productService = mockk<ProductService>()
    val customTransactionManager = mockk<CustomTransactionManager>()

    // 테스트할 서비스 생성
    val stockService = StockService(lockManager, productService, customTransactionManager)

    // 테스트 데이터
    val productId = 1L
    val itemId = 2L

    // 재고가 있는 상품이 주어진 경우
    "상품에 재고가 있는 경우, 재고를 감소시키면 재고가 감소되어야 한다" {
        // Given
        val productItem = ProductItemBuilder.anItem()
            .id(itemId)
            .optionKey(ProductOption.COLOR)
            .optionValue("빨강")
            .stock(10)
            .build()

        val product = ProductBuilder.aProduct()
            .id(productId)
            .name("테스트 상품")
            .price("100.00")
            .addProductItem(productItem)
            .build()

        // 목 동작 설정
        every { productService.findById(productId) } returns product
        every { productService.save(any<Product>()) } returns Unit

        // When
        stockService.decreaseStock(productId, itemId)

        // Then
        productItem.stock shouldBe 9
        verify { productService.save(product) }
    }
})
