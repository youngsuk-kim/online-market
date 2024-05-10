package me.bread.order.domain.repository

import me.bread.order.domain.entity.OrderItem
import me.bread.order.infrastructure.r2dbc.entity.OrderItemEntity

interface OrderItemRepository {
    suspend fun save(orderItemEntity: OrderItemEntity)

    suspend fun findByOrderId(orderId: Long): List<OrderItem>
}
