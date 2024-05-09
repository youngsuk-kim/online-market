package me.bread.order.domain.repository

import me.bread.order.domain.entity.Order
import me.bread.order.domain.entity.OrderItem
import me.bread.order.infrastructure.r2dbc.entity.OrderEntity

interface OrderRepository {
    suspend fun save(orderEntity: OrderEntity): Long
    suspend fun updateToPaid(orderId: Long)
    suspend fun findById(id: Long, orderItems: List<OrderItem>): Order
}
