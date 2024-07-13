package com.rubenb.agile.service;

import com.rubenb.agile.dto.OrderDto;
import com.rubenb.agile.mapper.OrderMapper;
import com.rubenb.agile.model.Order;
import com.rubenb.agile.model.OrderItem;
import com.rubenb.agile.repository.OrderItemRepository;
import com.rubenb.agile.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    public List<OrderDto> getAllOrders() {
        List<Order> existingOrders = orderRepository.findAll();

        return orderRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<OrderDto> getOrderById(UUID id) {
        return orderRepository.findById(id)
                .map(mapper::toDto);
    }

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
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

    public OrderDto updateOrder(UUID id, OrderDto orderDto) {
        validateOrderItemUuids(orderDto.getOrderItemIds());

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        mapper.updateFromDto(orderDto, existingOrder);
        Order updatedOrder = orderRepository.save(existingOrder);
        return mapper.toDto(updatedOrder);
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    private void validateOrderItemUuids(List<UUID> orderItemIds) {
        for (UUID orderId : orderItemIds) {
            if (!orderItemRepository.existsById(orderId)) {
                throw new IllegalArgumentException("Invalid OrderItem UUID: " + orderId);
            }
        }
    }
}
