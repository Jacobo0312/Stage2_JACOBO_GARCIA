package com.jacobo0312.backstage2.service;

import com.jacobo0312.backstage2.dto.CommentRequestDTO;
import com.jacobo0312.backstage2.dto.CommentResponseDTO;
import com.jacobo0312.backstage2.model.Comment;
import com.jacobo0312.backstage2.model.Query;
import com.jacobo0312.backstage2.repository.CommentRepository;
import com.jacobo0312.backstage2.repository.QueryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.jacobo0312.backstage2.mapper.CommentMapper;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final QueryRepository queryRepository;

    public CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO) {
        Comment comment = commentMapper.toComment(commentRequestDTO);
        Query query = queryRepository.findById(UUID.fromString(commentRequestDTO.getQueryId())).orElseThrow(() -> new RuntimeException("Query not found"));
        comment.setQuery(query);
        commentRepository.save(comment);
        return commentMapper.toCommentResponseDTO(comment);
    }

}
