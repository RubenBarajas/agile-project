package com.rubenb.agile.service;

import com.rubenb.agile.dto.ProductDto;
import com.rubenb.agile.model.Product;
import com.rubenb.agile.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDto productDto;
    private UUID productId;

    @BeforeEach
    public void setUp() {
        productId = UUID.randomUUID();
        product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPriceInCents(1000);
        product.setWeightInGrams(500);

        productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setName("Test Product");
        productDto.setDescription("Test Description");
        productDto.setPriceInCents(1000);
        productDto.setWeightInGrams(500);
    }

    @Test
    public void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Stream.of(product).collect(Collectors.toList()));

        List<ProductDto> productDtos = productService.getAllProducts();

        assertNotNull(productDtos);
        assertEquals(1, productDtos.size());
        assertEquals("Test Product", productDtos.getFirst().getName());
    }

    @Test
    public void testGetProductById() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductDto retrievedProduct = productService.getProductById(productId);

        assertNotNull(retrievedProduct);
        assertEquals("Test Product", retrievedProduct.getName());
    }

    @Test
    public void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDto createdProduct = productService.createProduct(productDto);

        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
    }

    @Test
    public void testUpdateProduct() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDto updatedProduct = productService.updateProduct(productId, productDto);

        assertNotNull(updatedProduct);
        assertEquals("Test Product", updatedProduct.getName());
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}
