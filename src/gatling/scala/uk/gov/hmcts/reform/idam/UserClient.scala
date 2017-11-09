package uk.gov.hmcts.reform.idam

import java.nio.charset.Charset

import dispatch._
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.JsonDSL._
import net.liftweb.json._

import scala.concurrent.ExecutionContext.Implicits.global

class UserClient(val baseURL: String) {
  private val utf8 = Charset.forName("UTF-8")

  private implicit val formats: DefaultFormats.type = DefaultFormats

  def create(user: User, userGroupCode: String): Future[User] = {
    val json = ("email" -> user.email) ~ ("password" -> user.password) ~ ("userGroup" -> ("code", userGroupCode)) ~
      ("forename" -> "John") ~ ("surname" -> "Smith") ~ ("levelOfAccess" -> 1) ~ ("activationDate" -> "") ~
      ("lastAccess" -> "")

    val request = (url(baseURL) / "testing-support" / "accounts").POST
      .setContentType("application/json", utf8) << compactRender(json)

    Http.default(request OK as.lift.Json) map { _: JValue =>
      user
    }
  }

  def shutdown(): Unit = {
    Http.default.shutdown()
  }

}
