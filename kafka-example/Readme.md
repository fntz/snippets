# How-to run

1. start kafka: `bin/confluent start kafka`

2. create topics:

```
bin/kafka-topics --zookeeper localhost:2181 --create --topic books --partitions 1 --replication-factor 1
bin/kafka-topics --zookeeper localhost:2181 --create --topic authors --partitions 1 --replication-factor 1
bin/kafka-topics --zookeeper localhost:2181 --create --topic books_typed_stream --partitions 1 --replication-factor 1
bin/kafka-topics --zookeeper localhost:2181 --create --topic authors_typed_stream --partitions 1 --replication-factor 1
bin/kafka-topics --zookeeper localhost:2181 --create --topic books_by_author --partitions 1 --replication-factor 1
bin/kafka-topics --zookeeper localhost:2181 --create --topic results --partitions 1 --replication-factor 1
```

3. check topics: `bin/kafka-topics --list --zookeeper localhost:2181`

4. push books:

`cat books.topic | bin/kafka-console-producer --broker-list localhost:9092 --topic books --property parse.key=true --property key.separator=, `

5. push authors:

`cat authors.topic | bin/kafka-console-producer --broker-list localhost:9092 --topic authors --property parse.key=true --property key.separator=, `

6. Run ExampleStreams

7. Run ExampleConsumer


### useful command (get data from topic)

`bin/kafka-console-consumer --zookeeper localhost:2181 --topic results --from-beginning --property print.key=true`
