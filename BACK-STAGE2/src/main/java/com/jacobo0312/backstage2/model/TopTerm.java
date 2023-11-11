package com.jacobo0312.backstage2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopTerm {
    private String term;
    private Date week;
    private Integer rank;
    private Integer score;
    private Date refreshDate;
    private String countryName;
    private String countryCode;
    private String regionName;
}