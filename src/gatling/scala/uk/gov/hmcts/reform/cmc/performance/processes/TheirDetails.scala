package uk.gov.hmcts.reform.cmc.performance.processes

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CsrfCheck.{csrfParameter, csrfTemplate}
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CurrentPageCheck.currentPageTemplate
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.{CsrfCheck, CurrentPageCheck}

object TheirDetails {

  def run(implicit postHeaders: Map[String, String]): ChainBuilder = {
    exec(http("Party type selection - GET")
      .get("/claim/defendant-party-type-selection")
      .check(CsrfCheck.save)
      .check(CurrentPageCheck.save))
      .pause(2)
      .exec(http("Party type selection - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("type", "individual")
        .check(CsrfCheck.save)
        .check(CurrentPageCheck.save))
      .pause(3)
      .exec(http("Their details - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("name", "Defendant John Smith")
        .formParam("address[line1]", "222B Baker street")
        .formParam("address[line2]", "")
        .formParam("address[line3]", "")
        .formParam("address[city]", "London")
        .formParam("address[postcode]", "NW2 6XE")
        .check(CsrfCheck.save)
        .check(CurrentPageCheck.save))
      .pause(2)
      .exec(http("Email address - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("address", "")
      )
      .pause(2)
  }

}
