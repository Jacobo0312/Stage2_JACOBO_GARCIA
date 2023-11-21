package com.jacobo0312.backstage2.mapper;

import com.jacobo0312.backstage2.dto.DataFilterDTO;
import com.jacobo0312.backstage2.model.DataFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DataFilterMapper {

    @Mapping(target = "id", ignore = true)
    DataFilter toDataFilter(DataFilterDTO dataFilterDTO);

    DataFilterDTO toDataFilterDTO(DataFilter dataFilter);
}