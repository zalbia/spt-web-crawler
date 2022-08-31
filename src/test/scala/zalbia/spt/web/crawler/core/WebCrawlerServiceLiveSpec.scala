package zalbia.spt.web.crawler.core

import zio.test.Assertion._
import zio.test.{ErrorMessage => _, _}

object WebCrawlerServiceLiveSpec extends ZIOSpecDefault {
  override def spec =
    suite("WebCrawlerServiceLive")(
      test("can crawl URLs that are up") {
        val service = new WebCrawlerServiceLive(
          new StubWeb(
            Map(
              "https://foo.com" -> Right("<html><body>foo</body></html>"),
              "https://bar.com" -> Right("<html><body>bar</body></html>"),
              "https://baz.com" -> Right("<html><body>baz</body></html>")
            )
          )
        )

        val expected = CrawlResult(
          error = None,
          result = Some(
            List(
              CrawlData("https://foo.com", "<html><body>foo</body></html>"),
              CrawlData("https://bar.com", "<html><body>bar</body></html>")
            )
          )
        )

        assertZIO(service.crawl(List("https://foo.com", "https://bar.com")))(equalTo(expected))
      },
      test("crawl errors are returned") {
        val service = new WebCrawlerServiceLive(
          new StubWeb(
            Map(
              "https://foo.com" -> Left(CrawlErrorMessage("Can't be reached")),
              "https://bar.com" -> Left(CrawlErrorMessage("Connection timed out")),
              "https://baz.com" -> Right("<html><body>baz</body></html>")
            )
          )
        )

        val expected = CrawlResult(
          error = Some(
            List(
              CrawlError(
                url = "https://foo.com",
                error = CrawlErrorMessage(value = "Can't be reached")
              ),
              CrawlError(
                url = "https://bar.com",
                error = CrawlErrorMessage(value = "Connection timed out")
              )
            )
          ),
          result = None
        )

        assertZIO(service.crawl(List("https://foo.com", "https://bar.com")))(equalTo(expected))
      },
      test("can return both errors and successful results") {
        val service = new WebCrawlerServiceLive(
          new StubWeb(
            Map(
              "https://foo.com" -> Left(CrawlErrorMessage("Can't be reached")),
              "https://bar.com" -> Right("<html><body>bar</body></html>")
            )
          )
        )

        val expected = CrawlResult(
          error = Some(
            List(
              CrawlError(
                url = "https://foo.com",
                error = CrawlErrorMessage(value = "Can't be reached")
              )
            )
          ),
          result = Some(
            List(
              CrawlData(
                url = "https://bar.com",
                data = "<html><body>bar</body></html>"
              )
            )
          )
        )

        assertZIO(service.crawl(List("https://foo.com", "https://bar.com")))(equalTo(expected))
      }
    )
}
