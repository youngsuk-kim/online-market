package me.bread.product.infrastructure.kafka

import me.bread.product.application.service.manager.NotificationManager
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaNotificationService(private val kafkaTemplate: KafkaTemplate<String, Any>) :
    NotificationManager {

    override fun sendNotification(topic: String, message: String) {
        kafkaTemplate.send(topic, message)
    }
}
