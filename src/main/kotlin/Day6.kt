import java.io.File

fun main(args: Array<String>) {
    val (times, distances) = File("src/main/resources/input")
        .readLines()
        .map { line ->
            line
                .split(Regex("\\s+"))
                .drop(1)
                .map { it.toLong() }
        }

    val result = times.zip(distances)
        .map { (time, distance) ->
            LongRange(1, time).count { waited ->
                (time - waited) * waited > distance
            }
        }

    println(result.reduce { acc, c -> acc * c })
}
