package me.bread.product.application.service.manager

import me.bread.product.application.annotation.Local
import me.bread.product.application.annotation.LocalDev
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * 로컬 알림 관리자
 * 로컬 개발 및 테스트 환경에서 사용되는 알림 관리자 구현체
 * 실제 알림을 전송하는 대신 로그로 기록한다
 */
@Local
@LocalDev
@Component
class LocalNotificationManager : NotificationManager {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun sendNotification(topic: String, message: String) {
        logger.info("notification send success / $topic: $message")
    }
}
