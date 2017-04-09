
object Main extends App {

  val xs = args.toList

  println(s"args: ${xs.mkString(", ")}")

  xs match {
    case x :: _ if x == "1" =>
      hot_method_allocation_1()

    case x :: _ if x == "2" =>
      hot_method_allocation_2()

    case x :: _ if x == "3" =>
      threading_example_1()

    case x :: y :: _ if x == "4" =>
      file_read_example(y)

    case x :: y :: _ if x == "5" =>
      json_parsing_example(y)

    case Nil =>
      hot_method_allocation_1()

  }


  def json_parsing_example(p: String): Unit = {
    println("----> json parsing")
    parsing.Parsing.parse(p)
  }

  def file_read_example(p: String): Unit = {
    println("----> file read")
    read.FileRead.read(p)
  }

  def threading_example_1(): Unit = {
    println("----> threading #1")
    val t1 = new Thread(new threads.MyRunnable1())
    t1.setName("thread-1-atan")

    val t2 = new Thread(new threads.MyRunnable2())
    t2.setName("thread-2-sum")

    t1.start()
    t2.start()

  }


  def hot_method_allocation_1(): Unit = {
    println("----> hot methods & allocation #1")
    while(true) {
      hot.MyObject.method(System.nanoTime(), System.nanoTime())
    }
  }

  def hot_method_allocation_2(): Unit = {
    println("----> hot methods & allocation #2")
    while(true) {
      hot.MyObject.method1(System.nanoTime(), System.nanoTime())
    }
  }

}
