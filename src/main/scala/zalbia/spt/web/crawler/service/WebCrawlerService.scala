package zalbia.spt.web.crawler.service

import io.lemonlabs.uri.Url
import zalbia.spt.web.crawler.domain.CrawlResult
import zio.UIO

import java.io.IOException

trait WebCrawlerService {
  def crawl(urls: List[Url]): UIO[List[Either[IOException, CrawlResult]]]
}
