package com.gormless.programmingexercise.repository;

import com.gormless.programmingexercise.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
