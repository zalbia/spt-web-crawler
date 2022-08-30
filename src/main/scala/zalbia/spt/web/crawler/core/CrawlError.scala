package zalbia.spt.web.crawler.core

import zio.json.{DeriveJsonEncoder, JsonEncoder}

/** An error encountered while crawling a URL.
  *
  * @param url
  *   failing URL
  * @param error
  *   error string
  */
final case class CrawlError(
    url: String,
    error: String
)

object CrawlError {
  implicit val encoder: JsonEncoder[CrawlError] = DeriveJsonEncoder.gen
}
