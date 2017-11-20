package uk.gov.hmcts.reform.cmc.performance.simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import uk.gov.hmcts.reform.cmc.performance.legalprocesses._
import uk.gov.hmcts.reform.cmc.performance.simulations.lifecycle.SimulationHooks
import uk.gov.hmcts.reform.idam.LegalUser

import scala.concurrent.duration.FiniteDuration

class CreateLegalSimulation extends Simulation with SimulationHooks {
  testLegalUsers = List(LegalUser.default)

  val baseURL: String = System.getenv("LEGAL_URL")

  val httpProtocol: HttpProtocolBuilder = http
    .baseURL(baseURL)
    .inferHtmlResources(BlackList(), WhiteList())
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate, sdch, br")
    .header("Upgrade-Insecure-Requests", "1")

  implicit val postHeaders: Map[String, String] = Map(
    "Origin" -> baseURL
  )

  val createLegalClaimScenario: ScenarioBuilder = scenario("Create legal Claim")
    .exec(
      LoginPage.logLegalIn(testLegalUsers.head),
      ClaimantLegalRepresentative.run
    )

  setUp(createLegalClaimScenario
    .inject(rampUsers(1).over(1 seconds))
    .protocols(httpProtocol))
    .maxDuration(10 minutes)
    .assertions(
      global.responseTime.max.lt(5000),
      forAll.failedRequests.count.lt(1)
    )

}
