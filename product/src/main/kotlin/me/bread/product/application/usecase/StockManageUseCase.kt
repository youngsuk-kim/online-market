package me.bread.product.application.usecase

import me.bread.product.application.service.StockService
import org.springframework.stereotype.Component

/**
 * 재고 관리 유스케이스
 * 상품 재고 관리 기능을 제공하는 유스케이스
 * 재고 감소 등의 재고 관련 작업을 처리한다
 */
@Component
class StockManageUseCase(private val stockService: StockService) {

    /**
     * 재고 감소 실행
     * 지정된 상품의 특정 아이템 재고를 감소시킨다
     *
     * @param itemId 재고를 감소시킬 상품 아이템의 ID
     */
    fun execute(itemId: Long) {
        stockService.decrease(itemId)
    }
}
