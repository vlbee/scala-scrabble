package scrabble

import org.scalatest._

class BagSpec extends FlatSpec with Matchers {

  "initialize()" should "generate a random List of 101 Tiles" in {
    Bag.initialise() should have length (101)
    // TODO test for randomness
  }

}


