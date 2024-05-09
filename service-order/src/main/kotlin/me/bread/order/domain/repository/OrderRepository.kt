package me.bread.order.domain.repository

import me.bread.order.domain.entity.Order
import me.bread.order.domain.entity.OrderItem
import me.bread.order.infrastructure.r2dbc.entity.OrderEntity

interface OrderRepository {
    suspend fun save(orderEntity: OrderEntity)
    suspend fun findById(id: Long, orderItems: List<OrderItem>): Order
}
