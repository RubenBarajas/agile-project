package com.rubenb.agile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductDto {

    @Schema(description = "Unique identifier of the product", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Name of the product", required = true, example = "T-Shirt")
    private String name;

    @Schema(description = "Description of the product", example = "A comfortable cotton T-Shirt")
    private String description;

    @Schema(description = "Price of the product in cents", example = "1999")
    private Integer priceInCents;

    @Schema(description = "Weight of the product in grams", example = "1999")
    private Integer weightInGrams;
}