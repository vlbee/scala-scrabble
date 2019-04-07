import GamePrompts._
import model._

object GameHelpers {

  def isGameOver(computerRack: List[Tile], computerName: String,humanRack: List[Tile], humanName: String, drawnBag: List[Tile]): Boolean = {
    def existsPossibleWordsInRack(rack: List[Tile]): Boolean = {
      Dictionary.filterPossibleWords(rack) match {
        case Some(l) => true
        case None => false
      }
    }

    val computerGameOver = !existsPossibleWordsInRack(computerRack)
    val humanGameOver = !existsPossibleWordsInRack(humanRack)

    if  (drawnBag.isEmpty && (computerGameOver && humanGameOver)) {
      printBagIsEmptyWarning()
      printNoPossibleWords(humanName, humanRack, computerName, computerRack)
      true
    } else false
  }

  def endGame(gameOver: Boolean, game: Game): Unit = {
    if (gameOver) {
      printGameOverSummary(game.computer, game.human)
      sys.exit()
    }
  }

  def fillRack(player: Player, letterBag: List[Tile]): (List[Tile], List[Tile]) = {
    val (drawnTiles, newBag) = letterBag.splitAt(7 - player.rack.length)
    val filledRack = player.rack ++ drawnTiles
    (filledRack, newBag)
  }

  def removeTilesFromRack(word: String, rack: List[Tile]): List[Tile] = {
    val wordTiles: List[Tile] = word.toList.map(c => Tile(c))
    rack diff wordTiles
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
