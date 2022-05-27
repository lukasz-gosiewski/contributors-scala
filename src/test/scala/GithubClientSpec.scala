import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import client.BlankNameError
import client.GithubClient._
import dto.RepositoryDto
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.UnexpectedStatus
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

// Those tests are performing real requests to the 3rd party API
// Those are not intended to take part in the normal QA process
// The purpose of those test is to perform TDD easily
class GithubClientSpec extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  private val ORG_NAME = "lgosiewski-test-org"
  private val ORG_AUTH_TOKEN = sys.env("GH-TOKEN") // If there is no GH-TOKEN env variable, we will default to an empty string

  "getOrganizationRepos" - {
    "return no errors" in {
      val result: IO[Seq[RepositoryDto]] = BlazeClientBuilder[IO].resource
        .use(client => getOrganizationRepos(ORG_NAME, ORG_AUTH_TOKEN, client)
      )

      result.assertNoException
    }

    "return a list of repositories" in {
      val result: IO[Seq[RepositoryDto]] = BlazeClientBuilder[IO].resource
        .use(client => getOrganizationRepos(ORG_NAME, ORG_AUTH_TOKEN, client)
      )

      result.assertNoException

      // There are 2 public 2 private repositories added to this org manually
      result.asserting(repositories => repositories.size shouldBe 4)
    }

    "return error when organization does not exists" in {
      val result: IO[Seq[RepositoryDto]] = BlazeClientBuilder[IO].resource
        .use(client => getOrganizationRepos("non-existing-org-name-123", ORG_AUTH_TOKEN, client)
      )

      recoverToSucceededIf[UnexpectedStatus] {
        result.unsafeToFuture()
      }
    }

    "return error when organization name blank" in {
      val result: IO[Seq[RepositoryDto]] = BlazeClientBuilder[IO].resource
        .use(client => getOrganizationRepos(" ", ORG_AUTH_TOKEN, client)
      )

      recoverToSucceededIf[BlankNameError] {
        result.unsafeToFuture()
      }
    }
  }
}
