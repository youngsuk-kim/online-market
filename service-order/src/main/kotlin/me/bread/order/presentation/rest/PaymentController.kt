package me.bread.order.presentation.rest

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController {

    @PostMapping("/api/payments/confirm")
    fun confirm(@RequestBody request: PaymentRequest) {
        println("Payment confirm")
        println(request.paymentKey)
        println(request.orderId)
        println(request.amount)
    }
}
