import zalbia.spt.web.crawler.api.HttpRoutes
import zalbia.spt.web.crawler.infra.ZioHttpWeb
import zalbia.spt.web.crawler.service.WebCrawlerServiceLive
import zhttp.service._
import zio._

object Main extends ZIOAppDefault {

  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    Server
      .start(8080, HttpRoutes.app)
      .provide(
        ChannelFactory.auto,
        EventLoopGroup.auto(),
        ZioHttpWeb.layer,
        WebCrawlerServiceLive.layer
      )
}
