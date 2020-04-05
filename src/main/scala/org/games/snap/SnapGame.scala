package org.games.snap

import java.util.concurrent.atomic.AtomicInteger

import scala.util.Random

class SnapGame(cfg: SnapGameConfig) {

  def shuffleCards(coll: Seq[Card]): Seq[Card] = Random.shuffle(coll)

  //returns the winner and total pending cards on the table
  def simulate(players: Seq[Player], firstToSnap: Seq[Player] => Player): (Option[Player], Int) = {

    //shuffle decks individually and merge them all and shuffle again
    val cardsToDeal = shuffleCards(cfg.decks.map(Deck.shuffle).flatMap(_.cards))

    val playerTurns = (LazyList continually players).flatten
    val (cardsRemainingOnTable, _) = cardsToDeal.zip(playerTurns).foldLeft((0, Option.empty[Card])) {
      case ((cardsToWin, previousCardOpt), (currentCard, playerToDraw)) =>
        Console.out.println(s"Player ${playerToDraw.name} draws $currentCard, cards on table $cardsToWin, last draw ${previousCardOpt.getOrElse("None")}")
        previousCardOpt.map { previousCard =>
          if (cfg.areCardsIdentical(previousCard, currentCard)) {
            val roundWinner = firstToSnap(players)
            Console.out.println(s"Player ${roundWinner.name} shouts 'Snap!' first, collects $cardsToWin cards")
            roundWinner.addToPile(cardsToWin + 1) // wins all cards on the table + last drawn card
            (0, None)
          } else {
            (cardsToWin + 1, Some(currentCard))
          }
        }.getOrElse((cardsToWin + 1, Some(currentCard)))
    }
    val winner =  if (players.map(_.total).toSet == 1) None else Some(players.maxBy(_.total))
    (winner, cardsRemainingOnTable)
  }
}
