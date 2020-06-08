import client.BlankNameError
import client.GithubClient._

class GithubClientSpec extends UnitSpec {

  "getOrganizationRepos" should "return right" in {
    val result = getOrganizationRepos("dook", "sampleToken")

    assert(result.isRight)
  }

  it should "return BlankNameError if organization name is blank" in {
    val result = getOrganizationRepos("      ", "sampleToken")

    assert(result.isLeft)
    result.left.value shouldBe a[BlankNameError]
  }

//  it should "return proper results when checked on live API" in {
//    val result = getOrganizationRepos("dook", "sampleToken").
//  }
}
