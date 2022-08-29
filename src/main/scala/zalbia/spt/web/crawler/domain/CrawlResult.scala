package zalbia.spt.web.crawler.domain

import io.lemonlabs.uri.Url

/** Text representation of the result of crawling a URL
  *
  * @param url
  *   valid URL
  * @param data
  *   text representation of a URL's crawled content
  */
final case class CrawlResult(
    url: Url,
    data: String
)
