package me.bread.product.presentation.rest

import me.bread.order.presentation.support.response.RestResponse
import me.bread.product.presentation.support.error.ErrorType
import me.bread.product.presentation.support.error.ProductException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ProductException::class)
    fun handleApiException(e: ProductException): ResponseEntity<RestResponse<Any>> {
        logBasedOnLevel(e.errorType.logLevel, e.message, e)
        val response = RestResponse.error<Any>(e.errorType, e.data)
        return ResponseEntity(response, e.errorType.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<RestResponse<Any>> {
        log.error("Exception: {}", e.message, e)
        val response = RestResponse.error<Any>(ErrorType.DEFAULT_ERROR)
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun logBasedOnLevel(logLevel: LogLevel, message: String?, exception: Throwable) {
        when (logLevel) {
            LogLevel.ERROR -> log.error("ApiException: {}", message, exception)
            LogLevel.WARN -> log.warn("ApiException: {}", message, exception)
            else -> log.info("ApiException: {}", message, exception)
        }
    }
}
