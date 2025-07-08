FROM bellsoft/liberica-openjdk-alpine:21.0.4

# Install curl jq
RUN apk add curl jq

# Workspace
WORKDIR /home/selenium-docker

# Add required files in the docker image
ADD target/docker-resources ./
ADD gridReady.sh gridReady.sh

#Run the shell script
ENTRYPOINT sh gridReady.sh
