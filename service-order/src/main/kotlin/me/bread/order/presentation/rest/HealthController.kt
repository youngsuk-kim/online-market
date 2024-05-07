package me.bread.order.presentation.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {

    @GetMapping("/")
    fun health(): ResponseEntity<String> {
        return ResponseEntity.ok().body("health ok")
    }
}
