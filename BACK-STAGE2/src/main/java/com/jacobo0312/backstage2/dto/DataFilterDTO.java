package com.jacobo0312.backstage2.dto;

import lombok.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataFilterDTO {

    @Size(max = 255, message = "Term should not exceed 255 characters")
    private String term;

    @Size(max = 255, message = "Country should not exceed 255 characters")
    private String country;

    @Size(max = 255, message = "Region should not exceed 255 characters")
    private String region;

    @Size(max = 255, message = "DMA should not exceed 255 characters")
    private String dma;

    private String timeRangeStart;
    private String timeRangeEnd;

    private boolean topTrends;

    private boolean booleanLogic;

    @PositiveOrZero(message = "Percent gain should be a positive or zero value")
    private int percentGain;

    private List<@Size(max = 255, message = "Country should not exceed 255 characters") String> selectedCountries;

    @PositiveOrZero(message = "Custom ranking start should be a positive or zero value")
    private int customRankingStart;

    @PositiveOrZero(message = "Custom ranking end should be a positive or zero value")
    private int customRankingEnd;

    private String refreshDate;

    @Size(max = 255, message = "Sorting criteria should not exceed 255 characters")
    private String sortingCriteria;

}

