# qkdemo


## how to run with  docker

mvn clean package -Dmaven.test.skip=true

docker build -t perfvs/qkemo:1.0.1 .

docker run -p 23080:8080 perfvs/qkemo:1.0.1


## how to run with  docker native

docker build -t perfvs/qkemo-native:1.0.1 -f Dockerfile-native .

docker run -p 24080:8080 perfvs/qkemo-native:1.0.1
