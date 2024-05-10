package me.bread.customer.presentation.rest

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class CustomerController {

    @PostMapping("/api/customers")
    fun getCustomers(@RequestBody token: String): Long {
        return Random.nextInt(100).toLong()
    }
}
