# weather-service
Consumes Yahoo weather-channel service using play 2.8, akka-type and slick 3.

### build image
$ sbt docker:publishLocal

### run environment
docker-compose $ docker-compose up -d

### run container
docker run --rm --net host --name weather-service-play weather-service-play:1.0.0

http://localhost:9000/board

