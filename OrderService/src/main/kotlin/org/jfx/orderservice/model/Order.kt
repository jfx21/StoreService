package org.jfx.orderservice.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "ORDERS")
data class Order @JsonCreator constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    val id: Long? = null,

    @Column(nullable = false)
    @JsonProperty("userId")
    val userId: Long = 0,

    @Column(nullable = false)
    @JsonProperty("product")
    val product: String = "",

    @Column(nullable = false)
    @JsonProperty("quantity")
    val quantity: Int = 1,

    @Column(nullable = false)
    @JsonProperty("price")
    val price: Double = 0.0,

    @Column(nullable = false)
    @JsonProperty("status")
    val status: String = "PENDING",

    @Column(nullable = false)
    @JsonProperty("createdAt")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
