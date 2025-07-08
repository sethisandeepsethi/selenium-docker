FROM bellsoft/liberica-openjdk-alpine:21.0.4

# Workspace
WORKDIR /home/selenium-docker

# Add required files in the docker image
ADD target/docker-resources ./

# Environment Variables
# BROWSER
# HUB_HOST
# THREAD_COUNT
# TEST_SUITE

# Run the tests
ENTRYPOINT java -cp 'libs/*' \
           -DisRemote=true \
           -Dbrowser=${BROWSER} \
           -DremoteUrlHost=${HUB_HOST} \
           org.testng.TestNG \
           -threadcount ${THREAD_COUNT} \
           test-suites/${TEST_SUITE}