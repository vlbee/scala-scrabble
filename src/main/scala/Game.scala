package scrabble

object Game extends App {

  // UTILITIES //

  // Assign seven tiles chosen randomly from the english alphabet to a rack.
  // TODO use option to handle empty bag
  def drawTile(bag: List[Tile]): Tile = {
    val tile = bag.head
    letterBag = bag.tail
    tile
  }

  // Refill player rack after playing a word
  def fillRack(rack: List[Tile]): List[Tile] = {
    if (rack == Nil) List.fill(7)(drawTile(letterBag))
    else rack ++ List.fill(7 - rack.length)(drawTile(letterBag))
  }

  // TODO test
  def removeTilesFromRack(word: String, rack: List[Tile]): List[Tile] = {
    val wordTiles: List[Tile] = word.toList.map(c => Tile(c))
    rack diff wordTiles
  }

  def displayRack(player: Player): Unit = {
    val rack = player.rack
    print("Rack tiles:   ")
    rack.map(tile => {
      print(tile.letter + " ")
    })
    println()
    print("Rack values:  ")
    rack.map(tile => {
      print(tile.value + " ")
    })
    println()
  }

  def promptWord(player: Player): String = {
    println("Play a word:")
    val input = scala.io.StdIn.readLine()
    input.toUpperCase
  }

  // Calculate the score for a word (you may ignore double/triple letter/word scores).
  def calculateWordScore(word: String): Int = {
    word.toList.map(Tile(_).value).sum
  }

  def isInDictionary(word: String): Boolean = {
    if (Dictionary.all.contains(word.toLowerCase())) true else false
  }

  def isInRack(word: String, rack: List[Tile]): Boolean = {
    val sortedWord = word.toList.sortWith(_.compareTo(_) < 0)
    val sortedRack = rack.map(_.letter).sortWith(_.compareTo(_) < 0)

    def loop(wL: List[Char], wI: Int, rL: List[Char], rI: Int ): Boolean  = {
      if (wI + 1 == sortedWord.length) true
      else if (rI + 1 == sortedRack.length) false
      else if (wL(wI) == rL(rI)) loop(wL, wI + 1, rL, rI + 1)
      else if (wL(wI) != rL(rI)) loop(wL, wI, rL, rI + 1)
      else false
    }

    loop(sortedWord, 0, sortedRack, 0)
  }

  def isValidWord(word: String, rack: List[Tile]): Boolean = {
    if (isInDictionary(word) && isInRack(word, rack)) true else false
  }

  // INITIALIZE GAME //

  //Letters should be distributed based on the English Scrabble letter distribution (you may ignore blank tiles).
  var letterBag: List[Tile] = Bag.initialise()
  var player = Player(Nil, Nil, 0)
  var computer = Player(Nil, Nil, 0)


  // GAME PLAY //
  def playerTurn(player: Player): (Player) => Player = {
    displayRack(player);
    val word: String = promptWord(player)
    // forfeit turn and get a new rack
    if (word == "") {
      playerTurn(Player(fillRack(Nil), player.wordsPlayed, player.score))
    } else if (!isValidWord(word, player.rack)) {
      println(word + " is not a valid word. Try again.")
      println("---------")
      playerTurn(player)
    } else {
      val playedRack = removeTilesFromRack(word, player.rack)
      val newScore = player.score + calculateWordScore(word)
      println()
      println("The word score for " + word + " is " + calculateWordScore(word))
      println("Your total score is " + newScore)
      println("---------")
      playerTurn(Player(fillRack(playedRack), player.wordsPlayed ++ List(word), newScore))
    }
  }


  def gameStart(): Unit = {
    println()
    println("Welcome to Scala Scrabble Practice")
    println()
    player = Player(fillRack(Nil), Nil, 0) // reinitialize player with empty rack and word list
    println("This is your starting rack of letters:")
    playerTurn(player)
  }

  gameStart();
}


//Find the longest valid word that can be formed from the seven tiles.
//Find the highest scoring word that can be formed.
//Find the highest scoring word if any one of the letters can score triple.
//For discussion: how would we adapt our solution for a multiplayer environment?