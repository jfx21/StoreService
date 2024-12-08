package org.jfx.orderservice.repository

import org.jfx.orderservice.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository("OrderRepository")
interface OrderRepository : JpaRepository<Order, Long>{
    fun findByUserName(username: String): List<Order>?
}
