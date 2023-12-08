import java.io.File

fun main(args: Array<String>) {
    val lines = File("src/main/resources/input").readLines()

    fun <T> List<T>.repeated(): Sequence<T> = sequence { while (true) yieldAll(iterator()) }

    val directionToIndex = mapOf('L' to 0, 'R' to 1)
    val instruction = lines.first().map { directionToIndex[it]!! }.repeated().iterator()

    val transitions = lines.drop(2)
        .map { line -> line.split("=").map { it.trim() } }
        .associateBy(
            { (from, _) -> from },
            { (_, to) ->
                to
                    .removePrefix("(")
                    .removeSuffix(")")
                    .split(",")
                    .map { it.trim() }
            })

    var current = "AAA"
    var steps = 0
    while (current != "ZZZ") {
        steps += 1
        current = transitions[current]!![instruction.next()]
    }
    println(steps)
}
