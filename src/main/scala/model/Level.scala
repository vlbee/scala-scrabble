package model

sealed abstract class Level(val label: String)
case object Easy extends Level("easy")
case object Hard extends Level("hard")