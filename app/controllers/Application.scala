package controllers

import controllers.request.SampleRequest
import play.api._
import play.api.mvc._
import play.api.libs.json.{ JsError, Json }
import services.WsService
import play.api.libs.ws.WS
import play.api.Play.current
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import scala.concurrent._
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.duration._

object Application extends Controller {

//  implicit val context = scala.concurrent.ExecutionContext.Implicits.global
  val URL = "http://localhost:9200"

  def index = Action {
    Ok
  }

  /**
   * {
   *   "q": {
   *     "alpha": [1, 2, 10],
   *     "bravo": [{
   *       "a": "文字列",
   *       "b": { "start": 1111111111111, "end": 2223232323 },
   *       "c": ["1番目", "2番目"]
   *     }]
   *   }
   * }
   */
  def sample = Action(parse.json) { request =>
    request.body.validate(SampleRequest.bReads).map {
      case (params: SampleRequest.bType) =>
        {
          // execute
        }
        Ok(Json.toJson(params._1))
    }.recoverTotal {
      e => BadRequest("Detected error:" + JsError.toFlatJson(e))
    }

  }

  def await = Action {
    val response = Await.result(WS.url(URL).get(), 1.seconds)
    Ok(response.body)
  }

  def body() = Action.async {
    WS.url(URL).get().map { response =>
      Ok("body: " + response.body)
    }
  }

  def notAsync() = Action { request =>
    WS.url(URL).get().map { response =>
      Ok("body: " + response.body)
    }
    Ok("notAsync")
  }
}