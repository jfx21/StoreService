package org.jfx.orderservice.service


import org.jfx.orderservice.model.Order
import org.jfx.orderservice.model.request.OrderRequest
import org.jfx.orderservice.repository.OrderRepository
import org.jfx.productservice.model.Product
import org.jfx.productservice.service.ProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
open class OrderService(
    private val orderRepository: OrderRepository,
    private val productService: ProductService  // Inject ProductService here
) {
    @Transactional
    open fun createOrder(orderRequest: OrderRequest): Order {
        // Process the product details and calculate the total price
        val productDetails = orderRequest.products.map { productOrder ->
            val product = productService.getProductByName(productOrder.productName)
                ?: throw IllegalArgumentException("Product not found: ${productOrder.productName}")

            // Check if the product is available
            if (!product.isAvailable(productOrder.quantity)) {
                throw IllegalArgumentException("Not enough stock for product: ${product.name}")
            }

            // Reduce the stock of the product
            productService.reduceProductStock(product.id!!, productOrder.quantity)

            // Calculate the total price for this product (price * quantity)
            val totalPriceForProduct = product.price.toDouble() * productOrder.quantity

            // Return the product ID and the calculated total price for this product
            product.id to totalPriceForProduct
        }

        // Sum up the total price for all products in the order
        val totalPrice = productDetails.sumOf { it.second }

        // Ensure totalPrice is not null or zero
        if (totalPrice <= 0) {
            throw IllegalArgumentException("Total price must be greater than zero")
        }

        // Create the Order object
        val order = Order(
            userName = orderRequest.username,
            productIds = productDetails.map { it.first!! },  // List of product IDs
            address = orderRequest.address,
            phoneNumber = orderRequest.phoneNumber,
            totalPrice = totalPrice  // Ensure this value is set properly
        )
        println(order)

        // Persist the order to the database
        return orderRepository.save(order)
    }




    fun getOrderById(id: Long): Order? {
        return orderRepository.findById(id).orElse(null)
    }

    fun getOrdersByUserName(userName: String): List<Order>? {
        return orderRepository.findByUserName(userName)
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
