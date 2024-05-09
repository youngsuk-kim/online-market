package me.bread.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OrderApplication

fun main(args: Array<String>) {
    System.setProperty("kotlinx.coroutines.debug", "on")
    runApplication<OrderApplication>(*args)
}
