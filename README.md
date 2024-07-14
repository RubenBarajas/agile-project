### Requirements

To run this application locally, you need to have the following installed:


* Docker
* Docker Compose

### Getting Started

* Build the Docker image with a specific name, this command will also execute the test cases
  automatically as per the Dockerfile:

      docker build -t <image_name> .

* Start application using Docker Compose in the project folder:

      docker-compose up

* The OpenAPI 3 descriptions are available at:

      http://localhost:8080/v3/api-docs

* THe OpenAPI 3 UI is available at:

      http://localhost:8080/swagger-ui/index.html


However, this project can also be executed in the command line with the command **_./gradlew bootRun._**
The project already includes a **_.envrc_** file with the needed environment variables, these can set
by using the tool [direnv]( https://direnv.net/). A local instance of **_Postgres_** is needed for this approach
that matches the DB details in the env variables.

The project is using **_Flyway_** to create the needed DB tables as well as to inserting test data.
In order to use said data the directory includes an API test-collection using [Bruno](https://www.usebruno.com/)
which is a Fast and Git-Friendly Opensource API client as well as a **_Postman_** version in resources/api folder.
**_Before interacting with the collection have in mind the up-to-date ID's and references in entities_**
