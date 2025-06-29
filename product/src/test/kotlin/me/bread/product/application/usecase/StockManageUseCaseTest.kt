package me.bread.product.application.usecase

import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.application.service.StockService

class StockManageUseCaseTest : StringSpec({
    // 의존성 목 객체
    val stockService = mockk<StockService>()

    // 테스트 대상
    val stockManageUseCase = StockManageUseCase(stockService)

    // 테스트 데이터
    val productId = 1L
    val itemId = 2L

    "execute 메서드는 상품 아이템의 재고를 감소시켜야 한다" {
        // Given
        every { stockService.decrease(itemId) } returns Unit

        // When
        stockManageUseCase.execute(itemId)

        // Then
        verify { stockService.decrease(itemId) }
    }
})
