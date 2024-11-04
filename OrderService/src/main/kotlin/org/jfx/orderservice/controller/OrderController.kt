package org.jfx.orderservice.controller

import org.apache.coyote.Response
import org.jfx.orderservice.model.Order
import org.jfx.orderservice.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
@Validated
class OrderController(private val orderService: OrderService) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(@RequestBody order: Order): ResponseEntity<Order> {
        val savedOrder =  orderService.createOrder(order)
        return ResponseEntity.ok(savedOrder)
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: Long): Order? {
        return orderService.getOrderById(id)
    }

    @GetMapping("/user/{userId}")
    fun getOrdersByUserId(@PathVariable userId: Long): List<Order> {
        return orderService.getOrdersByUserId(userId)
    }

    @PatchMapping("/{id}/status")
    fun updateOrderStatus(
        @PathVariable id: Long,
        @RequestParam newStatus: String
    ): Order {
        return orderService.updateOrderStatus(id, newStatus)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrder(@PathVariable id: Long) {
        orderService.deleteOrderById(id)
    }
}