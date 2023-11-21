package com.jacobo0312.backstage2.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRequestDTO {

    @ApiModelProperty(notes = "Query name")
    @NotEmpty
    @NotNull
    @NotBlank
    private String queryName;

    @ApiModelProperty(notes = "User name")
    @NotEmpty
    @NotNull
    @NotBlank
    private String username;



    @ApiModelProperty(notes = "Data filter")
    @NotNull
    private DataFilterDTO dataFilter;

}
