package me.bread.order.adapter

import me.bread.order.domain.entity.Order
import me.bread.order.infrastructure.r2dbc.entity.OrderEntity

object OrderR2dbcMapper {

    fun toEntity(order: Order): OrderEntity {
        return OrderEntity(
            status = order.status.name,
        )
    }
}
