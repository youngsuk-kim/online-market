package me.bread.order.infrastructure.r2dbc

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("payment_item")
class PaymentItemEntity(
    @Id val id: Long? = null,
    @Column val paymentId: Long,
    @Column val productId: Long,
    @Column val name: String,
    @Column val price: BigDecimal,
    @Column val quantity: Long,
)
