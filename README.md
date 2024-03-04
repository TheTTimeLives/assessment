# Java-programming-exercise


## Setup

First ensure you have [Docker](https://docs.docker.com/engine/install/) downloaded, with the latest updates. 

Once you have docker installed, navigate to the root of the application and run the following command in your terminal.

```bash
docker compose up --build
```

Note: Ensure you have a quality internet connection as some layers of these image may take some time or time out on a poor connection.
docker compose --build up 

Spring startup logs will display. Towards the end, logs stating "Tomcat started on port 8080..." and "Started ProgrammingExerciseApplication in..." will appear.

This means the application is running and ready to accept traffic.

## How to Use

This application allows you to interact with a Postgres database via GET, POST, PUT and DELETE requests. You can find, add, update and delete products, orders and order items.

You can use curl in the terminal or [Postman](https://www.postman.com/downloads/) to most easily make these requests.

```
curl http://localhost:8080/api/products
```

You can also use the Swagger documentation accesible at [localhost:8080/swagger-ui.html](localhost:8080/swagger-ui.html), which you can access via your web browser. This documentation describes all endpoints and their required models.