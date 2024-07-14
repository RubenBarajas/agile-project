### Requirements

To run this application locally, you need to have the following installed:


* Docker
* Docker Compose

### Getting Started

* Start application using Docker Compose in the project folder, this command will also execute the test cases
automatically as per the Dockerfile:

      docker-compose up

* The OpenAPI 3 descriptions are available at:

      http://localhost:8080/v3/api-docs

* THe OpenAPI 3 UI is available at:

      http://localhost:8080/swagger-ui/index.html


However, this project can also be executed in the command line with the command ./gradlew bootRun.
The project already includes a .envrc file with the needed environment variables, these can set
by using the tool [direnv]( https://direnv.net/).

The project is using Flyway to create the needed DB tables as well as to inserting test data.
In order to use said data the directory includes an API test-collection using [Bruno](https://www.usebruno.com/)
which is a Fast and Git-Friendly Opensource API client as well as a Postman version in resources/api folder.
