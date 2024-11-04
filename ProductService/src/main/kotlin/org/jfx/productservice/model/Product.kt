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
    var name: String ="",

    @Column(nullable = true)
    var description: String? = "",

    @Column(nullable = false)
    var price: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var stock: Int = 0
){
    constructor() : this(id = null)
}
