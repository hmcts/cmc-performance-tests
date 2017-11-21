package uk.gov.hmcts.reform.cmc.performance.legalprocesses

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CsrfCheck.{csrfParameter, csrfTemplate}
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CurrentPageCheck.currentPageTemplate
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.{CsrfCheck, CurrentPageCheck}

object ClaimantLegalRepresentative {

  def run(implicit postHeaders: Map[String, String]): ChainBuilder = {
    val startPagePath = "/claim/start"
    exec(http("Start Page")
      .get(startPagePath)
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
    )
    .pause(2)
    .exec(http("Legal Rep Organisation Name POST")
      .post("/claim/representative-name")
      .formParam(csrfParameter, csrfTemplate)
      .formParam("name", "Abc Organisation")
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
    )
    .exec(http("Legal Rep Organisation Address POST")
      .post(currentPageTemplate)
      .formParam(csrfParameter, csrfTemplate)
      .formParam("line1", "102 Petty France")
      .formParam("line2", "6th floor")
      .formParam("city", "London")
      .formParam("postcode", "SW1H 9AJ")
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
    )
    .pause(2)
    .exec(http("Legal Rep Organisation Contact POST")
      .post(currentPageTemplate)
      .formParam(csrfParameter, csrfTemplate)
      .formParam("phoneNumber", "0700000000")
      .formParam("email", "abc@abc.com")
      .formParam("dxAddress", "DX1234")
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
    )
    .exec(http("Legal Rep Reference POST")
      .post(currentPageTemplate)
      .formParam(csrfParameter, csrfTemplate)
      .formParam("reference", "REFLR001")
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
    )
    .exec(http("Legal Rep Court POST")
      .post(currentPageTemplate)
      .formParam(csrfParameter, csrfTemplate)
      .formParam("name", "Central Court")
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
      .check(regex("Choose claimant type"))
    )
  }

}
