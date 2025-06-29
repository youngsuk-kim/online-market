package me.bread.product.presentation.support.error

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ErrorType(
    val status: HttpStatus,
    val code: ErrorCode,
    val message: String,
    val logLevel: LogLevel,
) {
    DEFAULT_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        ErrorCode.E500,
        "예상치 못한 오류가 발생했습니다.",
        LogLevel.ERROR,
    ),
    INVALID_ARG_ERROR(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "잘못된 인수 오류가 발생했습니다",
        LogLevel.WARN,
    ),

    // Inventory related errors
    INVENTORY_NOT_FOUND(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "재고를 찾을 수 없습니다: %s",
        LogLevel.WARN,
    ),
    PRODUCT_ITEM_INVENTORY_NOT_FOUND(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "상품 아이템에 대한 재고를 찾을 수 없습니다: %s",
        LogLevel.WARN,
    ),
    INSUFFICIENT_STOCK(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "재고가 부족합니다",
        LogLevel.WARN,
    ),
    INVALID_INCREASE_QUANTITY(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "증가시킬 수량은 0보다 커야 합니다",
        LogLevel.WARN,
    ),

    // Product related errors
    PRODUCT_NOT_FOUND(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "상품 아이템을 찾을 수 없습니다",
        LogLevel.WARN,
    ),

    // Lock related errors
    UNABLE_TO_ACQUIRE_LOCK(
        HttpStatus.INTERNAL_SERVER_ERROR,
        ErrorCode.E500,
        "락을 획득할 수 없습니다",
        LogLevel.ERROR,
    );
}
