package me.bread.product.domain.repository

import me.bread.product.domain.entity.Inventory

/**
 * 재고 저장소 인터페이스
 * 재고 엔티티에 대한 영속성 작업을 정의하는 인터페이스
 */
interface InventoryRepository {
    /**
     * ID로 재고 조회
     * 지정된 ID에 해당하는 재고를 조회한다
     *
     * @param id 조회할 재고의 ID
     * @return 조회된 재고 또는 null(재고가 없는 경우)
     */
    fun findById(id: Long): Inventory?

    /**
     * 상품 아이템 ID로 재고 조회
     * 지정된 상품 아이템 ID에 해당하는 재고를 조회한다
     *
     * @param productItemId 조회할 상품 아이템의 ID
     * @return 조회된 재고 또는 null(재고가 없는 경우)
     */
    fun findByProductItemId(productItemId: Long): Inventory?

    /**
     * 재고 저장
     * 재고 엔티티를 저장소에 저장한다
     *
     * @param inventory 저장할 재고 엔티티
     * @return 저장된 재고 엔티티
     */
    fun save(inventory: Inventory): Inventory
}
