package zalbia.spt.web.crawler.core

import zio.json.{JsonDecoder, JsonEncoder}

/** Wraps an error message from crawling a URL */
final case class CrawlErrorMessage(value: String)

object CrawlErrorMessage {
  implicit val decoder: JsonDecoder[CrawlErrorMessage] =
    JsonDecoder.string.map(CrawlErrorMessage(_))

  implicit val encoder: JsonEncoder[CrawlErrorMessage] =
    JsonEncoder.string.contramap((errorMessage: CrawlErrorMessage) => errorMessage.value)
}
