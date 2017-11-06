package uk.gov.hmcts.reform.cmc.performance.processes

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.data.Credentials

object LoginPage {

  def logIn(userData: Credentials)(implicit postHeaders: Map[String, String]): ChainBuilder = {
    exec(http("Home - load")
      .get("/"))
    .pause(1)
    .exec(http("Login - load")
      .get("/login"))
    .pause(1)
    .exec(http("Login - submit")
      .post("/login")
      .headers(postHeaders)
      .formParam("email", userData.email)
      .formParam("password", userData.password))
    .pause(1)
  }

}
