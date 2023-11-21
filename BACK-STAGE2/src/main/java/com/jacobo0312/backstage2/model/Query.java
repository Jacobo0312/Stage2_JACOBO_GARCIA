package com.jacobo0312.backstage2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String queryName;

    private String username;

    private String description;

    @OneToMany(mappedBy = "query")
    private List<Comment> comments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "data_filter_id")
    private DataFilter dataFilter;

}
