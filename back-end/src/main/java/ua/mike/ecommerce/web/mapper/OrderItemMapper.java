package ua.mike.ecommerce.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ua.mike.ecommerce.persistence.entity.OrderItem;
import ua.mike.ecommerce.web.dto.OrderDetailsDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {

    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "imageUrl", source = "product.imageUrl")
    @Mapping(target = "unitPrice", source = "product.unitPrice")
    OrderDetailsDto.OrderItemDto convert(OrderItem item);
}