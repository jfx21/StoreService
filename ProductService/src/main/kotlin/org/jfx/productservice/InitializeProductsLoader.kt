package org.jfx.productservice

import org.jfx.productservice.model.Product
import org.jfx.productservice.service.ProductService
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
open class InitializeProductsLoader(private val productService: ProductService) {
    //later on we should load products from json config

    @Bean
    open fun initializeProducts(): ApplicationRunner {
        return ApplicationRunner {
            // Check if products are already in the database to prevent duplicate entries
            if (productService.getAllProducts().isEmpty()) {
                val initialProducts = listOf(
                    Product(
                        name = "Laptop",
                        description = "High-performance laptop",
                        price = BigDecimal("1200.00"),
                        stock = 10
                    ),
                    Product(
                        name = "Smartphone",
                        description = "Latest model smartphone",
                        price = BigDecimal("800.00"),
                        stock = 20
                    ),
                    Product(
                        name = "Headphones",
                        description = "Noise-cancelling headphones",
                        price = BigDecimal("150.00"),
                        stock = 30
                    )
                )
                // Save initial products
                initialProducts.forEach { productService.createProduct(it) }
                println("Initial products loaded successfully.")
            } else {
                println("Products already exist, skipping initialization.")
            }
        }
    }
}
