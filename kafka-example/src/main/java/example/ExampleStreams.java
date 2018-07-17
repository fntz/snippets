package example;

import com.google.gson.Gson;
import example.util.Author;
import example.util.AutoGen;
import example.util.Book;
import example.util.MatView;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Serialized;

import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collectors;

public class ExampleStreams {

  static Gson gson = new Gson();

  static final AutoGen autoBook = AutoGen.gen(Book.class);
  static final AutoGen autoAuthor = AutoGen.gen(Author.class);
  static final AutoGen autoMatview = AutoGen.gen(MatView.class);
  static final AutoGen autoAl = AutoGen.genCol(Book.class, Book[].class);
  static final AutoGen autoAlMV = AutoGen.genCol(MatView.class, MatView[].class);

  static final Serde<String> strSerde = Serdes.String();
  static final Serde<Book> bookSerde = autoBook.getSerder();
  static final Serde<Author> authorSerde = autoAuthor.getSerder();
  static final Serde<MatView> matViewSerde = autoMatview.getSerder();
  static final Serde<ArrayList<Book>> alBSerde = autoAl.getColSerder();
  static final Serde<ArrayList<MatView>> alMatViewSerde = autoAlMV.getColSerder();

  public static void main(String... args) {
    Properties props = Helper.load();

    // *****************

    final StreamsBuilder builder = new StreamsBuilder();

    // ---- string to typed

    final KStream<String, String> books_stream = builder.stream(Topics.BOOKS);
    final KStream<String, Book> books_typed_stream = books_stream.map((k, v) -> {
      Book tmp = gson.fromJson(v, Book.class);
      return KeyValue.pair(String.valueOf(tmp.author_id), tmp);
    });
    books_typed_stream.to(Topics.BOOKS_TYPED, Produced.with(strSerde, bookSerde));


    final KStream<String, String> authors_stream = builder.stream(Topics.AUTHORS);
    final KStream<String, Author> authors_type_stream = authors_stream.map((k, v) -> {
      Author tmp = gson.fromJson(v, Author.class);
      return KeyValue.pair(String.valueOf(tmp.id), tmp);
    });
    authors_type_stream.to(Topics.AUTHOR_TYPED, Produced.with(strSerde, authorSerde));

    // ----- books by author

    final KStream<String, ArrayList<Book>> aggregated = books_typed_stream
        .map((k, v) -> KeyValue.pair(String.valueOf(v.author_id), v))
        .groupByKey(Serialized.with(strSerde, bookSerde))
        .aggregate(ArrayList<Book>::new, (k, v, xs) -> {
          xs.add(v);
          return xs;
        }, alBSerde).toStream();

    aggregated.to(Topics.BOOKS_BY_AUTHOR, Produced.with(strSerde, alBSerde));

    final KTable<String, ArrayList<Book>> aggregated_books_table =
        builder.table(Topics.BOOKS_BY_AUTHOR, Consumed.with(strSerde, alBSerde));
    final KTable<String, Author> author_table =
        builder.table(Topics.AUTHOR_TYPED, Consumed.with(strSerde, authorSerde));

    // ------- join & result

    final KStream<String, MatView> results =
        author_table.join(aggregated_books_table, (author, xs) -> {
          return xs.stream()
              .map(x -> MatView.create(x, author))
              .collect(Collectors.toCollection(ArrayList::new));
        }).toStream()  // <- String -> [MatView]
        .flatMapValues(xs -> xs); // <- String -> MatView

    results.to(Topics.RESULTS, Produced.with(strSerde, matViewSerde));


    final KafkaStreams streams = new KafkaStreams(builder.build(), props);
    Helper.run(streams);

  }
}