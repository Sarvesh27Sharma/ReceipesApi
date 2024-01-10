# Recipes-api

API to manage recipes. It provides following functionalities:
* Easy option to add, update, delete and read recipes. Recipe contains name, number of people it can serve, ingredients and instructions to make it. It also tell us if it is vegetarian or not.
* Search options on recipe name, ingredients, instructions, number of served people or whether it is vegetarian or not.

![recipes-api-logo.png](doc%2Frecipes-api-logo.png)


## API Specification
Specification can be found at Yaml(/doc/spec/api.yml)

## Design Decisions:
* Springboot app together with standard libraries. Write less code to manage less code.
* 2 endpoints for managing receives and providing search functionalities 
* In memory database is used for persistence.
* Since there is an endpoint for search, performance can be improved by creating secondary indices on search filters.

## How to run
```
mvn clean
mvn compile                     // compile only
mvn test                        // run tests only
mvn install
```

## Improvements
* Add more unit and integration tests. Due to lack of time, all test cases are not covered/created.
* Ideally a more robust solution perhaps elastic search could be used to provide search/filter capabilities.
* Rest end point can be made asynchronous

