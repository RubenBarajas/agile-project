package com.rubenb.agile.service;

import com.rubenb.agile.dto.OrderItemDto;
import com.rubenb.agile.mapper.OrderItemMapper;
import com.rubenb.agile.model.OrderItem;
import com.rubenb.agile.model.Product;
import com.rubenb.agile.repository.OrderItemRepository;
import com.rubenb.agile.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    private final OrderItemMapper mapper = Mappers.getMapper(OrderItemMapper.class);

    public List<OrderItemDto> getAllOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<OrderItemDto> getOrderItemById(UUID id) {
        return orderItemRepository.findById(id)
                .map(mapper::toDto);
    }

    public OrderItemDto createOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = mapper.toEntity(orderItemDto);

        Product product = productRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        orderItem.setProduct(product);

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        return mapper.toDto(savedOrderItem);
    }

    public OrderItemDto updateOrderItem(UUID id, OrderItemDto orderItemDto) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        mapper.updateFromDto(orderItemDto, existingOrderItem);

        if (orderItemDto.getProductId() != existingOrderItem.getProduct().getId()) {
            Product product = productRepository.findById(orderItemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            existingOrderItem.setProduct(product);
        }

        return mapper.toDto(orderItemRepository.save(existingOrderItem));
    }

    public void deleteOrderItem(UUID id) {
        orderItemRepository.deleteById(id);
    }
}
