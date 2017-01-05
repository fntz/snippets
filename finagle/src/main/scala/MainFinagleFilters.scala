
import com.twitter.finagle.example.thriftscala.MyService
import com.twitter.finagle.{Service, SimpleFilter, Thrift}
import com.twitter.util._

object MainFinagleFilters extends App {

  import Common._

  val whoopFilter1 = new SimpleFilter[String, String] {
    override def apply(name: String, service: Service[String, String]): Future[String] = {
      service(s"$name!")
    }
  }

  val client = Thrift.client
    .newIface[MyService.FutureIface](s"$addr")

  val hiw = (s: String) => client.hi(s)

  val f = whoopFilter1 andThen hiw

  f("foo").foreach(result => println(result))


}
