import java.io.File
import java.math.BigDecimal

fun main(args: Array<String>) {
    File("src/main/resources/input").readLines().sumOf { line ->
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

        return@sumOf if (rightGuesses >= 1) 1L shl (rightGuesses - 1) else 0L
    }.let { println(it) }
}
