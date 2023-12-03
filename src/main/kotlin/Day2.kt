import java.io.File

fun main(args: Array<String>) {
    var result = 0

    val limits = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )

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

        if (sets.all { colors -> colors.all { color -> color.value <= limits[color.key]!! } }) {
            result += index + 1
        }
    }

    println(result)
}
