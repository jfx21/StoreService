package org.jfx.orderservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
open class OrderServiceApplication
fun main(args: Array<String>) {
    runApplication<OrderServiceApplication>(*args)
}