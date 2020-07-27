# weather-service
The purpose of this microservice is build a board with weather Forecast. Each user can define on or more boards, 
in order to include what locations are interesting for get information about weather forecast.
This microservice has being created with Play Framework 2.8, akka-typed and Slick 3 stack technologies. 

This API-rest uses the Yahoo weather-channel service.

Here is a postman collection example to understand how to work:
https://www.getpostman.com/collections/1d0aa193aab4638811b3

### run environment
docker-compose $ docker-compose up -d

### run local example
$ sbt "run 10000"

### build image
$ sbt docker:publishLocal

### run container
docker run --rm --net host --name weather-service weather-service-play:1.0.0

