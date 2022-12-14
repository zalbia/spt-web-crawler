package zalbia.spt.web.crawler.core

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

/** A URL and error message describing what went wrong during an attempt to crawl it. */
final case class CrawlError(
    url: String,
    error: CrawlErrorMessage
)

object CrawlError {
  implicit val decoder: JsonDecoder[CrawlError] = DeriveJsonDecoder.gen
  implicit val encoder: JsonEncoder[CrawlError] = DeriveJsonEncoder.gen
}
