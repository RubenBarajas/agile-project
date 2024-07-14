package com.rubenb.agile.service;

import com.rubenb.agile.dto.ProductDto;
import com.rubenb.agile.exception.NotFoundException;
import com.rubenb.agile.mapper.ProductMapper;
import com.rubenb.agile.model.Product;
import com.rubenb.agile.repository.ProductRepository;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<ProductDto> getAllProducts() {
        logger.debug("Getting all products");
        return productRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(UUID id) {
        logger.debug("Getting product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        return mapper.toDto(product);
    }

    public ProductDto createProduct(ProductDto productDto) {
        logger.debug("Creating product with data: {}", productDto);
        Product product = mapper.toEntity(productDto);
        return mapper.toDto(productRepository.save(product));
    }

    public ProductDto updateProduct(UUID id, ProductDto productDetails) {
        logger.debug("Updating product with ID: {}", id);
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        mapper.updateFromDto(productDetails, existingProduct);
        return mapper.toDto(productRepository.save(existingProduct));
    }

    public void deleteProduct(UUID id) {
        logger.debug("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
    }
}
