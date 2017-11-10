//package uk.gov.hmcts.reform.cmc.performance.simulations
//
//import io.gatling.core.Predef._
//import io.gatling.http.Predef._
//import io.gatling.http.protocol.HttpProtocolBuilder
//
//import scala.concurrent.duration._
//
//class MakeClaimNew extends Simulation {
//
//  val httpProtocol: HttpProtocolBuilder = http
//    .baseURL("https://localhost:3010")
//    .inferHtmlResources(BlackList(), WhiteList())
//    .acceptHeader("*/*")
//    .acceptEncodingHeader("gzip, deflate")
//    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:56.0) Gecko/20100101 Firefox/56.0")
//    .header("Upgrade-Insecure-Requests", "1")
//
//
//  val uri1 = "localhost"
//



//                                    .get("/claim/fees")
//                                            .exec(http("request_86")
//                                              .get("/claim/reason")
//                                              .resources(http("request_87")
//                                                .get("/analytics"),
//                                                http("request_88")
//                                                  .pause(4)
//                                                  .exec(http("request_89")
//                                                    .post("/claim/reason")
//                                                    .formParam("_csrf", "O6ZJFCaV-y1CUyn7zmNeglsLC_1X53J8j41c")
//                                                    .formParam("reason", "Performance test")
//                                                    .resources(http("request_90")
//                                                      .get("/analytics"),
//                                                      http("request_91")
//                                                        .pause(1)
//                                                        .exec(http("request_92")
//                                                          .get("/claim/check-and-send")
//                                                          .resources(http("request_93")
//                                                          ))
//                                                        .pause(2)
//                                                        .exec(http("request_95")
//                                                          .post("/claim/check-and-send")
//                                                          .formParam("_csrf", "g1iqx2vI-tJQOYQkhFx7Oyhcg6_BLiatRZSY")
//                                                          .formParam("signed", "true")
//                                                          .formParam("type", "basic"))
//
//                                                        setUp (scn.inject(atOnceUsers(1))).protocols(httpProtocol)
//}
