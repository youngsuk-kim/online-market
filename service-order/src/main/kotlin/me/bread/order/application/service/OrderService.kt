package me.bread.order.application.service

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.bread.order.adapter.OrderItemMapper
import me.bread.order.adapter.OrderMapper
import me.bread.order.domain.entity.Order
import me.bread.order.domain.entity.OrderItem
import me.bread.order.domain.repository.OrderItemRepository
import me.bread.order.domain.repository.OrderRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Component
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
) {

    @Transactional
    suspend fun preorder(orderItem: List<OrderItem>) {
        val order = Order.preorder(orderItems = orderItem)

        coroutineScope {
            launch {
                order.orderItems.forEach {
                    orderItemRepository.save(OrderItemMapper.toEntity(it))
                }
            }

            launch {
                orderRepository.save(OrderMapper.toEntity(order))
            }
        }.join()
    }

    @Transactional
    suspend fun paid(orderId: Long) {
        val order = findOrderBy(orderId)
        orderRepository.save(OrderMapper.toEntity(order))
    }

    @Transactional(readOnly = true)
    suspend fun validatePay(orderId: Long, amount: BigDecimal) {
        val order = findOrderBy(orderId)
        order.validatePayment(orderId, amount)
    }

    @Transactional(readOnly = true)
    suspend fun findOrderBy(orderId: Long): Order {
        val orderItems = orderItemRepository.findByOrderId(orderId)
        return orderRepository.findById(orderId, orderItems)
    }
}
