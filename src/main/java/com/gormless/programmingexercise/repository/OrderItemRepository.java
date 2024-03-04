package com.gormless.programmingexercise.repository;

import com.gormless.programmingexercise.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
