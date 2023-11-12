# STAGE 2 ver+ tech fellows

## Development Stack

- FRONT: Typescript - Next JS
- BACK: Java - SpringBoot
- DB: PostgresSQL

## User Stories

- Visual Query Builder
- Visual Summary of Queried Data
- Save Query With Name, Username, and Comment
- Show All Saved Queries
- Comment on Query
- Select Saved Query
- Persistence
- Multiplayer Functionality

## Data Analysis

First, it is necessary to analyze the data to understand its structure and model. This will generate the most common use cases and those that generate value for the user.

The dataset for this project is a Google Trends dataset. This dataset will provide critical signals that both individual users and businesses can leverage to make better data-driven decisions.


The dataset has 4 tables:

- International Top Terms
- International Top Rising Terms
- Top Terms USA
- Top Rising Terms USA

## Data model:


### Attributes:

- **Term - STRING:** The human-readable identifier for a term, e.g., "Acme Inc".

- **DMA Name - STRING:** Stores the full text name of the Designated Market Area (DMA).

- **DMA Id - INT:** 3-digit numerical ID used to identify a Designated Market Area (DMA).

- **WEEK - DATE:** The first day of the week for the current row's position in the time series for the combination of term, DMA, and score.

- **Refresh Date - DATE:** Date when the new set of term, score, and DMA combination was added. Note: This column also serves as the partition key.

- **Percent Gain - INT:** Percentage gain (rate) at which the term rose compared to the previous date period.

- **Score - INT:** Index from 0-100 that denotes how popular this term was for a DMA during the current date, relative to the other dates in the same time series for this term (260 weeks = 52 weeks * 5 years).

- **Rank - INT:** Numeric representation of where the term falls in comparison to the other top terms for the day across the US market (e.g., rank of 1-25). Note: The rank value remains consistent across all historical data and DMA.

- **Country Name - STRING:** The name of the country.

- **Country Code - STRING:** The code representing the country.

- **Region Name - STRING:** The name of the region within the chosen country.


> **_NOTE:_**  DMA stands for "Designated Market Area." It's a term commonly used in the United States to define specific geographic regions that television and media markets cover.




[link] https://console.cloud.google.com/bigquery?p=bigquery-public-data&d=google_trends&page=dataset&project=stage2-404603&ws=!1m5!1m4!4m3!1sbigquery-public-data!2sgoogle_trends!3stop_rising_terms

