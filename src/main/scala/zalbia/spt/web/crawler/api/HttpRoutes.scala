package zalbia.spt.web.crawler.api

import zhttp.http._
import zio.ZIO

object HttpRoutes {
  val app: HttpApp[Any, Nothing] =
    Http.collectZIO { case Method.POST -> !! / "api" / "crawl" =>
      ZIO.succeed(Response.text("stub response\n"))
    }
}
