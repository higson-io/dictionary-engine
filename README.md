# Hyperon Dictionary-Engine Demo App

This is a sample configuration application to demonstrate capabilities of Hyperon.io library. 

Hyperon.io tutorials are availble [here](https://hyperon.io/tutorials/getting-started).

## Prerequisites

Make sure you have at least:

#### Java 11

#### Gradle 5.4.1

To install gradle go to:

https://gradle.org/releases/

Previous Gradle versions may also work but this was not verified.

#### Hyperon Studio 1.6.50

1. Go to:

    http://hyperon.io/download

2. Download bundle, extract it to directory of your choice and configure 
Hyperon Studio so that it uses the same database as this application 
(see [Setup](#setup) below). Run Hyperon Studio as described 
[here](http://hyperon.io/tutorials/deploying-hyperon-studio).  

## Setup

Make sure that the command ```gradle``` is accessible through system path.

This sample is using a H2 Hyperon database. 
Property ```hyperon.database.url``` defined in file ```/src/main/resources/application.yml``` 
points by default to h2 database file located in directory ```/db``` within this project.

## Running

Before running the application make sure that system Path variable points to java version 11.

Execute below gradle command to run Spring Boot:

Windows:
   ```text
   gradlew build && java -jar build/libs/dictionary-engine-0.1-SNAPSHOT.jar
   ```

Unix:
```text
./gradlew build && java -jar build/libs/dictionary-engine-0.1-SNAPSHOT.jar
```

Application will be accessible on port 8082. 
If you need to use different port, change the value of property  ```server.port``` 
in file ```application.yml```. 

### REST api

When the application is running, documentation of REST api is available at  
[http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html).

### SOAP endpoint

The WSDL definition of SOAP endpoint is available at 
[http://localhost:8082/ws/dictionaries.wsdl](http://localhost:8082/ws/dictionaries.wsdl). 
Available SOAP requests correspond to appropriate REST endpoints and provide the same functionality.  

