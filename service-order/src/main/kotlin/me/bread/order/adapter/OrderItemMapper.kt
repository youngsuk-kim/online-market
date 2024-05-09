package me.bread.order.adapter

import me.bread.order.domain.entity.OrderItem
import me.bread.order.infrastructure.r2dbc.entity.OrderItemEntity

object OrderItemMapper {

    fun toEntity(orderItem: OrderItem): OrderItemEntity {
        return OrderItemEntity(
            productItemId = orderItem.productItemId,
            productPrice = orderItem.productPrice,
            quantity = orderItem.quantity,
            productName = orderItem.productName,
            orderId = orderItem.orderId,
        )
    }
}
