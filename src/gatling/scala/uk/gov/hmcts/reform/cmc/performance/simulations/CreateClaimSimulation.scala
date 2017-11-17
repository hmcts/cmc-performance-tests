package uk.gov.hmcts.reform.cmc.performance.simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scala.concurrent.duration._
import uk.gov.hmcts.reform.cmc.performance.processes._
import uk.gov.hmcts.reform.cmc.performance.simulations.lifecycle.SimulationHooks
import uk.gov.hmcts.reform.idam.User

import scala.concurrent.duration.FiniteDuration

class CreateClaimSimulation extends Simulation with SimulationHooks {
  testUsers = List(User.default)

  val baseURL: String = System.getenv("URL")

  val httpProtocol: HttpProtocolBuilder = http
    .baseURL(baseURL)
    .inferHtmlResources(BlackList(), WhiteList())
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate, sdch, br")
    .header("Upgrade-Insecure-Requests", "1")

  implicit val postHeaders: Map[String, String] = Map(
    "Origin" -> baseURL
  )

  val createClaimScenario: ScenarioBuilder = scenario("Create Claim")
      .exec(
        LoginPage.logIn(testUsers.head),
        Eligibility.run,
        ResolvingThisDispute.run,
        CompletingYourClaim.run,
        YourDetails.run,
        TheirDetails.run,
        Amount.run,
        Reason.run,
        CheckAndSend.run
      )

  setUp(createClaimScenario
    .inject(rampUsers(100).over(40 seconds))
    .protocols(httpProtocol))
    .maxDuration(10 minutes)
    .assertions(
      global.responseTime.max.lt(5000),
      forAll.failedRequests.count.lt(1)
    )

}
