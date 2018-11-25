package scrabble

object Game extends App {
  //Letters should be distributed based on the English Scrabble letter distribution (you may ignore blank tiles).
  var letterBag: List[Tile] = Bag.initialise()
  var player = Player(draw(7), Nil)

  // Assign seven tiles chosen randomly from the english alphabet to a rack.
  // TODO need to use Option[Tile] & bag.headOption to handle empty Bag
  def drawTile(bag: List[Tile]): Tile = {
    bag.head
  }

  def draw(size: Int): List[Tile] = {
    val rack = List.fill(size)(drawTile(letterBag))
    letterBag = letterBag.takeRight(letterBag.length - size)
    rack
  }

  // Calculate the score for a word (you may ignore double/triple letter/word scores).
  def calculateWordScore(word: String): Int = {
    word.toList.map(Tile(_).value).sum
  }

  def playerTurn(player: Player): Unit = {
    //plays word
    //refills tiles
  }

  def playWord(player: Player): Unit  = {
  //Find a valid word formed from the seven tiles. A list of valid words can be found in twl06.txt.
  }

  def drawTiles(player: Player, num: Int): Player = {
     val refilledRack = player.rack ++ draw(num)
     Player(refilledRack, player.wordsPlayed)
  }


}


//Find the longest valid word that can be formed from the seven tiles.
//Find the highest scoring word that can be formed.
//Find the highest scoring word if any one of the letters can score triple.
//For discussion: how would we adapt our solution for a multiplayer environment?