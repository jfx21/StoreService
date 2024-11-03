package org.jfx.orderservice.service

import jakarta.transaction.Transactional
import org.jfx.orderservice.model.Order
import org.jfx.orderservice.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
open class OrderService(private val orderRepository: OrderRepository) {

    @Transactional
    open fun createOrder(order: Order): Order {
        return orderRepository.save(order)
    }

    fun getOrderById(id: Long): Order? {
        return orderRepository.findById(id).orElse(null)
    }

    fun getAllOrders(): List<Order> {
        return orderRepository.findAll()
    }

    @Transactional
    open fun deleteOrderById(id: Long) {
        orderRepository.deleteById(id)
    }
}