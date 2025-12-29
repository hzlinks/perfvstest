# Getting Started

### how to run

go mod tidy
go run .

### how to run with  docker

docker build -t perfvs/gindemo:1.0.1 .
docker run -p 21070:8080 perfvs/gindemo:1.0.1
