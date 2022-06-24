package controller


import client._
import dto.ContributorDto
import org.http4s._
import cats.effect._
import cats.effect.unsafe.implicits.global
import io.circe.Encoder
import org.http4s.dsl.io._
import io.circe.generic.semiauto.deriveEncoder
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.circe.jsonEncoderOf
import service.ContributorsService

object ContributorController {
  private val ORG_AUTH_TOKEN = sys.env("GH-TOKEN")

  private implicit val contributorCirceEncoder: Encoder[ContributorDto] = deriveEncoder[ContributorDto]
  private implicit val contributorsEncoder: EntityEncoder[IO, Seq[ContributorDto]] = jsonEncoderOf

  private implicit val errorCirceEncoder: Encoder[DomainError] = deriveEncoder[DomainError]
  private implicit val errorEncoder: EntityEncoder[IO, DomainError] = jsonEncoderOf

  val contributorRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "organizations" / organizationName / "contributors" / "ranked" =>
      getRankedContributors(organizationName)
        .map(result => Ok(result))
        .onError(error => IO({
          println(s"Error response send to client")
          error.printStackTrace()
        }))
        .handleError {
          case error: BlankNameError => BadRequest(error.getMessage)
          case error: OrganizationDoesNotExistsError => NotFound(error.getMessage)
          case _ => InternalServerError("Oooupps, something went wrong, sorry!")
        }.unsafeRunSync()
  }

  def getRankedContributors(organizationName: String): IO[Seq[ContributorDto]] =
    BlazeClientBuilder[IO].resource
      .use(client => ContributorsService.getOrganizationContributorsRanked(organizationName, client, ORG_AUTH_TOKEN))
}
