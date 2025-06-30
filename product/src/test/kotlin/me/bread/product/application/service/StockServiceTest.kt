package me.bread.product.application.service

import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.bread.product.application.service.manager.CustomTransactionManager
import me.bread.product.application.service.manager.LockManager
import me.bread.product.domain.entity.Inventory

class StockServiceTest : StringSpec({
    // 목 의존성
    val lockManager = mockk<LockManager>(relaxed = true)
    val inventoryService = mockk<InventoryService>()
    val customTransactionManager = mockk<CustomTransactionManager>()

    // 테스트할 서비스 생성
    val stockService = StockService(lockManager, inventoryService, customTransactionManager)

    // 테스트 데이터
    val itemId = 2L

    // 재고가 있는 상품이 주어진 경우
    "상품 아이템에 재고가 있는 경우, 재고를 감소시키면 재고가 감소되어야 한다" {
        // Given
        // 목 동작 설정
        every { inventoryService.decrease(itemId) } returns Inventory(1L, itemId, 9)

        // When
        stockService.decreaseStock(itemId)

        // Then
        verify { inventoryService.decrease(itemId) }
    }
})
