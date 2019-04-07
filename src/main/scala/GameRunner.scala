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


  def takeTurn(game: Game, player: Player): Unit = {

    checkIsGameOverAndEnd(game)

    val (rack, bag) = fillRack(player, game.bag)

    if (checkIsPlayerGameOver(game.bag, player)) {
      printNoPossibleWords()
      player match {
        case player: Human =>
          val gameOverHuman = Human.apply(
            name = player.name,
            rack = player.rack,
            wordsPlayed = game.human.wordsPlayed,
            gameOver = true
          )
          takeTurn(Game.apply(game.bag, gameOverHuman, game.computer, game.level), game.computer)

        case player: Computer =>
          val gameOverComputer = Computer.apply(
            rack = player.rack,
            wordsPlayed = game.computer.wordsPlayed,
            gameOver = true
          )
          takeTurn(Game.apply(game.bag, game.human, gameOverComputer, game.level), game.human)
      }
    }



    val word = player match {
      case player: Human => promptWord()
      case player: Computer => player.playWord(rack, game.level)
    }

    word match {
      case Some(word) =>
        val playedRack = removeTilesFromRack(word, rack)
        printWordPlayed(player.name, word)

        player match {
          case player: Human =>
            if (!isValidWord(word, rack)) {
              invalidWordPlayed(word, rack)

              val repeatHuman = Human.apply(
                name = player.name,
                rack = rack,
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

      case None => player match {
        case player: Human => printHumanForfeit()
          val nextHuman = Human.apply(
            name = player.name,
            rack = Nil,
            wordsPlayed = player.wordsPlayed
          )
          takeTurn(Game.apply(Random.shuffle(bag ++ Random.shuffle(rack)), nextHuman, game.computer, game.level), game.computer)

        case player: Computer => printComputerForfeit()
          val nextComputer = Computer.apply(
            rack = Nil,
            wordsPlayed = player.wordsPlayed
          )
          takeTurn(Game.apply(
            bag ++ Random.shuffle(rack), game.human, nextComputer, game.level), game.human)
      }
    }
  }
}

