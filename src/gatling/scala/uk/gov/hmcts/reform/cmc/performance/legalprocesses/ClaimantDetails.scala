package uk.gov.hmcts.reform.cmc.performance.legalprocesses

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CsrfCheck.{csrfParameter, csrfTemplate}
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CurrentPageCheck.currentPageTemplate
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.{CsrfCheck, CurrentPageCheck}

object ClaimantDetails {

  def run(implicit postHeaders: Map[String, String]): ChainBuilder = {
     exec(http("Claimant type POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("type", "ORGANISATION")
        .formParam("organisation", "abc claimant org")
        .formParam("companyHouseNumber","FX12345")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save)
     )
     .exec(http("Claimant organization address POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("line1", "103 Petty France")
        .formParam("line2", "6th floor")
        .formParam("city", "London")
        .formParam("postcode", "SW1I 9AJ")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save)
     )
     .exec(http("Add More Claimants POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("isAddClaimant", "NO")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save)
        .check(regex("Choose defendant type"))
     )
  }

}
