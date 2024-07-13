package com.rubenb.agile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {

    @Schema(description = "Order ID", example = "12345678-abcd-1234-efgh-abcdefghijkl")
    private UUID id;

    @Schema(description = "Order date and time")
    private LocalDateTime orderDate;

    @Schema(description = "Total amount of the order in cents")
    private Integer totalAmount;

    @Schema(description = "Status of the order", allowableValues = {"CREATED", "PROCESSING", "COMPLETED", "CANCELLED"})
    private String status;

    @Schema(description = "Item UUIs included in the order")
    private List<UUID> orderItemIds;
}
