package scrabble

import org.scalatest._

class GameSpec extends FlatSpec with Matchers {
  "calculateWordScore()" should "return correct word score" in {
    Game.calculateWordScore("TEST") should equal(4);
    Game.calculateWordScore("QUICK") should equal(20);
  }

}
