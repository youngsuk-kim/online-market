package me.bread.product.application.service

import me.bread.product.domain.entity.Inventory
import me.bread.product.domain.repository.InventoryRepository
import me.bread.product.presentation.support.error.ErrorType
import me.bread.product.presentation.support.error.RestException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * 재고 서비스
 * 재고 관련 기능을 제공하는 서비스
 */
@Component
class InventoryService(
    private val inventoryRepository: InventoryRepository,
) {
    /**
     * ID로 재고 조회
     * 지정된 ID에 해당하는 재고를 조회한다
     *
     * @param id 조회할 재고의 ID
     * @return 조회된 재고
     * @throws RestException 재고가 없는 경우
     */
    @Transactional(readOnly = true)
    fun findById(id: Long): Inventory {
        return inventoryRepository.findById(id) ?: throw RestException(
            ErrorType.INVALID_ARG_ERROR,
            "재고를 찾을 수 없습니다: $id"
        )
    }

    /**
     * 상품 아이템 ID로 재고 조회
     * 지정된 상품 아이템 ID에 해당하는 재고를 조회한다
     *
     * @param productItemId 조회할 상품 아이템의 ID
     * @return 조회된 재고
     * @throws RestException 재고가 없는 경우
     */
    @Transactional(readOnly = true)
    fun findByProductItemId(productItemId: Long): Inventory {
        return inventoryRepository.findByProductItemId(productItemId) ?: throw RestException(
            ErrorType.INVALID_ARG_ERROR,
            "상품 아이템에 대한 재고를 찾을 수 없습니다: $productItemId"
        )
    }

    /**
     * 재고 저장
     * 재고 엔티티를 저장한다
     *
     * @param inventory 저장할 재고 엔티티
     * @return 저장된 재고 엔티티
     */
    @Transactional
    fun save(inventory: Inventory): Inventory {
        return inventoryRepository.save(inventory)
    }

    /**
     * 재고 감소
     * 지정된 상품 아이템의 재고를 1개 감소시킨다
     *
     * @param productItemId 재고를 감소시킬 상품 아이템의 ID
     * @return 업데이트된 재고 엔티티
     */
    @Transactional
    fun decrease(productItemId: Long): Inventory {
        val inventory = findByProductItemId(productItemId)
        inventory.decrease()
        return save(inventory)
    }

    /**
     * 재고 증가
     * 지정된 상품 아이템의 재고를 지정된 수량만큼 증가시킨다
     *
     * @param productItemId 재고를 증가시킬 상품 아이템의 ID
     * @param quantity 증가시킬 수량
     * @return 업데이트된 재고 엔티티
     */
    @Transactional
    fun increase(productItemId: Long, quantity: Int): Inventory {
        val inventory = findByProductItemId(productItemId)
        inventory.increase(quantity)
        return save(inventory)
    }
}
