package me.bread.product.infrastructure.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

/**
 * 재고 엔티티
 * 상품 아이템의 재고를 관리하는 JPA 엔티티
 */
@Entity
@Table(name = "inventory")
class InventoryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,

    @Column(name = "sku")
    val sku: String,

    @Column(name = "stock")
    var stock: Int,
)
