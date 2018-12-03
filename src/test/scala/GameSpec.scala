package scrabble

import org.scalatest._
import scrabble.Game.letterBag

class GameSpec extends FlatSpec with Matchers {

  "drawTile(bag)" should "return a Tile" in {
    var letterBag = Bag.initialise()
    Game.drawTile(letterBag) shouldBe a[Tile]
  }

  "drawTile(bag)" should "reduce letterBag length by 1" in {
    var letterBag = Bag.initialise()
    println(letterBag.length)
    Game.drawTile(letterBag) // TODO this is changing the letterBag in the Game object??
    println(letterBag.length)
    letterBag.length should equal(100)
  }

  "calculateWordScore(word)" should "return correct word score" in {
    Game.calculateWordScore("TEST") should equal(4);
    Game.calculateWordScore("QUICK") should equal(20);
  }

  "fillRack(player)" should "return a filled rack of length 7" in {
    Game.fillRack(Nil) should have length 7
    Game.fillRack(List(Tile('A'))) should have length 7
  }

  "isInRack(word, rack)" should "return true if rack tiles can create word input" in {
    Game.isInRack("CAT", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe(true)
    Game.isInRack("DOG", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe(false)
    Game.isInRack("TATUGBC", List(Tile('T'), Tile('B'), Tile('G'), Tile('T'), Tile('A'), Tile('U'), Tile('C'))) shouldBe(true)
  }

  "isInDictionary(word)" should "return true if word input is in Scrabble dictionary" in {
    Game.isInDictionary("aardvark") shouldBe(true)
    Game.isInDictionary("") shouldBe(false)
    Game.isInDictionary("aa") shouldBe(true)
    Game.isInDictionary("brejklg") shouldBe(false)
  }


}
