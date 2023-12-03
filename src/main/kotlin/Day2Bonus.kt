import java.io.File
import java.math.BigDecimal
import kotlin.math.max

fun main(args: Array<String>) {
    var result = BigDecimal(0)

    File("src/main/resources/input").readLines().forEachIndexed { index, line ->
        val sets = line // "Game 1: 3 blue, 2 red; 1 red, 2 green, 6 blue"
            .split(":")[1] // "3 blue, 2 red; 1 red, 2 green, 6 blue"
            .split(";") // [" 3 blue, 2 red", " 1 red, 2 green, 6 blue"]
            .map { set ->
                set // " 3 blue, 2 red"
                    .split(",") // [" 3 blue", " 2 red"]
                    .map {
                        Pair(
                            first = it.filter { c -> c.isDigit() }.toInt(), // 3
                            second = it.filter { c -> c.isLetter() } // blue
                        )
                    }
                    .associateBy({ it.second }, { it.first }) // blue -> 3
            }

        val counts = mutableMapOf(
            "red" to 0,
            "green" to 0,
            "blue" to 0
        )
        sets.forEach { colors ->
            colors.forEach { color ->
                counts[color.key] = max(counts[color.key]!!, color.value)
            }
        }
        result += BigDecimal(counts.values.reduce { acc, i -> acc * i })
    }

    println(result)
}
