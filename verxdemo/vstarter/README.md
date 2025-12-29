= Vstarter

image:https://img.shields.io/badge/vert.x-5.0.4-purple.svg[link="https://vertx.io"]

This application was generated using http://start.vertx.io


# Getting Started

### how to run

mvn clean compile exec:java

### how to run with  docker

mvn clean package -Dmaven.test.skip=true

docker build -t perfvs/vxtdemo:1.0.1 .
docker run -p 22080:8080 perfvs/vxtdemo:1.0.1


