package me.bread.customer.application.usecase

import me.bread.customer.domain.entity.Customer
import me.bread.customer.domain.repository.CustomerRepository

class RegisterUseCase(
    private val customerRepository: CustomerRepository
) {

    fun execute(name: String, nickName: String, phone: String) {
        val customer = Customer(
            name = name,
            nickname = nickName,
            phone = phone,
        )

        customerRepository.create(customer)
    }
}
