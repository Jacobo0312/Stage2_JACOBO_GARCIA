package com.jacobo0312.backstage2.service;


import com.google.cloud.bigquery.*;
import com.jacobo0312.backstage2.dto.DataFilterDTO;
import com.jacobo0312.backstage2.dto.GoogleTrendsResponseDTO;
import com.jacobo0312.backstage2.enums.TableName;
import com.jacobo0312.backstage2.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class BigQueryService {

    private BigQuery bigQuery;

    private static final String INTERNATIONAL_TOP_TERMS_TABLE = "bigquery-public-data.google_trends.international_top_terms";
    private static final String INTERNATIONAL_TOP_RISING_TERMS_TABLE = "bigquery-public-data.google_trends.international_top_rising_terms";
    private static final String TOP_TERMS_TABLE = "bigquery-public-data.google_trends.top_terms";
    private static final String TOP_RISING_TERMS_TABLE = "bigquery-public-data.google_trends.top_rising_terms";


    public GoogleTrendsResponseDTO filterGoogleTrendsData(int limit, DataFilterDTO filterDTO) {

        // Build the query
        //SELECT
        //  term,
        //  rank,
        //  AVG(score) AS score
        //FROM
        StringBuilder query = new StringBuilder("SELECT term, rank, AVG(score)AS score FROM `table` WHERE 1=1");

        //Filter by countries
        if (filterDTO.getCountries() != null && !filterDTO.getCountries().isEmpty()) {
            query.append(" AND country_name IN (");
            for (String country : filterDTO.getCountries()) {
                query.append("'").append(country).append("',");
            }
            // Delete the last comma
            query.deleteCharAt(query.length() - 1);
            query.append(")");
        }

        //Filter by time range
        // Add date range filter
        if (filterDTO.getStartDate() != null && filterDTO.getEndDate() != null) {
            query.append(" AND week BETWEEN '")
                    .append(filterDTO.getStartDate())
                    .append("' AND '")
                    .append(filterDTO.getEndDate())
                    .append("'");
        }

        if (filterDTO.getTerm() != null && !filterDTO.getTerm().isEmpty()) {
            query.append(" AND term LIKE '%' || '").append(filterDTO.getTerm()).append("' || '%'");
        }


        // Add the order by score
        query.append("GROUP BY\n" +
                "  term, rank\n" +
                "ORDER BY\n" +
                "  rank ASC,score DESC");

        //Add the limit
        query.append(" LIMIT ").append(limit);

        GoogleTrendsResponseDTO responseDTO = new GoogleTrendsResponseDTO();


        for (TableName tableName : TableName.values()) {
            //Replace table in query
            query.replace(43, query.indexOf(" WHERE"), getTableName(tableName));
            TableResult result;
            try {

                result = executeQuery(query.toString());

            } catch (BigQueryException | InterruptedException e) {
                log.error("Error executing query: " + e.getMessage());
                result = null;
            }

            if (result == null) continue;

            List<? extends Term> terms = getTermsFromTable(result, tableName);

            switch (tableName) {
                case INTERNATIONAL_TOP_TERMS -> responseDTO.setTermInternationalList((List<TermInternational>) terms);
                case INTERNATIONAL_TOP_RISING_TERMS ->
                        responseDTO.setTermRisingInternationalList((List<TermRisingInternational>) terms);
                case TOP_TERMS -> responseDTO.setTermUSAList((List<TermUSA>) terms);
                case TOP_RISING_TERMS -> responseDTO.setTermRisingUSAList((List<TermRisingUSA>) terms);
            }


        }

        return responseDTO;
    }

    private TableResult executeQuery(String query) throws InterruptedException {
        log.info("Execute Query: " + query);

        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(query)
                        .setUseLegacySql(false)
                        .build();

        String jobIdStr = UUID.randomUUID().toString();

        log.info("service gctj jobIdStr: " + jobIdStr);

        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(jobIdStr);

        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        // Wait for the query to complete.
        queryJob = queryJob.waitFor();

        return queryJob.getQueryResults();
    }


    private List<? extends Term> getTermsFromTable(TableResult result, TableName tableName) {
        // Create a list
        List<Term> terms = new ArrayList<>();

        // Iterate through the results
        for (FieldValueList row : result.iterateAll()) {
            Term termData = switch (tableName) {
                case INTERNATIONAL_TOP_TERMS -> new TermInternational();
                case INTERNATIONAL_TOP_RISING_TERMS -> new TermRisingInternational();
                case TOP_TERMS -> new TermUSA();
                case TOP_RISING_TERMS -> new TermRisingUSA();
            };

            // Set the values
            termData.setTerm(row.get("term").isNull() ? null : row.get("term").getStringValue());
            termData.setRank(row.get("rank").isNull() ? null : row.get("rank").getNumericValue().intValue());
            termData.setScore(row.get("score").isNull() ? null : row.get("score").getNumericValue().intValue());
//            termData.setRefreshDate(row.get("refresh_date").isNull() ? null : FormatDate.parseDateString(row.get("refresh_date").getStringValue()));
//            termData.setWeek(row.get("week").isNull() ? null : FormatDate.parseDateString(row.get("week").getStringValue()));

            if (termData instanceof TermInternational) {
//                ((TermInternational) termData).setCountryCode(row.get("country_code").isNull() ? null : row.get("country_code").getStringValue());
//                ((TermInternational) termData).setCountryName(row.get("country_name").isNull() ? null : row.get("country_name").getStringValue());
//                ((TermInternational) termData).setRegionCode(row.get("region_code").isNull() ? null : row.get("region_code").getStringValue());
//                ((TermInternational) termData).setRegionName(row.get("region_name").isNull() ? null : row.get("region_name").getStringValue());
            }

            if (termData instanceof TermRisingInternational) {
//                ((TermRisingInternational) termData).setCountryCode(row.get("country_code").isNull() ? null : row.get("country_code").getStringValue());
//                ((TermRisingInternational) termData).setCountryName(row.get("country_name").isNull() ? null : row.get("country_name").getStringValue());
//                ((TermRisingInternational) termData).setRegionCode(row.get("region_code").isNull() ? null : row.get("region_code").getStringValue());
//                ((TermRisingInternational) termData).setRegionName(row.get("region_name").isNull() ? null : row.get("region_name").getStringValue());
//                ((TermRisingInternational) termData).setPercentGain(row.get("percent_gain").isNull() ? null : row.get("percent_gain").getNumericValue().intValue());
            }

            if (termData instanceof TermUSA) {
//                ((TermUSA) termData).setDmaId(row.get("dma_id").isNull() ? null : row.get("dma_id").getNumericValue().intValue());
//                ((TermUSA) termData).setDmaName(row.get("dma_name").isNull() ? null : row.get("dma_name").getStringValue());
            }

            if (termData instanceof TermRisingUSA) {
//                ((TermRisingUSA) termData).setDmaId(row.get("dma_id").isNull() ? null : row.get("dma_id").getNumericValue().intValue());
//                ((TermRisingUSA) termData).setDmaName(row.get("dma_name").isNull() ? null : row.get("dma_name").getStringValue());
//                ((TermRisingUSA) termData).setPercentGain(row.get("percent_gain").isNull() ? null : row.get("percent_gain").getNumericValue().intValue());
            }

            terms.add(termData);
        }
        return terms;
    }

    private String getTableName(TableName tableName) {
        return switch (tableName) {
            case INTERNATIONAL_TOP_TERMS -> INTERNATIONAL_TOP_TERMS_TABLE;
            case INTERNATIONAL_TOP_RISING_TERMS -> INTERNATIONAL_TOP_RISING_TERMS_TABLE;
            case TOP_TERMS -> TOP_TERMS_TABLE;
            case TOP_RISING_TERMS -> TOP_RISING_TERMS_TABLE;
        };
    }

    public GoogleTrendsResponseDTO getTopTermUSA() {
        DataFilterDTO filterDTO = new DataFilterDTO();

        filterDTO.setStartDate("2023-11-04");
        filterDTO.setEndDate("2023-11-14");

        return filterGoogleTrendsData(25, filterDTO);

    }
}
