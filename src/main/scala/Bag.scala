//package scrabble

import scala.util.Random

object Bag {

  var letterDistribution: Map[Char, Int] = Map(
    'A' -> 9,
    'B' -> 2,
    'C' -> 2,
    'D' -> 4,
    'E' -> 12,
    'F' -> 2,
    'G' -> 3,
    'H' -> 5,
    'I' -> 9,
    'J' -> 1,
    'K' -> 1,
    'L' -> 4,
    'M' -> 2,
    'N' -> 6,
    'O' -> 8,
    'P' -> 2,
    'Q' -> 1,
    'R' -> 6,
    'S' -> 4,
    'T' -> 6,
    'U' -> 4,
    'V' -> 2,
    'W' -> 2,
    'Y' -> 2,
    'X' -> 1,
    'Z' -> 1,
  )

  def initialise(): List[Tile] = {
    def dupeLetters(letter: (Char, Int)): List[Tile] = List.fill(letter._2)(Tile(letter._1))
    Random.shuffle(letterDistribution.toList.flatMap(dupeLetters))
  }

}

