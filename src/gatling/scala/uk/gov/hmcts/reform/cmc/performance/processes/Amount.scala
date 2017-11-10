package uk.gov.hmcts.reform.cmc.performance.processes

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CsrfCheck.{csrfParameter, csrfTemplate}
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CurrentPageCheck.currentPageTemplate
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.{CsrfCheck, CurrentPageCheck}

object Amount {

  def run(implicit postHeaders: Map[String, String]): ChainBuilder = {
    exec(http("Amount - GET")
      .get("/claim/amount")
      .check(CsrfCheck.save)
      .check(CurrentPageCheck.save))
      .pause(2)
      .exec(http("Amount - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("rows[0][reason]", "Performance test")
        .formParam("rows[0][amount]", "202")
        .check(CsrfCheck.save)
        .check(CurrentPageCheck.save))
      .pause(3)
      .exec(http("Interest - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("rate", "")
        .formParam("reason", "")
        .formParam("type", "no interest")
        .check(CsrfCheck.save)
        .check(CurrentPageCheck.save))
      .pause(2)
      .exec(http("Fees - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .check(CsrfCheck.save)
        .check(CurrentPageCheck.save))
      .pause(2)
      .exec(http("Total - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
      )
      .pause(1)
  }

}
