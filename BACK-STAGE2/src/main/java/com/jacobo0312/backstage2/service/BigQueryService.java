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

        StringBuilder query = new StringBuilder("SELECT term, rank, AVG(score) AS score FROM `table` WHERE 1=1");

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
        if (filterDTO.getStartDate() != null && filterDTO.getEndDate() != null) {
            query.append(" AND week BETWEEN '")
                    .append(filterDTO.getStartDate())
                    .append("' AND '")
                    .append(filterDTO.getEndDate())
                    .append("'");
        }

        //Filter by term
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

        GoogleTrendsResponseDTO responseDTO = GoogleTrendsResponseDTO.builder().build();


        for (TableName tableName : TableName.values()) {

            //Replace table in query
            query.replace(44, query.indexOf(" WHERE"), getTableName(tableName));
            TableResult result;
            try {

                result = executeQuery(query.toString());

            } catch (BigQueryException | InterruptedException e) {
                log.error("Error executing query: " + e.getMessage());
                result = null;
            }

            if (result == null) continue;

            List<Term> terms = getTermsFromTable(result);

            switch (tableName) {
                case INTERNATIONAL_TOP_TERMS -> responseDTO.setTermInternationalList(terms);
                case INTERNATIONAL_TOP_RISING_TERMS -> responseDTO.setTermRisingInternationalList(terms);
                case TOP_TERMS -> responseDTO.setTermUSAList(terms);
                case TOP_RISING_TERMS -> responseDTO.setTermRisingUSAList(terms);
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


    private List<Term> getTermsFromTable(TableResult result) {
        // Create a list
        List<Term> terms = new ArrayList<>();

        // Iterate through the results
        for (FieldValueList row : result.iterateAll()) {

            Term termData = Term.builder()
                    .term(row.get("term").isNull() ? null : row.get("term").getStringValue())
                    .rank(row.get("rank").isNull() ? null : row.get("rank").getNumericValue().intValue())
                    .score(row.get("score").isNull() ? null : row.get("score").getNumericValue().intValue())
                    .build();

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

    public GoogleTrendsResponseDTO getTopTerm() {
        DataFilterDTO filterDTO = DataFilterDTO.builder().build();

        filterDTO.setStartDate("2023-11-04");
        filterDTO.setEndDate("2023-11-14");

        return filterGoogleTrendsData(25, filterDTO);

    }

    public List<Country> getCountries() {
        String query = "SELECT DISTINCT country_name,country_code FROM bigquery-public-data.google_trends.international_top_terms ORDER BY country_name ASC";
        TableResult result;
        try {
            result = executeQuery(query);
        } catch (BigQueryException | InterruptedException e) {
            log.error("Error executing query: " + e.getMessage());
            result = null;
        }

        if (result == null) return null;

        List<Country> countries = new ArrayList<>();

        for (FieldValueList row : result.iterateAll()) {
            Country country = Country.builder()
                    .name(row.get("country_name").isNull() ? null : row.get("country_name").getStringValue())
                    .code(row.get("country_code").isNull() ? null : row.get("country_code").getStringValue())
                    .build();
            countries.add(country);
        }


        return countries;

    }

    public List<Region> getRegions(String countryCode) {

        String query = "SELECT DISTINCT region_name,region_code FROM bigquery-public-data.google_trends.international_top_terms WHERE country_code = '" + countryCode + "' ORDER BY region_name ASC";
        TableResult result;
        try {
            result = executeQuery(query);
        } catch (BigQueryException | InterruptedException e) {
            log.error("Error executing query: " + e.getMessage());
            result = null;
        }

        if (result == null) return null;

        List<Region> regions = new ArrayList<>();

        for (FieldValueList row : result.iterateAll()) {
            Region region = Region.builder()
                    .name(row.get("region_name").isNull() ? null : row.get("region_name").getStringValue())
                    .code(row.get("region_code").isNull() ? null : row.get("region_code").getStringValue()).build();
            regions.add(region);

        }
        return regions;
    }

    public List<DMA> getDmaList(){
        String query = "SELECT DISTINCT dma_name,dma_id FROM bigquery-public-data.google_trends.top_terms ORDER BY dma_name ASC";
        TableResult result;
        try {
            result = executeQuery(query);
        } catch (BigQueryException | InterruptedException e) {
            log.error("Error executing query: " + e.getMessage());
            result = null;
        }

        if (result == null) return null;

        List<DMA> dmaList = new ArrayList<>();

        for (FieldValueList row : result.iterateAll()) {
            DMA dma = DMA.builder()
                    .id(row.get("dma_id").isNull() ? null : row.get("dma_id").getStringValue())
                    .name(row.get("dma_name").isNull() ? null : row.get("dma_name").getStringValue()).build();
            dmaList.add(dma);

        }
        return dmaList;
    }
}
