import cats.effect._
import cats.implicits._
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import controller.{ContributorController, HelloWorldController}

object Main extends IOApp {

  private val routes = HelloWorldController.helloWorldRoutes <+> ContributorController.contributorRoutes
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
