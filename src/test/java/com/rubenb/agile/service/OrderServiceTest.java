package com.rubenb.agile.service;

import com.rubenb.agile.dto.OrderDto;
import com.rubenb.agile.mapper.OrderMapper;
import com.rubenb.agile.model.Order;
import com.rubenb.agile.model.OrderItem;
import com.rubenb.agile.repository.OrderItemRepository;
import com.rubenb.agile.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderMapper mapper;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderDto orderDto;
    private OrderItem orderItem;
    private UUID orderId;
    private UUID orderItemId;

    @BeforeEach
    public void setUp() {
        orderId = UUID.randomUUID();
        orderItemId = UUID.randomUUID();

        ArrayList<UUID> orderItemIds = new ArrayList<>();
        orderItemIds.add(orderItemId);

        orderItem = new OrderItem();
        orderItem.setId(orderItemId);
        orderItem.setQuantity(2);
        orderItem.setPriceInCents(2000);

        ArrayList<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        order = new Order();
        order.setId(orderId);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(4000);
        order.setStatus("NEW");
        order.setOrderItems(orderItems);

        orderDto = new OrderDto();
        orderDto.setId(orderId);
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setTotalAmount(4000);
        orderDto.setStatus("NEW");
        orderDto.setOrderItemIds(orderItemIds);
    }

    @Test
    public void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Stream.of(order).collect(Collectors.toList()));

        List<OrderDto> orderDtos = orderService.getAllOrders();

        assertNotNull(orderDtos);
        assertEquals(1, orderDtos.size());
        assertEquals(orderId, orderDtos.get(0).getId());
    }

    @Test
    public void testGetOrderById() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        OrderDto retrievedOrder = orderService.getOrderById(orderId);

        assertNotNull(retrievedOrder);
        assertEquals(orderId, retrievedOrder.getId());
    }

    @Test
    public void testCreateOrder() {
        when(orderItemRepository.existsById(any(UUID.class))).thenReturn(true);
        when(orderItemRepository.findAllById(anyList())).thenReturn(List.of(orderItem));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDto createdOrder = orderService.createOrder(orderDto);

        assertNotNull(createdOrder);
        assertEquals(orderId, createdOrder.getId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testUpdateOrder() {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.existsById(any(UUID.class))).thenReturn(true);
        when(orderItemRepository.findAllById(anyList())).thenReturn(orderItems);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDto updatedOrder = orderService.updateOrder(orderId, orderDto);

        assertNotNull(updatedOrder);
        assertEquals(orderId, updatedOrder.getId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testDeleteOrder() {
        doNothing().when(orderRepository).deleteById(orderId);

        orderService.deleteOrder(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
