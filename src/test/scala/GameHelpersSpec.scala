import org.scalatest._
import GameHelpers._

class GameHelpersSpec extends FlatSpec with Matchers {

  "fillRack(player)" should "return a filled rack of length 7" in {
    val letterBag = Bag.initialise()
    fillRack(Nil, letterBag)._1 should have length 7
    fillRack(Nil, letterBag)._2 should have length (101 - 7)
    fillRack(List(Tile('A')), letterBag)._1 should have length 7
    fillRack(List(Tile('A')), letterBag)._2 should have length (101 - 6)
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