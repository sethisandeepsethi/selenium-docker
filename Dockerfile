FROM bellsoft/liberica-openjdk-alpine:21.0.4
WORKDIR /home/selenium-docker
ADD target/docker-resources ./
