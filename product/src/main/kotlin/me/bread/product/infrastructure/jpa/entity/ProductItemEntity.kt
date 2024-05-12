package me.bread.product.infrastructure.jpa.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import me.bread.product.domain.enums.ProductOption

@Entity
@Table(name = "product_item")
class ProductItemEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_id")
    val productEntity: ProductEntity,
    val optionKey: ProductOption,
    val optionValue: String,
    var stock: Int,
)
