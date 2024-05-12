package me.bread.product.infrastructure.jpa.config.local

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class DataInitializer(private val redisTemplate: RedisTemplate<String, Int>) {

    private val logger = LoggerFactory.getLogger(DataInitializer::class.java)

    @PostConstruct
    fun loadData() {
        val key = "stock"
        val opsForValue = redisTemplate.opsForValue()

        opsForValue.set("$key:1", 1000)
        opsForValue.set("$key:2", 2322)
        opsForValue.set("$key:3", 3232)

        // 데이터 확인용 출력
        logger.info("Loaded data into Redis: ${opsForValue.get("$key:1")}")
        logger.info("Loaded data into Redis: ${opsForValue.get("$key:2")}")
        logger.info("Loaded data into Redis: ${opsForValue.get("$key:3")}")
    }
}
