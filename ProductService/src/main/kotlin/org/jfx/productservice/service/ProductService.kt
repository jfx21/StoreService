package org.jfx.productservice.service

import org.jfx.productservice.model.Product
import org.jfx.productservice.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<Product> = productRepository.findAll()

    fun getProductById(id: Long): Product? = productRepository.findById(id).orElse(null)

    fun createProduct(product: Product): Product = productRepository.save(product)

    fun updateProduct(id: Long, updatedProduct: Product): Product? {
        val existingProduct = productRepository.findById(id).orElse(null) ?: return null
        val productToSave = existingProduct.copy(
            name = updatedProduct.name,
            description = updatedProduct.description,
            price = updatedProduct.price,
            stock = updatedProduct.stock
        )
        return productRepository.save(productToSave)
    }

    fun deleteProduct(id: Long): Boolean {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
            return true
        }
        return false
    }
}
