package uk.gov.hmcts.reform.cmc.performance.simulations.lifecycle

import io.gatling.core.Predef.Simulation
import org.slf4j.LoggerFactory
import uk.gov.hmcts.reform.cmc.performance.data.Credentials
import uk.gov.hmcts.reform.idam.UserClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration.{Inf => infinite}
import scala.concurrent.{Await, Future}

trait SimulationHooks {
  self: Simulation =>

  val logger = LoggerFactory.getLogger(classOf[SimulationHooks])

  var testUserCredentials: List[Credentials] = Nil

  private val userClient: UserClient = new UserClient(System.getenv("IDAM_URL"))

  self.before {
    Await.ready(bootstrap(), infinite)
  }

  def bootstrap(): Future[Any] = {
    val testUserInitialisations: List[Future[Unit]] = testUserCredentials map { credentials =>
      for {
        user <- userClient.create(credentials.email, credentials.password)
        _ <- userClient.activate(user.uuid, user.activationKey)
      } yield {}
    }

    val initialisationsSequence = Future.sequence(testUserInitialisations)
    initialisationsSequence.onFailure { case exception =>
      logger.error(s"Bootstrap failed (${exception.getMessage}) - shutting down...")
      userClient.shutdown()
      System.exit(1)
    }
    initialisationsSequence
  }

  self.after {
    Await.ready(teardown(), infinite)
  }

  def teardown(): Future[Any] = {
    val userDeletions: List[Future[Unit]] = testUserCredentials map { credentials =>
      userClient.delete(credentials.email)
    }

    val deletionsSequence = Future.sequence(userDeletions)
    deletionsSequence.onComplete(_ => userClient.shutdown())
    deletionsSequence
  }

}
