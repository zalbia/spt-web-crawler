package zalbia.spt.web.crawler.core

import zio.json.JsonEncoder

/** Wraps an error message from crawling a URL */
final case class CrawlErrorMessage(value: String)

object CrawlErrorMessage {
  implicit val encoder: JsonEncoder[CrawlErrorMessage] =
    JsonEncoder.string.contramap((errorMessage: CrawlErrorMessage) => errorMessage.value)
}
