import GameHelpers.{calculateWordScore}

object GamePrompts {

  def promptName(): String = {
    println("\u001b[33;1mWhat is your name?")
    scala.io.StdIn.readLine().toLowerCase.capitalize
  }

  def promptLevel(): String = {
    println("\u001b[33;1mHow good are you at Scrabble?\u001b[0m ")
    println("[1] I'm a pro.")
    println("[2] Go easy.")
    println()
    val level = scala.io.StdIn.readLine().toString
    level match {
      case "1" => "hard"
      case "2" => "easy"
      case _ => promptLevel()
    }
  }

  def promptWord(): String = {
    println("\u001b[33;1mPlay a word:\u001b[0m ")
    scala.io.StdIn.readLine().toUpperCase
  }


  def displayScore(player: Player, word: String): Unit = {
    println("\u001b[0mThe word score for " + word + " is " + calculateWordScore(word))
    println("\u001b[31;1m" + player.name + "'s total score is " + (player.wordsPlayed ++ List(word)).map(calculateWordScore(_)).sum)
    println("\u001b[0m")
  }

  def displayRack(name: String, rack: List[Tile]): Unit = {
    println("---------")
    println(name + " has drawn:")
    print("Rack tiles:   ")
    rack.map(tile => {
      print(tile.letter + " ")
    })
    println()
    print("Tile values:  ")
    rack.map(tile => {
      print(tile.value + " ")
    })
    println()
  }
}