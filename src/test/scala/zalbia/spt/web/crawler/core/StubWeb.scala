package zalbia.spt.web.crawler.core

import zio._

/** Provides stubbed responses for web requests for testing */
final class StubWeb(responses: Map[String, Either[CrawlErrorMessage, String]]) extends Web {
  override def getUrl(url: String): IO[CrawlErrorMessage, String] =
    responses.get(url) match {
      case Some(response) =>
        ZIO.fromEither(response)
      case None           =>
        ZIO.fail(CrawlErrorMessage(s"""No response stubbed for "${url}""""))
    }
}
