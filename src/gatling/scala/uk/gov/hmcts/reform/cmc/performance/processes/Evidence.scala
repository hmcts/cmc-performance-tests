package uk.gov.hmcts.reform.cmc.performance.processes

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CsrfCheck.{csrfParameter, csrfTemplate}
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CurrentPageCheck.currentPageTemplate
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.{CsrfCheck, CurrentPageCheck}

object Evidence {

  def run(implicit postHeaders: Map[String, String]): ChainBuilder = {
    exec(http("Evidence - GET")
      .get("/claim/evidence")
        .check(CsrfCheck.save)
        .check(CurrentPageCheck.save))
      .pause(2)
      .exec(http("Evidence - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("rows[0][type]", "CONTRACTS_AND_AGREEMENTS")
        .formParam("rows[0][description]", "Description")
      )
      .pause(2)
  }

}
