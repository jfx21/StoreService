package org.jfx.orderservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.jfx.orderservice","org.jfx.productservice"])
open class OrderServiceApplication
fun main(args: Array<String>) {
    runApplication<OrderServiceApplication>(*args)

}