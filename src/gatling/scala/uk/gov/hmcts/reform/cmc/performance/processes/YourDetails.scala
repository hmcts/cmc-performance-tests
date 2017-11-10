package uk.gov.hmcts.reform.cmc.performance.processes

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CsrfCheck.{csrfParameter, csrfTemplate}
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CurrentPageCheck.currentPageTemplate
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.{CsrfCheck, CurrentPageCheck}

object YourDetails {

  def run(implicit postHeaders: Map[String, String]): ChainBuilder = {
    exec(http("Party type selection - GET")
      .get("/claim/claimant-party-type-selection")
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
      .exec(http("Your details - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("name", "John Smith")
        .formParam("address[line1]", "221B Baker street")
        .formParam("address[line2]", "")
        .formParam("address[city]", "London")
        .formParam("address[postcode]", "NW1 6XE")
        .formParam("hasCorrespondenceAddress", "false")
        .formParam("correspondenceAddress[line1]", "")
        .formParam("correspondenceAddress[line2]", "")
        .formParam("correspondenceAddress[city]", "")
        .formParam("correspondenceAddress[postcode]", "")
        .check(CsrfCheck.save)
        .check(CurrentPageCheck.save))
      .pause(2)
      .exec(http("Date of birth - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("known", "true")
        .formParam("date[day]", "31")
        .formParam("date[month]", "3")
        .formParam("date[year]", "1980")
        .check(CsrfCheck.save)
        .check(CurrentPageCheck.save))
      .pause(2)
      .exec(http("Mobile - POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("number", "07123456789")
        .check(CsrfCheck.save)
        .check(CurrentPageCheck.save))
      .pause(1)
  }

}
