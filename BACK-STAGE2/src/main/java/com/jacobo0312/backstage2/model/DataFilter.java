package com.jacobo0312.backstage2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String term;

    @ElementCollection
    private List<String> countries;

    @ElementCollection
    private List<String> regions;

    @ElementCollection
    private List<String> dmaList;

    private String startDate;
    private String endDate;

}
