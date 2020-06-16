package client

import cats.effect.{ContextShift, IO}
import io.circe.Decoder
import io.circe.generic.semiauto._
import org.http4s.client.blaze._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.client.Client

object GithubClient {

  implicit private val responseDecoder: Decoder[RepositoryResponseDto] = deriveDecoder

  // URL: ("https://api.github.com/orgs/" + name + "/repos" <- that's for later
  def getOrganizationRepos(name: String, authToken: String)(implicit cs: ContextShift[IO], client: Client[IO]): IO[RepositoryResponseDto] = {
    if (name.isBlank) IO.raiseError(BlankNameError())
    else client.expect[RepositoryResponseDto]("https://api.github.com/orgs/repos")
  }
}