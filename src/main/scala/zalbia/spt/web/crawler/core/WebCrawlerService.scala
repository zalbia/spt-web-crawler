package zalbia.spt.web.crawler.core

import zio._

trait WebCrawlerService {

  /** Attempts to crawl the text content of each URL which yields a [[CrawlError]] or [[CrawlData]].
    *
    * @param urls
    * @return
    */
  def crawl(urls: List[String]): UIO[List[Either[CrawlError, CrawlData]]]
}

object WebCrawlerService {

  /** Accessor method for `WebCrawlerService#crawl`. */
  def crawl(urls: List[String]): URIO[WebCrawlerService, List[Either[CrawlError, CrawlData]]] =
    ZIO.serviceWithZIO(_.crawl(urls))
}
