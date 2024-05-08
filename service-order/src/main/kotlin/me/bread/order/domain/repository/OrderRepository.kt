package me.bread.order.domain.repository

import me.bread.order.infrastructure.r2dbc.entity.OrderEntity

interface OrderRepository {
    suspend fun save(orderEntity: OrderEntity)
}
