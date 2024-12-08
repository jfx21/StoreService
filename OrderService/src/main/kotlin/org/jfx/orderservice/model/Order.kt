package org.jfx.orderservice.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "ORDERS")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    val id: Long? = null,

    @JsonProperty("userId")
    val userId: Long? = 0L,

    @JsonProperty("productId")
    @OneToMany
    var products: List<ProductOrder> = emptyList(),

    @JsonProperty("address")
    val address: String? = null,

    @JsonProperty("phoneNumber")
    val phoneNumber: String? = null,

    @JsonProperty("status")
    var status: String? = "PENDING",

    @JsonProperty("createdAt")
    val createdAt: LocalDateTime? = LocalDateTime.now()
)
