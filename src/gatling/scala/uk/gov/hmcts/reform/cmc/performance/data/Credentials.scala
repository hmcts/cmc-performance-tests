package uk.gov.hmcts.reform.cmc.performance.data

object Credentials {
  val default = Credentials("user@cmc-tests.com", "Passw0rd")
}

case class Credentials(email: String, password: String)
