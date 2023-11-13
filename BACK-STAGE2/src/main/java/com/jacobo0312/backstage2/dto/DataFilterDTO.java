package com.jacobo0312.backstage2.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataFilterDTO {

    private List<@Size(max = 255, message = "Country should not exceed 255 characters") String> countries;

    @ApiModelProperty(notes = "Start date for the time range filter (format: yyyy-MM-dd)")
    private String startDate;

    @ApiModelProperty(notes = "End date for the time range filter (format: yyyy-MM-dd)")
    private String endDate;


}

