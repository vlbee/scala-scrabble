package scrabble
import org.scalatest._
//import org.scalatest.{FlatSpec, Matchers}

//flat spec = it should
//matchers = assertions
class BagSpec extends FlatSpec with Matchers {

  "initialize()" should "generate random List of 101 Tiles" in {
    Bag.initialise() should have length(101)
  }

}


