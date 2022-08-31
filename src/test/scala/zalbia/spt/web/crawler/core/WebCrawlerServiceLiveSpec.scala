package zalbia.spt.web.crawler.core

import zalbia.spt.web.crawler.api.{CrawlParams, CrawlResult, HttpRoutes}
import zalbia.spt.web.crawler.infra.ZioHttpWeb
import zhttp.http.{HttpData, Method}
import zhttp.service._
import zio.json._
import zio.test.Assertion._
import zio.test.TestAspect._
import zio.test._

object WebCrawlerServiceLiveSpec extends ZIOSpecDefault {

  val serverFiber = Server.start(8080, HttpRoutes.app).fork

  override def spec =
    suite("WebCrawlerServiceLive")(
      suite("unit tests")(
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

          val expected = List(
            Right(CrawlData("https://foo.com", "<html><body>foo</body></html>")),
            Right(CrawlData("https://bar.com", "<html><body>bar</body></html>"))
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

          val expected = List(
            Left(CrawlError("https://foo.com", CrawlErrorMessage(value = "Can't be reached"))),
            Left(CrawlError("https://bar.com", CrawlErrorMessage(value = "Connection timed out")))
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

          val expected = List(
            Left(CrawlError("https://foo.com", CrawlErrorMessage(value = "Can't be reached"))),
            Right(CrawlData("https://bar.com", "<html><body>bar</body></html>"))
          )

          assertZIO(service.crawl(List("https://foo.com", "https://bar.com")))(equalTo(expected))
        }
      ), {
        val webCrawlerEndpointUrl = "http://localhost:8080/api/crawl"
        suite("integration tests")(
          test("crawling google and github returns data in JSON") {
            for {
              response <- Client.request(
                            url = webCrawlerEndpointUrl,
                            method = Method.POST,
                            content =
                              HttpData.fromString(CrawlParams(List("https://google.com", "https://github.com")).toJson)
                          )
              data     <- response.bodyAsString
            } yield assertTrue(
              data.fromJson[CrawlResult].exists(result => result.result.isDefined && result.error.isEmpty)
            )
          },
          test("crawling an invalid URL yields an error") {
            for {
              response <- Client.request(
                            url = webCrawlerEndpointUrl,
                            method = Method.POST,
                            content = HttpData.fromString(CrawlParams(List("invalid url")).toJson)
                          )
              data     <- response.bodyAsString
            } yield assertTrue(
              data.fromJson[CrawlResult].exists(result => result.error.exists(_.nonEmpty) && result.result.isEmpty)
            )
          }
        ) @@ sequential @@ beforeAll(serverFiber) @@ afterAll(serverFiber.map(_.interrupt))
      }.provide(
        ChannelFactory.auto,
        EventLoopGroup.auto(),
        ZioHttpWeb.layer,
        WebCrawlerServiceLive.layer
      )
    )
}
