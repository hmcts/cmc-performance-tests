package uk.gov.hmcts.reform.cmc.performance.processes

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CsrfCheck.{csrfParameter, csrfTemplate}
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CurrentPageCheck.currentPageTemplate
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.{CsrfCheck, CurrentPageCheck}

object ResolvingThisDispute {

  def run(implicit postHeaders: Map[String, String]): ChainBuilder = {
    exec(http("Task list - GET")
      .get("/claim/task-list"))
      .pause(2)
      .exec(http("Resolving this dispute - GET")
        .get("/claim/resolving-this-dispute")
        .check(CsrfCheck.save)
        .check(CurrentPageCheck.save))
      .pause(1)
      .exec(http("Resolving this dispute - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
      )
      .pause(2)
  }

}
