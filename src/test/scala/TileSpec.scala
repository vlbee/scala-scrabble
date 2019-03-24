import model.Tile
import org.scalatest._

class TileSpec extends FlatSpec with Matchers {
  it should "return correct tile letter value" in {
    Tile('A').value should be(1)
    Tile('Z').value should be(10)
    Tile('C').value should be(3)
    Tile('G').value should be(2)
    Tile('K').value should be(5)
  }
}
