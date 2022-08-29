package zalbia.spt.web.crawler.domain

import zio.json.{DeriveJsonEncoder, JsonEncoder}

/** Representation
  *
  * @param error
  *   A list of strings
  * @param result
  */
final case class CrawlResult(
    error: List[CrawlError],
    result: List[CrawlData]
)

object CrawlResult {
  implicit val encoder: JsonEncoder[CrawlResult] = DeriveJsonEncoder.gen
}
