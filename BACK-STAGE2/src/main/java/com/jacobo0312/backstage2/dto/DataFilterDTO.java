package com.jacobo0312.backstage2.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataFilterDTO {

    @ApiModelProperty(notes = "Term to search")
    @Size(max = 255, message = "Term should not exceed 255 characters")
    private String term;

    private List<@Size(max = 255, message = "Country should not exceed 255 characters") String> countries;

    private List<@Size(max = 255, message = "Region should not exceed 255 characters") String> regions;

    private List<@Size(max = 255, message = "DMA should not exceed 255 characters") String> dmaList;

    @ApiModelProperty(notes = "Start date for the time range filter (format: yyyy-MM-dd)")
    private String startDate;

    @ApiModelProperty(notes = "End date for the time range filter (format: yyyy-MM-dd)")
    private String endDate;

}

