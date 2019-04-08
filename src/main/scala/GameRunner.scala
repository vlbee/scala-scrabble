import GameHelpers._
import GamePrompts._
import model._

import scala.util.Random

object GameRunner extends App {

  printWelcome()

  val name: String = promptName()
  printGreeting(name)

  val level: Level = promptLevel()
  val letterBag = Random.shuffle(Bag.initialise())
  val newGame = Game.apply(letterBag, Human.apply(name = name), Computer.apply(), level)

  takeTurn(newGame, newGame.human)

  // Game control flow //

  def takeTurn(game: Game, player: Player): Unit = {

    val (rack, bag) = fillRack(player, game.bag)
    player match {
      case player: Human => endGame(isGameOver(game.computer.rack, game.computer.name, player.rack, player.name, bag), game)
      case player: Computer => endGame(isGameOver(player.rack, player.name, game.human.rack, game.human.name, bag), game)
    }

    printRack(player.name, rack)
    if (rack.length < 7) {
      printBagIsEmptyWarning()
    }

    val word = player match {
      case player: Human => promptWord()
      case player: Computer => player.playWord(rack, game.level)
    }

    word match {
      case Some(word) => handlePlayedWord(game, player, word, rack, bag)
      case None => handleTurnForfeit(game, player, rack, bag)
    }
  }

  def handlePlayedWord(game: Game, player: Player, word: String, filledRack: List[Tile], bag: List[Tile]): Unit = {

    val playedRack = removeTilesFromRack(word, filledRack)
    printWordPlayed(player.name, word)

    player match {
      case player: Human =>
        if (!Dictionary.isValidWord(word, filledRack)) {
          invalidWordPlayed(word, filledRack)

          val repeatHuman = Human.apply(
            name = player.name,
            rack = filledRack,
            wordsPlayed = player.wordsPlayed
          )
          takeTurn(Game.apply(bag, repeatHuman, game.computer, game.level), repeatHuman)

        } else {
          printScore(player, word)
          val nextHuman = Human.apply(
            name = player.name,
            rack = playedRack,
            wordsPlayed = game.human.wordsPlayed ++ List(word)
          )
          takeTurn(Game.apply(bag, nextHuman, game.computer, game.level), game.computer)
        }
      case player: Computer =>
        printScore(player, word)
        val nextComputer = Computer.apply(
          rack = playedRack,
          wordsPlayed = game.computer.wordsPlayed ++ List(word)
        )
        takeTurn(Game.apply(bag, game.human, nextComputer, game.level), game.human)

    }
  }

  def handleTurnForfeit(game: Game, player: Player, filledRack: List[Tile], bag: List[Tile]): Unit = {
    player match {
      case player: Human => printHumanForfeit()
        val nextHuman = Human.apply(
          name = player.name,
          rack = Nil,
          wordsPlayed = player.wordsPlayed
        )
        takeTurn(Game.apply(Random.shuffle(bag ++ Random.shuffle(filledRack)), nextHuman, game.computer, game.level), game.computer)

      case player: Computer => printComputerForfeit()
        val nextComputer = Computer.apply(
          rack = Nil,
          wordsPlayed = player.wordsPlayed
        )
        takeTurn(Game.apply(
          bag ++ Random.shuffle(filledRack), game.human, nextComputer, game.level), game.human)
    }
  }

}

