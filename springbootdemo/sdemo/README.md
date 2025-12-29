# Getting Started

### how to run

mvn spring-boot:run

### how to run with  docker

mvn clean package -Dmaven.test.skip=true

docker build -t perfvs/sdemo:1.0.1 .
docker run -p 21080:8080 perfvs/sdemo:1.0.1
