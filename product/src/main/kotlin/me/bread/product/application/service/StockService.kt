package me.bread.product.application.service

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StockService {

    @Transactional
    fun decrease(itemId: Long) {
    }
}
