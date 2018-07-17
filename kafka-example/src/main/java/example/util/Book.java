package example.util;

public class Book {
  public int id;
  public String title;
  public int author_id;
  public double price;

  @Override
  public String toString() {
    return "Book[id=" + id + " title=" + title + " author=" + author_id + " price=" + price + "]";
  }
}