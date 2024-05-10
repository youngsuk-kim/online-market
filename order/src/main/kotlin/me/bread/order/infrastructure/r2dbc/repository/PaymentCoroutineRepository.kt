package me.bread.order.infrastructure.r2dbc.repository

import me.bread.order.infrastructure.r2dbc.entity.PaymentEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PaymentCoroutineRepository : CoroutineCrudRepository<PaymentEntity, Long>
