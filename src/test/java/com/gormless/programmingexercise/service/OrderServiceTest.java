package com.gormless.programmingexercise.service;

import com.gormless.programmingexercise.model.Order;
import com.gormless.programmingexercise.model.Product;
import com.gormless.programmingexercise.repository.OrderRepository;
import com.gormless.programmingexercise.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testGetOrder() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order found = orderService.getOrder(orderId);

        assertThat(found.getId()).isEqualTo(orderId);
    }

    @Test
    void testGetOrders() {
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);
        List<Order> orders = Arrays.asList(order1, order2);

        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        List<Order> foundOrders = orderService.getOrders();

        assertThat(foundOrders).hasSize(2);
        assertThat(foundOrders.get(0).getId()).isEqualTo(1L);
        assertThat(foundOrders.get(1).getId()).isEqualTo(2L);
    }

    @Test
    void testPostOrder() {
        Order order = new Order();
        // Assuming you want to test setting a price on the order
        order.setPrice(new BigDecimal("100.00"));

        Mockito.when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        Order savedOrder = orderService.postOrder(order);

        assertThat(savedOrder.getPrice()).isEqualTo(new BigDecimal("100.00"));
    }

    @Test
    void testPutOrder() {
        Long orderId = 1L;
        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        existingOrder.setPrice(new BigDecimal("100.00"));

        Order updatedOrder = new Order();
        updatedOrder.setPrice(new BigDecimal("150.00"));

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        Mockito.when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        Order result = orderService.putOrder(orderId, updatedOrder);

        assertThat(result.getPrice()).isEqualTo(new BigDecimal("150.00"));
    }

    @Test
    void testPutOrder_NotFound() {
        Long orderId = 1L;
        Order updatedOrder = new Order();
        updatedOrder.setPrice(new BigDecimal("150.00"));

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.putOrder(orderId, updatedOrder))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order Not Found");
    }

    @Test
    void testDeleteOrder() {
        Long orderId = 1L;

        Mockito.doNothing().when(orderRepository).deleteById(orderId);

        orderService.deleteOrder(orderId);

        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(orderId);
    }
}




