package com.gormless.programmingexercise.controller;

import com.gormless.programmingexercise.model.OrderItem;
import com.gormless.programmingexercise.service.OrderItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderItemController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemService orderItemService;

    @Test
    @WithMockUser
    void testGetOrderItem() throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setPrice(new BigDecimal("10.00"));
        orderItem.setQuantity(2);
        Mockito.when(orderItemService.getOrderItem(1L)).thenReturn(orderItem);

        mockMvc.perform(get("/api/orderItems/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(orderItem.getId().intValue())))
                .andExpect(jsonPath("$.price", is(10.0))) // Adjusted to expect a double value
                .andExpect(jsonPath("$.quantity", is(orderItem.getQuantity())));
    }

    @Test
    @WithMockUser
    void testFindOrderItems() throws Exception {
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(1L);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(2L);
        List<OrderItem> orderItems = Arrays.asList(orderItem1, orderItem2);

        Mockito.when(orderItemService.getOrderItems()).thenReturn(orderItems);

        mockMvc.perform(get("/api/orderItems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser
    void testCreateOrderItem() throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);

        Mockito.when(orderItemService.postOrderItem(Mockito.any(OrderItem.class))).thenReturn(orderItem);

        mockMvc.perform(post("/api/orderItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\":2,\"price\":\"10.00\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id", is(orderItem.getId().intValue())));
    }

    @Test
    @WithMockUser
    void testUpdateOrderItem() throws Exception {
        OrderItem existingOrderItem = new OrderItem();
        existingOrderItem.setId(1L);

        Mockito.when(orderItemService.putOrderItem(Mockito.eq(1L), Mockito.any(OrderItem.class))).thenReturn(existingOrderItem);

        mockMvc.perform(put("/api/orderItems/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\":3,\"price\":\"15.00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingOrderItem.getId().intValue())));
    }

    @Test
    @WithMockUser
    void testDeleteOrderItem() throws Exception {
        Mockito.doNothing().when(orderItemService).deleteOrderItem(1L);

        mockMvc.perform(delete("/api/orderItems/1"))
                .andExpect(status().isNoContent());
    }
}
