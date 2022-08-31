package zalbia.spt.web.crawler.core

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

/** Text representation of the data of crawling a URL */
final case class CrawlData(
    url: String,
    data: String
)

object CrawlData {
  implicit val decoder: JsonDecoder[CrawlData] = DeriveJsonDecoder.gen
  implicit val encoder: JsonEncoder[CrawlData] = DeriveJsonEncoder.gen
}
