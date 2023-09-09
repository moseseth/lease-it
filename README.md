[![Tests](https://github.com/moseseth/lease-it/workflows/Tests/badge.svg)](https://github.com/moseseth/lease-it/actions/workflows/tests.yml)


Service for leasing a vehicle to a customer
----------------------------------------------
#### Entity Relationship Design

![ER](assets/ER.png)

This project is a web application that allows you to manage vehicle leasing, customers, and lease contracts. It uses MySQL as the database, Java 17 as the backend service, and React as the frontend UI.

### How to Run

You can run the project using Docker.

-   Requires Docker Desktop. Then, follow these steps:

    1. Rename `.env.example` to `.env` under `lease-api/`
    2. Go to the root directory and run: `docker compose up -d`
    3. The backend service will be available at `localhost:9090/api`
    4. The frontend UI will be available at `localhost` (port 80)

### Unit tests for lease-api 

All code pushes to github has to pass unit tests using GitActions workflow.
Available under .github/tests and the badge is a legit one too :)

### API Documentation

You can access the API documentation at `localhost:9090/api/docs`

### Features

-   The backend service supports all the required operations and provides paging for GET requests to improve performance.
-   The frontend UI uses Material-UI designs and allows you to view contract overviews only [task focused on Backend]


![API](assets/api-list.png)
