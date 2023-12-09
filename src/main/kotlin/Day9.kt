import java.io.File

fun main(args: Array<String>) {
    File("src/main/resources/input")
        .readLines()
        .map { line -> line.split(" ").map { it.toLong() } }
        .sumOf { numbers ->
            val lastNumbers = mutableListOf<Long>()

            var current = numbers
            while (current.any { it != 0L }) {
                lastNumbers.add(current.last())
                current = current.zipWithNext { a, b -> b - a }
            }

            lastNumbers.sum()
        }
        .also { println(it) }
}
