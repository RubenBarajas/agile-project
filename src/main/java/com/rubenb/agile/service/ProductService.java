package com.rubenb.agile.service;

import com.rubenb.agile.dto.ProductDto;
import com.rubenb.agile.mapper.ProductMapper;
import com.rubenb.agile.model.Product;
import com.rubenb.agile.repository.ProductRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductById(UUID id) {
        return productRepository.findById(id)
                .map(mapper::toDto);
    }

    public ProductDto createProduct(ProductDto productDto) {
        Product product = mapper.toEntity(productDto);
        return mapper.toDto(productRepository.save(product));
    }

    public ProductDto updateProduct(UUID id, ProductDto productDetails) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        mapper.updateFromDto(productDetails, existingProduct);

        return mapper.toDto(productRepository.save(existingProduct));
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
