package scrabble

import org.scalatest._

class GameSpec extends FlatSpec with Matchers {

  "drawTile()" should "return a Tile" in {
    var letterBag = Bag.initialise()
    Game.drawTile(letterBag) shouldBe a [Tile]
  }

  "drawTile()" should "reduce letterBag length by 1" in {
    var letterBag = Bag.initialise()
    Game.drawTile(letterBag)
    letterBag.length should equal(100)
  }

  "calculateWordScore()" should "return correct word score" in {
    Game.calculateWordScore("TEST") should equal(4);
    Game.calculateWordScore("QUICK") should equal(20);
  }

  "draw(player, number)" should "return new rack of length 7" in {

  }

}
