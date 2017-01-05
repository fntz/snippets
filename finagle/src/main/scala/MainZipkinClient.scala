
import com.twitter.finagle.Thrift
import com.twitter.finagle.example.thriftscala.MyService
import zipkin.finagle.http.HttpZipkinTracer

object MainZipkinClient extends App {

  import Common._

  val client = Thrift.client
    .withTracer(new HttpZipkinTracer())
    .withLabel("thrift-impl-client")
    .newIface[MyService.FutureIface](addr)

  (0 to 10).foreach { index =>
    Thread.sleep(1000)
    client.hi(s"name-$index").foreach(println)
  }

  Thread.sleep(10000)

}
