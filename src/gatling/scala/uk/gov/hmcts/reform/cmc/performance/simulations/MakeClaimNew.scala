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


//                                .post("/claim/defendant-email")
//                                .headers(headers_0)
//                                .formParam("_csrf", "VsScCiki-MmCzJ7F31MKgktmX01lsqyFSetk")
//                                .formParam("address", "")
//                                .resources(http("request_67")
//                                  .get("/analytics"),
//                                  http("request_68")
//                                    .get(uri2 + "")
//                                    .headers(headers_2)))
//                              .pause(1)
//                              .exec(http("request_69")
//                                .get("/claim/amount")
//                                .resources(http("request_70")
//                                  .pause(7)
//                                  .exec(http("request_72")
//                                    .post("/claim/amount")
//                                    .formParam("_csrf", "pXpRSQkZ-CZpOk1PNAzBJgyMYk8lLHyrQ3PM")
//                                    .formParam("rows[0][reason]", "Performance test")
//                                    .formParam("rows[0][amount]", "202")
//                                    .formParam("rows[1][reason]", "")
//                                    .formParam("rows[1][amount]", "")
//                                    .formParam("rows[2][reason]", "")
//                                    .formParam("rows[2][amount]", "")
//                                    .formParam("rows[3][reason]", "")
//                                    .formParam("rows[3][amount]", "")
//                                    .resources(http("request_73")
//                                      .get("/analytics"),
//                                      http("request_74")))
//                                  .pause(2)
//                                  .exec(http("request_75")
//                                    .post("/claim/interest")
//                                    .formParam("_csrf", "CS6foQlk-vF1VESQGil-xB09Pdewa7VIy0UE")
//                                    .formParam("rate", "")
//                                    .formParam("reason", "")
//                                    .formParam("type", "no interest")
//                                    .resources(http("request_76")
//                                      http ("request_77"))
//                                    .get("/claim/fees")
//                                    http ("request_78")
//                                    .get("/analytics"),
//                                    http("request_79")
//                                      .pause(1)
//                                      .exec(http("request_80")
//                                        .post("/claim/fees")
//                                        .formParam("_csrf", "JqVHSvQ8-stsfptzU-JpWt7GVhlM-AYIs6DI")
//                                        .resources(http("request_81")
//                                          .get("/analytics"),
//                                          http("request_82")
//                                            .post("/claim/total")
//                                            .formParam("_csrf", "AlIGyUtm-mv0mu8Od-GMVpB2qF3o0yPGsfgY"),
//                                          http("request_84")
//                                            .get("/analytics"),
//                                          http("request_85")
//                                            .pause(1)
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
