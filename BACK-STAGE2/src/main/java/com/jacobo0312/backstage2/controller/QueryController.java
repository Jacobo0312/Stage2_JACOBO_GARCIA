package com.jacobo0312.backstage2.controller;

import com.jacobo0312.backstage2.dto.QueryRequestDTO;
import com.jacobo0312.backstage2.dto.QueryResponseDTO;
import com.jacobo0312.backstage2.service.QueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/queries")
@Slf4j
@Api(tags = "Query API")
public class QueryController {

    private final QueryService queryService;

    @PostMapping("/create")
    @ApiOperation(value = "Create a new query", notes = "Create a new query")
    public QueryResponseDTO createQuery(@Valid @RequestBody QueryRequestDTO queryRequestDTO) {
        return queryService.createQuery(queryRequestDTO);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all queries", notes = "Get all queries")
    public List<QueryResponseDTO> getAllQuery() {
        return queryService.getAllQueries();
    }

}