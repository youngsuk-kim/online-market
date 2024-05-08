package me.bread.order.infrastructure.r2dbc.repository

import me.bread.order.domain.repository.OrderRepository
import me.bread.order.infrastructure.r2dbc.entity.OrderEntity
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryAdapter(
    private val orderR2dbcRepository: OrderR2dbcRepository,
) : OrderRepository {
    override suspend fun save(orderEntity: OrderEntity) {
        orderR2dbcRepository.save(orderEntity)
    }
}
