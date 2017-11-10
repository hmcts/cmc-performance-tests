package uk.gov.hmcts.reform.cmc.performance.simulations.checks

import io.gatling.core.Predef._
import io.gatling.core.check.CheckBuilder
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import jodd.lagarto.dom.NodeSelector

object CsrfCheck {
  def save: CheckBuilder[HttpCheck, Response, NodeSelector, String] = css("input[name='_csrf']", "value").saveAs("csrf")
}
