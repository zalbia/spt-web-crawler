package zalbia.spt.web.crawler.domain

import zio.json.{DeriveJsonEncoder, JsonEncoder}

/** Represents the result of crawling urls, including both errors and data */
final case class CrawlResult(
    error: Option[List[CrawlError]],
    result: Option[List[CrawlData]]
)

object CrawlResult {
  implicit val encoder: JsonEncoder[CrawlResult] = DeriveJsonEncoder.gen
}
