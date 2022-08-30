package zalbia.spt.web.crawler.infra

import zalbia.spt.web.crawler.core.Web
import zhttp.service.{ChannelFactory, Client, EventLoopGroup}
import zio._

final class ZioHttpWeb(channelFactory: ChannelFactory, eventLoopGroup: EventLoopGroup) extends Web {

  override def getUrl(url: String): Task[String] = {
    for {
      res  <- Client.request(url)
      data <- res.bodyAsString
    } yield data
  }.provide(ZLayer.succeed(channelFactory), ZLayer.succeed(eventLoopGroup))
}

object ZioHttpWeb {
  val layer: URLayer[ChannelFactory with EventLoopGroup, Web] =
    ZLayer(ZIO.service[ChannelFactory].zipWith(ZIO.service[EventLoopGroup])(new ZioHttpWeb(_, _)))
}
