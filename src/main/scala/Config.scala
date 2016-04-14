package migration.status

import com.sumologic.elasticsearch.restlastic.{StaticEndpoint, Endpoint}

import com.typesafe.config.ConfigFactory

case class Config(
  es_url: String,
  follow: Boolean  = false,
  index: String    = "_all",
  pattern: String  = "HTTPS-MIGRATION",
  verbose: Boolean = false,
  page_size: Int   = 10
) {

  private val UrlRegex = "(https?)://(.+?)(:[0-9]+)?(/.*)".r

  lazy val (es_host, es_port, es_base_path) = es_url match {
    case UrlRegex("http",  host, null, path) => (host, 80,  path)
    case UrlRegex("https", host, null, path) => (host, 443, path)
    case UrlRegex(_, host, port, path) => (host, port.toInt, path)
    case error => throw new IllegalArgumentException(s"Couldn't parse url $error")
  }

  lazy val endpoint = new StaticEndpoint(Endpoint(es_host, es_port))

}

object Config {
  def readConfig = {
    val conf = ConfigFactory.load
    Config(es_url = conf.getString("elasticsearch.url"))
  }
}
