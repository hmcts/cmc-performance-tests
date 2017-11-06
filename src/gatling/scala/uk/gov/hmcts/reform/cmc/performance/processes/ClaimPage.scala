package uk.gov.hmcts.reform.cmc.performance.processes

import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

object ClaimPage {

  def issue(implicit postHeaders: Map[String, String]): ChainBuilder = {
    exec(http("Claim start - load")
      .get("/claim/start"))
    .pause(2)
    .exec(http("Claim start - submit")
      .post("/claim/start")
      .headers(postHeaders)
      .formParam("triedToResolve", "true"))
    .pause(1)
    .exec(http("Claim details - submit")
      .post("/claim/details")
      .headers(postHeaders)
      .formParam("claimant[name]", "Jonny")
      .formParam("claimant[address]", """Address\nBN2 9NN""")
      .formParam("claimant[email]", "claimant@cmc-tests.com")
      .formParam("claimant[mobileNumber]", "0000000")
      .formParam("claimant[dateOfBirth]", "2010-10-10")
      .formParam("defendant[isCompany]", "true")
      .formParam("defendant[name]", "Alex")
      .formParam("defendant[address]", """An address\nBN2 9NN""")
      .formParam("defendant[email]", "defendant@cmc-tests.com")
      .formParam("defendant[mobileNumber]", "7700000001")
      .formParam("timelineEvents[0][ordinal]", "0")
      .formParam("timelineEvents[0][description]", "Nothing")
      .formParam("timelineEvents[0][date]", "2010-10-10")
      .formParam("amount", "100")
      .formParam("claimant[notificationPreference]", "NONE"))
  }

}
