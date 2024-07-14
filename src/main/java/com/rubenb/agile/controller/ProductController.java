package com.rubenb.agile.controller;

import com.rubenb.agile.dto.ProductDto;
import com.rubenb.agile.exception.ErrorResponse;
import com.rubenb.agile.service.ProductService;
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
@RequestMapping("/api/v1/products")
@Tag(name = "Product Controller")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(
            summary = "Retrieve all products",
            description = "Retrieve all products."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ProductDto.class))
                    )
            })
    })
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return  ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(
            summary = "Retrieve a product by ID",
            description = "Retrieve a product by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = ProductDto.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            })
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(
            summary = "Create a product",
            description = "Create a product."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = ProductDto.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            })
    })
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @Operation(
            summary = "Update a product by ID",
            description = "Update a product by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = ProductDto.class)
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
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID id, @RequestBody ProductDto productDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
    }

    @Operation(
            summary = "Delete a product by ID",
            description = "Delete a product by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
