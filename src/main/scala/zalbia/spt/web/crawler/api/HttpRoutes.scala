package zalbia.spt.web.crawler.api

import zhttp.http._
import zio.ZIO
import zio.json._

object HttpRoutes {

  /** Specifies the POST `/api/crawl` endpoint.
    */
  val app: HttpApp[Any, Nothing] =
    Http.collectZIO { case req @ Method.POST -> !! / "api" / "crawl" =>
      req.bodyAsString
        .map(_.fromJson[CrawlParams])
        .absolve[Serializable, CrawlParams]
        .tapError(_ => ZIO.logInfo("Request body could not be parsed"))
        .either
        .map {
          case Left(_)       => Response.status(Status.BadRequest)
          case Right(params) => Response.text(s"Parsed params: ${params}")
        }
    }
}
