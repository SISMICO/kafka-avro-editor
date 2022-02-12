# Developer Guidelines

## Kafka Environment
To configure a Kafka environment you can use the `docker-compose.yml` file at `docker` folder.
It will start one Zookeeper, Kafka Broker and Confluent Control Center.

``` bash
docker-compose -f docker/docker-compose.yml up
```

### Confluent Control Center
[Click here to use Confluent Control Center](http://localhost:9021)

## Creating some topics
First you need create a [Kafka Environment](#kafka-environment).
After that you can run `create-topic.sh` script at `docker` folder.

``` bash
./docker/create_topics.sh
```
