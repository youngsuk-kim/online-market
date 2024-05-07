package me.bread.order.application.service

import me.bread.order.application.external.ProductApi
import me.bread.order.domain.entity.OrderItem
import me.bread.order.presentation.support.error.ErrorType
import me.bread.order.presentation.support.error.RestException
import org.springframework.stereotype.Component

@Component
class ProductService(
    private val productApi: ProductApi,
) {
    fun verifyStock(orderItem: List<OrderItem>) {
        val outOfStockItems = orderItem.filterNot { isProductQuantityEnough(it.productItemId) }
        if (outOfStockItems.isNotEmpty()) {
            throw RestException(
                ErrorType.INVALID_ARG_ERROR,
                "One or more products do not have enough quantity",
            )
        }
    }

    private fun isProductQuantityEnough(itemId: Long) = productApi.isProductQuantityEnough(itemId)
}
