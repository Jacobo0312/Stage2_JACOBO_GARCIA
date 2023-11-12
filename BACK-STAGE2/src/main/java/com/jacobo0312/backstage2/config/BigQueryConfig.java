package com.jacobo0312.backstage2.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Configuration class for BigQuery integration.
 * This class provides the necessary configuration to connect and authenticate
 * with Google BigQuery using the provided credentials.
 */
@Configuration
@Slf4j
public class BigQueryConfig {

    /**
     * Path to the Google Cloud credentials file.
     */
    @Value("${credentials.path}")
    private String path;

    /**
     * Bean to provide a configured instance of BigQuery.
     *
     * @return Configured instance of BigQuery with the provided credentials.
     * @throws RuntimeException If there is an error reading the credentials or building the BigQuery instance.
     */
    @Bean
    public BigQuery bigQueryClient() {

        log.info("Initializing bigQueryClient");

        GoogleCredentials credentials = null;
        try {
            credentials = GoogleCredentials.fromStream(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException("Error reading Google Cloud credentials.", e);
        }

        BigQuery bigQuery;
        bigQuery = BigQueryOptions.newBuilder()
                .setCredentials(credentials)
                .build().getService();
        return bigQuery;
    }
}
