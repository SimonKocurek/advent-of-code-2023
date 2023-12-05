import java.io.File

fun main(args: Array<String>) {
    data class TransformRanges(val source: LongRange, val destination: LongRange) {
        constructor(sourceRangeStart: Long, destinationRangeStart: Long, rangeLength: Long) : this(
            source = LongRange(sourceRangeStart, sourceRangeStart + rangeLength - 1),
            destination = LongRange(destinationRangeStart, destinationRangeStart + rangeLength - 1)
        )
    }

    var lines = File("src/main/resources/input").readLines()

    val seeds = lines
        .first()
        .split(": ")[1]
        .split(" ")
        .map { it.toLong() }
    lines = lines.drop(2)

    val transitions = mutableMapOf<String, MutableMap<String, List<TransformRanges>>>()
    while (lines.isNotEmpty()) {
        val (from, _, to) = lines
            .first()
            .split(" ")[0]
            .split("-")

        val mapping = lines
            .drop(1)
            .takeWhile { it.isNotBlank() }
            .map { line ->
                val (destination, source, size) = line.split(" ").map { it.toLong() }
                TransformRanges(source, destination, size)
            }
        lines = lines.drop(2 + mapping.size)

        transitions.putIfAbsent(from, mutableMapOf())
        transitions[from]!![to] = mapping
    }

    seeds.minOf {
        var state = "seed"
        var value = it

        while (state != "location") {

            transitions[state]!!.forEach { (to, ranges) ->
                value = ranges
                    .filter { it.source.contains(value) }
                    .minOfOrNull { it.destination.first - it.source.first + value }
                    ?: value

                state = to
            }
        }

        value
    }.also { println(it) }
}
