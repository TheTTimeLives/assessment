package com.gormless.programmingexercise.service;

import com.gormless.programmingexercise.model.Product;
import com.gormless.programmingexercise.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ProductServiceTest {
    @Mock
    private ProductRepository repository;
    @InjectMocks
    private ProductService service;

    @Test
    void testGetProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        Mockito.when(repository.findById(productId)).thenReturn(Optional.of(product));
        Product found = service.getProduct(productId);
        assertThat(found.getId()).isEqualTo(productId);
    }

    @Test
    void testGetProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);
        List<Product> products = Arrays.asList(product1, product2);

        Mockito.when(repository.findAll()).thenReturn(products);

        List<Product> foundProducts = service.getProducts();

        assertThat(foundProducts).hasSize(2);
        assertThat(foundProducts.get(0).getId()).isEqualTo(1L);
        assertThat(foundProducts.get(1).getId()).isEqualTo(2L);
    }

    @Test
    void testPostProduct() {
        Product product = new Product();
        product.setName("New Product");

        Mockito.when(repository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);

        Product savedProduct = service.postProduct(product);

        assertThat(savedProduct.getName()).isEqualTo("New Product");
    }

    @Test
    void testPutProduct() {
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Product");

        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");

        Mockito.when(repository.findById(productId)).thenReturn(Optional.of(existingProduct));
        Mockito.when(repository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);

        Product result = service.putProduct(productId, updatedProduct);

        assertThat(result.getName()).isEqualTo("Updated Product");
    }

    @Test
    void testPutProduct_NotFound() {
        Long productId = 1L;
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");

        Mockito.when(repository.findById(productId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.putProduct(productId, updatedProduct))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order Item Not Found");
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;

        Mockito.doNothing().when(repository).deleteById(productId);

        service.deleteProduct(productId);

        Mockito.verify(repository, Mockito.times(1)).deleteById(productId);
    }
}
