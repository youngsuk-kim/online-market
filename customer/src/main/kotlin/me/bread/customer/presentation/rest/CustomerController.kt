package me.bread.customer.presentation.rest

import me.bread.customer.application.service.RegisterService
import me.bread.logging.LogUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(
    private val registerService: RegisterService
) {
    private val logger = LogUtils.logger<CustomerController>()

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id: String): Map<String, String> {
        logger.info("Fetching user with id: $id")

        // 사용자 ID를 MDC에 추가
        LogUtils.setUserId(id)

        try {
            registerService.processUserAction("id", "create")
            // 비즈니스 로직 처리
            logger.debug("Processing user data for id: $id")

            if (id == "error") {
                logger.error("Error occurred while fetching user with id: $id")
                throw RuntimeException("User not found")
            }

            return mapOf("id" to id, "name" to "Test User")
        } catch (e: Exception) {
            logger.error("Exception occurred while fetching user", e)
            throw e
        }
    }
}
