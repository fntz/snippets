
package read {

  import java.io.File

  object FileRead {

    def read(path: String) = {
      println(s"read from $path")
      while (true) {
        val r = scala.io.Source.fromFile(new File(path))
        r.close()
      }
    }

  }

}
