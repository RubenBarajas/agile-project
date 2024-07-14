package com.rubenb.agile.service;

import com.rubenb.agile.dto.OrderDto;
import com.rubenb.agile.exception.BadRequestException;
import com.rubenb.agile.exception.NotFoundException;
import com.rubenb.agile.mapper.OrderMapper;
import com.rubenb.agile.model.Order;
import com.rubenb.agile.model.OrderItem;
import com.rubenb.agile.repository.OrderItemRepository;
import com.rubenb.agile.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public List<OrderDto> getAllOrders() {
        logger.debug("Getting all orders");
        return orderRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(UUID id) {
        logger.debug("Getting order with ID: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
        return mapper.toDto(order);
    }

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        logger.debug("Creating order with data: {}", orderDto);
        validateOrderItemUuids(orderDto.getOrderItemIds());

        Order order = mapper.toEntity(orderDto);
        List<OrderItem> existingOrderItems = orderItemRepository.findAllById(orderDto.getOrderItemIds());
        order.setOrderItems(existingOrderItems);

        Order savedOrder = orderRepository.save(order);
        for (OrderItem existingOrderItem : existingOrderItems) {
            existingOrderItem.setOrder(savedOrder);
        }

        return mapper.toDto(savedOrder);
    }

    @Transactional
    public OrderDto updateOrder(UUID id, OrderDto orderDto) {
        logger.debug("Updating order with ID: {}", id);
        validateOrderItemUuids(orderDto.getOrderItemIds());

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
        mapper.updateFromDto(orderDto, existingOrder);

        List<OrderItem> existingOrderItems = orderItemRepository.findAllById(orderDto.getOrderItemIds());
        existingOrder.getOrderItems().clear();
        existingOrder.getOrderItems().addAll(existingOrderItems);

        for (OrderItem existingOrderItem : existingOrderItems) {
            existingOrderItem.setOrder(existingOrder);
        }

        Order updatedOrder = orderRepository.save(existingOrder);

        return mapper.toDto(updatedOrder);
    }

    public void deleteOrder(UUID id) {
        logger.debug("Deleting order with ID: {}", id);
        orderRepository.deleteById(id);
    }

    private void validateOrderItemUuids(List<UUID> orderItemIds) {
        for (UUID orderId : orderItemIds) {
            if (!orderItemRepository.existsById(orderId)) {
                throw new BadRequestException("Invalid OrderItem UUID: " + orderId);
            }
        }
    }
}
