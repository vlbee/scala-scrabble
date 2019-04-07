package model

import scala.util.Random

sealed trait Player {
  def name: String

  def rack: List[Tile]

  def wordsPlayed: List[String]

  def gameOver: Boolean

  def calculateWordScore(word: String): Int = {
    word.toList.map(Tile(_).value).sum
  }

  def totalScore() = {
    wordsPlayed.map(calculateWordScore(_)).sum
  }

}

final case class Human(name: String, rack: List[Tile] = Nil, wordsPlayed: List[String] = Nil, gameOver: Boolean = false) extends Player

final case class Computer(rack: List[Tile] = Nil, wordsPlayed: List[String] = Nil, gameOver: Boolean = false) extends Player {

  val name: String = "The Computer"

  def playWord(rack: List[Tile], level: Level): Option[String] = {
    val possibleWords = Dictionary.filterPossibleWords(rack)

    possibleWords.filter(_.nonEmpty).map { possibleWords =>
      level match {
        case Easy => Random.shuffle(possibleWords)
        case Hard => possibleWords.sortBy(word => (word.length)).reverse
      }
    }.map(_.head.toUpperCase)
  }

}