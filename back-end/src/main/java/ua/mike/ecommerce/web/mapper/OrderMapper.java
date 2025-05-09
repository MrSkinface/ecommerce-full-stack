package ua.mike.ecommerce.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ua.mike.ecommerce.persistence.entity.Order;
import ua.mike.ecommerce.web.dto.OrderDetailsDto;
import ua.mike.ecommerce.web.dto.OrderDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = OrderItemMapper.class)
public interface OrderMapper {

    OrderDto convert(Order order);

    OrderDetailsDto toDetails(Order order);
}