package me.bread.order.presentation.support.error

class RestException(
    val errorType: ErrorType,
    val data: Any? = null,
) : RuntimeException(errorType.message)
