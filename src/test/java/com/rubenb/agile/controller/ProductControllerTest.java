package com.rubenb.agile.controller;

import com.rubenb.agile.dto.ProductDto;
import com.rubenb.agile.exception.NotFoundException;
import com.rubenb.agile.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllProducts_ReturnsProductList() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(UUID.randomUUID());

        List<ProductDto> productDtoList = Collections.singletonList(productDto);

        when(productService.getAllProducts()).thenReturn(productDtoList);

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getProductById_ReturnsProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        productDto.setId(productId);

        when(productService.getProductById(productId)).thenReturn(productDto);

        mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getProductById_ReturnsNotFound() throws Exception {
        UUID productId = UUID.randomUUID();

        given(productService.getProductById(productId)).willThrow(new NotFoundException("Product not found with id: " + productId));

        mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createProduct_ReturnsCreatedProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        productDto.setId(productId);

        given(productService.createProduct(any(ProductDto.class))).willReturn(productDto);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + productId + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProduct_ReturnsUpdatedProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        ProductDto productDto = new ProductDto();
        productDto.setId(productId);

        given(productService.updateProduct(any(UUID.class), any(ProductDto.class))).willReturn(productDto);

        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + productId + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProduct_ReturnsNoContent() throws Exception {
        UUID productId = UUID.randomUUID();

        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
