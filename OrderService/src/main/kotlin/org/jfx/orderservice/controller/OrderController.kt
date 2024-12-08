package org.jfx.orderservice.controller

import org.jfx.orderservice.model.Order
import org.jfx.orderservice.model.request.OrderRequest
import org.jfx.orderservice.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = ["http://localhost:3000"])
open class OrderController(private val orderService: OrderService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    open fun createOrder(@RequestBody order: OrderRequest): ResponseEntity<Any> {
        return try {
            val savedOrder = orderService.createOrder(order)
            ResponseEntity.ok(savedOrder)
        } catch (e: IllegalArgumentException) {
            val errorMessage = e.message ?: "Invalid order data"
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to errorMessage))
        }
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: Long): ResponseEntity<Order> {
        return orderService.getOrderById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/user/{userId}")
    fun getOrdersByUserId(@PathVariable userId: Long): List<Order> {
        return orderService.getOrdersByUserId(userId)
    }

    @PatchMapping("/{id}/status")
    fun updateOrderStatus(
        @PathVariable id: Long,
        @RequestParam newStatus: String
    ): ResponseEntity<Order> {
        val updatedOrder = orderService.updateOrderStatus(id, newStatus)
        return ResponseEntity.ok(updatedOrder)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrder(@PathVariable id: Long) {
        orderService.deleteOrderById(id)
    }
}
