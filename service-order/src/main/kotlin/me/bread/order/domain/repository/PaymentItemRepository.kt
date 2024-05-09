package me.bread.order.domain.repository

import me.bread.order.infrastructure.r2dbc.entity.PaymentItemEntity

interface PaymentItemRepository {
    suspend fun save(paymentItemEntity: PaymentItemEntity)
}
