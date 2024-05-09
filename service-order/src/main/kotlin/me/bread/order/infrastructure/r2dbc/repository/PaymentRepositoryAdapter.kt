package me.bread.order.infrastructure.r2dbc.repository

import me.bread.order.domain.repository.PaymentRepository
import me.bread.order.infrastructure.r2dbc.entity.PaymentEntity
import org.springframework.stereotype.Component

@Component
class PaymentRepositoryAdapter(
    private val paymentCoroutineRepository: PaymentCoroutineRepository,
) : PaymentRepository {
    override suspend fun save(paymentEntity: PaymentEntity): Long {
        return paymentCoroutineRepository.save(paymentEntity).id!!
    }
}
