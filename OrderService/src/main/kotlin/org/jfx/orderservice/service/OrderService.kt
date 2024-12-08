package org.jfx.orderservice.service


import org.jfx.orderservice.model.Order
import org.jfx.orderservice.repository.OrderRepository
import org.jfx.productservice.model.Product
import org.jfx.productservice.service.ProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class OrderService(
    private val orderRepository: OrderRepository,
    private val productService: ProductService  // Inject ProductService here
) {
    @Transactional
    open fun createOrder(order: Order): Order {
        val orderWithStatus = order.copy(status = order.status.takeIf { it!!.isNotEmpty() } ?: "PENDING")

        // Check product availability
        var i = 0;
        var product: MutableList<Product> = mutableListOf()
        while( i< order.products.size){
         product.add(productService.getProductByName(orderWithStatus.products[i].productName)
            ?: throw IllegalArgumentException("Product not found"))
            if (!product[i].let { productService.isAvailable(product[i].id!!, orderWithStatus.products[i].quantity.toInt()) }) {
                throw IllegalArgumentException("Not enough stock for product: ${product[i].name}")
            }
            product[i].id?.let { productService.reduceProductStock(it, product[i].stock) } //reducing product stock in db
            i++
        }


        // Save the order
        return orderRepository.save(orderWithStatus)
    }

    fun getOrderById(id: Long): Order? {
        return orderRepository.findById(id).orElse(null)
    }

    fun getOrdersByUserId(userId: Long): List<Order> {
        return orderRepository.findByUserId(userId)
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
