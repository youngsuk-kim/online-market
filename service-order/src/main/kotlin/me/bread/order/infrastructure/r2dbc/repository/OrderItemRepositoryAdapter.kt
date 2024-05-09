package me.bread.order.infrastructure.r2dbc.repository

import me.bread.order.domain.entity.OrderItem
import me.bread.order.domain.repository.OrderItemRepository
import me.bread.order.infrastructure.r2dbc.entity.OrderItemEntity
import me.bread.order.presentation.support.error.ErrorType
import me.bread.order.presentation.support.error.RestException
import org.springframework.stereotype.Repository

@Repository
class OrderItemRepositoryAdapter(
    private val orderItemR2dbcRepository: OrderItemR2dbcRepository,
) : OrderItemRepository {
    override suspend fun save(orderItemEntity: OrderItemEntity) {
        orderItemR2dbcRepository.save(orderItemEntity)
    }

    override suspend fun findByOrderId(orderId: Long): List<OrderItem> {
        return orderItemR2dbcRepository.findByOrderId(orderId)
            ?.map {
                OrderItem(
                    orderId = it.orderId,
                    productItemId = it.productItemId,
                    productName = it.productName,
                    productPrice = it.productPrice,
                    quantity = it.quantity,
                )
            }
            ?: throw RestException(ErrorType.INVALID_ARG_ERROR, "order with id $orderId not found")
    }
}
