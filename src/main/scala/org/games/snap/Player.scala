package org.games.snap

import java.util.concurrent.atomic.AtomicInteger

final case class Player(name: String) {
  private val cardsWon = new AtomicInteger(0)
  def addToPile(cards: Int): Int = cardsWon.addAndGet(cards)
  def total: Int = cardsWon.get()
  def reset(): Unit = cardsWon.set(0)
  override def toString = s"Player $name: $total cards"
}
