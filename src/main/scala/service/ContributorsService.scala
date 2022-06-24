package service

import cats.effect.IO
import client.GithubClient
import dto.ContributorDto
import org.http4s.client.Client

import cats.implicits._

object ContributorsService {

  def getOrganizationContributorsRanked(organizationName: String, client: Client[IO], authToken: String)
  : IO[Seq[ContributorDto]] =
    for (
      repositories <- GithubClient.getOrganizationRepositories(organizationName, authToken, client);
      contributors <-
        repositories.traverse(r => GithubClient.getRepositoryContributors(r.owner.login, r.name, authToken, client))
    ) yield contributors.flatten
      .sortWith(_.contributions > _.contributions)
}
