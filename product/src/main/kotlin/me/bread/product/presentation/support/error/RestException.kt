package me.bread.product.presentation.support.error

class RestException(
    val errorType: ErrorType,
    val data: Any? = null,
) : RuntimeException(errorType.message)
