package zalbia.spt.web.crawler.core

import zio._

final class WebCrawlerServiceLive(web: Web) extends WebCrawlerService {
  override def crawl(urls: List[String]): UIO[List[Either[CrawlError, CrawlData]]] =
    ZIO.foreachPar(urls) { url =>
      web
        .getUrl(url)
        .fold(
          errorMessage => Left(CrawlError(url, errorMessage)),
          data => Right(CrawlData(url, data))
        )
    }
}

object WebCrawlerServiceLive {
  val layer: URLayer[Web, WebCrawlerService] =
    ZLayer(ZIO.service[Web].map(new WebCrawlerServiceLive(_)))
}
