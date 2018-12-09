//package scrabble

import org.scalatest._


class GameSpec extends FlatSpec with Matchers {

  "drawTile(bag)" should "return a Tile" in {
    var letterBag = Bag.initialise()
    Game.drawTile(letterBag) shouldBe an[Option[Tile]]
  }

  "drawTile(bag)" should "reduce letterBag length by 1" in {
    var letterBag = Bag.initialise()
    println(letterBag.length)
    Game.drawTile(letterBag) // TODO this is changing the letterBag in the Game object??
    println(letterBag.length)
    letterBag.length should equal(100)
  }

  "fillRack(player)" should "return a filled rack of length 7" in {
    Game.fillRack(Nil) should have length 7
    Game.fillRack(List(Tile('A'))) should have length 7
  }

  "removeTilesFromRack(word, rack)" should "retrun rack with leftover tiles" in {
    Game.removeTilesFromRack("SHEEP", List(Tile('H'), Tile('E'), Tile('E'), Tile('S'), Tile('P'), Tile('U'), Tile('C'))).shouldBe(List(Tile('U'), Tile('C')))
    Game.removeTilesFromRack("", List(Tile('H'), Tile('E'), Tile('E'), Tile('S'), Tile('P'), Tile('U'), Tile('C'))).shouldBe(List(Tile('H'), Tile('E'), Tile('E'), Tile('S'), Tile('P'), Tile('U'), Tile('C')))
    Game.removeTilesFromRack("LETTERS", List(Tile('L'), Tile('E'), Tile('T'), Tile('S'), Tile('T'), Tile('R'), Tile('E'))).shouldBe(Nil)
  }

  "calculateWordScore(word)" should "return correct word score" in {
    Game.calculateWordScore("TEST") should equal(4);
    Game.calculateWordScore("QUICK") should equal(20);
  }

  "isInDictionary(word)" should "return true if word input is in Scrabble dictionary" in {
    Game.isInDictionary("aardvark") shouldBe (true)
    Game.isInDictionary("") shouldBe (false)
    Game.isInDictionary("zebra") shouldBe (true)
    Game.isInDictionary("brejklg") shouldBe (false)
  }

  "isInRack(word, rack)" should "return true if rack tiles can create word input" in {
    Game.isInRack("CAT", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe (true)
    Game.isInRack("DOG", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe (false)
    Game.isInRack("ZZZ", List(Tile('T'), Tile('Z'), Tile('G'), Tile('T'), Tile('Z'), Tile('U'), Tile('Z'))) shouldBe (true)
    Game.isInRack("ZZZ", List(Tile('T'), Tile('Z'), Tile('G'), Tile('T'), Tile('P'), Tile('U'), Tile('Z'))) shouldBe (false)
    Game.isInRack("EMPTIER", List(Tile('T'), Tile('I'), Tile('R'), Tile('E'), Tile('M'), Tile('P'), Tile('E'))) shouldBe (true)
    Game.isInRack("ABBB", List(Tile('T'), Tile('B'), Tile('B'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe (false)
    Game.isInRack("AA", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe (false)
    Game.isInRack("LETTERS", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe (false)
    Game.isInRack("LETTERS", List(Tile('T'), Tile('E'), Tile('E'), Tile('T'), Tile('L'), Tile('S'), Tile('R'))) shouldBe (true)
    Game.isInRack("KILOMETER", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe (false)
  }

}
