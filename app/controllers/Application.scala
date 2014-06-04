package controllers

import controllers.request.SampleRequest

import play.api._
import play.api.mvc._
import play.api.libs.json.{ JsError, Json }

object Application extends Controller {

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
}