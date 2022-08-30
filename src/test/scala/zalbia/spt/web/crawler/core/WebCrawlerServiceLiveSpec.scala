package zalbia.spt.web.crawler.core

import zio.test.Assertion._
import zio.test._

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
      }
    )
}
