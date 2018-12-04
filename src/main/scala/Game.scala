package scrabble

import scala.util.Random

object Game extends App {

  // UTILITIES //

  def drawTile(bag: List[Tile]): Option[Tile] = {
    val tile = bag.headOption
    tile match {
      case Some(Tile(_)) => {
        letterBag = bag.tail
        tile
      }
      case None => None
    }
  }

  // TODO better way to use Option here?
  def fillRack(rack: List[Tile]): List[Tile] = {
    val filledRack = {
      if (rack == Nil) List.fill(7)(drawTile(letterBag)).flatten
      else rack ++ List.fill(7 - rack.length)(drawTile(letterBag)).flatten
    }
    if (filledRack.length < 7) {
      println("The letter bag is empty")
    }
    filledRack
  }

  def removeTilesFromRack(word: String, rack: List[Tile]): List[Tile] = {
    val wordTiles: List[Tile] = word.toList.map(c => Tile(c))
    rack diff wordTiles
  }

  def displayRack(player: Player, start: Boolean): Unit = {
    val rack = player.rack
    if (!start) println(player.name + " has drawn:")
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

  def promptWord(): String = {
    println("Play a word:")
    scala.io.StdIn.readLine().toUpperCase
  }

  def promptName(): String = {
    println("What is your name?")
    scala.io.StdIn.readLine().toLowerCase.capitalize
  }

  def calculateWordScore(word: String): Int = {
    word.toList.map(Tile(_).value).sum
  }

  def isInDictionary(word: String): Boolean = {
    if (Dictionary.all.contains(word.toLowerCase())) true else false
  }

  // TODO fix "AA" edge case?
  def isInRack(word: String, rack: List[Tile]): Boolean = {
    val sortedWord = word.toList.sortWith(_.compareTo(_) < 0)
    val sortedRack = rack.map(_.letter).sortWith(_.compareTo(_) < 0)
    //    println(sortedWord)
    //    println(sortedRack)

    def loop(wL: List[Char], wI: Int, rL: List[Char], rI: Int): Boolean = {
      //      println(wL(wI), wI, rL(rI), rI)
      if (wI + 1 == sortedWord.length) true // remove + 1 but then loop fails
      else if (rI + 1 == sortedRack.length) false
      else if (wL(wI) == rL(rI)) loop(wL, wI + 1, rL, rI + 1)
      else if (wL(wI) != rL(rI)) loop(wL, wI, rL, rI + 1)
      else true
    }

    loop(sortedWord, 0, sortedRack, 0)
  }

  def isValidWord(word: String, rack: List[Tile]): Boolean = {
    isInDictionary(word) && isInRack(word, rack)
  }

  // TODO Highest scoring word
  def playWord(rack: List[Tile], level: String): Option[String] = {
    val possibleWords = Dictionary.all.filter(word => isInRack(word.toUpperCase, rack))
    if (possibleWords == Nil) {
      None
    } else {
      level match {
        case "random" => Some(Random.shuffle(possibleWords).head.toUpperCase)
        case "longest" => Some(possibleWords.sortBy(word => (word.length)).reverse.head.toUpperCase)
      }
    }
  }

  // INITIALIZE GAME //
  var letterBag: List[Tile] = Bag.initialise()

  // GAME PLAY //
  def displayScore(player: Player, word: String): Unit = {
    println("The word score for " + word + " is " + calculateWordScore(word))
    println(player.name + "'s total score is " + (player.wordsPlayed ++ List(word)).map(calculateWordScore(_)).sum)
    println()
    println("---------")
    println()
  }

  def computerTurn(player: Player): Player = {
    val word = playWord(player.rack, "longest")
    word match {
      case Some(w) => {
        println(player.name + " has played: " + w)
        val playedRack = removeTilesFromRack(w, player.rack)
        displayScore(player, w)
        Player(player.name, fillRack(playedRack), player.wordsPlayed ++ List(w))
      }
      case None => {
        println("No possible words can be played from tiles.")
        println("---------")
        println()
        Player(player.name, player.rack, player.wordsPlayed)
      }
    }
  }

  def playerTurn(player: Player): Player = {
    val word: String = promptWord()
    if (word == "") {
      println("You've forfeited your turn in order to draw all new tiles for your next turn.")
      println("---------")
      println()
      Player(player.name, fillRack(Nil), player.wordsPlayed)
    } else if (!isValidWord(word, player.rack)) {
      println(word + " is not a valid word. Try again.")
      playerTurn(player)
    } else {
      val playedRack = removeTilesFromRack(word, player.rack)
      displayScore(player, word)
      Player(player.name, fillRack(playedRack), player.wordsPlayed ++ List(word))
    }
  }

  def turn(player: Player, nextPlayer: Player, start: Boolean = false): (Player, Player) => Player = {
    displayRack(player, start);
    if (player.name == "The Computer") {
      turn(nextPlayer, computerTurn(player))
    } else {
      turn(nextPlayer, playerTurn(player))
    }
  }

  def gameStart(): Unit = {
    println()
    println("Welcome to Scala Scrabble Practice")
    println()
    val name: String = promptName()
    println("---------")
    println()
    println("Hello " + name + ".) This is your starting rack of letters:")
    turn(Player(name, fillRack(Nil), Nil), Player("The Computer", fillRack(Nil), Nil), true)
  }

  gameStart()

}


//Find the longest valid word that can be formed from the seven tiles.
//Find the highest scoring word that can be formed.
//Find the highest scoring word if any one of the letters can score triple.
//For discussion: how would we adapt our solution for a multiplayer environment?