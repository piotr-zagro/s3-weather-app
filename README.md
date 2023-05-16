# Nexer Insight Java + AWS technical task starting point
This repository contains optional starting point for your implementation of the technical task. 
It has implemented integration test using [TestContainers](https://www.testcontainers.org/) and
[LocalStack](https://localstack.cloud/). Mocked S3 contains example data in the format specified in the 
description of the task.

Feel free to modify it for the purpose of implementing the technical task as a part of the interview process
for Nexer Insight. Please don't share it with people not involved in the interview process.

Suggestions and information about issues, appreciated!

Note: The ExampleService and implemented test don't follow best practices. 
The purpose of them is to test the configuration of the project and show how you can use it. 
Feel free to remove them.

# Prerequisites
- Java 17
- Docker

# Building project
`
./gradlew build
`

# Running tests

`
./gradlew test
`
