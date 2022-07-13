# Java Challenge

## How to run

- Test
```shell
./gradlew clean test
```

- Build
```shell
./gradlew clean build
```

- Start database
```shell
docker-compose build
docker-compose up --detach
```

- Start Project
```shell
java -jar ./build/libs/bowling-service-0.0.1-SNAPSHOT.jar
```

## Technologies
* **Java 11**: OO language, one of most updated versions.
* **Spring Batch**: Batch framework from spring ecosystem, made to handle ELT process
* **MySQL**: Relational database to store information needed for **Spring Batch** 
* **JUnit 5**: Unit test framework
* **Lombok**: Development Plugin to clean POJO's
* **Docker**: Container tool to create environment for application
* **Docker Compose**: Containers orchestration