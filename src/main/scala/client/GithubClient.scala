package client

import cats.effect.IO
import io.circe.Decoder
import io.circe.generic.semiauto._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.http4s.client.blaze._
import org.http4s.client._

import scala.concurrent.ExecutionContext.global
import cats.effect.Blocker
import java.util.concurrent._

import javax.imageio.plugins.tiff.GeoTIFFTagSet
import org.http4s.{Method, Request, Uri}

object GithubClient {

  implicit private val responseDecoder: Decoder[RepositoryResponseDto] = deriveDecoder

  val blockingPool = Executors.newFixedThreadPool(5)
  val blocker = Blocker.liftExecutorService(blockingPool)
  val httpClient: Client[IO] = JavaNetClientBuilder[IO](blocker).create

  def getOrganizationRepos(name: String, authToken: String): Either[DomainError, ContributorResponseDto] = {
    if (name.isBlank) return Left(BlankNameError())

    AsyncHttpClient
      .resource

    val req = Request[IO](Method.GET, Uri.uri("https://api.github.com/orgs/" + name + "/repos"))

    Right()
  }
}