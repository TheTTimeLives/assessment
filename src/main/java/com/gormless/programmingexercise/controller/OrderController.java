package com.gormless.programmingexercise.controller;

import com.gormless.programmingexercise.model.Order;
import com.gormless.programmingexercise.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(summary = "Returns all Orders", description = "Returns all Orders in the system. Will return an empty list if there is no data.")
    public ResponseEntity<List<Order>> findOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns an Order", description = "Returns a single Order given an id. Returns not found if no Order exists under the id.")
    public ResponseEntity<Order> findOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Creates an Order", description = "Creates an Order. Will throw an error if submitting a model which does not match an Order.")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.postOrder(order);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id")
                .buildAndExpand(savedOrder.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedOrder);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an Order", description = "Updates an Order. Will throw not found if the id does not exist.")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order updatedOrder = orderService.putOrder(id, order);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an Order", description = "Deletes an Order. Returns no content.")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
