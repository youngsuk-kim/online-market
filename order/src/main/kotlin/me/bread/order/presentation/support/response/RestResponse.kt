package me.bread.order.presentation.support.response

import me.bread.order.presentation.support.error.ErrorMessage
import me.bread.order.presentation.support.error.ErrorType

data class RestResponse<T> private constructor(
    val result: ResultType? = null,
    val data: T? = null,
    val error: ErrorMessage? = null,
) {
    companion object {
        fun success(): RestResponse<Any> {
            return RestResponse(
                ResultType.SUCCESS,
                null,
                null,
            )
        }

        fun <S> success(data: S): RestResponse<S> {
            return RestResponse(
                ResultType.SUCCESS,
                data,
                null,
            )
        }

        fun <S> error(error: ErrorType, errorData: Any? = null): RestResponse<S> {
            return RestResponse(
                ResultType.ERROR,
                null,
                ErrorMessage(error, errorData),
            )
        }
    }
}
