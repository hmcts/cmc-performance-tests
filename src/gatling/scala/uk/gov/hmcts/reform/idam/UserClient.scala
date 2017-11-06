package uk.gov.hmcts.reform.idam

import dispatch._
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.JsonDSL._
import net.liftweb.json.{JField, _}

import scala.concurrent.ExecutionContext.Implicits.global

class UserClient(val baseURL: String) {

  private implicit val formats = DefaultFormats

  def create(email: String, password: String): Future[User] = {
    val json = ("email" -> email) ~ ("password" -> password)

    val request = (url(baseURL) / "users").POST
      .setContentType("application/json", "UTF-8") << compactRender(json)

    Http(request OK as.lift.Json) map { response: JValue =>
      response.transformField {
        case JField("activation_key", value) => JField("activationKey", value)
      }.extract[User]
    }
  }

  def activate(uuid: String, activationKey: String): Future[Unit] = {
    val json = "activation_key" -> activationKey

    val request = (url(baseURL) / "users" / uuid / "activation").PUT
      .setContentType("application/json", "UTF-8") << compactRender(json)

    Http(request OK as.String).map { _ => }
  }

  def delete(email: String): Future[Unit] = {
    val request = (url(baseURL) / "users" / email).DELETE
      .setContentType("application/json", "UTF-8")

    Http(request OK as.String).map { _ => }
  }

  def shutdown(): Unit = {
    Http.shutdown()
  }

}
