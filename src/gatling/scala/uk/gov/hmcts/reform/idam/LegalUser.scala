package uk.gov.hmcts.reform.idam

object LegalUser {
  val default = LegalUser("civilmoneyclaims+legal2@gmail.com", "Abcdefg123")
}

case class LegalUser(email: String, password: String)
