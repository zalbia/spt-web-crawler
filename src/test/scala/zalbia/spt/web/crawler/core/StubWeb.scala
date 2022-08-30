package zalbia.spt.web.crawler.core

import zio._

/** Provides stubbed responses for web requests for testing */
final class StubWeb(responses: Map[String, Either[Throwable, String]]) extends Web {
  override def getUrl(url: String): Task[String] =
    responses.get(url) match {
      case Some(response) =>
        ZIO.fromEither(response)
      case None           =>
        ZIO.fail(new RuntimeException("URL could not be reached"))
    }
}
