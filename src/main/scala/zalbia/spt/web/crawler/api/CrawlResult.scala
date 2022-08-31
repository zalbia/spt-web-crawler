package zalbia.spt.web.crawler.api

import zalbia.spt.web.crawler.core.{CrawlData, CrawlError}
import zio.json.{DeriveJsonEncoder, JsonEncoder}

/** The result of crawling urls, including both errors and data */
final case class CrawlResult(
    error: Option[List[CrawlError]],
    result: Option[List[CrawlData]]
)

object CrawlResult {
  implicit val encoder: JsonEncoder[CrawlResult] = DeriveJsonEncoder.gen

  def fromList(list: List[Either[CrawlError, CrawlData]]): CrawlResult = {
    // empty errors or results are flattened to None
    val errors  = list.collect { case e @ Left(_) => e } match {
      case Nil => None
      case l   => Some(l.map(_.value))
    }
    val results = list.collect { case res @ Right(_) => res } match {
      case Nil => None
      case l   => Some(l.map(_.value))
    }
    CrawlResult(errors, results)
  }
}
