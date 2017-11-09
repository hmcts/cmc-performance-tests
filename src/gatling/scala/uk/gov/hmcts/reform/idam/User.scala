package uk.gov.hmcts.reform.idam

object User {
  val default = User("civilmoneyclaims+blah4@gmail.com", "Abcdefg123")
}

case class User(email: String, password: String)

