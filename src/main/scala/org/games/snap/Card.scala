package org.games.snap

import org.games.snap.Card.{CardValue, Suite}
import scala.util.Random

object Card {

  sealed trait Suite
  object Suite {
    case object SPADES extends Suite
    case object HEARTS extends Suite
    case object DIAMONDS extends Suite
    case object CLUBS extends Suite
    val values: Seq[Suite] = Seq(SPADES, HEARTS, DIAMONDS, CLUBS)
  }

  sealed trait CardValue
  object CardValue {
    case object ACE extends CardValue
    case object TWO extends CardValue
    case object THREE extends CardValue
    case object FOUR extends CardValue
    case object FIVE extends CardValue
    case object SIX extends CardValue
    case object SEVEN extends CardValue
    case object EIGHT extends CardValue
    case object NINE extends CardValue
    case object TEN extends CardValue
    case object JACK extends CardValue
    case object QUEEN extends CardValue
    case object KING extends CardValue
    val values: Seq[CardValue] = Seq(ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING)
  }

  sealed trait MatchCondition {
    def name: String
    def isIdentical(c1: Card, c2: Card): Boolean
  }
  object MatchCondition {
    case object SUITE extends MatchCondition {
      val name = "suite"
      def isIdentical(c1: Card, c2: Card): Boolean = c1.suite == c2.suite
    }
    case object VALUE extends MatchCondition {
      val name = "value"
      def isIdentical(c1: Card, c2: Card): Boolean = c1.value == c2.value
    }

    case object BOTH extends MatchCondition {
      val name = "both"
      def isIdentical(c1: Card, c2: Card): Boolean = c1 == c2
    }

    case class Unknown(name: String) extends MatchCondition {
      def isIdentical(c1: Card, c2: Card): Boolean = c1 == c2
    }

    val values: Seq[MatchCondition] = Seq(SUITE, VALUE, BOTH)
    private val valueMap = values.map(m => m.name -> m).toMap

    def apply(cond: String): MatchCondition = valueMap.getOrElse(cond.toLowerCase, Unknown(cond))
  }
}
final case class Card(suite: Suite, value: CardValue){
  override def toString = s"$value of $suite"
}