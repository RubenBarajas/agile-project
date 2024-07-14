package com.rubenb.agile.controller;

import com.rubenb.agile.dto.OrderItemDto;
import com.rubenb.agile.exception.NotFoundException;
import com.rubenb.agile.service.OrderItemService;
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

@WebMvcTest(OrderItemController.class)
public class OrderItemControllerTest {

    @MockBean
    private OrderItemService orderItemService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllOrderItems_ReturnsOrderItemList() throws Exception {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(UUID.randomUUID());

        List<OrderItemDto> orderItemDtoList = Collections.singletonList(orderItemDto);

        when(orderItemService.getAllOrderItems()).thenReturn(orderItemDtoList);

        mockMvc.perform(get("/api/v1/order-items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getOrderItemById_ReturnsOrderItem() throws Exception {
        UUID orderItemId = UUID.randomUUID();
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItemId);

        when(orderItemService.getOrderItemById(orderItemId)).thenReturn(orderItemDto);

        mockMvc.perform(get("/api/v1/order-items/{id}", orderItemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getOrderItemById_ReturnsNotFound() throws Exception {
        UUID orderItemId = UUID.randomUUID();

        given(orderItemService.getOrderItemById(orderItemId)).willThrow(new NotFoundException("Order item not found with id: " + orderItemId));

        mockMvc.perform(get("/api/v1/order-items/{id}", orderItemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createOrderItem_ReturnsCreatedOrderItem() throws Exception {
        UUID orderItemId = UUID.randomUUID();
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItemId);

        given(orderItemService.createOrderItem(any(OrderItemDto.class))).willReturn(orderItemDto);

        mockMvc.perform(post("/api/v1/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + orderItemId + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateOrderItem_ReturnsUpdatedOrderItem() throws Exception {
        UUID orderItemId = UUID.randomUUID();
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItemId);

        given(orderItemService.updateOrderItem(any(UUID.class), any(OrderItemDto.class))).willReturn(orderItemDto);

        mockMvc.perform(put("/api/v1/order-items/{id}", orderItemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + orderItemId + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOrderItem_ReturnsNoContent() throws Exception {
        UUID orderItemId = UUID.randomUUID();

        doNothing().when(orderItemService).deleteOrderItem(orderItemId);

        mockMvc.perform(delete("/api/v1/order-items/{id}", orderItemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
