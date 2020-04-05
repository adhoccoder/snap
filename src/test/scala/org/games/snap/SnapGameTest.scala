package org.games.snap

import org.games.snap.Card.MatchCondition
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SnapGameTest extends AnyWordSpec with Matchers {

  "A game of snap with 1 deck" can {
    val snapGameCfg = SnapGameConfig(numDecks = 1, matchCondition = MatchCondition.SUITE)
    val g = new SnapGame(snapGameCfg) {
      override def shuffleCards(coll: Seq[Card]): Seq[Card] = {
        //ensures when matching by Suite that every second card is a match , except when we switch suites
        coll.groupBy(_.suite).values.flatten.toSeq
      }
    }
    "with custom shuffling, matching by Suite and probability skewed to player A" should {
      val players = Seq(Player("A"), Player("B"))
      val (winner, pending) = g.simulate(players, (p: Seq[Player]) => p.head)
      "have a winner" in {
        winner.isDefined should be (true)
      }
      "be won by player A by 51 cards with 1 card remaining on table" in {
        winner.get.name should be ("A")
        winner.get.total should be (51)
        pending should be (1)
      }
    }
    "with custom shuffling, matching by Suite and probability skewed to player B" should {
      val players = Seq(Player("A"), Player("B"))
      val (winner, pending) = g.simulate(players, (p: Seq[Player]) => p.last)
      "have a winner" in {
        winner.isDefined should be (true)
      }
      "be won by player B by 51 cards with 1 card remaining on table" in {
        winner.get.name should be ("B")
        winner.get.total should be (51)
        pending should be (1)
      }
    }
  }
}
