import GameHelpers._
import GamePrompts._
import scala.util.Random

object GameRunner extends App {

  def gameStart(): Unit = {
    println()
    println("Welcome to \u001b[31;1mScala Scrabble Practice")
    println()
    val name: String = promptName()
    println()
    println("Hello " + name + ". \u001b[0mThe Human plays against the Computer.")
    println()
    val level: String = promptLevel()

    val letterBag = Bag.initialise()
    val human = Player(name, Nil, Nil, false)
    val computer = Player("The Computer", Nil, Nil, false)
    val newGame = Game(letterBag, human, computer, level)

    humanTurn(newGame)
  }

  def computerTurn(game: Game): Unit = {
    checkGameOver(game)

    val computer = game.computer
    val (rack, bag) = fillRack(computer.rack, game.bag)
    displayRack(computer.name, rack)

    val word = playWord(rack, game.level)

    word match {
      case Some(w) =>
        println(computer.name + s" has played: '$w'")
        val playedRack = removeTilesFromRack(w, rack)
        displayScore(computer, w)
        val nextComputer = Player(computer.name, playedRack, computer.wordsPlayed ++ List(w), false)
        humanTurn(Game(bag, game.human, nextComputer, game.level))

      case None =>
        println("\u001b[36mNo possible words can be played from tiles. Computer forfeits turn.")
        println("\u001b[0m")
        checkGameOver(game)
        val nextComputer = Player(computer.name, Nil, computer.wordsPlayed, flagPlayerGameOver(bag))
        humanTurn(Game(bag ++ Random.shuffle(rack), game.human, nextComputer, game.level))
    }
  }

  def humanTurn(game: Game): Unit = {
    checkGameOver(game)

    val human = game.human
    val (rack, bag) = fillRack(human.rack, game.bag)
    displayRack(human.name, rack)

    val word: String = promptWord()

    if (bag == Nil && word == "") {
      println("You forfeited but as the letter bag is empty, it's \u001b[33;1mthe end of the game for you.")
      println("\u001b[0m")
      val nextHuman = Player(human.name, Nil, human.wordsPlayed, flagPlayerGameOver(bag))
      computerTurn(Game(bag, nextHuman, game.computer, game.level))

    } else if (word == "") {
      print("You have \u001b[36mforfeited your turn ")
      println("\u001b[0min order to draw all new tiles for your next turn.")
      println()
      val nextHuman = Player(human.name, Nil, human.wordsPlayed, flagPlayerGameOver(bag))
      computerTurn(Game(bag ++ Random.shuffle(rack), nextHuman, game.computer, game.level))

    } else if (!isValidWord(word, rack)) {
      print(s"Therefore '$word' is \u001b[36mnot a valid word. ")
      println("\u001b[33;1mTry again.\u001b[0m")
      humanTurn(game)

    } else {
      val playedRack = removeTilesFromRack(word, rack)
      displayScore(human, word)
      val nextHuman = Player(human.name, playedRack, human.wordsPlayed ++ List(word), false)
      computerTurn(Game(bag, nextHuman, game.computer, game.level))
    }
  }

  def checkGameOver(game: Game): Unit = {
    val name = game.human.name
    val computerScore: Int = (game.computer.wordsPlayed).map(calculateWordScore(_)).sum
    val humanScore: Int = (game.human.wordsPlayed).map(calculateWordScore(_)).sum

    if (game.bag == Nil && (game.human.gameOver || game.computer.gameOver)) {
      println("\u001b[31;1mIt's GAME OVER.")
      println(s"\u001b[0mThe computer's final score: $computerScore")
      println(s"Your final score: $humanScore")


      if (humanScore == computerScore) {
        print("\u001b[31;1mIt's a TIE. ")
        println(s"\u001b[33;1mMore practice is needed $name.")
      } else if (humanScore > computerScore) {
        print("\u001b[31;1mYou WIN. ")
        println(s"\u001b[33;1mWell done, $name.")
      } else {
        print("\u001b[31;1mYou LOST to the Computer. ")
        println(s"\u001b[33;1mBetter luck next time, $name.")
      }
      sys.exit()
    }
  }

  gameStart()

}