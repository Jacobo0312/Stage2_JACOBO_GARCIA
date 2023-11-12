package com.jacobo0312.backstage2.controller;

import com.jacobo0312.backstage2.dto.DataFilterDTO;
import com.jacobo0312.backstage2.dto.GoogleTrendsResponseDTO;
import com.jacobo0312.backstage2.service.BigQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
@Api(tags = "Google Trends API")
public class BigQueryController {

    private final BigQueryService bigQueryService;

    @PostMapping("/google-trends")
    @ApiOperation(value = "Filter Google Trends Data", notes = "Filter Google Trends data based on provided criteria.")
    public ResponseEntity<GoogleTrendsResponseDTO> filterData(
            @ApiParam(value = "Limit for the number of results", defaultValue = "10")
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestBody DataFilterDTO filterDTO) {
        try {
            GoogleTrendsResponseDTO responseDTO = bigQueryService.executeQuery(limit, filterDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error processing Google Trends request", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
