package org.jfx.productservice.repository

import org.jfx.productservice.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository("ProductRepository")
interface ProductRepository : JpaRepository<Product, Long>{
    fun findProductById(id: Long): Product?
    fun deleteProductById(id: Long)
    fun findProductByName(productName: String): Product?
}
