import java.io.File
import kotlin.math.min

fun main(args: Array<String>) {
    val lines = File("src/main/resources/input").readLines()
    val cardCounts = lines.map { 1L }.toMutableList()

    lines.forEachIndexed { index, line ->
        val (winningNumbers, stakedNumbers) = line
            .split(":")[1]
            .split("|")
            .map { numberSet ->
                numberSet
                    .trim()
                    .split(Regex("\\s+"))
                    .map { it.toInt() }
                    .toSet()
            }

        val rightGuesses = winningNumbers.intersect(stakedNumbers).size
        for (i in index + 1 until min(lines.size, index + rightGuesses + 1)) {
            cardCounts[i] += cardCounts[index]
        }
    }

    println(cardCounts.sum())
}
