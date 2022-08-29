package zalbia.spt.web.crawler.service

import io.lemonlabs.uri.Url
import zio.IO

import java.io.IOException

trait Web {

  /** Gets the string content of a URL
    *
    * @param url
    *   The url to get
    * @return
    *   The web content of the URL as a string
    */
  def getUrl(url: Url): IO[IOException, String]
}
