# Getting Started

### how to run

go mod tidy
go run .

### how to run with  docker

docker build -t perfvs/beedemo:1.0.1 .
docker run -p 21170:8080 perfvs/beedemo:1.0.1
