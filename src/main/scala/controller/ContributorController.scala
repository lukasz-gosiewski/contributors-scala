package controller


import client._
import dto.ContributorDto
import org.http4s._
import cats.effect._
import io.circe.Encoder
import org.http4s.dsl.io._
import io.circe.generic.semiauto.deriveEncoder
import org.http4s.circe.jsonEncoderOf

object ContributorController {
  private implicit val contributorCirceEncoder: Encoder[ContributorDto] = deriveEncoder[ContributorDto]
  private implicit val contributorsEncoder: EntityEncoder[IO, Seq[ContributorDto]] = jsonEncoderOf

  private implicit val errorCirceEncoder: Encoder[DomainError] = deriveEncoder[DomainError]
  private implicit val errorEncoder: EntityEncoder[IO, DomainError] = jsonEncoderOf

  val contributorRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "organizations" / organizationName / "contributors" / "ranked" =>
      getRankedContributors(organizationName).fold(BadRequest(_), Ok(_))
  }

  def getRankedContributors(organizationName: String): Either[DomainError, Seq[ContributorDto]] = {
    Left(BlankNameError())
  }
}
