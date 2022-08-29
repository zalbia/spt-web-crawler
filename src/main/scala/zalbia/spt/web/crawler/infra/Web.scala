package zalbia.spt.web.crawler.infra

import zio.Task

trait Web {

  /** Gets the string content of a URL
    *
    * @param url
    *   The url to get
    * @return
    *   The web content of the URL as a string
    */
  def getUrl(url: String): Task[String]
}
