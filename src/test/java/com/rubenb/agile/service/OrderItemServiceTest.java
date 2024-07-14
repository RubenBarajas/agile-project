package com.rubenb.agile.service;


import com.rubenb.agile.dto.OrderItemDto;
import com.rubenb.agile.model.OrderItem;
import com.rubenb.agile.model.Product;
import com.rubenb.agile.repository.OrderItemRepository;
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
public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    private OrderItem orderItem;
    private OrderItemDto orderItemDto;
    private Product product;
    private UUID orderItemId;
    private UUID productId;

    @BeforeEach
    public void setUp() {
        orderItemId = UUID.randomUUID();
        productId = UUID.randomUUID();

        product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPriceInCents(1000);
        product.setWeightInGrams(500);

        orderItem = new OrderItem();
        orderItem.setId(orderItemId);
        orderItem.setQuantity(2);
        orderItem.setPriceInCents(2000);
        orderItem.setProduct(product);

        orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItemId);
        orderItemDto.setQuantity(2);
        orderItemDto.setPriceInCents(2000);
        orderItemDto.setProductId(productId);
    }

    @Test
    public void testGetAllOrderItems() {
        when(orderItemRepository.findAll()).thenReturn(Stream.of(orderItem).collect(Collectors.toList()));

        List<OrderItemDto> orderItemDtos = orderItemService.getAllOrderItems();

        assertNotNull(orderItemDtos);
        assertEquals(1, orderItemDtos.size());
        assertEquals(orderItemId, orderItemDtos.getFirst().getId());
    }

    @Test
    public void testGetOrderItemById() {
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));

        OrderItemDto retrievedOrderItem = orderItemService.getOrderItemById(orderItemId);

        assertNotNull(retrievedOrderItem);
        assertEquals(orderItemId, retrievedOrderItem.getId());
    }

    @Test
    public void testCreateOrderItem() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

        OrderItemDto createdOrderItem = orderItemService.createOrderItem(orderItemDto);

        assertNotNull(createdOrderItem);
        assertEquals(orderItemId, createdOrderItem.getId());
    }

    @Test
    public void testUpdateOrderItem() {
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

        OrderItemDto updatedOrderItem = orderItemService.updateOrderItem(orderItemId, orderItemDto);

        assertNotNull(updatedOrderItem);
        assertEquals(orderItemId, updatedOrderItem.getId());
    }

    @Test
    public void testDeleteOrderItem() {
        doNothing().when(orderItemRepository).deleteById(orderItemId);

        orderItemService.deleteOrderItem(orderItemId);

        verify(orderItemRepository, times(1)).deleteById(orderItemId);
    }
}
