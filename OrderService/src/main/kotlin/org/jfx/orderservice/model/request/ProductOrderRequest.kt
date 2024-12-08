package org.jfx.orderservice.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductOrderRequest(
    @JsonProperty("productName")
    val productName: String = "",
    @JsonProperty("quantity")
    val quantity: Int = 0
)