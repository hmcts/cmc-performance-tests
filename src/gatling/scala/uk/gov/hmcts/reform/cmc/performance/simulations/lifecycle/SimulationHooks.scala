package uk.gov.hmcts.reform.cmc.performance.simulations.lifecycle

import io.gatling.core.Predef.Simulation
import org.slf4j.LoggerFactory
import uk.gov.hmcts.reform.idam.{User, UserClient, LegalUser}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration.{Inf => infinite}
import scala.concurrent.{Await, Future}

trait SimulationHooks {
  self: Simulation =>

  private val logger = LoggerFactory.getLogger(classOf[SimulationHooks])

  var testUsers: List[User] = Nil
  var testLegalUsers: List[LegalUser] = Nil

  private val userClient: UserClient = new UserClient(System.getenv("IDAM_API_URL"))

  self.before {
    Await.ready(bootstrap(), infinite)
    Await.ready(legalBootstrap(), infinite)
  }

  def bootstrap(): Future[Any] = {
    val testUserInitialisations: List[Future[Unit]] = testUsers map { user =>
      for {
        _ <- userClient.create(user, "cmc-private-beta")
      } yield {}
    }

    val initialisationsSequence = Future.sequence(testUserInitialisations)
    initialisationsSequence.failed.foreach { exception =>
      logger.error(s"Bootstrap failed (${exception.getMessage}) - shutting down...")
      userClient.shutdown()
      System.exit(1)
    }
    initialisationsSequence
  }

   def legalBootstrap(): Future[Any] = {
    val testUserInitialisations: List[Future[Unit]] = testLegalUsers map { user =>
      for {
        _ <- userClient.createLegal(user, "cmc-solicitor")
      } yield {}
    }

    val initialisationsSequence = Future.sequence(testUserInitialisations)
    initialisationsSequence.failed.foreach { exception =>
      logger.error(s"Bootstrap failed (${exception.getMessage}) - shutting down...")
      userClient.shutdown()
      System.exit(1)
    }
    initialisationsSequence
  }

  self.after {
    teardown()
  }

  def teardown(): Unit = {
    userClient.shutdown()
  }

}
