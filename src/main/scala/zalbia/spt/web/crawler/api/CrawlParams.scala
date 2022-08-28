package zalbia.spt.web.crawler.api

import zio.json.{DeriveJsonDecoder, JsonDecoder}

final case class CrawlParams(
    urls: List[String]
)

object CrawlParams {
  implicit val crawlParamsDecoder: JsonDecoder[CrawlParams] = DeriveJsonDecoder.gen[CrawlParams]
}
