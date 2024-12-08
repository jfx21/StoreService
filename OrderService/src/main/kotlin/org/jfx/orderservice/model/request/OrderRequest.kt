package org.jfx.orderservice.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderRequest(
    @JsonProperty("userId")
    val userId: Long = 0L,
    @JsonProperty("products")
    val products: List<ProductOrderRequest> = emptyList(),
    @JsonProperty("address")
    val address: String ="",
    @JsonProperty("phoneNumber")
    val phoneNumber: String =""
)
