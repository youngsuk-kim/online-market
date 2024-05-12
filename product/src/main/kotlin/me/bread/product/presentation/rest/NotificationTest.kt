package me.bread.product.presentation.rest

import me.bread.product.application.service.NotificationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NotificationTest(
    private val notificationService: NotificationService,
) {

    @PostMapping("/send")
    fun sendNotification() {
        notificationService.sendNotification("hello-topic", "sms요")
    }
}
