# Clickbus Live Coding Challenge


## Dependencies

This project is built using:

- Java 8
- Spring Boot
- Spring Data
- Lombok
- Swagger
- Junit 5 - H2 to repository tests
- H2 DataBase

## Test End-Points 

- Create:
    ```curl -X POST http://localhost:8080/place -H 'Content-Type: application/json' -d '{ "name": "Butanta", "slug": "bt", "state": "SP", "city": "Sao Paulo" }'```

- Edit:
    ```curl -X PUT http://localhost:8080/place/1 -H 'Content-Type: application/json' -d '{ "name": "Cotia", "slug": "bt", "state": "SP", "city": "Sao Paulo" }'```

- Find all:
    ```curl -X GET http://localhost:8080/place```

- Find by name:
    ```curl -X GET http://localhost:8080/place/?name={name}```

- Find by id:
    ```curl -X GET http://localhost:8080/place/{id}```