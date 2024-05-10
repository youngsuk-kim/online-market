package me.bread.order.adapter

import me.bread.order.domain.entity.Payment
import me.bread.order.infrastructure.r2dbc.entity.PaymentEntity

object PaymentMapper {

    fun toEntity(payment: Payment): PaymentEntity {
        return PaymentEntity(
            orderId = payment.orderId,
            customerId = payment.customerId,
        )
    }
}
