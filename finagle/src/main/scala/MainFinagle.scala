
import com.twitter.finagle.Thrift
import com.twitter.finagle.example.thriftscala.MyService
import com.twitter.util._

object MainFinagle extends App {

  import Common._

  val impl = new MyServiceImpl

  val service = Thrift.server.serveIface(addr, impl)

  Await.ready(service)

  // use from another place

  val client = Thrift.client
    .newIface[MyService.FutureIface](s"$addr")

  client.hi("foo").foreach(result => println(s"$result"))


}
