package me.bread.customer.domain.repository

import me.bread.customer.domain.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, Long> {
    fun create(customer: Customer): Customer
}
