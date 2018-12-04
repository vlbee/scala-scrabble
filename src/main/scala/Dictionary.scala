package scrabble

import scala.io.Source

object Dictionary {
  val all: List[String] = Source.fromFile("src/main/data/dictionary.txt").getLines.toList
}
