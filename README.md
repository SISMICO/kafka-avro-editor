# kafka-avro-editor
A tool to help developers in the development process when using Kafka messages with Schema Registry.
This solution has two modules:
## 1. Editor
The core solution that allows developers to send messages with different schemas

## 3. Console (CLI)
A CLI solution that helps developers to use the Editor module at any Linux terminal

# How to use
You can use Docker Compose:
```
docker-compose -f docker/docker-compose-editor.yml run editor
```

## List Topics
```
docker-compose -f docker/docker-compose-editor.yml run editor -l
```

## Get a Json Object example
```
docker-compose -f docker/docker-compose-editor.yml run editor -j {topic}
```

## Send a message
```
docker-compose -f docker/docker-compose-editor.yml run editor -s {topic}
```

# Kafka and Schema Registry Security
To use this app on a real world environment you will need to configure some Environment varibles at Docker Compose file.
For example:
```
...
    environment:
      - SCHEMA_REGISTRY_SERVER=https://myserver.mydomain.com
      - SCHEMA_REGISTRY_USER=admin
      - SCHEMA_REGISTRY_PASSWORD=password
      - KAFKA_SERVER=myserver.mydomain.com:9092
      - KAFKA_SASL_MECHANISM=PLAIN
      - KAFKA_SASL_JAAS_CONFIG=org.apache.kafka.common.security.plain.PlainLoginModule required username\="admin" password\="password"\;
      - KAFKA_SECURITY_PROTOCOL=SASL_SSL
...
```