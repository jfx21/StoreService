package org.jfx.orderservice.model.response

data class OrderResponse(
    val orderId: Long = 0L,
    val userId: Long =0L,
    val address: String = "",
    val phoneNumber: String = "",
   // val products: List<ProductDetail> = emptyList(),
    val totalPrice: Double = 0.0
)
