package scrabble

case class Tile(c: Char) {

  def value: Int = {
    c match {
      case 'A' | 'E' | 'I' | 'O' | 'U' | 'N' | 'R' | 'T' | 'L' | 'S' => 1
      case 'G' | 'D' => 2
      case 'B' | 'C' | 'M' | 'P' => 3
      case 'F' | 'H' | 'V' | 'W' | 'Y' => 4
      case 'K' => 5
      case 'J' | 'X' => 8
      case 'Q' | 'Z' => 10
      case _ => 0
    }
  }

  def letter: Char = c

}


