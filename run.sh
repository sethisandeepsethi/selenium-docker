# start the grid with 2 chrome and 2 firefox containers
docker-compose -f grid.yml up --scale chrome=2 --scale firefox=2 -d

# run vendor-portal test suites with 4 chrome threads
BROWSER=chrome THREAD_COUNT=4 TEST_SUITE=vendor-portal.xml docker-compose up

# run flight-reservation test suites with 4 firefox threads
BROWSER=firefox THREAD_COUNT=4 TEST_SUITE=flight-reservation.xml docker-compose up

# bring down all
docker-compose -f grid.yaml down
docker-compose down