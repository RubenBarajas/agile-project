package com.rubenb.agile.controller;

import com.rubenb.agile.dto.OrderItemDto;
import com.rubenb.agile.exception.ErrorResponse;
import com.rubenb.agile.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order-items")
@Tag(name = "Order Item Controller")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Operation(
            summary = "Retrieve all order items",
            description = "Retrieve all order items."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = OrderItemDto.class))
                    )
            })
    })
    @GetMapping
    public ResponseEntity<List<OrderItemDto>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    @Operation(
            summary = "Retrieve an order item by ID",
            description = "Retrieve an order item by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = OrderItemDto.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            })
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDto> getOrderItemById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }

    @Operation(
            summary = "Create an order item",
            description = "Create an order item."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = OrderItemDto.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            })
    })
    @PostMapping
    public ResponseEntity<OrderItemDto> createOrderItem(@RequestBody OrderItemDto orderItem) {
        return ResponseEntity.ok(orderItemService.createOrderItem(orderItem));
    }

    @Operation(
            summary = "Update an order item by ID",
            description = "Update an order item by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = OrderItemDto.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            })
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDto> updateOrderItem(@PathVariable UUID id, @RequestBody OrderItemDto orderItemDto) {
        return ResponseEntity.ok(orderItemService.updateOrderItem(id, orderItemDto));
    }

    @Operation(
            summary = "Delete an order item by ID",
            description = "Delete an order item by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable UUID id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
