package me.bread.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.util.UUID
import net.logstash.logback.argument.StructuredArguments

object LogUtils {
    inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)

    fun setMDCRequestId() {
        MDC.put("requestId", UUID.randomUUID().toString())
    }

    fun clearMDC() {
        MDC.clear()
    }

    fun setUserId(userId: String) {
        MDC.put("userId", userId)
    }


}

fun Logger.infoStructured(message: String, vararg pairs: Pair<String, Any?>) {
    info(message, *pairs.map { StructuredArguments.kv(it.first, it.second) }.toTypedArray())
}

fun Logger.errorStructured(message: String, throwable: Throwable? = null, vararg pairs: Pair<String, Any?>) {
    if (throwable != null) {
        error(message, *pairs.map { StructuredArguments.kv(it.first, it.second) }.toTypedArray(), throwable)
    } else {
        error(message, *pairs.map { StructuredArguments.kv(it.first, it.second) }.toTypedArray())
    }
}

fun Logger.debugStructured(message: String, vararg pairs: Pair<String, Any?>) {
    debug(message, *pairs.map { StructuredArguments.kv(it.first, it.second) }.toTypedArray())
}
