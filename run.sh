# start the grid with 2 chrome and 2 firefox containers
docker-compose -f docker-compose_grid.yml up --scale chrome=2 --scale firefox=2 -d

# run vendor-portal test suites with 4 chrome threads
BROWSER=chrome THREAD_COUNT=4 TEST_SUITE=vendor-portal.xml docker-compose -f docker-compose_tests.yml up

# run flight-reservation test suites with 4 firefox threads
BROWSER=firefox THREAD_COUNT=4 TEST_SUITE=flight-reservation.xml docker-compose -f docker-compose_tests.yml up

# bring down all
docker-compose -f docker-compose_grid.yml down
docker-compose -f docker-compose_tests.yml down
