package me.bread.order.application.service

import me.bread.order.domain.repository.PaymentItemRepository
import me.bread.order.domain.repository.PaymentRepository
import org.springframework.stereotype.Component

@Component
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val paymentItemRepository: PaymentItemRepository,
) {

//    @Transactional
//    fun save(order: Order) {
//        Payment(
//            orderId = order.id!!,
//            buyerName =
//        )
//    }
}
