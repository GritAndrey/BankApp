# Bank Application microservices

![Architecture](https://i.imgur.com/KFf2Bam.png)
![Logic](https://i.imgur.com/DgD53bf.png)
![Entity](https://i.imgur.com/hO9aZS3.png)

## Docker-compose
```shell
docker-compose build 
```
bankapp_notification-service:latest
```shell
docker-compose up
```
## Eureka
http://localhost:8761/

## RabbitMQ
```shell
docker run -p 15672:15672 -p 5672:5672 rabbitmq:3-management
```
http://localhost:15672/
l:guest p:guest

## Config-Service
DockerFile
```
FROM openjdk:17-jdk-alpine
EXPOSE 8001
COPY build/libs/config-service-1.0-SNAPSHOT-plain.jar .
ENTRYPOINT [ "java", "-jar", "config-service-1.0-SNAPSHOT-plain.jar"]
```
build:
```shell
docker build -t config-service .
```
check:
```shell
docker images
```
run:
```shell
docker run -p 8001:8001 config-service:latest
```