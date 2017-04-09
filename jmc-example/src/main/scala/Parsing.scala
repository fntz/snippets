
package parsing {

  import java.io.File
  import play.api.libs.json._

  case class Person(id: Int, name: String)


  object Parsing {
    implicit val personFormat: Format[Person] = Json.format[Person]

    def parse(path: String) = {
      val s = scala.io.Source.fromFile(new File(path)).mkString

      while (true) {
        Json.parse(s).as[Vector[Person]]
        Thread.sleep(5000)
      }

    }
  }

}