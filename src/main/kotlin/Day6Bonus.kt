import java.io.File

fun main(args: Array<String>) {
    val (time, distance) = File("src/main/resources/input")
        .readLines()
        .map { line ->
            line
                .split(":")[1]
                .replace(Regex("\\s+"), "")
                .toLong()
        }

    LongRange(1, time).count { waited ->
        (time - waited) * waited > distance // Modern CPUs are fast!
    }.also { println(it) }
}
