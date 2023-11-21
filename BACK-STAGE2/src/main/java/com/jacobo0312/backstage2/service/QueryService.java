package com.jacobo0312.backstage2.service;


import com.jacobo0312.backstage2.dto.QueryRequestDTO;
import com.jacobo0312.backstage2.dto.QueryResponseDTO;
import com.jacobo0312.backstage2.mapper.QueryMapper;
import com.jacobo0312.backstage2.model.Query;
import com.jacobo0312.backstage2.repository.QueryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class QueryService {


    private final QueryRepository queryRepository;

    private final QueryMapper queryMapper;

    public QueryResponseDTO createQuery(QueryRequestDTO queryRequestDTO) {
        log.info("Creating query: {}", queryRequestDTO);
        Query query = queryMapper.toQuery(queryRequestDTO);
        query.setComments(new ArrayList<>());
        if (queryRequestDTO.getDataFilter().getLimitData() ==0 ) {
            query.getDataFilter().setLimitData(10);
        }else {
            query.getDataFilter().setLimitData(queryRequestDTO.getDataFilter().getLimitData());
        }
        Query savedQuery = queryRepository.save(query);
        return queryMapper.toQueryResponseDTO(savedQuery);
    }

    public List<QueryResponseDTO> getAllQueries() {
        return queryRepository.findAll().stream().map(queryMapper::toQueryResponseDTO).toList();
    }


}
