package me.bread.order.adapter

import me.bread.order.domain.entity.OrderItem
import me.bread.order.infrastructure.r2dbc.entity.OrderItemEntity

object OrderItemMapper {

    fun toEntity(orderItem: OrderItem, orderId: Long): OrderItemEntity {
        return OrderItemEntity(
            productId = orderItem.productId,
            productPrice = orderItem.productPrice,
            quantity = orderItem.quantity,
            productName = orderItem.productName,
            orderId = orderId,
        )
    }
}
