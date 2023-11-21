package com.jacobo0312.backstage2.mapper;


import com.jacobo0312.backstage2.dto.CommentRequestDTO;
import com.jacobo0312.backstage2.dto.CommentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.jacobo0312.backstage2.model.Comment;


@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(target = "id", ignore = true)
    Comment toComment(CommentRequestDTO commentRequestDTO);


    @Mapping(target = "username", expression = "java(comment.getUsername())")
    @Mapping(target = "query", expression = "java(comment.getQuery().getId())")
    CommentResponseDTO toCommentResponseDTO(Comment comment);
}
