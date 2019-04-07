import model._

object GamePrompts {

  val yellow = "\u001b[33;1m"
  val red = "\u001b[31;1m"
  val white = "\u001b[0m"
  val blue = "\u001b[36m"

  // Message builders
  def notifyUser(message: String, color: String): Unit = {
    println(s"${color}${message}\u001b[0m")
  }

  def promptUser(question: String, color: String): String = {
    notifyUser(question, color)
    scala.io.StdIn.readLine().toString
  }

  // Messages
  def printWelcome(): Unit = {
    notifyUser("Welcome to Scala Scrabble Practice", red)
  }

  def printGreeting(name: String): Unit = {
    notifyUser(s"Hello $name", white)
    notifyUser("You will play against the computer.", white)
  }

  def promptName(): String = {
    promptUser("What is your name?", yellow).toLowerCase.capitalize
  }

  def promptLevel(): Level = {
    val input = promptUser("How good are you at Scrabble?\n[1] I'm a pro.\n[2] Go easy.\n", yellow)
    input match {
      case "1" => Hard
      case "2" => Easy
      case _ => promptLevel()
    }
  }

  def promptWord(): Option[String] = {
    val word = promptUser("Play a word:", yellow).toUpperCase
    word match {
      case "" => None
      case _ => Some(word)
    }
  }

  def printBagIsEmptyWarning(): Unit = {
    notifyUser("WARNING: the letter bag is empty", blue)
  }

  def printNoPossibleWords(): Unit = {
    notifyUser("There are no valid Scrabble words possible from these tiles.", blue)
  }

  def printWordPlayed(name: String, word: String): Unit = {
    notifyUser(s"$name has played: $word", white)
  }

  def printScore(player: Player, word: String): Unit = {
    notifyUser(s"The word score for $word is ${player.calculateWordScore(word)}", white)
    notifyUser(s"${player.name}'s total score is ${(player.wordsPlayed ++ List(word)).map(player.calculateWordScore(_)).sum}\n", red)
  }

  def printRack(name: String, rack: List[Tile]): Unit = {
    notifyUser(s"$name has drawn:", white)
    rack.map(tile => print(s"${tile.letter} "))
    println()
    rack.map(tile => print(s"${tile.value} "))
    println()
  }

  def printComputerForfeit() = {
    notifyUser("No possible words can be played from tiles. Computer forfeits turn.", blue)
  }

  def printHumanForfeit() = {
    notifyUser("You have forfeited your turn.", blue)
    notifyUser("Your tiles will be returned to the bag\nand you will draw all new tiles on your next turn.\n", white)
  }

  sealed trait error

  case object NotInDictionary extends error

  case object TilesNotInRack extends error

  def printInvalidWordReason(word: String, reason: error): Unit = {
    reason match {
      case NotInDictionary => notifyUser(s"'$word' is not in the Scrabble Dictionary.", white)
      case TilesNotInRack => notifyUser("You don't have those letter tiles in your rack.", white)
    }
  }

  def printTryAgain(word: String): Unit = {
    notifyUser(s"Therefore '$word' is not a valid word.", blue)
    notifyUser("Try again", yellow)
  }

  def printGameOverSummary(computerScore: Int, humanScore: Int, name: String, computer: String): Unit = {
    notifyUser("\nIt's GAME OVER", red)
    notifyUser(s"The computer's final score is $computerScore", white)
    notifyUser(s"Your final score is $humanScore", white)
    val result = computerScore - humanScore
    result match {
      case 0 =>
        notifyUser("It's a TIE.", yellow)
        notifyUser(s"More practice is needed $name.", yellow)
      case result if result > 0 =>
        notifyUser("You LOST to the Computer.", yellow)
        notifyUser(s"Better luck next time, $name.", yellow)
      case result if result < 0 =>
        notifyUser("You WIN.", yellow)
        notifyUser(s"Well done $name.", yellow)
    }
  }
}