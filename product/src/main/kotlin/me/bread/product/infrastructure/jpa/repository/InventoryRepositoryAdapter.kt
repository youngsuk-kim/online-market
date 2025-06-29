package me.bread.product.infrastructure.jpa.repository

import me.bread.product.domain.entity.Inventory
import me.bread.product.domain.repository.InventoryRepository
import me.bread.product.mapper.InventoryMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class InventoryRepositoryAdapter(
    private val inventoryJpaRepository: InventoryJpaRepository,
) : InventoryRepository {
    override fun findById(id: Long): Inventory? {
        val inventoryEntity = inventoryJpaRepository.findByIdOrNull(id) ?: return null
        return InventoryMapper.toDomain(inventoryEntity)
    }

    override fun findByProductItemId(productItemId: Long): Inventory? {
        val sku = productItemId.toString()
        val inventoryEntity = inventoryJpaRepository.findBySku(sku) ?: return null
        return InventoryMapper.toDomain(inventoryEntity)
    }

    override fun save(inventory: Inventory): Inventory {
        val inventoryEntity = InventoryMapper.toEntity(inventory)
        val savedEntity = inventoryJpaRepository.save(inventoryEntity)
        return InventoryMapper.toDomain(savedEntity)
    }
}
