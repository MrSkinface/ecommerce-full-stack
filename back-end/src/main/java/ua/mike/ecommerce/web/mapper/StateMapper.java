package ua.mike.ecommerce.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ua.mike.ecommerce.persistence.entity.State;
import ua.mike.ecommerce.web.dto.StateDto;

/**
 * Created by mike on 13.04.2024 14:21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StateMapper {

    StateDto convert(State state);
}
