package me.bread.order.infrastructure.r2dbc.repository

import me.bread.order.infrastructure.r2dbc.entity.OrderItemEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface OrderItemCoroutineRepository : CoroutineCrudRepository<OrderItemEntity, Long> {
    suspend fun findByOrderId(orderId: Long): List<OrderItemEntity>?
}
