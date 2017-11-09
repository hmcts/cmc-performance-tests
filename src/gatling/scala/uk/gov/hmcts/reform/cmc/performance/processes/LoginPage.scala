package uk.gov.hmcts.reform.cmc.performance.processes

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.idam.User

object LoginPage {

  def logIn(user: User)(implicit postHeaders: Map[String, String]): ChainBuilder = {
    exec(http("Trigger login")
      .get("/")
      .check(currentLocation.saveAs("loginUrl"))
      .check(css(".form-group>input[name='_csrf']", "value").saveAs("csrf"))
      .check(css(".form-group>input[name='client_id']", "value").saveAs("clientId"))
      .check(css(".form-group>input[name='state']", "value").saveAs("state"))
      .check(css(".form-group>input[name='redirect_uri']", "value").saveAs("redirectUri"))
      .check(css(".form-group>input[name='continue']", "value").saveAs("continue")))
      .pause(1)
      .exec(session => {
        println(">>>>>>>>>>>>>>>>>>>> " + session("loginUrl").as[String])
        println(">>>>>>>>>>>>>>>>>>>> " + session("clientId").as[String])
        println(">>>>>>>>>>>>>>>>>>>> " + session("state").as[String])
        println(">>>>>>>>>>>>>>>>>>>> " + session("redirectUri").as[String])
        println(">>>>>>>>>>>>>>>>>>>> " + session("continue").as[String])
        session
      })
      .exec(http("Login - submit")
        .post("${loginUrl}")
        .headers(postHeaders)
        .formParam("username", user.email)
        .formParam("password", user.password)
        .formParam("response_type", "code")
        .formParam("continue", "${continue}") // tactical idam maps redirect_uri to continue =/
        .formParam("upliftToken", "")
        .formParam("_csrf", "${csrf}")
        .formParam("redirect_uri", "${redirectUri}")
        .formParam("client_id", "${clientId}")
        .formParam("scope", "")
        .formParam("state", "${state}")
        .check(regex("Make a money claim"))
      )
      .pause(1)
  }

}
