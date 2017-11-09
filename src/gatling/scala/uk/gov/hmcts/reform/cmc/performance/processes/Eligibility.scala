package uk.gov.hmcts.reform.cmc.performance.processes

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

object Eligibility {

  def run(implicit postHeaders: Map[String, String]): ChainBuilder = {
    val eligibilityPath = "/claim/eligibility"

    exec(http("Eligibility start")
      .get(eligibilityPath))
      .pause(2)
    exec(http("Eligibility - claim value get")
      .get(s"$eligibilityPath/claim-value"))
      .pause(1)
    exec(http("Elibility - claim value post")
      .post(s"$eligibilityPath/claim-value")
      .formParam("_csrf", "Ick9Dn7H-1DplSE36UkiI5N6lYdNru0yDfHU")
      .formParam("claimValue", "UNDER_10000"))
  }

}
