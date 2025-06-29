package me.bread.product.application.service

import me.bread.product.application.service.manager.CustomTransactionManager
import me.bread.product.application.service.manager.LockManager
import org.springframework.stereotype.Component

/**
 * 재고 관리 서비스
 * 상품의 재고를 관리하는 서비스 클래스
 */
@Component
class StockService(
    private val lockManager: LockManager,
    private val inventoryService: InventoryService,
    private val customTransactionManager: CustomTransactionManager,
) {

    /**
     * 재고 감소
     * 지정된 상품의 특정 아이템 재고를 1개 감소시킨다.
     * 동시성 문제를 방지하기 위해 락을 사용하고 트랜잭션 내에서 실행된다.
     *
     * @param productId 재고를 감소시킬 상품의 ID (현재는 사용하지 않음)
     * @param itemId 재고를 감소시킬 상품 아이템의 ID
     */
    fun decrease(itemId: Long) {
        customTransactionManager.executeInTransaction {
            lockManager.run {
                getLock().use {
                    decreaseStock(itemId)
                }
            }
        }
    }

    /**
     * 재고 감소 처리
     * 지정된 상품 아이템의 재고를 1개 감소시킨다.
     *
     * @param itemId 재고를 감소시킬 상품 아이템의 ID
     */
    fun decreaseStock(itemId: Long) {
        inventoryService.decrease(itemId)
    }
}
