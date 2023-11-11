package com.jacobo0312.backstage2.service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.*;
import com.jacobo0312.backstage2.model.TopTerm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class BigQueryService {

    GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/credentials.json"));

    public BigQueryService() throws IOException {
    }

    public List<TopTerm> basicQuery() throws InterruptedException {
        log.info("basicQuery Started");


        BigQuery bigquery = BigQueryOptions.newBuilder()
                .setCredentials(credentials)
                .build().getService();

        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(
                                "SELECT * FROM `bigquery-public-data.google_trends.international_top_terms`  LIMIT 1000")
                        .setUseLegacySql(false)
                        .build();

        String jobIdStr = UUID.randomUUID().toString();

        log.info("service gctj jobIdStr: " + jobIdStr);

        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(jobIdStr);

        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        log.info("service gctj queryJob: " + queryJob);

        // Wait for the query to complete.

        try {
            queryJob = queryJob.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("service gctj queryJob: " + queryJob);

        // Check for errors
        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            // You can also look at queryJob.getStatus().getExecutionErrors() for all
            // errors, not just the latest one.
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }

        // Get the results.

        QueryResponse response = bigquery.getQueryResults(jobId);

        TableResult result = queryJob.getQueryResults();

        // Crear una lista para almacenar los objetos TuModelo
        List<TopTerm> tuModeloList = new ArrayList<>();

        // Iterar sobre los resultados y mapearlos a TuModelo
        for (FieldValueList row : result.iterateAll()) {
            String term = row.get("term").isNull() ? null : row.get("term").getStringValue();
//            TopTerm tuModelo = TopTerm.builder()
//                    .term(row.get("term").isNull() ? null : row.get("term").getStringValue())
//                    .week(row.get("week").isNull() ? null : new Date(row.get("week").getTimestampValue()))
//                    .countryName(row.get("country_name").isNull() ? null : row.get("country_name").getStringValue())
//                    .countryCode(row.get("country_code").isNull() ? null : row.get("country_code").getStringValue())
//                    .refreshDate(row.get("refresh_date").isNull() ? null : new Date(row.get("refresh_date").getTimestampValue()))
//                    .build();
//
            TopTerm tuModelo = new TopTerm();
            tuModelo.setTerm(term);
            tuModelo.setRank(row.get("rank").isNull() ? null : row.get("rank").getNumericValue().intValue());
            tuModelo.setScore(row.get("score").isNull() ? null : row.get("score").getNumericValue().intValue());
//            tuModelo.setRefreshDate(row.get("refresh_date").isNull() ? null : new Date(row.get("refresh_date").getTimestampValue()));
            tuModelo.setCountryCode(row.get("country_code").isNull() ? null : row.get("country_code").getStringValue());
            tuModelo.setCountryName(row.get("country_name").isNull() ? null : row.get("country_name").getStringValue());
            tuModelo.setRegionName(row.get("region_name").isNull() ? null : row.get("region_name").getStringValue());
            tuModeloList.add(tuModelo);

        }
        return tuModeloList;
    }


}
