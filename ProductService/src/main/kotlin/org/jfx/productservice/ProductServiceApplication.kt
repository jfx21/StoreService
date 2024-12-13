package org.jfx.productservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ProductServiceApplication
fun main(args: Array<String>) {
    runApplication<ProductServiceApplication>(*args)
}