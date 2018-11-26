package scrabble
import scala.io.Source

object Game extends App {
  //Letters should be distributed based on the English Scrabble letter distribution (you may ignore blank tiles).
  var letterBag: List[Tile] = Bag.initialise()
  var player = Player(Nil, Nil)

  // Assign seven tiles chosen randomly from the english alphabet to a rack.
  // TODO test
  // TODO need to use Option[Tile] & bag.headOption to handle empty Bag
  // TODO how to handle letterBag var here? Scoping?
  def drawTile(bag: List[Tile]): Tile = {
    val tile = bag.head // TODO use option
    letterBag = bag.tail
    tile
  }

  // TODO test
  def draw(player: Player, number: Int): Player = {
    // TODO player scoping?
    // check if play has rack/Nil??
    if (player.rack == Nil) {
      // if no, draw 7 & return new Player with rack of 7
      val newRack = List.fill(7)(drawTile(letterBag))
      letterBag = letterBag.takeRight(letterBag.length - 7) // TODO redundant if drawTile() handles
      Player(newRack, player.wordsPlayed)
    } else {
      // if yes draw difference to 7, return play with rack of 7
      val refilledRack = player.rack ++ List.fill(number)(drawTile(letterBag))
      letterBag = letterBag.takeRight(letterBag.length - number) // TODO redundant if drawTile() handles
      Player(refilledRack, player.wordsPlayed)
    }
  }

  def playWord(player: Player): Player  = {
    //Find a valid word formed from the seven tiles. A list of valid words can be found in twl06.txt.
    val validDictionary: List[String] = Source.fromFile("src/main/data/validWords.txt").getLines.toList
//    var word = 0;
//    for (word <- 0 to 10) {
//      println(validDictionary(word))
//    }
    player
  }

  def playerTurn(player: Player): Unit = {
    // compose playWord and draw?
    //plays word
    //draws to refill rack based on word length
  }

  def gameStart(): Unit = {
    player = Player(Nil, Nil) // reinitialize player with empty rack and word list
    draw(player, 7) // draw new rack
  }

  // Calculate the score for a word (you may ignore double/triple letter/word scores).
  def calculateWordScore(word: String): Int = {
    word.toList.map(Tile(_).value).sum
  }


 playWord(player);


}


//Find the longest valid word that can be formed from the seven tiles.
//Find the highest scoring word that can be formed.
//Find the highest scoring word if any one of the letters can score triple.
//For discussion: how would we adapt our solution for a multiplayer environment?