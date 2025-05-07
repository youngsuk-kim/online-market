package me.bread.customer.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Customer(
    @Id
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val nickname: String,

    @Column(nullable = false, unique = true)
    val phone: String,

    ) : AuditableEntity() {

    companion object {
        fun create(name: String, phone: String, nickname: String) =
            Customer(
                name = name,
                phone = phone,
                nickname = nickname,
            )
    }

    fun update(phone: String?, nickname: String?) =
        Customer(
            name = this.name,
            nickname = nickname ?: this.nickname,
            phone = phone ?: this.phone,
        )
}
