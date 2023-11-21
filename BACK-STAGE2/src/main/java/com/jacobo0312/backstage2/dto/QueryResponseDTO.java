package com.jacobo0312.backstage2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryResponseDTO {

    private String id;
    private String queryName;
    private String username;
    private String description;
    private List<CommentResponseDTO> comments;
    private DataFilterDTO dataFilter;
}
