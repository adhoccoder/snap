# snap

Simplistic snap game

Usage:

```sh
$ sbt "run --help"
[info] Running org.games.snap.SnapCli --help
snap 1.0
Usage: snap [options]

  -d, --decks <value>    enter the number of decks
  -m, --match-by <value> Match by either 'value', 'suite', 'both'
  --help                 usage information

Exception: sbt.TrapExitSecurityException thrown from the UncaughtExceptionHandler in thread "run-main-0"
[success] Total time: 1 s, completed

$ sbt "run --match-by suite --decks 1"
.....
Player A shouts 'Snap!' first, collects 8 cards
Player B draws QUEEN of HEARTS, cards on table 0, last draw None
Player A draws ACE of SPADES, cards on table 1, last draw QUEEN of HEARTS
Player B draws SEVEN of SPADES, cards on table 2, last draw ACE of SPADES
Player A shouts 'Snap!' first, collects 2 cards
Player A draws FIVE of HEARTS, cards on table 0, last draw None
Player B draws NINE of DIAMONDS, cards on table 1, last draw FIVE of HEARTS
Player A wins !, Player A: 27 cards, Player B: 23 cards, remaining on table: 2
[success] Total time: 1 s, completed
```

## Additional dependencies

  - [**Scopt**](https://github.com/scopt/scopt)
  - [**Scalatest**](http://www.scalatest.org/)

## Assumptions

- A round starts with players taking turns to draw cards and is considered to end when a player shouts "Snap" on two matching cards.
- All cards on the table till the end of a round will be added to the winning players victory pile.

## Things to improve.

- The player class is currently stateful as it uses an AtomicInteger to act as a counter, this can be changed by having custom 
counter/accumulators for the two players as we walk through the deck of cards in the foldLeft.
- No validation for upper bounds on input args for number of decks.
- The tests are limited and test a single match condition, we can extend that to add more users.
- Shuffle the start order for player turns.
- Add logging impl and make certain logs debug. 
