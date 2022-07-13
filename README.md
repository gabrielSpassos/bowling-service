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

## Outputs
- Standard game
```
Frame		1		2		3		4		5		6		7		8		9		10
Jeff
Pinfalls	X		7	/	9	0	X		0	8	8	/	0	6	X		X		X	8	1	
Score		20		39		48		66		74		84		90		120		148		167		
John
Pinfalls	3	/	6	3	X		8	1	X		X		9	0	7	/	4	4	X	9	0	
Score		16		25		44		53		82		101		110		124		132		151	
```

- Perfect Game
```
Frame		1		2		3		4		5		6		7		8		9		10
Carl
Pinfalls	X		X		X		X		X		X		X		X		X		X	X	X	
Score		30		60		90		120		150		180		210		240		270		300
```

- Empty File
- Just process but nothing happens


- Extra score
- Throws `ValidationException`, with message: `Invalid frame, player with more than one play in this frame`
- Is possible to checkout execution error with query:
```
select * from BATCH_JOB_EXECUTION;
```

- Free Text
- Throws `FlatFileParseException`
- Is possible to checkout execution error with query:
```
select * from BATCH_JOB_EXECUTION;
```

- Invalid score
- `IllegalArgumentException`, with message: `Invalid fault score`
- Is possible to checkout execution error with query:
```
select * from BATCH_JOB_EXECUTION;
```

- Negative score
- `IllegalArgumentException`, with message: `Invalid fault score`
- Is possible to checkout execution error with query:
```
select * from BATCH_JOB_EXECUTION;
```