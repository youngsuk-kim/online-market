package me.bread.order.infrastructure.r2dbc.repository

import me.bread.order.domain.entity.OrderItem
import me.bread.order.domain.repository.OrderItemRepository
import me.bread.order.infrastructure.r2dbc.entity.OrderItemEntity
import me.bread.order.presentation.support.error.ErrorType
import me.bread.order.presentation.support.error.RestException
import org.springframework.stereotype.Repository

@Repository
class OrderItemRepositoryAdapter(
    private val orderItemCoroutineRepository: OrderItemCoroutineRepository,
) : OrderItemRepository {
    override suspend fun save(orderItemEntity: OrderItemEntity) {
        orderItemCoroutineRepository.save(orderItemEntity)
    }

    override suspend fun findByOrderId(orderId: Long): List<OrderItem> {
        return orderItemCoroutineRepository.findByOrderId(orderId)
            ?.map {
                OrderItem(
                    orderId = it.orderId,
                    productId = it.productId,
                    productName = it.productName,
                    productPrice = it.productPrice,
                    quantity = it.quantity,
                )
            }
            ?: throw RestException(ErrorType.INVALID_ARG_ERROR, "order with id $orderId not found")
    }
}
