#!/bin/bash

# Start the first process
gradle -t :bootJar &

# Start the second process
./gradlew bootRun &

# Wait for any process to exit
wait -n

# Exit with status of process that exited first
exit $?