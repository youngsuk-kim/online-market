package me.bread.order.infrastructure.r2dbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("payments")
class PaymentEntity(
    @Id @Column("id") val id: Long? = null,
    @Column("orderId") val orderId: Long,
    @Column("customerId") val customerId: Long,
)
