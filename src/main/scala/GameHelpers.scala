import GamePrompts._
import model._

object GameHelpers {

  def checkIsPlayerGameOver(bag: List[Tile], player: Player): Boolean = {
    if (bag.isEmpty) {
      Dictionary.filterPossibleWords(player.rack) match {
        case Some(l) => false
        case None => player match {
          case player: Human => true
          case player: Computer => true
        }
      }
    } else false
  }

  def checkIsGameOverAndEnd(game: Game): Unit = {
    if (game.bag.isEmpty && (game.human.gameOver || game.computer.gameOver)) {
      val computerScore: Int = (game.computer.totalScore())
      val humanScore: Int = (game.human.totalScore())

      printGameOverSummary(computerScore, humanScore, game.human.name, game.computer.name)
      sys.exit()
    }
  }

  def fillRack(player: Player, letterBag: List[Tile]): (List[Tile], List[Tile]) = {
    val (drawnTiles, newBag) = letterBag.splitAt(7 - player.rack.length)
    val filledRack = player.rack ++ drawnTiles

    printRack(player.name, filledRack)

    if (filledRack.length < 7) {
      printBagIsEmptyWarning()
    }

    (filledRack, newBag)
  }

  def removeTilesFromRack(word: String, rack: List[Tile]): List[Tile] = {
    val wordTiles: List[Tile] = word.toList.map(c => Tile(c))
    rack diff wordTiles
  }


  def isValidWord(word: String, rack: List[Tile]): Boolean = {
    Dictionary.isInDictionary(word) && Dictionary.isInRack(word, rack)
  }

  def invalidWordPlayed(word: String, rack: List[Tile]): Unit = {
      if (!Dictionary.isInDictionary(word)) {
        printInvalidWordReason(word, NotInDictionary)
      }
      if (!Dictionary.isInRack(word, rack)) {
        printInvalidWordReason(word, TilesNotInRack)
      }
      printTryAgain(word)
  }

}
