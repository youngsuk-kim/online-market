package me.bread.order.application.service

import me.bread.order.adapter.PaymentItemMapper
import me.bread.order.adapter.PaymentMapper
import me.bread.order.domain.entity.Order
import me.bread.order.domain.entity.Payment
import me.bread.order.domain.entity.PaymentItem
import me.bread.order.domain.repository.PaymentItemRepository
import me.bread.order.domain.repository.PaymentRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val paymentItemRepository: PaymentItemRepository,
) {

    @Transactional
    suspend fun save(order: Order) {
        val payment = Payment(orderId = order.id!!, customerId = order.customerId)
        val paymentId = paymentRepository.save(PaymentMapper.toEntity(payment))
        order.orderItems.forEach {
            val paymentItem = PaymentItem(
                productPrice = it.productPrice,
                productId = it.productId,
                productName = it.productName,
                quantity = it.quantity,
            )

            paymentItemRepository.save(PaymentItemMapper.toEntity(paymentId, paymentItem))
        }
    }
}
