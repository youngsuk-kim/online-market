package me.bread.product.application.service

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.domain.entity.Product
import me.bread.product.domain.entity.ProductItem
import me.bread.product.domain.enums.ProductOption
import java.math.BigDecimal
import java.util.concurrent.locks.Lock

class StockServiceTest : StringSpec({
    // 목 의존성
    val lockManager = mockk<LockManager>()
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
        val productItem = ProductItem(id = itemId, optionKey = ProductOption.COLOR, optionValue = "빨강", stock = 10)
        val product = Product(id = productId, name = "테스트 상품", price = BigDecimal("100.00"), productItems = mutableListOf(productItem))

        // 이 테스트를 위한 새 락 생성
        val lock = mockk<Lock>()

        // 목 동작 설정
        every { lockManager.getLock() } returns lock
        every { lock.tryLock() } returns true
        every { lock.unlock() } returns Unit
        every { productService.findById(productId) } returns product
        every { productService.save(any<Product>()) } returns Unit
        every { customTransactionManager.executeInTransaction<Unit>(any()) } answers {
            firstArg<() -> Unit>().invoke()
        }

        // When
        stockService.decrease(productId, itemId)

        // Then
        productItem.stock shouldBe 9
        verify { productService.save(product) }
    }
})
