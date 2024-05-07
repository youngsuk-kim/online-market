package me.bread.order.infrastructure.r2dbc

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("delivery")
class DeliveryEntity(
    @Id val id: Long? = null,
    @Column val orderId: Long,
    @Column val customerEmail: String,
    @Column val customerName: String,
    @Column val customerPostalCode: String,
    @Column val customerDestination: String,
)
