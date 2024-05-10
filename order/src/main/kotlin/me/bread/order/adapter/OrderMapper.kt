package me.bread.order.adapter

import me.bread.order.domain.entity.Order
import me.bread.order.infrastructure.r2dbc.entity.OrderEntity

object OrderMapper {

    fun toEntity(order: Order): OrderEntity {
        return OrderEntity(
            id = order.id,
            status = order.status.name,
            customerId = order.customerId,
        )
    }
}
