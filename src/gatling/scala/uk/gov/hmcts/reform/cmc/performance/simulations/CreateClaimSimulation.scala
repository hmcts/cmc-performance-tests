package uk.gov.hmcts.reform.cmc.performance.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.cmc.performance.data.Credentials
import uk.gov.hmcts.reform.cmc.performance.processes.{ClaimPage, LoginPage}
import uk.gov.hmcts.reform.cmc.performance.simulations.lifecycle.SimulationHooks

class CreateClaimSimulation extends Simulation with SimulationHooks {
  testUserCredentials = List(Credentials.default)

  val baseURL: String = System.getenv("URL")

  val httpProtocol = http
    .baseURL(baseURL)
    .inferHtmlResources(BlackList(), WhiteList())
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate, sdch, br")
    .header("Upgrade-Inescure-Requests", "1")

  implicit val postHeaders = Map(
    "Origin" -> baseURL
  )

  val createClaimScenario = scenario("Create Claim")
      .exec(
        LoginPage.logIn(testUserCredentials.head),
        ClaimPage.issue
      )

  setUp(createClaimScenario.inject(atOnceUsers(100))).protocols(httpProtocol)

}
