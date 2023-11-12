package com.jacobo0312.backstage2.service;


import com.google.cloud.bigquery.*;
import com.jacobo0312.backstage2.dto.DataFilterDTO;
import com.jacobo0312.backstage2.dto.GoogleTrendsResponseDTO;
import com.jacobo0312.backstage2.model.Term;
import com.jacobo0312.backstage2.model.TermInternational;
import com.jacobo0312.backstage2.util.FormatDate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class BigQueryService {

    private BigQuery bigQuery;

    public GoogleTrendsResponseDTO executeQuery(int limit, DataFilterDTO filterDTO) throws InterruptedException {
        log.info("Execute query");


        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(
                                "SELECT * FROM `bigquery-public-data.google_trends.international_top_terms`  LIMIT 10")
                        .setUseLegacySql(false)
                        .build();

        String jobIdStr = UUID.randomUUID().toString();

        log.info("service gctj jobIdStr: " + jobIdStr);

        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(jobIdStr);

        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        log.info("service gctj queryJob: " + queryJob);

        // Wait for the query to complete.

        try {
            queryJob = queryJob.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check for errors
        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            // You can also look at queryJob.getStatus().getExecutionErrors() for all
            // errors, not just the latest one.
            log.error(queryJob.getStatus().toString());
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }

        // Get the results.

        TableResult result = queryJob.getQueryResults();

        // Crear una lista para almacenar los objetos TuModelo
        List<Term> terms = new ArrayList<>();

        // Iterar sobre los resultados y mapearlos a TuModelo
        for (FieldValueList row : result.iterateAll()) {

            TermInternational termData = new TermInternational();

            // Setear todos los atributos antes de pasarlos al constructor
            termData.setTerm(row.get("term").isNull() ? null : row.get("term").getStringValue());
            termData.setRank(row.get("rank").isNull() ? null : row.get("rank").getNumericValue().intValue());
            termData.setScore(row.get("score").isNull() ? null : row.get("score").getNumericValue().intValue());
            termData.setRefreshDate(row.get("refresh_date").isNull() ? null : FormatDate.parseDateString(row.get("refresh_date").getStringValue()));
            termData.setWeek(row.get("week").isNull() ? null : FormatDate.parseDateString(row.get("week").getStringValue()));
            termData.setCountryCode(row.get("country_code").isNull() ? null : row.get("country_code").getStringValue());
            termData.setCountryName(row.get("country_name").isNull() ? null : row.get("country_name").getStringValue());
            termData.setRegionName(row.get("region_name").isNull() ? null : row.get("region_name").getStringValue());

            // Ahora pasa los atributos al constructor
            terms.add(termData);
        }
        return new GoogleTrendsResponseDTO();
    }


}
