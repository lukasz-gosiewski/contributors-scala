package client

import cats.effect.{ContextShift, IO}
import dto.RepositoryDto
import io.circe.Decoder
import io.circe.generic.semiauto._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.client.Client

object GithubClient {
  implicit private val responseDecoder: Decoder[RepositoryDto] = deriveDecoder

  def getOrganizationRepos(name: String, authToken: String)(implicit cs: ContextShift[IO], client: Client[IO]): IO[Seq[RepositoryDto]] = {
    if (name.isBlank) IO.raiseError(BlankNameError())
    else client.expect[Seq[RepositoryDto]](s"https://api.github.com/orgs/$name/repos")
  }
}