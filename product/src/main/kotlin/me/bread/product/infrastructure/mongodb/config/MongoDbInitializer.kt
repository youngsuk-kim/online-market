package me.bread.product.infrastructure.mongodb.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.bread.product.infrastructure.mongodb.document.ProductDocument
import me.bread.product.infrastructure.mongodb.repository.ProductMongoRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

/**
 * MongoDB 초기화 설정
 * 애플리케이션 시작 시 MongoDB에 더미 데이터를 초기화하는 설정 클래스
 * JSON 파일에서 데이터를 로드하여 MongoDB에 저장
 */
@Configuration
class MongoDbInitializer(private val objectMapper: ObjectMapper) {

    @Bean
    fun initializeMongoDb(productMongoRepository: ProductMongoRepository): CommandLineRunner {
        return CommandLineRunner {
            try {
                // 기존 데이터 삭제
                productMongoRepository.deleteAll()

                // JSON 파일에서 더미 데이터 로드
                val resource = ClassPathResource("mongodb/products.json")
                val productsJson = resource.inputStream.use { it.reader().readText() }
                val products: List<ProductDocument> = objectMapper.readValue(productsJson)

                // MongoDB에 저장
                productMongoRepository.saveAll(products)

                println("MongoDB initialized with dummy data from JSON file")
            } catch (e: Exception) {
                println("Error initializing MongoDB: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}
