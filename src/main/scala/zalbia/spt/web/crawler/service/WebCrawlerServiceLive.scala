package zalbia.spt.web.crawler.service

import zalbia.spt.web.crawler.domain.{CrawlData, CrawlError, CrawlResult}
import zalbia.spt.web.crawler.infra.Web
import zio._

final class WebCrawlerServiceLive(web: Web) extends WebCrawlerService {
  override def crawl(urls: List[String]): UIO[CrawlResult] =
    ZIO
      .foreachPar(urls) { url =>
        web
          .getUrl(url)
          .fold(
            e => Left(CrawlError(url, e.getMessage)),
            data => Right(CrawlData(url, data))
          )
      }
      .map { list =>
        val errors  = list.collect { case e @ Left(_) => e }
        val results = list.collect { case res @ Right(_) => res }
        CrawlResult(errors.map(_.value), results.map(_.value))
      }
}

object WebCrawlerServiceLive {
  val layer: URLayer[Web, WebCrawlerService] =
    ZLayer(ZIO.service[Web].map(new WebCrawlerServiceLive(_)))
}
