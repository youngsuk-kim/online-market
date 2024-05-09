package me.bread.order.adapter

import me.bread.order.domain.entity.PaymentItem
import me.bread.order.infrastructure.r2dbc.entity.PaymentItemEntity

object PaymentItemMapper {

    fun toEntity(paymentId: Long, paymentItem: PaymentItem): PaymentItemEntity {
        return PaymentItemEntity(
            paymentId = paymentId,
            productId = paymentItem.productId,
            productName = paymentItem.name,
            productPrice = paymentItem.price,
            quantity = paymentItem.quantity,
        )
    }
}
