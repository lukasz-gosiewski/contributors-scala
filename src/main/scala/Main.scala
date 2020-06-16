import cats.effect._
import cats.implicits._
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import service.{ContributorService, HelloWorldService}

import scala.concurrent.ExecutionContext

object Main extends IOApp {

  implicit private val CS: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  private val routes = HelloWorldService.helloWorldRoutes <+> ContributorService.contributorRoutes
  private val httpApp = Router("/" -> routes).orNotFound

  override def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(httpApp)
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}
