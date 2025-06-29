package me.bread.product.application.service.manager

/**
 * 알림 관리자 인터페이스
 * 다양한 채널을 통해 알림을 전송하는 기능을 제공하는 인터페이스
 */
interface NotificationManager {
    /**
     * 알림 전송
     * 지정된 토픽으로 메시지를 전송한다
     *
     * @param topic 알림을 전송할 토픽 또는 채널
     * @param message 전송할 알림 메시지 내용
     */
    fun sendNotification(topic: String, message: String)
}
