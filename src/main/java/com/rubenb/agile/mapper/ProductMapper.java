package com.rubenb.agile.mapper;


import com.rubenb.agile.dto.ProductDto;
import com.rubenb.agile.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ProductMapper {

    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(ProductDto productDto, @MappingTarget Product product);
}
