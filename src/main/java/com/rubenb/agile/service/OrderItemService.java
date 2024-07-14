package com.rubenb.agile.service;

import com.rubenb.agile.dto.OrderItemDto;
import com.rubenb.agile.exception.NotFoundException;
import com.rubenb.agile.mapper.OrderItemMapper;
import com.rubenb.agile.model.OrderItem;
import com.rubenb.agile.model.Product;
import com.rubenb.agile.repository.OrderItemRepository;
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
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    private final OrderItemMapper mapper = Mappers.getMapper(OrderItemMapper.class);
    private static final Logger logger = LoggerFactory.getLogger(OrderItemService.class);

    public List<OrderItemDto> getAllOrderItems() {
        logger.debug("Getting all order items");
        return orderItemRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderItemDto getOrderItemById(UUID id) {
        logger.debug("Getting order item with ID: {}", id);
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order item not found with id: " + id));
        return mapper.toDto(orderItem);
    }

    public OrderItemDto createOrderItem(OrderItemDto orderItemDto) {
        logger.debug("Creating order item with data: {}", orderItemDto);
        OrderItem orderItem = mapper.toEntity(orderItemDto);

        Product product = productRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + orderItemDto.getProductId()));
        orderItem.setProduct(product);

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return mapper.toDto(savedOrderItem);
    }

    public OrderItemDto updateOrderItem(UUID id, OrderItemDto orderItemDto) {
        logger.debug("Updating order item with ID: {}", id);
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() ->new NotFoundException("Order item not found with id: " + orderItemDto.getProductId()));

        mapper.updateFromDto(orderItemDto, existingOrderItem);

        if (orderItemDto.getProductId() != existingOrderItem.getProduct().getId()) {
            Product product = productRepository.findById(orderItemDto.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found with id: " + orderItemDto));
            existingOrderItem.setProduct(product);
        }
        return mapper.toDto(orderItemRepository.save(existingOrderItem));
    }

    public void deleteOrderItem(UUID id) {
        logger.debug("Deleting order item with ID: {}", id);
        orderItemRepository.deleteById(id);
    }
}
