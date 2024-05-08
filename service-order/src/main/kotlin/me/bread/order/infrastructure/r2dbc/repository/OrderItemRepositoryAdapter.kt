package me.bread.order.infrastructure.r2dbc.repository

import me.bread.order.domain.repository.OrderItemRepository
import me.bread.order.infrastructure.r2dbc.entity.OrderItemEntity
import org.springframework.stereotype.Repository

@Repository
class OrderItemRepositoryAdapter(
    private val orderItemR2dbcRepository: OrderItemR2dbcRepository,
) : OrderItemRepository {
    override suspend fun save(orderItemEntity: OrderItemEntity) {
        println(orderItemEntity.toString())
        orderItemR2dbcRepository.save(orderItemEntity)
    }
}
