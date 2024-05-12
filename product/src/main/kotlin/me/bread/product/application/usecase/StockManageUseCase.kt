package me.bread.product.application.usecase

import me.bread.product.application.service.StockService
import org.springframework.stereotype.Component

@Component
class StockManageUseCase(private val stockService: StockService) {

    fun execute(productId: Long, itemId: Long) {
        stockService.decrease(productId, itemId)
    }
}
