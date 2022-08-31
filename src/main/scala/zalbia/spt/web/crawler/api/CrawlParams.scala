package zalbia.spt.web.crawler.api

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

/** The request body for the endpoint "`api/crawl`" */
final case class CrawlParams(
    urls: List[String]
)

object CrawlParams {
  implicit val crawlParamsDecoder: JsonDecoder[CrawlParams] = DeriveJsonDecoder.gen

  implicit val crawlParamsEncoder: JsonEncoder[CrawlParams] = DeriveJsonEncoder.gen
}
