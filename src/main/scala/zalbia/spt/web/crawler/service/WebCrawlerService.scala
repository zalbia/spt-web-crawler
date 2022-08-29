package zalbia.spt.web.crawler.service

import zalbia.spt.web.crawler.domain.CrawlResult
import zio._

trait WebCrawlerService {

  /** Attempts to crawl the text content of each URL,
    *
    * @param urls
    * @return
    */
  def crawl(urls: List[String]): UIO[CrawlResult]
}

object WebCrawlerService {

  /** Accessor method for `WebCrawlerService#crawl`. */
  def crawl(urls: List[String]): URIO[WebCrawlerService, CrawlResult] =
    ZIO.serviceWithZIO(_.crawl(urls))
}
