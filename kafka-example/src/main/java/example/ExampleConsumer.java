package example;

import example.util.MatView;
import example.util.MatViewDeserializer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class ExampleConsumer {

  static final String ConsumerId = "example-consumer";

  public static void main(String... args) {
    Properties props = Helper.load();

    // ---- additional properties
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class.getName());
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        MatViewDeserializer.class.getName());
    props.put(ConsumerConfig.GROUP_ID_CONFIG, ConsumerId);

    final Consumer<String, MatView> consumer = new KafkaConsumer<String, MatView>(props);
    consumer.subscribe(Collections.singletonList(Topics.RESULTS));

    int noRecordsCount = 0;
    final int giveUp = Integer.MAX_VALUE-1;

    while (true) {
      final ConsumerRecords<String, MatView> consumerRecords =
          consumer.poll(10);
      if (consumerRecords.count() == 0) {
        noRecordsCount++;
        if (noRecordsCount > giveUp) break;
        else continue;
      }
      consumerRecords.forEach(record -> {
        System.out.printf("key=" + record.key() + " value=" + record.value() + " partition= " +
            record.partition() + "offset=" + record.offset() + "\n");
      });
      consumer.commitAsync();
    }
    consumer.close();
  }
}

