package example.util;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class AutoGen<T, A> {

  private final Class<T> type;
  private final Class<A> arrType;

  private static final Charset charset = StandardCharsets.UTF_8;
  private static Gson gson = new Gson();

  private Class<A> getArrType() {
    return arrType;
  }

  private AutoGen(Class<T> type, Class<A> arrType) {
    this.type = type;
    this.arrType = arrType;
  }

  public static <X> AutoGen<X, X> gen(Class<X> type) {
    return new AutoGen<X, X>(type, type);
  }

  public static <X, Y> AutoGen<X, Y> genCol(Class<X> type, Class<Y> arrType) {
    return new AutoGen<X, Y>(type, arrType);
  }

  private final Serializer<T> serializer = new Serializer<T>() {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, T t) {
      return gson.toJson(t).getBytes(charset);
    }

    @Override
    public void close() {

    }
  };

  private final Serializer<ArrayList<T>> colSerializer = new Serializer<ArrayList<T>>() {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, ArrayList<T> ts) {
      return gson.toJson(ts).getBytes(charset);
    }

    @Override
    public void close() {

    }
  };

  private final Deserializer<ArrayList<T>> colDeserializer = new Deserializer<ArrayList<T>>() {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public ArrayList<T> deserialize(String s, byte[] bytes) {
      String tmp = new String(bytes, charset);
      Class<A> a = getArrType();
      T[] result = (T[])gson.fromJson(tmp, a);
      return new ArrayList<>(Arrays.asList(result));
    }

    @Override
    public void close() {

    }
  };

  private final Deserializer<T> deserializer = new Deserializer<T>() {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public T deserialize(String s, byte[] bytes) {
      String tmp = new String(bytes, charset);
      return gson.fromJson(tmp, type);
    }

    @Override
    public void close() {

    }
  };

  private final Serde<T> serder = new Serde<T>() {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public void close() {

    }

    @Override
    public Serializer<T> serializer() {
      return serializer;
    }

    @Override
    public Deserializer<T> deserializer() {
      return deserializer;
    }
  };

  private final Serde<ArrayList<T>> colSerder = new Serde<ArrayList<T>>() {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public void close() {

    }

    @Override
    public Serializer<ArrayList<T>> serializer() {
      return colSerializer;
    }

    @Override
    public Deserializer<ArrayList<T>> deserializer() {
      return colDeserializer;
    }
  };

  public Serde<T> getSerder() {
    return serder;
  }

  public Serde<ArrayList<T>> getColSerder() { return colSerder; }

}
