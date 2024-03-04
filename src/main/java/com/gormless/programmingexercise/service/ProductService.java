package com.gormless.programmingexercise.service;

import com.gormless.programmingexercise.model.Product;
import com.gormless.programmingexercise.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product postProduct(Product product) {
        return productRepository.save(product);
    }

    public Product putProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setStockQuantity(updatedProduct.getStockQuantity());

            return productRepository.save(product);
        }).orElseThrow(() -> new EntityNotFoundException("Order Item Not Found"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
