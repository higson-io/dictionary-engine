# Hyperon Dictionary-Engine Demo App

This is a sample configuration application to demonstrate capabilities of Hyperon.io library. 

Hyperon.io tutorials are availble [here](https://hyperon.io/tutorials/getting-started).

## Prerequisites

Make sure you have at least:

#### Java 11

#### Gradle 4.10.2

To install gradle go to:

https://gradle.org/releases/

Previous Gradle versions might work as well but this was not verified.

#### Hyperon Studio 1.6.50

1. Go to:

http://hyperon.io/download

2. Download bundle, unpack it to the directory of your choice and run it as described 
[here](http://hyperon.io/tutorials/deploying-hyperon-studio).

## Setup

Make sure that the command ```gradle``` is accessible through system path. If not, add it.

This sample is using a H2 Hyperon database. 
Property ```hyperon.database.url``` defined in file ```/src/main/resources/application.yml``` 
points by default to h2 database file located in directory ```/db``` within this project.

## Running

Before running the application make sure that system Path variable points to java version 11.

Execute below gradle command to run Spring Boot.

```text
./gradlew build && java -jar build/libs/dictionary-engine-0.1-SNAPSHOT.jar
```

Application will be accessible on port 8082 
(URL: [http://localhost:8082/greeting](http://localhost:8082/greeting)). 
If you need to use different port, change the value of property  ```server.port``` 
in file ```application.yml```.

