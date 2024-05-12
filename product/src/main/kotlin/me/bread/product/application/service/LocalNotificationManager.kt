package me.bread.product.application.service

import me.bread.order.application.annotation.Local
import me.bread.order.application.annotation.LocalDev
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Local
@LocalDev
@Component
class LocalNotificationManager : NotificationManager {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun sendNotification(topic: String, message: String) {
        logger.info("notification send success / $topic: $message")
    }
}
