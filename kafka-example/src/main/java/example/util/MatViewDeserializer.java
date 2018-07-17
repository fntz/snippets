package example.util;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

// special for consumer
public class MatViewDeserializer implements Deserializer<MatView> {
  private final Deserializer<MatView> d = AutoGen.gen(MatView.class).getSerder().deserializer();
  @Override
  public void configure(Map<String, ?> map, boolean b) {

  }

  @Override
  public MatView deserialize(String s, byte[] bytes) {
    return d.deserialize(s, bytes);
  }

  @Override
  public void close() {

  }
}
