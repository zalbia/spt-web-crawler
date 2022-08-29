package zalbia.spt.web.crawler.api

import zio.json.{DeriveJsonDecoder, JsonDecoder}

/** Represents the request body for the endpoint "`api/crawl`" */
final case class CrawlParams(
    urls: List[String]
)

object CrawlParams {
  implicit val crawlParamsDecoder: JsonDecoder[CrawlParams] = DeriveJsonDecoder.gen[CrawlParams]
}
