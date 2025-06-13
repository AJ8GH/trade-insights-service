# Trade Insights Service

[![build](https://github.com/AJ8GH/trade-insights-service/actions/workflows/build.yaml/badge.svg)](https://github.com/AJ8GH/trade-insights-service/actions/workflows/build.yaml)
[![codecov](https://codecov.io/gh/AJ8GH/trade-insights-service/graph/badge.svg?token=JSKSHIJAAO)](https://codecov.io/gh/AJ8GH/trade-insights-service)

## Overview

### Trade Insights API

- REST API written with Kotlin and Spring Boot.
- Uses JPA and an H2 in-memory database to persist and retrieve the trade data.
- Integration tests written using JUnit and Kotest assertions

### Test Data Generator

- CLI tool written with Python and Click to generate trades and post them to the API.
- Accepts command line options for configuring the number of trades, traders and commodities.
- Default and maximum values are defined in the `.env` file.
- Quantity and price are randomised for each trade between the min (`1` and `0.1` respectively) and max values.
- Use `--help` to show available options:

```
-t,  --trades      INTEGER  Number of trades to generate.
-tr, --traders     INTEGER  Number of traders to generate trades for.
-c,  --commodities INTEGER  Number of commodities to be traded.
```

### Setup and Run

Clone the repo:

```sh
git clone git@github.com:AJ8GH/trade-insights-service.git

cd trade-insights-service
````

#### Running Locally

To run locally using the Gradle wrapper and Python `3.13`:

```sh
./gradlew bootRun

cd test-data-generator
pip install -r requirements.txt
python trade_generator.py # no options - use default values
python trade_generator.py --trades 999 --traders 99 --commodities 9 

curl 'http://localhost:8080/trades' | jq
curl 'http://localhost:8080/insights' | jq
```

#### Running in Docker

To run with Docker:

```sh
# Start API and run the trade generator in one command:
docker compose up

# Or run them separately in separate shells:
docker compose up trade-insights-service
docker compose up test-data-generator

curl 'http://localhost:8080/trades' | jq
curl 'http://localhost:8080/insights' | jq
```

> If running through Docker, override test-data-generator input values in the `.env` file instead of using python CLI options.

#### Running Tests

```sh
./gradlew test
```

### Dependencies

> Library / package / plugin versions defined in `gradle/libs.versions.toml` and `test-data-generator/requirements.txt`

#### Trade Insights Service

- Kotlin `2.1.21`
- JDK `21`
- Gradle `8.14.1`
- Spring Boot `3.5.0`
- Libraries:
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-actuator`
  - `h2`
  - `kotlin-logging-jvm`
  - `kotlin-reflect`
  - `kotest-assertions-core`
  - `junit-jupiter-params`
- Plugins:
  - `org.jetbrains.kotlin.jvm`
  - `org.jetbrains.kotlin.plugin.jpa`
  - `org.springframework.boot`
  - `io.spring.dependency-management`

#### Test Data Generator

- Python `3.13`
- Packages:
  - `click`
  - `dataclasses-json`
  - `python-dotenv`
  - `requests`
