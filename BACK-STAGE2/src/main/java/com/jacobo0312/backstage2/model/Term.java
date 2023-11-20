package com.jacobo0312.backstage2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Term {

    private String term;
    private Date week;
    private Integer rank;
    private Integer score;

}