package zalbia.spt.web.crawler.api

import zalbia.spt.web.crawler.service.WebCrawlerService
import zhttp.http._
import zio.ZIO
import zio.json._

object HttpRoutes {

  /** Specifies the POST `/api/crawl` endpoint. Parses URLs from JSON and returns results in JSON */
  val app: HttpApp[WebCrawlerService, Nothing] =
    Http.collectZIO { case req @ Method.POST -> !! / "api" / "crawl" =>
      (for {
        crawlParams <- req.bodyAsString
                         .map(_.fromJson[CrawlParams].left.map(new RuntimeException(_)))
                         .absolve
                         .tapError((_: Throwable) => ZIO.logInfo("Request body could not be parsed"))
        crawlResult <- WebCrawlerService.crawl(crawlParams.urls)
      } yield crawlResult).either.map {
        case Left(_)            => Response.status(Status.BadRequest)
        case Right(crawlResult) => Response.json(crawlResult.toJson)
      }
    }
}
