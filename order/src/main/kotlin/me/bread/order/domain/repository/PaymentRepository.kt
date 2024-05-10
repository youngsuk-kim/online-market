package me.bread.order.domain.repository

import me.bread.order.infrastructure.r2dbc.entity.PaymentEntity

interface PaymentRepository {
    suspend fun save(paymentEntity: PaymentEntity): Long
}
