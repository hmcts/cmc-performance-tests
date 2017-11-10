package uk.gov.hmcts.reform.cmc.performance.processes

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CsrfCheck.{csrfParameter, csrfTemplate}
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CurrentPageCheck.currentPageTemplate
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.{CsrfCheck, CurrentPageCheck}

object CompletingYourClaim {

  def run(implicit postHeaders: Map[String, String]): ChainBuilder = {
    exec(http("Completing claim - GET")
      .get("/claim/completing-claim")
        .check(CsrfCheck.save)
        .check(CurrentPageCheck.save))
      .pause(2)
      .exec(http("Completing claim - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
      )
      .pause(2)
  }

}
