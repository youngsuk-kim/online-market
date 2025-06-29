package me.bread.product.infrastructure.mongodb.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 * MongoDB 설정
 * MongoDB 저장소를 활성화하는 설정 클래스
 */
@Configuration
@EnableMongoRepositories(basePackages = ["me.bread.product.infrastructure.mongodb.repository"])
class MongoConfig
