
import com.twitter.finagle._
import com.twitter.finagle.example.thriftscala.MyService
import com.twitter.util._

object MainZoo extends App {

  /**
  zookeeper configuration:

    tickTime=2000
    dataDir=~/zookeeper
    clientPort=2181

    */

  import Common._

  val zkPort = 2181

  val impl = new MyServiceImpl(port)
  val impl1 = new MyServiceImpl(port1)

  val service =  Thrift.server.serveIface(
    s":$port", impl
  )
  val service1 =  Thrift.server.serveIface(
    s":$port1", impl1
  )

  service.announce(s"zk!$host:$zkPort!/service/impl!0")
  service1.announce(s"zk!$host:$zkPort!/service/impl!1")


  val client = Thrift.client
    .newIface[MyService.FutureIface](
    dest = s"zk!$host:$zkPort!/service/impl",
    label = "my-service"
  )

  Thread.sleep(3000)

  (0 to 10).foreach { index =>
    client.hi(s"name#$index").foreach(println)
  }

  Thread.sleep(10000)

}
