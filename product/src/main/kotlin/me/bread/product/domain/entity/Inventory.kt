package me.bread.product.domain.entity

import me.bread.product.presentation.support.error.ErrorType
import me.bread.product.presentation.support.error.ProductException

/**
 * 재고 엔티티
 * 상품 아이템의 재고를 관리하는 도메인 엔티티
 */
class Inventory(
    val id: Long = 0,
    val productItemId: Long,
    var stock: Int,
) {
    /**
     * 재고 감소
     * 재고를 1개 감소시킨다
     */
    fun decrease() {
        if (stock <= 0) {
            throw ProductException(
                ErrorType.INSUFFICIENT_STOCK,
            )
        }
        stock -= 1
    }

    /**
     * 재고 증가
     * 재고를 지정된 수량만큼 증가시킨다
     *
     * @param quantity 증가시킬 수량
     */
    fun increase(quantity: Int) {
        if (quantity <= 0) {
            throw ProductException(
                ErrorType.INVALID_INCREASE_QUANTITY,
            )
        }
        stock += quantity
    }
}
