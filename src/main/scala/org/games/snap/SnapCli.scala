package org.games.snap

import org.games.snap.Card.MatchCondition
import org.games.snap.Card.MatchCondition.{Unknown, values}
import scopt.OParser

import scala.util.Random


final case class SnapGameConfig(numDecks: Int = 1, matchCondition: MatchCondition = MatchCondition.SUITE){
  val decks: Seq[Deck] = List.fill(numDecks)(Deck())
  val areCardsIdentical: (Card, Card) => Boolean = matchCondition.isIdentical
}

object SnapCli extends App {

  implicit val matchCondition: scopt.Read[Card.MatchCondition] = scopt.Read.reads(Card.MatchCondition(_))

  val builder = OParser.builder[SnapGameConfig]
  val parser = {
    import builder._
    OParser.sequence(
      programName("snap"),
      head("snap", "1.0"),
      opt[Int]('d', "decks")
        .required()
        .action((x, c) => c.copy(numDecks = x))
        .validate(x => if (x < 0) failure("num decks cannot be < 0 ") else success)
        .text("enter the number of decks"),
      opt[Card.MatchCondition]('m', "match-by")
        .required()
        .action((x, c) => c.copy(matchCondition = x))
        .validate {
          case Unknown(n) => failure(s"Unknown match condition $n, use one of: [${MatchCondition.values.map(_.name).mkString(", ")}]")
          case _ => success
        }
        .text("Match by either 'value', 'suite', 'both'"),
      help("help").text("usage information"),
    )
  }

  OParser.parse(parser, args, SnapGameConfig()) match {
    case Some(config) =>
      val players = List(Player("A"), Player("B"))
      val snapGame = new SnapGame(config)
      val firstToShoutSnap = (p: Seq[Player]) => Random.shuffle(p).head //random
      val (winner, pending) = snapGame.simulate(players, firstToShoutSnap)

      winner match {
        case Some(p) =>
          Console.out.println(s"Player ${p.name} wins !, ${players.mkString(", ")}, remaining on table: ${pending}")
        case None =>
          Console.out.println(s"Its a tie !, ${players.mkString(", ")}")
      }
    case None =>
      Console.err.println(s"Error parsing input args: ${args.mkString(", ")}")
      sys.exit(1)
  }
}
