package com.gormless.programmingexercise.service;

import com.gormless.programmingexercise.model.OrderItem;
import com.gormless.programmingexercise.repository.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setPrice(new BigDecimal("100.00"));
        orderItem.setQuantity(2);
        // Assume setting of Product is done here if necessary
    }

    @Test
    void getOrderItems() {
        when(orderItemRepository.findAll()).thenReturn(Arrays.asList(orderItem));
        List<OrderItem> result = orderItemService.getOrderItems();
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(orderItem);
    }

    @Test
    void getOrderItem() {
        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
        OrderItem found = orderItemService.getOrderItem(1L);
        assertThat(found).isEqualTo(orderItem);
    }

    @Test
    void getOrderItemNotFound() {
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.empty());
        OrderItem result = orderItemService.getOrderItem(1L);
        assertThat(result).isNull();
    }

    @Test
    void postOrderItem() {
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        OrderItem saved = orderItemService.postOrderItem(new OrderItem());
        assertThat(saved).isEqualTo(orderItem);
    }

    @Test
    void putOrderItem() {
        OrderItem updatedOrderItem = new OrderItem();
        updatedOrderItem.setPrice(new BigDecimal("150.00"));
        updatedOrderItem.setQuantity(3);
        // Assume updating of Product if necessary

        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
        when(orderItemRepository.save(any(OrderItem.class))).thenAnswer(i -> i.getArguments()[0]); // Return the updated order item

        OrderItem updated = orderItemService.putOrderItem(1L, updatedOrderItem);

        assertThat(updated.getPrice()).isEqualTo(new BigDecimal("150.00")); // Check for updated price
        assertThat(updated.getQuantity()).isEqualTo(3); // Check for updated quantity
    }

    @Test
    void putOrderItemNotFound() {
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderItemService.putOrderItem(1L, new OrderItem()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order Item Not Found");
    }

    @Test
    void deleteOrderItem() {
        doNothing().when(orderItemRepository).deleteById(1L);
        orderItemService.deleteOrderItem(1L);
        verify(orderItemRepository, times(1)).deleteById(1L);
    }
}
