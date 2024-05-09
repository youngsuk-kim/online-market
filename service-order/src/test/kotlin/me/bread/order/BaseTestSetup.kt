package me.bread.order

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseTestSetup {

    @LocalServerPort
    var port: Int = 0

    @BeforeEach
    fun configureRestAssured() {
        RestAssured.port = port
    }
}
