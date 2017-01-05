import java.time.LocalDateTime

import com.twitter.finagle.Thrift
import com.twitter.finagle.example.thriftscala.{DateTimeService, MyService}
import com.twitter.util.{Future, FuturePool}
import zipkin.finagle.http.HttpZipkinTracer

object Common {
  val port = 8000
  val port1 = 8001
  val host = "localhost"

  val addr = s"$host:$port"
  val addr1 = s"$host:$port1"

  val dtPort = 8090
  val dtAddr = s"$host:$dtPort"

  class MyServiceImpl(val port: Int = 8000) extends MyService[Future] {
    override def hi(name: String): Future[String] = {
      println(s"service: $port")
      Future.value(s"hi, $name")
    }
  }

  class DateTimeServiceImpl extends DateTimeService[Future] {
    val pool = FuturePool.unboundedPool
    override def getDate(): Future[String] = {
      pool {
        Thread.sleep(1000)
        LocalDateTime.now().toString
      }
    }
  }

  class MyServiceImplN extends MyService[Future] {
    val dtsClient =  Thrift.client
      .withTracer(new HttpZipkinTracer())
      .withLabel("thrift-datetime-client")
      .newIface[DateTimeService.FutureIface](dtAddr)

    override def hi(name: String): Future[String] = {
      dtsClient.getDate().map { date =>
        s"[$date] ---> hi, $name"
      }
    }
  }

}
