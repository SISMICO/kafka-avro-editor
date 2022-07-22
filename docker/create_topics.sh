#!/bin/bash
for n in {1..50}
do
    echo -e "\nAdding topic$n to Kafka and Schema Registry"
    docker exec kae-broker kafka-topics --bootstrap-server localhost:9092 --create --topic topic$n
    cat schemas/simple.avsc | jq '{ "schema": . | tostring }' | curl -X POST --data @- -H "Content-Type: application/vnd.schemaregistry.v1+json" http://localhost:8081/subjects/topic$n-value/versions
done
