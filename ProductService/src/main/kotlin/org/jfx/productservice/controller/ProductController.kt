package org.jfx.productservice.controller

import org.jfx.productservice.model.Product
import org.jfx.productservice.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = ["http://localhost:3000"])
open class ProductController(private val productService: ProductService) {

    // Get all products
    @GetMapping
    fun getAllProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return if (products.isNotEmpty()) {
            ResponseEntity.ok(products)
        } else {
            ResponseEntity.noContent().build()  // Returns 204 No Content if no products found
        }
    }

    // Get product by ID
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<Product> {
        val product = productService.getProductById(id)
        return if (product != null) {
            ResponseEntity.ok(product)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)  // 404 Not Found if product not found
        }
    }

    // Add a new product
    @PostMapping
    fun addProduct(@RequestBody product: Product): ResponseEntity<Product> {
        return try {
            val savedProduct = productService.addProduct(product)
            ResponseEntity.status(HttpStatus.CREATED).body(savedProduct)  // 201 Created if successful
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)  // 400 Bad Request if failed
        }
    }

    // Update product details
    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody updatedProduct: Product
    ): ResponseEntity<Product> {
        return try {
            val product = productService.updateProduct(id, updatedProduct)
            if (product != null) {
                ResponseEntity.ok(product)  // 200 OK if update successful
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()  // 404 Not Found if product does not exist
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)  // 400 Bad Request if update failed
        }
    }

    // Delete product by ID
    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        return if (productService.deleteProduct(id)) {
            ResponseEntity.noContent().build()  // 204 No Content if product is deleted successfully
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()  // 404 Not Found if product doesn't exist
        }
    }

    // Additional endpoint to handle product stock availability check
    @GetMapping("/{id}/availability/{quantity}")
    fun checkProductAvailability(
        @PathVariable id: Long,
        @PathVariable quantity: Int
    ): ResponseEntity<String> {
        val product = productService.getProductById(id)
        return if (product != null && product.isAvailable(quantity)) {
            ResponseEntity.ok("Product is available in the required quantity.")
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Not enough stock for product with ID: $id")
        }
    }
}
