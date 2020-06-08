package service


import client.DomainError
import dto.ContributorDto
import org.http4s._
import cats.effect._
import org.http4s.dsl.io._

object ContributorService {

  // TODO: I need to somehow get those encoders from Circe. Implicitly maybe?
  implicit def contributorsEncoder: EntityEncoder[IO, Seq[ContributorDto]] = ???
  implicit def errorEncoder: EntityEncoder[IO, DomainError] = ???

  val contributorRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "contributors" / "ranked" => getRankedContributors.fold(BadRequest(_), Ok(_))
  }

  def getRankedContributors: Either[DomainError, Seq[ContributorDto]] = Right(Seq.empty[ContributorDto])
}
