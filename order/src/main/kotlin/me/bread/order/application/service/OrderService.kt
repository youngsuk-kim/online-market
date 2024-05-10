package me.bread.order.application.service

import me.bread.order.adapter.OrderItemMapper
import me.bread.order.adapter.OrderMapper
import me.bread.order.domain.entity.Order
import me.bread.order.domain.entity.OrderItem
import me.bread.order.domain.repository.OrderItemRepository
import me.bread.order.domain.repository.OrderRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
) {

    @Transactional
    suspend fun preorder(orderItem: List<OrderItem>, customerId: Long): Long {
        val order = Order.preorder(orderItems = orderItem, customerId = customerId)

        val orderId = orderRepository.save(OrderMapper.toEntity(order))

        order.orderItems.forEach {
            orderItemRepository.save(OrderItemMapper.toEntity(it, orderId))
        }

        return orderId
    }

    @Transactional
    suspend fun paid(orderId: Long) {
        orderRepository.updateToPaid(orderId)
    }

    @Transactional(readOnly = true)
    suspend fun validatePay(orderId: Long, amount: Long): Order {
        val order = findOrderBy(orderId)
        order.validatePayment(orderId, amount)
        return order
    }

    @Transactional(readOnly = true)
    suspend fun findOrderBy(orderId: Long): Order {
        val orderItems = orderItemRepository.findByOrderId(orderId)
        return orderRepository.findById(orderId, orderItems)
    }
}
