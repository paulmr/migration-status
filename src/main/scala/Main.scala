package migration.status

import com.sumologic.elasticsearch.restlastic.dsl.Dsl._
import com.sumologic.elasticsearch.restlastic._
import scala.concurrent.Await
import scala.concurrent.duration._

object Main extends App {

  val parser = new scopt.OptionParser[Config]("migration-status") {
    head("migration-status")
    help("help") text("show help")

    opt[Unit]  ('f', "follow")  action { (_, c)     => c.copy(follow = true) } text("stay running to show new entries")
    opt[Unit]  ('v', "verbose") action { (_, c)     => c.copy(follow = true) } text("provide debug info")
    (opt[String]('u', "url")    action { (url, c)   => c.copy(es_url = url) }
       text("url of ES instance"))
    (opt[String]('p', "pattern") action { (patt, c) => c.copy(pattern = patt) }
       text("pattern which matches log entries of interest"))
    opt[String]('i', "index")   action { (ind, c)   => c.copy(index = ind) } text("ES index to use")
  }

  val startConfig = Config.readConfig
  // command line opts override on disk config
  val config = parser.parse(args, startConfig).get

  val endpoint = config.es_url

  val es = new ESClient(config)

  val res = es.count(Index(config.index), Type(""), QueryRoot(MatchAll))

  val result = Await.result(res, 30.seconds)

  println(s"Result: $result")

  es.close

}
