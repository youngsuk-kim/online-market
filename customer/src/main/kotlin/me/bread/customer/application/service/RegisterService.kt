package me.bread.customer.application.service

import me.bread.customer.domain.entity.Customer
import me.bread.customer.domain.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterService(
    private val customerRepository: CustomerRepository
) {

    @Transactional
    fun create(customer: Customer) {
        customerRepository.create(customer)
    }
}
