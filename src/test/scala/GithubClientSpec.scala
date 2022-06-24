import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import client.BlankNameError
import client.GithubClient._
import dto.{ContributorDto, RepositoryDto}
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.UnexpectedStatus
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

// Those tests are performing real requests to the 3rd party API
// Those are not intended to take part in the normal QA process
// The purpose of those test is to perform TDD easily
class GithubClientSpec extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  private val ORG_NAME = "lgosiewski-test-org"
  private val ORG_AUTH_TOKEN = sys.env("GH-TOKEN")

  "get organization repositories" - {
    "return no errors" in {
      val result: IO[Seq[RepositoryDto]] = BlazeClientBuilder[IO].resource
        .use(client => getOrganizationRepositories(ORG_NAME, ORG_AUTH_TOKEN, client)
        )

      result.assertNoException
    }

    "return a list of repositories" in {
      val result: IO[Seq[RepositoryDto]] = BlazeClientBuilder[IO].resource
        .use(client => getOrganizationRepositories(ORG_NAME, ORG_AUTH_TOKEN, client)
        )

      result.assertNoException

      // There are 2 public 2 private repositories added to this org manually
      result.asserting(repositories => repositories.size shouldBe 4)
    }

    "return error when organization does not exists" in {
      val result: IO[Seq[RepositoryDto]] = BlazeClientBuilder[IO].resource
        .use(client => getOrganizationRepositories("non-existing-org-name-123", ORG_AUTH_TOKEN, client))

      recoverToSucceededIf[UnexpectedStatus] {
        result.unsafeToFuture()
      }
    }

    "return error when organization name blank" in {
      val result: IO[Seq[RepositoryDto]] = BlazeClientBuilder[IO].resource
        .use(client => getOrganizationRepositories(" ", ORG_AUTH_TOKEN, client))

      recoverToSucceededIf[BlankNameError] {
        result.unsafeToFuture()
      }
    }
  }

  "get repository contributors" - {
    "return a list of contributors for a given repo" in {
      val result: IO[Seq[ContributorDto]] = BlazeClientBuilder[IO].resource
        .use(client => getRepositoryContributors(
          "lukasz-gosiewski",
          "contributors-scala",
          ORG_AUTH_TOKEN,
          client
        ))

      result.assertNoException
      result.asserting(contributors => contributors.size shouldBe 1)
      result.asserting(contributors => contributors.head.login shouldBe "lukasz-gosiewski")
    }
  }
}
