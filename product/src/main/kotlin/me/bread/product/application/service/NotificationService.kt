package me.bread.product.application.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class NotificationService(private val kafkaTemplate: KafkaTemplate<String, Any>) {

    fun sendNotification(topic: String, message: String) {
        kafkaTemplate.send(topic, message)
    }
}
