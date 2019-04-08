package model

import scala.annotation.tailrec
import scala.io.Source

object Dictionary {

  val all: List[String] = Source.fromFile("src/main/resources/dictionary.txt").getLines.toList

  def isValidWord(word: String, rack: List[Tile]): Boolean = {
    Dictionary.isInDictionary(word) && Dictionary.isInRack(word, rack)
  }

  def isInDictionary(word: String): Boolean = {
    Dictionary.all.contains(word.toLowerCase())
  }

  def isInRack(word: String, rack: List[Tile]): Boolean = {
    @tailrec
    def go(wordLetters: List[Char], rackLetters: List[Char]): Boolean = {
      wordLetters match {
        case Nil => true
        case h :: t => rackLetters.contains(h) && go(t, rackLetters diff List(h))
      }
    }

    go(word.toList, rack.map(_.letter))
  }

  def filterPossibleWords(rack: List[Tile]): Option[List[String]] = {
    Dictionary.all.filter(word => isInRack(word.toUpperCase, rack)) match {
      case Nil => None
      case possibleWords => Some(possibleWords)
    }
  }

}
