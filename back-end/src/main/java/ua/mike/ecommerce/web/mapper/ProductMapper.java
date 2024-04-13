package ua.mike.ecommerce.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ua.mike.ecommerce.persistence.entity.Product;
import ua.mike.ecommerce.web.dto.ProductDto;

/**
 * Created by mike on 13.04.2024 14:21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductDto convert(Product product);
}
