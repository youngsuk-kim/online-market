package me.bread.order.presentation.rest

import me.bread.order.application.usecase.PaymentUseCase
import me.bread.order.presentation.rest.request.PaymentRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController(
    private val paymentUseCase: PaymentUseCase,
) {

    @PostMapping("/api/payments/confirm")
    suspend fun confirm(@RequestBody request: PaymentRequest) {
        paymentUseCase.execute(request.orderId, request.paymentKey, request.amount)
    }
}
