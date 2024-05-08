package me.bread.order.infrastructure.r2dbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("delivery")
class DeliveryEntity(
    @Id @Column("id") val id: Long? = null,
    @Column("orderId") val orderId: Long,
    @Column("customerEmail") val customerEmail: String,
    @Column("customerName") val customerName: String,
    @Column("customerPostalCode") val customerPostalCode: String,
    @Column("customerDestination") val customerDestination: String,
)
