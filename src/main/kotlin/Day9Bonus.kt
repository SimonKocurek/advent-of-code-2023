import java.io.File

fun main(args: Array<String>) {
    File("src/main/resources/input")
        .readLines()
        .map { line -> line.split(" ").map { it.toLong() } }
        .map { numbers ->
            val firstNumbers = mutableListOf<Long>()

            var current = numbers
            while (current.any { it != 0L }) {
                firstNumbers.add(current.first())
                current = current.zipWithNext { a, b -> b - a }
            }

            firstNumbers
        }
        .sumOf { firstNumbers ->
            var result = firstNumbers.last()

            for (i in firstNumbers.size - 2 downTo 0) {
                result = firstNumbers[i] - result
            }

            result
        }
        .also { println(it) }
}
