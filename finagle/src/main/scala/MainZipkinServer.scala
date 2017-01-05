
import com.twitter.finagle.Thrift
import com.twitter.util._
import zipkin.finagle.http.HttpZipkinTracer

object MainZipkinServer extends App {

  // run via MainZipkinClient

  import Common._

  val impl = new MyServiceImplN
  val dtImpl = new DateTimeServiceImpl

  val service =  Thrift.server
    .withTracer(new HttpZipkinTracer())
    .withLabel("thrift-impln-service")
    .serveIface(addr, impl)

  val dateTimeService = Thrift.server
    .withLabel("thrift-datetime-service")
    .withTracer(new HttpZipkinTracer())
    .serveIface(dtAddr, dtImpl)


  Await.ready(service)







}
