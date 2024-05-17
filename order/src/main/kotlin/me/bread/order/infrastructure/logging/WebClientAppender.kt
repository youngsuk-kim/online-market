package me.bread.order.infrastructure.logging

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.format.DateTimeFormatter

class WebClientAppender : AppenderBase<ILoggingEvent>() {
    var url: String? = null
    private val webClient: WebClient = WebClient.create()

    override fun append(eventObject: ILoggingEvent) {
        val timestamp = DateTimeFormatter.ISO_INSTANT.format(
            Instant.ofEpochMilli(eventObject.timeStamp),
        )
        val json = buildJson(eventObject, timestamp)
        println(json)

        webClient.post()
            .uri(url)
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .body(Mono.just(json), String::class.java)
            .retrieve()
            .toEntity(String::class.java)
            .subscribe({ response ->
                println("HTTP Response Status: ${response.statusCode}")
                println("HTTP Response Body: ${response.body}")
            }, { error ->
                println("Error sending log to Seq: ${error.message}")
            })
    }

    private fun buildJson(event: ILoggingEvent, timestamp: String): String {
        val message = escapeJson(event.formattedMessage)
        val level = event.level.toString()
        val exceptionInfo = event.throwableProxy?.let { throwableProxy ->
            escapeJson(throwableProxy.message ?: "")
        } ?: ""

        // JSON 문자열을 생성할 때 공백 없이 생성
        return """{"@t":"$timestamp","@m":"$message","@l":"$level","@x":"$exceptionInfo"}"""
    }

    private fun escapeJson(text: String): String {
        return text.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")
    }
}
