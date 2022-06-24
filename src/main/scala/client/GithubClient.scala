package client

import cats.effect.IO
import dto.{ContributorDto, OwnerDto, RepositoryDto}
import io.circe.Decoder
import io.circe.generic.semiauto._
import org.http4s.Method.GET
import org.http4s.circe.CirceEntityCodec._
import org.http4s.client.Client
import org.http4s.client.dsl.io._
import org.http4s.headers.Authorization
import org.http4s.implicits.http4sLiteralsSyntax
import org.http4s.{AuthScheme, Credentials, Status}

object GithubClient {
  implicit private val repositoryResponseDecoder: Decoder[RepositoryDto] = deriveDecoder
  implicit private val contributorResponseDecoder: Decoder[ContributorDto] = deriveDecoder
  implicit private val ownerResponseDecoder: Decoder[OwnerDto] = deriveDecoder

  def getOrganizationRepositories(orgName: String, authToken: String, client: Client[IO]): IO[Seq[RepositoryDto]] = {
    if (orgName.isBlank) {
      return IO.raiseError(BlankNameError())
    }

    val request = GET(
      uri"https://api.github.com/orgs" / orgName / "repos",
      Authorization(Credentials.Token(AuthScheme.Bearer, authToken))
    )

    client.run(request).use {
      case Status.Ok(r) => r.as[Seq[RepositoryDto]]
      case Status.NoContent(_) => IO(Seq.empty[RepositoryDto])
      case Status.NotFound(_) => IO.raiseError[Seq[RepositoryDto]](OrganizationDoesNotExistsError(orgName))
      case r => IO.raiseError[Seq[RepositoryDto]](ApiRequestError(r.status.code))
    }
  }

  def getRepositoryContributors(owner: String, repositoryName: String, authToken: String, client: Client[IO])
  : IO[Seq[ContributorDto]] = {
    if (owner.isBlank || repositoryName.isBlank) {
      return IO.raiseError(BlankNameError())
    }

    val request = GET(
      uri"https://api.github.com/repos" / owner / repositoryName / "contributors",
      Authorization(Credentials.Token(AuthScheme.Bearer, authToken))
    )

    client.run(request).use {
      case Status.Ok(r) => r.as[Seq[ContributorDto]]
      case Status.NoContent(_) => IO(Seq.empty[ContributorDto])
      case r => IO.raiseError[Seq[ContributorDto]](ApiRequestError(r.status.code))
    }
  }
}