package me.bread.product.application.service

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.domain.entity.Product
import me.bread.product.domain.entity.ProductItem
import me.bread.product.domain.enums.ProductOption
import me.bread.product.presentation.support.error.ErrorType
import me.bread.product.presentation.support.error.RestException
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
        // 테스트 데이터
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

        // 실행
        stockService.decrease(productId, itemId)

        // 검증
        productItem.stock shouldBe 9
        verify { productService.save(product) }
    }

    // 존재하지 않는 상품이 주어진 경우
    "상품이 존재하지 않는 경우, 재고를 감소시키면 RestException이 발생해야 한다" {
        // 이 테스트를 위한 새 락 생성
        val lock = mockk<Lock>()

        // 목 동작 설정
        every { lockManager.getLock() } returns lock
        every { lock.tryLock() } returns true
        every { lock.unlock() } returns Unit
        every { productService.findById(productId) } returns null
        every { customTransactionManager.executeInTransaction<Unit>(any()) } answers {
            firstArg<() -> Unit>().invoke()
        }

        // 실행 및 검증
        val exception = runCatching {
            stockService.decrease(productId, itemId)
        }.exceptionOrNull()

        exception.shouldBeTypeOf<RestException>()
        exception.errorType shouldBe ErrorType.INVALID_ARG_ERROR
        exception.message shouldBe "Invalid arguments error has occurred"
    }

    // 락을 획득할 수 없는 경우가 주어진 경우
    "락을 획득할 수 없는 경우, 재고를 감소시키면 RestException이 발생해야 한다" {
        // 이 테스트를 위한 새 락 생성
        val lock = mockk<Lock>()

        // 목 동작 설정
        every { lockManager.getLock() } returns lock
        every { lock.tryLock() } returns false
        every { customTransactionManager.executeInTransaction<Unit>(any()) } answers {
            firstArg<() -> Unit>().invoke()
        }

        // 실행 및 검증
        val exception = runCatching {
            stockService.decrease(productId, itemId)
        }.exceptionOrNull()

        exception.shouldBeTypeOf<RestException>()
        exception.errorType shouldBe ErrorType.INVALID_ARG_ERROR
        exception.message shouldBe "Invalid arguments error has occurred"
    }
})
