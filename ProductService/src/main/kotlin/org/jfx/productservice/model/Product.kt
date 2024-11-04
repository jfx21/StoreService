package org.jfx.productservice.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = true)
    val description: String? = null,

    @Column(nullable = false)
    val price: BigDecimal,

    @Column(nullable = false)
    val stock: Int
)
