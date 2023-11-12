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

- **WEEK - DATE:** The first day of the week for the current row's position in the time series for the combination of term.

- **Refresh Date - DATE:** Date when the new set of term and score combination was added.

- **Percent Gain - INT:** Percentage gain (rate) at which the term rose compared to the previous date period.

- **Score - INT:** 0-100 index denoting how popular this term was during the current date, relative to the other dates in the same time series for this term (260 weeks = 52 weeks * 5 years).

- **Rank - INT:** Numeric representation of where the term falls in comparison to the other top terms for the day. (e.g., rank of 1-25).

- **Country Name - STRING:** The name of the country.

- **Country Code - STRING:** The code representing the country.

- **Region Name - STRING:** The name of the region within the chosen country.

- **DMA Name - STRING:** Stores the full text name of the Designated Market Area (DMA).

- **DMA Id - INT:** 3-digit numerical ID used to identify a Designated Market Area (DMA).

> **_NOTE:_**  DMA stands for "Designated Market Area." It's a term commonly used in the United States to define specific geographic regions that television and media markets cover.


