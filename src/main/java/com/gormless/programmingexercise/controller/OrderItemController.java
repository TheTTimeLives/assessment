package com.gormless.programmingexercise.controller;

import com.gormless.programmingexercise.model.OrderItem;
import com.gormless.programmingexercise.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orderItems")
public class OrderItemController {

    public final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    @Operation(summary = "Returns all OrderItems", description = "Returns all OrderItems in the system. Will return an empty list if there is no data.")
    public ResponseEntity<List<OrderItem>> findOrderItems() {
       List<OrderItem> orderItems = orderItemService.getOrderItems();
       return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns an OrderItem", description = "Returns a single OrderItem given an id. Returns not found if no OrderItem exists under the id.")
    public ResponseEntity<OrderItem> findOrderItem(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.getOrderItem(id);
        if (orderItem != null) {
            return ResponseEntity.ok(orderItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Creates an OrderItem", description = "Creates an OrderItem. Will throw an error if submitting a model which does not match an OrderItem.")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
        OrderItem savedOrderItem = orderItemService.postOrderItem(orderItem);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id")
                .buildAndExpand(savedOrderItem.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedOrderItem);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an OrderItem", description = "Updates an OrderItem. Will throw not found if the id does not exist.")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        OrderItem updatedOrderItem = orderItemService.putOrderItem(id, orderItem);
        if (updatedOrderItem != null) {
            return ResponseEntity.ok(updatedOrderItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an OrderItem", description = "Deletes an OrderItem. Returns no content.")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
