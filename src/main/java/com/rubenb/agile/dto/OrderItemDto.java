package com.rubenb.agile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemDto {

    @Schema(description = "Order item ID", example = "abcdef12-abcd-5678-efgh-ijklmnopqrst")
    private UUID id;

    @Schema(description = "Order ID that this item belongs to", example = "12345678-abcd-1234-efgh-abcdefghijkl")
    private UUID orderId;

    @Schema(description = "Product ID of the item", example = "87654321-abcd-4321-efgh-abcdefghijkl")
    private UUID productId;

    @Schema(description = "Quantity of the product in the order")
    private Integer quantity;

    @Schema(description = "Price of the product in cents")
    private Integer priceInCents;

    @Schema(description = "Total price of the order item in cents")
    private Integer totalPriceInCents;
}
