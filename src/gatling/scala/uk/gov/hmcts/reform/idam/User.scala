package uk.gov.hmcts.reform.idam

object User {
  val citizen = User("civilmoneyclaims+blah4@gmail.com", "Abcdefg123", "cmc-private-beta")
  val legal =  User("civilmoneyclaims+legal2@gmail.com", "Abcdefg123", "cmc-solicitor")
}

case class User(email: String, password: String, group: String)
