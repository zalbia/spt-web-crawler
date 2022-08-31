package zalbia.spt.web.crawler.infra

import zalbia.spt.web.crawler.core.{CrawlErrorMessage, Web}
import zhttp.service.{ChannelFactory, Client, EventLoopGroup}
import zio._

final class ZioHttpWeb(channelFactory: ChannelFactory, eventLoopGroup: EventLoopGroup) extends Web {

  override def getUrl(url: String): IO[CrawlErrorMessage, String] = {
    for {
      res  <- Client.request(url)
      data <- res.bodyAsString
    } yield data
  }.mapError(e => CrawlErrorMessage(e.getMessage))
    .provide(ZLayer.succeed(channelFactory), ZLayer.succeed(eventLoopGroup))
}

object ZioHttpWeb {
  val layer: URLayer[ChannelFactory with EventLoopGroup, Web] =
    ZLayer(ZIO.service[ChannelFactory].zipWith(ZIO.service[EventLoopGroup])(new ZioHttpWeb(_, _)))
}
