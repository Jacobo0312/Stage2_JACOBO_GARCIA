package com.jacobo0312.backstage2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)

@NoArgsConstructor
@AllArgsConstructor
public class TermRisingInternational extends TermInternational {

    private Integer percentGain;

}
