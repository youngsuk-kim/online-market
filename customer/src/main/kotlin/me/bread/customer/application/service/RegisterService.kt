package me.bread.customer.application.service

import me.bread.customer.domain.entity.Customer
import me.bread.customer.domain.repository.CustomerRepository
import me.bread.logging.LogUtils
import me.bread.logging.infoStructured
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class RegisterService(
    private val customerRepository: CustomerRepository
) {

    private val logger = LogUtils.logger<RegisterService>()

    @Transactional
    fun create(customer: Customer) {
        customerRepository.save(customer)
    }

    fun processUserAction(userId: String, action: String): Map<String, Any> {
        logger.infoStructured(
            "User action processed",
            "userId" to userId,
            "action" to action,
            "timestamp" to LocalDateTime.now().toString()
        )

        // 비즈니스 로직
        return mapOf(
            "userId" to userId,
            "action" to action,
            "status" to "completed",
            "processedAt" to LocalDateTime.now().toString()
        )
    }
}

