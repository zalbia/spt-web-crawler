package zalbia.spt.web.crawler.domain

import zio.json.{DeriveJsonEncoder, JsonEncoder}

/** Text representation of the data of crawling a URL */
final case class CrawlData(
    url: String,
    data: String
)

object CrawlData {
  implicit val encoder: JsonEncoder[CrawlData] = DeriveJsonEncoder.gen
}
