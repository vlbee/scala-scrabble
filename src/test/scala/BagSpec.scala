import model.Bag
import org.scalatest._

class BagSpec extends FlatSpec with Matchers {

  "initialize()" should "generate a random List of 101 Tiles" in {
    Bag.initialise() should have length (101)
    Bag.initialise()(0).letter should be ('E')
    Bag.initialise()(100).letter should be ('S')
    Bag.initialise()(12).letter should be ('X')
  }

}
