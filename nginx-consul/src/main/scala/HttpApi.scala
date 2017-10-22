
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import akka.util.Timeout

import scala.concurrent.ExecutionContext

class HttpApi(serviceId: String)(
  implicit mat: ActorMaterializer,
  ec: ExecutionContext,
  timeout: Timeout
) {

  import Directives._

  val route = {
    path("resource") {
      complete(s"ok: $serviceId")
    } ~ path("health") {
      complete("ok")
    }
  }
}
