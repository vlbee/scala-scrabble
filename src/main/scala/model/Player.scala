package model

case class Player(name: String, rack: List[Tile], wordsPlayed: List[String], gameOver: Boolean)

object Player {
  def initialise(name: String): Player = {
    Player(name, Nil, Nil, false)
  }
}