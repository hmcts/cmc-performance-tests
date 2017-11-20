package uk.gov.hmcts.reform.cmc.performance.legalprocesses

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.{CsrfCheck, CurrentPageCheck}
import uk.gov.hmcts.reform.idam.LegalUser

object LoginPage {

  def logLegalIn(user: LegalUser)(implicit postHeaders: Map[String, String]): ChainBuilder = {
    exec(http("Trigger login")
      .get("/")
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
      .check(css(".form-group>input[name='client_id']", "value").saveAs("clientId"))
      .check(css(".form-group>input[name='state']", "value").saveAs("state"))
      .check(css(".form-group>input[name='redirect_uri']", "value").saveAs("redirectUri"))
      .check(css(".form-group>input[name='continue']", "value").saveAs("continue")))
      .pause(1)
      .exec(http("Login - legal submit")
        .post("${currentPage}")
        .headers(postHeaders)
        .formParam("username", user.email)
        .formParam("password", user.password)
        .formParam("response_type", "code")
        .formParam("continue", "${continue}") // tactical idam maps redirect_uri to continue =/
        .formParam("upliftToken", "")
        .formParam("_csrf", "${csrf}")
        .formParam("redirect_uri", "${redirectUri}")
        .formParam("client_id", "${clientId}")
        .formParam("scope", "")
        .formParam("state", "${state}")
        .check(regex("Issue civil court proceedings"))
      )
      .pause(1)
  }

}
