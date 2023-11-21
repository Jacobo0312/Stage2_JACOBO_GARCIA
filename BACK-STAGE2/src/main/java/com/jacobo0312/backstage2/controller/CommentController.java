package com.jacobo0312.backstage2.controller;

import com.jacobo0312.backstage2.dto.CommentRequestDTO;
import com.jacobo0312.backstage2.dto.CommentResponseDTO;
import com.jacobo0312.backstage2.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
@Slf4j
@Api(tags = "Comments API")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    @ApiOperation(value = "Create a new comment", notes = "Create a new comment")
    public CommentResponseDTO createComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO) {
        return commentService.createComment(commentRequestDTO);
    }


}
