package me.bread.product.domain.entity

import java.math.BigDecimal

/**
 * 상품 엔티티
 * 상품의 기본 정보와 상품 아이템 목록을 관리하는 도메인 엔티티
 */
class Product(
    val id: Long = 0,
    var name: String,
    var price: BigDecimal,
    val productItems: MutableList<ProductItem> = mutableListOf(),
) {
    /**
     * 재고 감소
     * 지정된 ID의 상품 아이템 재고를 감소시킨다
     *
     * @param itemId 재고를 감소시킬 상품 아이템의 ID
     * @return 현재 상품 객체
     */
    fun decreaseStock(itemId: Long): Product {
        this.productItems.find { productItem -> productItem.id == itemId }
            ?.decrease()
        return this
    }

    /**
     * 재고 조회
     * 지정된 ID의 상품 아이템 재고를 조회한다
     *
     * @param itemId 재고를 조회할 상품 아이템의 ID
     * @return 상품 아이템의 재고 수량 또는 null(아이템이 없는 경우)
     */
    fun stock(itemId: Long): Int? {
        return this.productItems.find { productItem -> productItem.id == itemId }?.stock
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false
        if (name != other.name) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + price.hashCode()
        return result
    }
}
