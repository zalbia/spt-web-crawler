package zalbia.spt.web.crawler.core

import zalbia.spt.web.crawler.core
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
        // empty errors or results are flattened to None
        val errors  = list.collect { case e @ Left(_) => e } match {
          case Nil => None
          case l   => Some(l.map(_.value))
        }
        val results = list.collect { case res @ Right(_) => res } match {
          case Nil => None
          case l   => Some(l.map(_.value))
        }
        core.CrawlResult(errors, results)
      }
}

object WebCrawlerServiceLive {
  val layer: URLayer[Web, WebCrawlerService] =
    ZLayer(ZIO.service[Web].map(new WebCrawlerServiceLive(_)))
}
