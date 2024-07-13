package com.rubenb.agile.mapper;

import com.rubenb.agile.dto.OrderItemDto;
import com.rubenb.agile.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper
public interface OrderItemMapper {

    @Mappings({
            @Mapping(target = "order", ignore = true),
            @Mapping(target = "product", ignore = true)
    })
    OrderItem toEntity(OrderItemDto orderItemDto);

    @Mappings({
            @Mapping(source = "order.id", target = "orderId"),
            @Mapping(source = "product.id", target = "productId")
    })
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(OrderItemDto orderItemDto, @MappingTarget OrderItem orderItem);
}
