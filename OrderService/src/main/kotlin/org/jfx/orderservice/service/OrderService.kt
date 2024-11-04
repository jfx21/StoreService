package org.jfx.orderservice.service

import jakarta.transaction.Transactional
import org.jfx.orderservice.model.Order
import org.jfx.orderservice.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class OrderService(private val orderRepository: OrderRepository) {

    fun createOrder(order: Order): Order {
        return orderRepository.save(order)
    }

    fun getOrderById(id: Long): Order {
        return orderRepository.findById(id).orElse(null)
    }
    fun getOrdersByUserId(userId:Long) : List<Order>{
        return orderRepository.findByUserId(userId)
    }

    fun getAllOrders(): List<Order> {
        return orderRepository.findAll()
    }
    @Transactional
    open fun updateOrderStatus(orderId: Long, newStatus: String): Order{
        val order = getOrderById(orderId)
        val updatedOrder = order.copy(status = newStatus)
        return orderRepository.save(updatedOrder)
    }

    @Transactional
    open fun deleteOrderById(id: Long) {
        orderRepository.deleteById(id)
    }
}