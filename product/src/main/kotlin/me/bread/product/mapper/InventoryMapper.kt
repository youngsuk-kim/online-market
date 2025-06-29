package me.bread.product.mapper

import me.bread.product.domain.entity.Inventory
import me.bread.product.infrastructure.jpa.entity.InventoryEntity

object InventoryMapper {

    fun toEntity(inventory: Inventory): InventoryEntity {
        return InventoryEntity(
            id = inventory.id,
            sku = inventory.productItemId.toString(),
            stock = inventory.stock,
        )
    }

    fun toDomain(inventoryEntity: InventoryEntity): Inventory {
        return Inventory(
            id = inventoryEntity.id,
            productItemId = inventoryEntity.sku.toLongOrNull() ?: 0,
            stock = inventoryEntity.stock,
        )
    }
}
