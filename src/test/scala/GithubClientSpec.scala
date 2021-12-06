import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import client.BlankNameError
import client.GithubClient._
import dto.RepositoryDto
import org.http4s.client.UnexpectedStatus
import org.http4s.client.blaze.BlazeClientBuilder
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global

// Those tests are performing real requests to the 3rd party API
// Those are not intended to take part in the normal QA process
// The purpose of those test is to perform TDD easily
class GithubClientSpec extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  private val ORG_NAME = "lgosiewski-test-org"
  private val ORG_AUTH_TOKEN = ""

  "getOrganizationRepos" - {
    "return no errors" in {
      val result: IO[Seq[RepositoryDto]] = BlazeClientBuilder[IO](global).resource.use(client =>
        getOrganizationRepos(ORG_NAME, ORG_AUTH_TOKEN, client)
      )

      result.assertNoException
    }

    "return error when organization does not exists" in {
      val result: IO[Seq[RepositoryDto]] = BlazeClientBuilder[IO](global).resource.use(client =>
        getOrganizationRepos("non-existing-org-name-123", ORG_AUTH_TOKEN, client)
      )

      recoverToSucceededIf[UnexpectedStatus] {
        result.unsafeToFuture()
      }
    }

    "return error when organization name blank" in {
      val result: IO[Seq[RepositoryDto]] = BlazeClientBuilder[IO](global).resource.use(client =>
        getOrganizationRepos(" ", ORG_AUTH_TOKEN, client)
      )

      recoverToSucceededIf[BlankNameError] {
        result.unsafeToFuture()
      }
    }
  }
}
