package com.gormless.programmingexercise.controller;

import com.gormless.programmingexercise.model.Product;
import com.gormless.programmingexercise.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Returns all Products", description = "Returns all Products in the system. Will return an empty list if there is no product.")
    public ResponseEntity<List<Product>> findProducts() {
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a Product", description = "Returns a single Product given an id. Returns not found if no Product exists under the id.")
    public ResponseEntity<Product> findProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Creates a Product", description = "Creates a Product. Will throw an error if submitting a model which does not match a Product.")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.postProduct(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedProduct);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a Product", description = "Updates a Product. Will throw not found if the id does not exist.")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.putProduct(id, product);
        if (product != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Product", description = "Deletes a Product. Returns no content.")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
