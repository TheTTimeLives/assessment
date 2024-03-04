package com.gormless.programmingexercise.controller;

import com.gormless.programmingexercise.model.Product;
import com.gormless.programmingexercise.service.ProductService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // Correct import
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService service;

    @Test
    @WithMockUser
    void testGetProduct() throws Exception {
        Product product = new Product();
        product.setId(1L);
        Mockito.when(service.getProduct(1L)).thenReturn(product);
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id", is(product.getId().intValue())));
    }

    @Test
    @WithMockUser
    void testFindProducts() throws Exception {
        Product product1 = new Product(); // Consider setting properties for more detailed assertions
        Product product2 = new Product();
        List<Product> products = Arrays.asList(product1, product2);

        Mockito.when(service.getProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser
    void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setId(1L); // Mock the behavior to return a product with an ID after creation

        Mockito.when(service.postProduct(Mockito.any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Product\",\"description\":\"Test Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id", is(product.getId().intValue())));
    }

    @Test
    @WithMockUser
    void testUpdateProduct() throws Exception {
        Product existingProduct = new Product();
        existingProduct.setId(1L);

        Mockito.when(service.putProduct(Mockito.eq(1L), Mockito.any(Product.class))).thenReturn(existingProduct);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Product\",\"description\":\"Updated Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingProduct.getId().intValue())));
    }

    @Test
    @WithMockUser
    void testDeleteProduct() throws Exception {
        Mockito.doNothing().when(service).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}
