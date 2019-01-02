import scala.util.Random

object GameHelpers {

  def fillRack(rack: List[Tile], letterBag: List[Tile]): (List[Tile], List[Tile]) = {
    val (drawnTiles, newBag) = letterBag.splitAt(7 - rack.length)
    val filledRack = rack ++ drawnTiles

    if (filledRack.length < 7) {
      println("\u001b[0m---------")
      print("\u001b[31;1mWARNING: ")
      println("\u001b[36mThe letter bag is empty.")
      print("\u001b[0m")
    }

    (filledRack, newBag)
  }

  def removeTilesFromRack(word: String, rack: List[Tile]): List[Tile] = {
    val wordTiles: List[Tile] = word.toList.map(c => Tile(c))
    rack diff wordTiles
  }

  def calculateWordScore(word: String): Int = {
    word.toList.map(Tile(_).value).sum
  }

  def isInDictionary(word: String): Boolean = {
    if (Dictionary.all.contains(word.toLowerCase())) true else false
  }

  def isInRack(word: String, rack: List[Tile]): Boolean = {
    val sortedWord = word.toList.sortWith(_.compareTo(_) < 0)
    val sortedRack = rack.map(_.letter).sortWith(_.compareTo(_) < 0)

    def loop(wL: List[Char], wI: Int, rL: List[Char], rI: Int): Boolean = {
      if (wI == sortedWord.length) true
      else if (rI == sortedRack.length) false
      else if (wL(wI) == rL(rI)) loop(wL, wI + 1, rL, rI + 1)
      else if (wL(wI) != rL(rI)) loop(wL, wI, rL, rI + 1)
      else false
    }

    loop(sortedWord, 0, sortedRack, 0)
  }

  def isValidWord(word: String, rack: List[Tile]): Boolean = {
    if(!isInDictionary(word)) { println(s"'$word' is not in the Scrabble Dictionary.")}
    if(!isInRack(word, rack)) { println("You don't have those letter tiles in your rack.")}
    isInDictionary(word) && isInRack(word, rack)
  }

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

  def flagPlayerGameOver(bag: List[Tile]): Boolean = {
    if (bag == Nil) true else false
  }

}
