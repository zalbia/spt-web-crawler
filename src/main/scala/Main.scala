import zalbia.spt.web.crawler.api.HttpRoutes
import zhttp.service._
import zio._

object Main extends ZIOAppDefault {

  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    Server.start(8080, HttpRoutes.app)
}
