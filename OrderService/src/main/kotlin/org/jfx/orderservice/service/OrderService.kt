package org.jfx.orderservice.service


import org.jfx.orderservice.model.Order
import org.jfx.orderservice.model.request.OrderRequest
import org.jfx.orderservice.repository.OrderRepository
import org.jfx.productservice.service.ProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class OrderService(
    private val orderRepository: OrderRepository,
    private val productService: ProductService  // Inject ProductService here
) {
    @Transactional
    open fun createOrder(orderRequest: OrderRequest): Order {
        val productDetails = orderRequest.products.map { productOrder ->
            val product = productService.getProductByName(productOrder.productName)
                ?: throw IllegalArgumentException("Product not found: ${productOrder.productName}")
            if (!product.isAvailable(productOrder.quantity)) {
                throw IllegalArgumentException("Not enough stock for product: ${product.name}")
            }
            productService.reduceProductStock(product.id!!, productOrder.quantity)
            val totalPriceForProduct = product.price.toDouble() * productOrder.quantity
            product.id to totalPriceForProduct
        }

        val totalPrice = productDetails.sumOf { it.second }

        if (totalPrice <= 0) {
            throw IllegalArgumentException("Total price must be greater than zero")
        }
        val order = Order(
            username = orderRequest.username,
            productIds = productDetails.map { it.first!! },
            address = orderRequest.address,
            phoneNumber = orderRequest.phoneNumber,
            totalPrice = totalPrice
        )
        return orderRepository.save(order)
    }

    fun getOrderById(id: Long): Order? {
        return orderRepository.findById(id).orElse(null)
    }
    @Transactional(readOnly = true)
    open fun getOrdersByUsername(username: String): List<Order>? {
        return orderRepository.findByUsername(username)
    }

    fun updateOrderStatus(id: Long, newStatus: String): Order {
        val order = orderRepository.findById(id).orElseThrow { IllegalArgumentException("Order not found") }
        order.status = newStatus
        return orderRepository.save(order)
    }

    fun deleteOrderById(id: Long) {
        orderRepository.deleteById(id)
    }
}
