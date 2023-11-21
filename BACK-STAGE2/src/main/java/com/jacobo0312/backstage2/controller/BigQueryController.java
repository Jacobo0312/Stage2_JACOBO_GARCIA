package com.jacobo0312.backstage2.controller;

import com.jacobo0312.backstage2.dto.DataFilterDTO;
import com.jacobo0312.backstage2.dto.GoogleTrendsResponseDTO;
import com.jacobo0312.backstage2.model.Country;
import com.jacobo0312.backstage2.model.DMA;
import com.jacobo0312.backstage2.model.Region;
import com.jacobo0312.backstage2.service.BigQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/google-trends")
@Slf4j
@AllArgsConstructor
@Api(tags = "Google Trends API")
public class BigQueryController {

    private final BigQueryService bigQueryService;

    @PostMapping("/create")
    @ApiOperation(value = "Filter Google Trends Data", notes = "Filter Google Trends data based on provided criteria.")
    public ResponseEntity<GoogleTrendsResponseDTO> filterData(
            @Valid
            @RequestBody DataFilterDTO filterDTO) {
        try {
            GoogleTrendsResponseDTO responseDTO = bigQueryService.filterGoogleTrendsData(filterDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error processing Google Trends request", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/top_terms_global")
    @ApiOperation(value = "Get top term usa", notes = "Get top term usa")
    public ResponseEntity<GoogleTrendsResponseDTO> getTopTermUSA() {
        try {
            GoogleTrendsResponseDTO responseDTO = bigQueryService.getTopTerm();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error processing Google Trends request", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/countries")
    @ApiOperation(value = "Get countries", notes = "Get countries")
    public ResponseEntity<List<Country>> getCountries() {
        try {
            List<Country> countries = bigQueryService.getCountries();
            return new ResponseEntity<>(countries, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error processing Google Trends request", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/regions/{countryCode}")
    @ApiOperation(value = "Get regions", notes = "Get regions")
    public ResponseEntity<List<Region>> getRegions(@ApiParam(value = "Country code", defaultValue = "US") @PathVariable String countryCode) {
        try {
            List<Region> regions = bigQueryService.getRegions(countryCode);
            return new ResponseEntity<>(regions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error processing Google Trends request", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/dma")
    @ApiOperation(value = "Get DMA'S", notes = "Get DMA'S")
    public ResponseEntity<List<DMA>> getDMA() {
        try {
            List<DMA> dmaList = bigQueryService.getDmaList();
            return new ResponseEntity<>(dmaList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error processing Google Trends request", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
