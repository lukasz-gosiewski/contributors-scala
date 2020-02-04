package client

import io.circe.Decoder
import io.circe.generic.semiauto._
import sttp.client._
import sttp.client.ResponseError
import sttp.client.circe._
import sttp.client.httpclient.{HttpClientFutureBackend, WebSocketHandler}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object GithubClient {

  type SttpResponse = Response[Either[ResponseError[io.circe.Error], List[RepositoryResponseDto]]]

  implicit private val backend: SttpBackend[Future, Nothing, WebSocketHandler] = HttpClientFutureBackend()
  implicit private val responseDecoder: Decoder[RepositoryResponseDto] = deriveDecoder

  def getOrganizationRepos(name: String, authToken: String): Either[DomainError, Future[SttpResponse]] = {
    if (name.isBlank) return Left(BlankNameError())

    Right(
      basicRequest.get(uri"https://api.github.com/orgs/$name/repos")
        .response(asJson[List[RepositoryResponseDto]])
        .send()
    )
  }
}