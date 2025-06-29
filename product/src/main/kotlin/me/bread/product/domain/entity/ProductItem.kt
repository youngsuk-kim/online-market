package me.bread.product.domain.entity

import me.bread.product.domain.enums.ProductOption

/**
 * 상품 아이템 엔티티
 * 상품의 옵션과 재고 정보를 관리하는 도메인 엔티티
 * 하나의 상품은 여러 상품 아이템을 가질 수 있다
 */
class ProductItem(
    val id: String,
    val optionKey: ProductOption,
    val optionValue: String,
    var stock: Int,
) {
    /**
     * 재고 감소
     * 상품 아이템의 재고를 1개 감소시킨다
     */
    fun decrease() {
        stock -= 1
    }
}
