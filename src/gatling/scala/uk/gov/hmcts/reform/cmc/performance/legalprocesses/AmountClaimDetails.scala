package uk.gov.hmcts.reform.cmc.performance.legalprocesses

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CsrfCheck.{csrfParameter, csrfTemplate}
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CurrentPageCheck.currentPageTemplate
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.{CsrfCheck, CurrentPageCheck}

object AmountClaimDetails {
  def run(implicit postHeaders: Map[String, String]): ChainBuilder = {
    exec(http("Personal Claim POST")
      .post(currentPageTemplate)
      .formParam(csrfParameter, csrfTemplate)
      .formParam("personalInjury", "YES")
      .formParam("generalDamages[value]", "LESS")
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
    )
    .exec(http("housing disrepair Details POST")
      .post(currentPageTemplate)
      .formParam(csrfParameter, csrfTemplate)
      .formParam("housingDisrepair", "YES")
      .formParam("generalDamages[value]", "MORE")
      .formParam("otherDamages[value]", "LESS")
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
    )
    .exec(http("Summarise the Claim - POST")
      .post(currentPageTemplate)
      .formParam(csrfParameter, csrfTemplate)
      .formParam("text", "the type of claim, for example 'rent arrears' or 'damages' explained here blah")
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
    )
    .pause(2)
    .exec(http("Claim Amount - POST")
      .post(currentPageTemplate)
      .formParam(csrfParameter, csrfTemplate)
      .formParam("lowerValue", "500.00")
      .formParam("higherValue", "999.99")
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
      .check(regex("£70"))
    )
    .exec(http("Your issue fee - POST")
      .post(currentPageTemplate)
      .formParam(csrfParameter, csrfTemplate)
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
    )
    .exec(http("Check the claim details  - POST")
      .post(currentPageTemplate)
      .formParam(csrfParameter, csrfTemplate)
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
      .check(regex("Statement of truth"))
    )
    .pause(2)
    .exec(http("Statement of truth - POST")
      .post(currentPageTemplate)
      .formParam(csrfParameter, csrfTemplate)
      .formParam("signerName", "Blah")
      .formParam("signerRole", "QA")
      .check(CurrentPageCheck.save)
      .check(CsrfCheck.save)
      .check(regex("Pay by Fee Account"))
    )
    .pause(1)
  }
}
