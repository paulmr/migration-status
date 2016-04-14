package migration.status

import com.sumologic.elasticsearch.restlastic.{ RequestSigner, RestlasticSearchClient }
import RestlasticSearchClient.ReturnTypes._

import spray.http.HttpMethod
import spray.http.HttpMethods._
import spray.http.Uri.{Query => UriQuery}

import scala.concurrent.Future

class ESClient(config: Config,
               signer: Option[RequestSigner] = None)
    extends RestlasticSearchClient(config.endpoint, signer) {

  override def runRawEsRequest(op: String,
                               endpoint: String,
                               method: HttpMethod = POST,
                               query: UriQuery = UriQuery.Empty): Future[RawJsonResponse] = {
    super.runRawEsRequest(op, config.es_base_path + "/" + endpoint, method, query)
  }

  def close = system.shutdown

}
