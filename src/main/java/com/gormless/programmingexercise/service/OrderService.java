package com.gormless.programmingexercise.service;

import com.gormless.programmingexercise.model.Order;
import com.gormless.programmingexercise.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order postOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order putOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setOrderItems(updatedOrder.getOrderItems());
            order.setQuantity(updatedOrder.getQuantity());
            order.setPrice(updatedOrder.getPrice());

            return orderRepository.save(order);
        }).orElseThrow(() -> new EntityNotFoundException("Order Not Found"));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
