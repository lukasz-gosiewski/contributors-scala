import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{EitherValues, Inside, Inspectors}

abstract class UnitSpec extends AnyFlatSpec with Matchers with EitherValues with Inside with Inspectors
