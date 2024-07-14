package com.rubenb.agile.controller;

import com.rubenb.agile.dto.OrderDto;
import com.rubenb.agile.exception.NotFoundException;
import com.rubenb.agile.service.OrderService;
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

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllOrders_ReturnsOrderList() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(UUID.randomUUID());

        List<OrderDto> orderDtoList = Collections.singletonList(orderDto);

        when(orderService.getAllOrders()).thenReturn(orderDtoList);

        mockMvc.perform(get("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getOrderById_ReturnsOrder() throws Exception {
        UUID orderId = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);

        given(orderService.getOrderById(orderId)).willReturn(orderDto);

        mockMvc.perform(get("/api/v1/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getOrderById_ReturnsNotFound() throws Exception {
        UUID orderId = UUID.randomUUID();

        given(orderService.getOrderById(orderId)).willThrow(new NotFoundException("Order not found with id: " + orderId));

        mockMvc.perform(get("/api/v1/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createOrder_ReturnsCreatedOrder() throws Exception {
        UUID orderId = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);

        given(orderService.createOrder(any(OrderDto.class))).willReturn(orderDto);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + orderId + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateOrder_ReturnsUpdatedOrder() throws Exception {
        UUID orderId = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);

        given(orderService.updateOrder(any(UUID.class), any(OrderDto.class))).willReturn(orderDto);

        mockMvc.perform(put("/api/v1/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + orderId + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOrder_ReturnsNoContent() throws Exception {
        UUID orderId = UUID.randomUUID();

        doNothing().when(orderService).deleteOrder(orderId);

        mockMvc.perform(delete("/api/v1/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
