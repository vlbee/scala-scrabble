import org.scalatest._
import GameHelpers._

class GameHelpersSpec extends FlatSpec with Matchers {

  "drawTile(bag)" should "return a Tile" in {
//    var letterBag = Bag.initialise()
//    drawTile(letterBag) shouldBe an[Option[Tile]] // TODO letterBag var
  }

  "drawTile(bag)" should "reduce letterBag length by 1" in {
//    var letterBag = Bag.initialise()
//    println(letterBag.length)
//    drawTile(letterBag) // TODO letterBag var
//    println(letterBag.length)
//    letterBag.length should equal(100)
  }

  "fillRack(player)" should "return a filled rack of length 7" in {
    val letterBag = Bag.initialise()
    fillRack(letterBag, Nil) should have length 7
    fillRack(letterBag, List(Tile('A'))) should have length 7
  }

  "removeTilesFromRack(word, rack)" should "retrun rack with leftover tiles" in {
    removeTilesFromRack("SHEEP", List(Tile('H'), Tile('E'), Tile('E'), Tile('S'), Tile('P'), Tile('U'), Tile('C'))).shouldBe(List(Tile('U'), Tile('C')))
    removeTilesFromRack("", List(Tile('H'), Tile('E'), Tile('E'), Tile('S'), Tile('P'), Tile('U'), Tile('C'))).shouldBe(List(Tile('H'), Tile('E'), Tile('E'), Tile('S'), Tile('P'), Tile('U'), Tile('C')))
    removeTilesFromRack("LETTERS", List(Tile('L'), Tile('E'), Tile('T'), Tile('S'), Tile('T'), Tile('R'), Tile('E'))).shouldBe(Nil)
  }

  "calculateWordScore(word)" should "return correct word score" in {
    calculateWordScore("TEST") should equal(4);
    calculateWordScore("QUICK") should equal(20);
  }

  "isInDictionary(word)" should "return true if word input is in Scrabble dictionary" in {
    isInDictionary("aardvark") shouldBe (true)
    isInDictionary("") shouldBe (false)
    isInDictionary("zebra") shouldBe (true)
    isInDictionary("brejklg") shouldBe (false)
  }

  "isInRack(word, rack)" should "return true if rack tiles can create word input" in {
    isInRack("CAT", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe (true)
    isInRack("DOG", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe (false)
    isInRack("ZZZ", List(Tile('T'), Tile('Z'), Tile('G'), Tile('T'), Tile('Z'), Tile('U'), Tile('Z'))) shouldBe (true)
    isInRack("EMPTIER", List(Tile('T'), Tile('I'), Tile('R'), Tile('E'), Tile('M'), Tile('P'), Tile('E'))) shouldBe (true)
    isInRack("ABBB", List(Tile('T'), Tile('B'), Tile('B'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe (false)
    isInRack("AA", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe (false)
    isInRack("LETTERS", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe (false)
    isInRack("LETTERS", List(Tile('T'), Tile('E'), Tile('E'), Tile('T'), Tile('L'), Tile('S'), Tile('R'))) shouldBe (true)
    isInRack("KILOMETER", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe (false)
  }

}
