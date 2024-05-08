package me.bread.order.infrastructure.r2dbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("payment_item")
class PaymentItemEntity(
    @Id @Column("id") val id: Long? = null,
    @Column("paymentId") val paymentId: Long,
    @Column("productId") val productId: Long,
    @Column("name") val name: String,
    @Column("price") val price: BigDecimal,
    @Column("quantity") val quantity: Long,
)
