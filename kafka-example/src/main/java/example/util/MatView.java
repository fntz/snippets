package example.util;

public class MatView {

  public int id;
  public String title;
  public int author_id;
  public String author_name;
  public double price;

  public static MatView create(Book book, Author author) {
    MatView m = new MatView();

    m.id = book.id;
    m.title = book.title;
    m.price = book.price;
    m.author_id = author.id;
    m.author_name = author.name;

    return m;
  }

  @Override
  public String toString() {
    return "MatView[book=" + id + " title=" + title + " price=" + price + " author_id=" + author_id
        + " author_name=" + author_name + "]";
  }
}
