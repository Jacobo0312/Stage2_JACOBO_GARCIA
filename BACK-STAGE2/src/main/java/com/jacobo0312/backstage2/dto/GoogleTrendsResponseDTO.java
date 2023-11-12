package com.jacobo0312.backstage2.dto;

import com.jacobo0312.backstage2.model.TermInternational;
import com.jacobo0312.backstage2.model.TermRisingInternational;
import com.jacobo0312.backstage2.model.TermRisingUSA;
import com.jacobo0312.backstage2.model.TermUSA;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "DTO for Google Trends Response")
public class GoogleTrendsResponseDTO {

    @ApiModelProperty(notes = "List of international terms")
    @Builder.Default
    private List<TermInternational> termInternationalList = new ArrayList<>();

    @ApiModelProperty(notes = "List of rising international terms")
    @Builder.Default
    private List<TermRisingInternational> termRisingInternationalList = new ArrayList<>();

    @ApiModelProperty(notes = "List of USA terms")
    @Builder.Default
    private List<TermUSA> termUSAList = new ArrayList<>();

    @ApiModelProperty(notes = "List of rising USA terms")
    @Builder.Default
    private List<TermRisingUSA> termRisingUSAList = new ArrayList<>();


}
