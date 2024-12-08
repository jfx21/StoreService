package org.jfx.orderservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.jfx.productservice", "org.jfx.orderservice"])
open class OrderServiceApplication
fun main(args: Array<String>) {
    runApplication<OrderServiceApplication>(*args)
}