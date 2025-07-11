package me.bread.product.presentation.support.error

class ProductException(
    val errorType: ErrorType,
    val data: Any? = null,
) : RuntimeException(errorType.message)
