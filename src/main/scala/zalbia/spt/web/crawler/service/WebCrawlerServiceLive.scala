package zalbia.spt.web.crawler.service

import io.lemonlabs.uri.Url
import zalbia.spt.web.crawler.domain.CrawlResult
import zio._

import java.io.IOException

final class WebCrawlerServiceLive(web: Web) extends WebCrawlerService {
  override def crawl(urls: List[Url]): UIO[List[Either[IOException, CrawlResult]]] =
    ZIO.foreach(urls) { url =>
      web.getUrl(url).fold(Left(_), data => Right(CrawlResult(url, data)))
    }
}
