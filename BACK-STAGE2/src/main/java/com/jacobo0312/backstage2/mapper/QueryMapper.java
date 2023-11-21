package com.jacobo0312.backstage2.mapper;


import com.jacobo0312.backstage2.dto.QueryRequestDTO;
import com.jacobo0312.backstage2.dto.QueryResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.jacobo0312.backstage2.model.Query;


@Mapper(componentModel = "spring")
public interface QueryMapper {

    final CommentMapper commentMapper = new CommentMapperImpl();
    final DataFilterMapper dataFilterMapper = new DataFilterMapperImpl();

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = false)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "dataFilter", ignore = false)
    Query toQuery(QueryRequestDTO queryRequestDTO);

    @Mapping(target = "username", expression = "java(query.getUsername())")
    @Mapping(target = "queryName", expression = "java(query.getQueryName())")
    @Mapping(target = "comments", expression = "java(query.getComments().stream().map(commentMapper::toCommentResponseDTO).toList())")
    @Mapping(target = "dataFilter", expression = "java(dataFilterMapper.toDataFilterDTO(query.getDataFilter()))")
    QueryResponseDTO toQueryResponseDTO(Query query);

}

