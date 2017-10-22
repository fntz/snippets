
import java.net.URL

import akka.actor._
import akka.http.scaladsl.Http
import akka.util.Timeout
import akka.stream.ActorMaterializer

import com.orbitz.consul.Consul

import scala.concurrent.duration._

object Main extends App {
  if (args.length != 2) {
    println("=== Pass port and serviceId")
    sys.exit(1)
  }

  val port = args(0).toInt
  val serviceId = args(1)

  implicit val system = ActorSystem("my-system")
  import system.dispatcher
  implicit val mat = ActorMaterializer()
  implicit val timeout = Timeout(10 seconds)

  val interface = "localhost"

  val httpService = new HttpApi(serviceId)
  Http().bindAndHandle(httpService.route, interface, port)

  val consul = Consul.builder().build()
  val agentClient = consul.agentClient()

  val service = "my-backend-service"
  val url = new URL(s"http://$interface:$port/health")
  agentClient.register(port, url, 10L, service, serviceId, "http", "backend", "resource")

}