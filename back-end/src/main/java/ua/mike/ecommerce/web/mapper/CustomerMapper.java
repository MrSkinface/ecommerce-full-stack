package ua.mike.ecommerce.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ua.mike.ecommerce.persistence.entity.Customer;
import ua.mike.ecommerce.web.dto.CustomerDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    CustomerDto convert(Customer customer);
}