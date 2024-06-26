package me.bread.order.infrastructure.r2dbc.repository

import me.bread.order.domain.entity.Order
import me.bread.order.domain.entity.OrderItem
import me.bread.order.domain.enums.OrderStatus
import me.bread.order.domain.repository.OrderRepository
import me.bread.order.infrastructure.r2dbc.entity.OrderEntity
import me.bread.order.presentation.support.error.ErrorType
import me.bread.order.presentation.support.error.RestException
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryAdapter(
    private val orderCoroutineRepository: OrderCoroutineRepository,
    private val orderCustomRepository: OrderCustomRepository,
) : OrderRepository {
    override suspend fun save(orderEntity: OrderEntity): Long {
        return orderCoroutineRepository.save(orderEntity).id!!
    }

    override suspend fun updateToPaid(orderId: Long) {
        orderCustomRepository.updateStatus(orderId, OrderStatus.PAYED)
    }

    override suspend fun findById(id: Long, orderItems: List<OrderItem>): Order {
        val orderEntity = (
            orderCoroutineRepository.findById(id)
                ?: throw RestException(ErrorType.INVALID_ARG_ERROR, "Order with ID $id not found")
            )

        return orderEntity.run {
            Order(
                id = this.id,
                orderItems = orderItems,
                status = OrderStatus.valueOf(this.status),
                customerId = this.customerId,
            )
        }
    }
}
