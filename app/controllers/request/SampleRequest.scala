package controllers.request

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.json.Reads._

object SampleRequest {

  type aType = (String, (Long, Long), List[String])
  type bType = (List[Int], Option[List[aType]])

  implicit val aReads: Reads[aType] =
    (__ \ "a").read[String] and
      (__ \ "b").read(
        (__ \ "start").read[Long] and
          (__ \ "end").read[Long] tupled) and
        (__ \ "c").read[List[String]] tupled

  implicit val bReads: Reads[bType] =
    (__ \ "q" \ "alpha").read[List[Int]] and
      (__ \ "q" \ "bravo").readNullable(list[aType](aReads)) tupled
}