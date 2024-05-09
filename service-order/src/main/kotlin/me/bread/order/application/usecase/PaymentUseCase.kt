package me.bread.order.application.usecase

import me.bread.order.application.external.PaymentApi
import me.bread.order.application.service.OrderService
import me.bread.order.application.service.PaymentService
import org.springframework.stereotype.Component

@Component
class PaymentUseCase(
    private val paymentApi: PaymentApi,
    private val orderService: OrderService,
    private val paymentService: PaymentService,
) {
    suspend fun execute(orderId: Long, paymentKey: String, amount: Long) {
        // 결제 승인 요청
        val tossPayment = paymentApi.execute(orderId, paymentKey, amount)

        // 결제 데이터 검증
        val order = orderService.validatePay(tossPayment.orderId, tossPayment.amount)

        // 결제 데이터 저장
        paymentService.save(order)

        // 주문을 결제 완료 상태로 변경
        orderService.paid(tossPayment.orderId)
    }
}
