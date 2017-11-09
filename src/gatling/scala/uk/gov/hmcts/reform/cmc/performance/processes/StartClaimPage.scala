package uk.gov.hmcts.reform.cmc.performance.processes

import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

object StartClaimPage {

  def open(implicit postHeaders: Map[String, String]): ChainBuilder = {
    exec(http("Claim start - load")
      .get("/claim/start"))
    .pause(2)
  }

}
