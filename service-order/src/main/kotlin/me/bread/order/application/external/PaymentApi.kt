package me.bread.order.application.external

import me.bread.order.application.model.TossPayment

interface PaymentApi {
    fun execute(): TossPayment
}
