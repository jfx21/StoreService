package org.jfx.orderservice.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderRequest(
    @JsonProperty("username")
    val username: String= "",
    @JsonProperty("products")
    val products: List<ProductOrderRequest> = emptyList(),
    @JsonProperty("address")
    val address: String ="",
    @JsonProperty("phoneNumber")
    val phoneNumber: String =""
)
