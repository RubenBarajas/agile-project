package com.rubenb.agile.controller;

import com.rubenb.agile.dto.OrderDto;
import com.rubenb.agile.exception.ErrorResponse;
import com.rubenb.agile.service.OrderService;
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
@RequestMapping("/api/v1/orders")
@Tag(name = "Order Controller")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(
            summary = "Retrieve all orders",
            description = "Retrieve all orders."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = OrderDto.class))
                    )
            })
    })
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(
            summary = "Retrieve an order by ID",
            description = "Retrieve an order by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = OrderDto.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            })
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(
            summary = "Create an order",
            description = "Create an order."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = OrderDto.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            })
    })
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }

    @Operation(
            summary = "Update an order by ID",
            description = "Update an order by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = OrderDto.class)
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
    public ResponseEntity<OrderDto> updateOrder(@PathVariable UUID id, @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderDto));
    }

    @Operation(
            summary = "Delete an order by ID",
            description = "Delete an order by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}

