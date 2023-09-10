[![Tests](https://github.com/moseseth/lease-it/workflows/Tests/badge.svg)](https://github.com/moseseth/lease-it/actions/workflows/tests.yml)


Service for leasing a vehicle to a customer
----------------------------------------------
#### Entity Relationship Design

![ER](assets/ER.png)

This project is a web application that allows you to manage vehicle leasing, customers, and lease contracts. It uses MySQL for persisting data, Spring boot with Java 17 as the backend service, and React as a frontend tool.

### How to Run

You can run the project using Docker.

-   *Requires Docker Desktop*. Then, follow these steps:

    1. Clone the repository and `cd lease-it`
    2. Rename `.env.example` to `.env` under `lease-api/`
    3. Go to the root directory and run: `docker compose up -d`
    4. The backend service will be available at `localhost:9090/api`
    5. The frontend UI will be available at `localhost` (port 80)

### Unit tests for lease-api 

All code pushes to github has to pass unit tests using GitActions workflow.
Available under .github/tests and the badge is a legit one too :)

### API Documentation

You can access the API documentation at `localhost:9090/api/docs`

### Features

-   The backend service supports all the required operations and provides paging for Contract overview 
GET requests to improve performance.
-   Bonus: A frontend UI uses Material-UI designs & allows view contract overviews only [task focused on Backend]


![API](assets/api-list.png)
