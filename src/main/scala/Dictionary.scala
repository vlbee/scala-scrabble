import scala.io.Source

object Dictionary {
  val all: List[String] = Source.fromFile("src/main/resources/dictionary.txt").getLines.toList
}
