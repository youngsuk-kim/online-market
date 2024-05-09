package me.bread.order.infrastructure.r2dbc.repository

import me.bread.order.domain.repository.PaymentItemRepository
import me.bread.order.infrastructure.r2dbc.entity.PaymentItemEntity
import org.springframework.stereotype.Repository

@Repository
class PaymentItemRepositoryAdapter(
    private val paymentItemCoroutineRepository: PaymentItemCoroutineRepository,
) : PaymentItemRepository {

    override suspend fun save(paymentItemEntity: PaymentItemEntity) {
        paymentItemCoroutineRepository.save(paymentItemEntity)
    }
}
