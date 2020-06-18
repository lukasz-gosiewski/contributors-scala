import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import client.GithubClient._
import dto.RepositoryDto
import org.http4s.client.blaze.BlazeClientBuilder
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global

class GithubClientSpec extends AsyncFreeSpec with AsyncIOSpec with Matchers {

  "getOrganizationRepos" - {
    "return no errors" in {
      val orgName = "sampleName"
      val orgAuthToken = "sampleToken"

      val result: IO[Seq[RepositoryDto]] = BlazeClientBuilder[IO](global).resource.use(client =>
        getOrganizationRepos(orgName, orgAuthToken)(ioContextShift, client)
      )

      result.assertNoException
    }
  }

//  it should "return BlankNameError if organization name is blank" in {
//    val result = getOrganizationRepos("      ", "sampleToken")
//
//    assert(result.isLeft)
//    result.left.value shouldBe a[BlankNameError]
//  }
//
//  it should "return proper results when checked on live API" in {
//    val result = getOrganizationRepos("dook", "sampleToken").
//  }
}
