import java.io.File
import java.util.function.Predicate

fun main(args: Array<String>) {
    val cardStrengths = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

    val hasHandOfLevel = listOf<Predicate<List<Int>>>(
        Predicate { frequencies -> frequencies.any { it == 5 } }, // Five of a kind
        Predicate { frequencies -> frequencies.any { it == 4 } }, // Four of a kind
        Predicate { frequencies -> frequencies.any { it == 3 } && frequencies.any { it == 2 } }, // Full house
        Predicate { frequencies -> frequencies.any { it == 3 } && frequencies.any { it == 1 } }, // Three of a kind
        Predicate { frequencies -> frequencies.count { it == 2 } == 2 }, // Two pair
        Predicate { frequencies -> frequencies.count { it == 2 } == 1 }, // One pair
        Predicate { frequencies -> frequencies.count { it == 1 } == 5 }, // High card
        Predicate { true }, // Fallback
    )

    File("src/main/resources/input")
        .readLines()
        .map { line ->
            val (cards, bid) = line.split(" ")
            Pair(cards, bid.toLong())
        }
        .map { (cards, bid) ->
            val cardFrequencies = cards.groupBy { it }.values.map { it.size }
            val handLevel = hasHandOfLevel.indexOfFirst { it.test(cardFrequencies) }

            val strengths = cards.map { cardStrengths.indexOf(it) }

            Triple(strengths, handLevel, bid)
        }
        .sortedWith { (cardStrengths1, handLevel1, _), (cardStrengths2, handLevel2, _) ->
            handLevel2.compareTo(handLevel1)
                .takeIf { it != 0 }
                ?: cardStrengths1.zip(cardStrengths2).firstNotNullOf { (cardStrength1, cardStrength2) ->
                    cardStrength2.compareTo(cardStrength1).takeIf { it != 0 }
                }
        }
        .mapIndexed { playerRank, (_, _, bid) ->  bid * (playerRank + 1) }
        .sum()
        .also { println(it) }
}
