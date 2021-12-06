package client

import cats.effect.IO
import dto.RepositoryDto
import io.circe.Decoder
import io.circe.generic.semiauto._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.client.Client

object GithubClient {
  implicit private val responseDecoder: Decoder[RepositoryDto] = deriveDecoder

  // AuthToken is there for the future, when we would like to fetch not only public repos
  def getOrganizationRepos(name: String, authToken: String, client: Client[IO]): IO[Seq[RepositoryDto]] = {
    if (name.isBlank) IO.raiseError(BlankNameError())
    else client.expect[Seq[RepositoryDto]](s"https://api.github.com/orgs/$name/repos")
  }
}