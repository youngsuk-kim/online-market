package me.bread.product.application.service

interface NotificationManager {
    fun sendNotification(topic: String, message: String)
}
