package uk.gov.hmcts.reform.cmc.performance.processes

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CsrfCheck.{csrfParameter, csrfTemplate}
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CurrentPageCheck.currentPageTemplate
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.{CsrfCheck, CurrentPageCheck}

object Eligibility {

  def run(implicit postHeaders: Map[String, String]): ChainBuilder = {
    val eligibilityPath = "/claim/eligibility"

    exec(http("Eligibility start")
      .get(eligibilityPath))
      .pause(2)
      .exec(http("Eligibility - claim value GET")
        .get(s"$eligibilityPath/claim-value")
        .check(CsrfCheck.save))
      .exec(http("Eligibility - claim value POST")
        .post(s"$eligibilityPath/claim-value")
        .formParam(csrfParameter, csrfTemplate)
        .formParam("claimValue", "UNDER_10000")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save))
      .exec(http("Eligibility - help with fees POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("helpWithFees", "no")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save))
      .exec(http("Eligibility - claimant address POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("claimantAddress", "yes")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save))
      .exec(http("Eligibility - defendant address POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("defendantAddress", "yes")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save))
      .exec(http("Eligibility - over 18 POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("eighteenOrOver", "yes")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save))
      .exec(http("Eligibility - valid defendant POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("validDefendant", "PERSONAL_CLAIM")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save))
      .exec(http("Eligibility - single defendant POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("singleDefendant", "yes")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save))
        .exec(http("Eligibility - government department POST")
          .post(currentPageTemplate)
          .formParam(csrfParameter, csrfTemplate)
          .formParam("governmentDepartment", "no")
          .check(regex("You can use this service")))
      .exec(http("Eligibility - tenancy deposit POST")
        .post(s"$eligibilityPath/claim-is-for-tenancy-deposit")
        .formParam(csrfParameter, csrfTemplate)
        .formParam("claimIsForTenancyDeposit", "no")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save))
  }

}
