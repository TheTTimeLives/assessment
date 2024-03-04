package com.gormless.programmingexercise.service;

import com.gormless.programmingexercise.model.OrderItem;
import com.gormless.programmingexercise.repository.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItemRepository.findAll();
    }

    public OrderItem getOrderItem(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    public OrderItem postOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public OrderItem putOrderItem(Long id, OrderItem updatedOrderItem) {
        return orderItemRepository.findById(id).map(orderItem -> {
            orderItem.setProduct(updatedOrderItem.getProduct());
            orderItem.setQuantity(updatedOrderItem.getQuantity());
            orderItem.setPrice(updatedOrderItem.getPrice());

            return orderItemRepository.save(orderItem);
        }).orElseThrow(() -> new EntityNotFoundException("Order Item Not Found"));
    }

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

}
