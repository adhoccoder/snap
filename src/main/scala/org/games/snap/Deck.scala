package org.games.snap

import org.games.snap.Card.{CardValue, Suite}

import scala.util.Random

object Deck {
  def apply(): Deck = {
    val cards = Suite.values.flatMap {
      s => CardValue.values.map(v => Card(s, v))
    }
    // some example/dummy validation
    require(cards.toSet.size == 52, s"A deck should contain 52 unique cards found ${cards.toSet.size}")
    new Deck(cards)
  }

  def shuffle(d: Deck): Deck = d.copy(Random.shuffle(d.cards))
}

case class Deck private[snap](cards: Seq[Card])