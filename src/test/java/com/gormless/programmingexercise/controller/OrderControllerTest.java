package com.gormless.programmingexercise.controller;

import com.gormless.programmingexercise.model.Order;
import com.gormless.programmingexercise.service.OrderService;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser
    void testGetOrder() throws Exception {
        Order order = new Order();
        order.setId(1L);
        // Assume setting of other properties if necessary

        Mockito.when(orderService.getOrder(1L)).thenReturn(order);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(order.getId().intValue())));
        // Include additional assertions for other properties as necessary
    }

    @Test
    @WithMockUser
    void testFindOrders() throws Exception {
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);
        List<Order> orders = Arrays.asList(order1, order2);

        Mockito.when(orderService.getOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser
    void testCreateOrder() throws Exception {
        Order order = new Order();
        order.setId(1L); // Assume setting of other properties if necessary

        Mockito.when(orderService.postOrder(Mockito.any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Adjust the JSON content according to your Order model
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id", is(order.getId().intValue())));
        // Include additional assertions for other properties as necessary
    }

    @Test
    @WithMockUser
    void testUpdateOrder() throws Exception {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        // Assume setting of other properties if necessary

        Mockito.when(orderService.putOrder(Mockito.eq(1L), Mockito.any(Order.class))).thenReturn(existingOrder);

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Adjust the JSON content according to your Order model
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingOrder.getId().intValue())));
        // Include additional assertions for other properties as necessary
    }

    @Test
    @WithMockUser
    void testDeleteOrder() throws Exception {
        Mockito.doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());
    }
}
