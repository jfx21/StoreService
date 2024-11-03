package org.jfx.orderservice.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val customerName: String,
    val product: String,
    val quantity: Int,
    val totalPrice: BigDecimal,

    val orderDate: LocalDateTime = LocalDateTime.now()
)
