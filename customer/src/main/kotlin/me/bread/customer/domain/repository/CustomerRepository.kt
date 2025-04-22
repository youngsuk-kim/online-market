package me.bread.customer.domain.repository

import me.bread.customer.domain.entity.Customer

interface CustomerRepository {
    fun create(customer: Customer): Customer
}
