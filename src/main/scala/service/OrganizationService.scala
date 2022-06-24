package service

import cats.effect.IO
import dto.RepositoryDto
import org.http4s.client.Client
import client.GithubClient


sealed class OrganizationService(private val client: Client[IO], private val authToken: String) {

  def getOrganizationRepositories(organizationName: String): IO[Seq[RepositoryDto]] =
    GithubClient.getOrganizationRepositories(organizationName, authToken, client)
}
