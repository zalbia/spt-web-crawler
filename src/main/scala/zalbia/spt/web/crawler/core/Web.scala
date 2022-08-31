package zalbia.spt.web.crawler.core

import zio.IO

trait Web {

  /** Attempts to get the string content of a URL or fails with a [[CrawlErrorMessage]].
    *
    * @param url
    *   The url to get
    * @return
    *   The web content of the URL as a string
    */
  def getUrl(url: String): IO[CrawlErrorMessage, String]
}
