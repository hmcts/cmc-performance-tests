package uk.gov.hmcts.reform.cmc.performance.legalprocesses

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CsrfCheck.{csrfParameter, csrfTemplate}
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.CurrentPageCheck.currentPageTemplate
import uk.gov.hmcts.reform.cmc.performance.simulations.checks.{CsrfCheck, CurrentPageCheck}

object DefendantDetails {

  def run(implicit postHeaders: Map[String, String]): ChainBuilder = {
      exec(http("Defendant POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("type", "ORGANISATION")
        .formParam("organisation", "abc defandant org")
        .formParam("companyHouseNumber","FX12345")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save)
      )
      .exec(http("Defendant organization address POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("line1", "104 Petty France")
        .formParam("line2", "6th floor")
        .formParam("city", "London")
        .formParam("postcode", "SW1J 9AJ")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save)
      )
      .exec(http("Defendant Represntive POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("isDefendantRepresented", "NO")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save)
      )
      .exec(http("Defendant Represntive POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("defendantsAddress", "YES")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save)
      )
      .exec(http("Add More Defendants POST")
        .post(currentPageTemplate)
        .formParam(csrfParameter, csrfTemplate)
        .formParam("isAddDefendant", "NO")
        .check(CurrentPageCheck.save)
        .check(CsrfCheck.save)
        .check(regex("Is it a personal injury claim?"))
      )
  }

}
