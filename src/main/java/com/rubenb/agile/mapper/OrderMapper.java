package com.rubenb.agile.mapper;

import com.rubenb.agile.dto.OrderDto;
import com.rubenb.agile.model.Order;
import com.rubenb.agile.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface OrderMapper {

    @Mapping(target = "orderItemIds", source = "orderItems", qualifiedByName = "orderItemsToUUIDs")
    OrderDto toDto(Order order);

    Order toEntity(OrderDto orderDto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(OrderDto orderDto, @MappingTarget Order order);

    @Named("orderItemsToUUIDs")
    default List<UUID> orderItemsToUUIDs(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getId)
                .collect(Collectors.toList());
    }
}
