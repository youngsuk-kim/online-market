package me.bread.product.presentation.rest

import me.bread.product.infrastructure.kafka.KafkaNotificationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NotificationTest(
    private val kafkaNotificationService: KafkaNotificationService,
) {

    @PostMapping("/send")
    fun sendNotification() {
        kafkaNotificationService.sendNotification("hello-topic", "sms요")
    }
}
