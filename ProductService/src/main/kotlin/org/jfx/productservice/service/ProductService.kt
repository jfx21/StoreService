package org.jfx.productservice.service

import org.jfx.productservice.model.Product
import org.jfx.productservice.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun isAvailable(productId: Long, quantity: Int): Boolean {
        val product = getProductById(productId)
        return product != null && product.stock >= quantity
    }

    fun reduceProductStock(productId: Long, quantity: Int): Boolean {
        val product = getProductById(productId) ?: return false
        if (product.stock < quantity) {
            return false
        }
        product.stock -= quantity
        productRepository.save(product)
        return true
    }
    fun getAllProducts(): List<Product> = productRepository.findAll()

    fun getProductById(id: Long): Product? = productRepository.findById(id).orElse(null)

    fun getProductByName(productName: String): Product? = productRepository.findProductByName(productName)

    fun addProduct(product: Product): Product {
        return productRepository.save(product)
    }

    fun updateProduct(id: Long, updatedProduct: Product): Product? {
        val existingProduct = productRepository.findById(id).orElse(null)
        return if (existingProduct != null) {
            existingProduct.name = updatedProduct.name
            existingProduct.description = updatedProduct.description
            existingProduct.price = updatedProduct.price
            existingProduct.stock = updatedProduct.stock
            productRepository.save(existingProduct)
        } else {
            null
        }
    }

    fun deleteProduct(id: Long): Boolean {
        val product = productRepository.findById(id).orElse(null)
        return if (product != null) {
            productRepository.delete(product)
            true
        } else {
            false
        }
    }
}

