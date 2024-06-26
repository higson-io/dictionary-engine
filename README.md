# Higson Dictionary-Engine Demo App

This is a sample configuration application to demonstrate capabilities of Higson.io library. 

Higson.io tutorials are available [here](https://www.higson.io/docs/start-documentation).

## Prerequisites

Make sure you have at least:

#### Java 11

#### Gradle 5.4.1

To install gradle go to:

https://gradle.org/releases/

Previous Gradle versions may also work but this was not verified.

#### Higson Studio 1.6.51

1. Go to:

    https://www.higson.io/docs/download

2. Download bundle, extract it to directory of your choice and configure 
Higson Studio so that it uses the same database as this application 
(see [Setup](#setup) below). Run Higson Studio as described 
[here](https://www.higson.io/docs/start-documentation).  

## Setup

Make sure that the command ```gradle``` is accessible through system path.

This sample is configured to use data from H2 database file bundled with Higson
Studio. You should edit the property ```hyperon.database.url``` defined in file 
```/src/main/resources/application.yml``` so that it points to database file included in 
sub-directory ```database``` of the Higson bundle directory. Replacing the string 
```YOUR_HYPERON_BUNDLE_DIRECTORY``` by path to extracted Higson bundle should be 
sufficient.

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
Users of Intellij IDEA can check the settings option 'Build, Execution, Deployment' > 
'Build Tools > 'Gradle' > 'Runner' > '**Delegate IDE build/run actions to Gradle**' to allow
auto-generation of sources when building the project directly from IDE.

Application will be accessible on port 8082. 
If you need to use different port, change the value of property  ```server.port``` 
in file ```application.yml```. 

### Running with Docker
This demo application can be run in docker container based on provided Dockerfile.
For building image execute code below:
```text
docker build -t hyperonio/dictionary-engine-demo .
```
Build is optional since motor-demo is available on docker hub:
https://hub.docker.com/r/hyperonio/dictionary-engine-demo
```text
docker pull hyperonio/dictionary-engine-demo
```
If image is build, then application can be run in docker container like:
```text
docker run -p 38080:8082 
    -e mpp.database.url=<jdbc_url_to_running_db>
    -e mpp.database.dialect=<choose>
    -e mpp.database.username=<db_username>
    -e mpp.database.password=<db_password>
    -e mpp.environment.id=higson_docker
    hyperonio/dictionary-engine-demo
```
OR application can be run with bundle-h2-demo and higson-studio images
using docker-compose based on docker-compose.yml. Simply run:
```text
docker-compose up
```
* By default Higson Studio will be available at: [host]:38080/higson/app
* By default Demo application will be available at: [host]:48080


### REST api

`When application is started with Docker (without any custom port mappings), documentation of REST api is available at:
[http://localhost:48080/swagger-ui.html](http://localhost:48080/swagger-ui.html).
Otherwise, when application is build and started with gradle, documentation is available at:
[http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html).
### SOAP endpoint

The WSDL definition of SOAP endpoint is available at 
[http://localhost:8082/ws/dictionaries.wsdl](http://localhost:8082/ws/dictionaries.wsdl). 
Available SOAP requests correspond to appropriate REST endpoints and provide the same functionality.  

