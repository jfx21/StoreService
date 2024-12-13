package org.jfx.orderservice.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @JsonProperty("username")
    @Column(name = "username")
    val username: String = "",

    @JsonProperty("productIds")
    @ElementCollection
    val productIds: List<Long> = emptyList(),

    @JsonProperty("totalPrice")
    val totalPrice: Double? = 0.0,

    @JsonProperty("address")
    val address: String? = null,

    @JsonProperty("phoneNumber")
    val phoneNumber: String? = null,

    @JsonProperty("status")
    var status: String? = "PENDING",

    @JsonProperty("createdAt")
    var createdAt: LocalDateTime? = LocalDateTime.now()
)
