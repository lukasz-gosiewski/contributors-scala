package client

import cats.effect.IO
import dto.{ContributorDto, RepositoryDto}
import io.circe.Decoder
import io.circe.generic.semiauto._
import org.http4s.Method.GET
import org.http4s.circe.CirceEntityCodec._
import org.http4s.client.Client
import org.http4s.client.dsl.io._
import org.http4s.headers.Authorization
import org.http4s.implicits.http4sLiteralsSyntax
import org.http4s.{AuthScheme, Credentials}

object GithubClient {
  implicit private val repositoryResponseDecoder: Decoder[RepositoryDto] = deriveDecoder

  def getOrganizationRepos(orgName: String, authToken: String, client: Client[IO]): IO[Seq[RepositoryDto]] = {
    if (orgName.isBlank) {
      return IO.raiseError(BlankNameError())
    }

    val request = GET(
      uri"https://api.github.com/orgs" / orgName / "repos",
      Authorization(Credentials.Token(AuthScheme.Bearer, authToken))
    )

    client.expect[Seq[RepositoryDto]](request)
  }

  implicit private val contributorResponseDecoder: Decoder[ContributorDto] = deriveDecoder

  def getRepositoryContributors(owner: String, repositoryName: String, authToken: String, client: Client[IO]): IO[Seq[ContributorDto]] = {
    if (owner.isBlank || repositoryName.isBlank) {
      return IO.raiseError(BlankNameError())
    }

    val request = GET(
      uri"https://api.github.com/repos" / owner / repositoryName / "contributors",
      Authorization(Credentials.Token(AuthScheme.Bearer, authToken))
    )

    client.expect[Seq[ContributorDto]](request)
  }
}