package ua.mike.ecommerce.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ua.mike.ecommerce.persistence.entity.Category;
import ua.mike.ecommerce.web.dto.ProductCategoryDto;

/**
 * Created by mike on 12.04.2024 17:03
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductCategoryMapper {

    ProductCategoryDto convert(Category category);
}
