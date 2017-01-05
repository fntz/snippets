
import com.twitter.finagle.Thrift
import com.twitter.finagle.example.thriftscala.MyService
import com.twitter.util._

object MainFinagleLB extends App {
  import Common._

  val impl = new MyServiceImpl(port = port)
  val impl1 = new MyServiceImpl(port = port1)

  val service = Thrift.server.serveIface(addr, impl)
  val service1 = Thrift.server.serveIface(addr1, impl1)

  // Await

  // use from another place
  val client = Thrift.client
    .newIface[MyService.FutureIface](s"$addr, $addr1")
  (0 to 10).foreach { index =>
    client.hi("foo").foreach(println)
  }

}
