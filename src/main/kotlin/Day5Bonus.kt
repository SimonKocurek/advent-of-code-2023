import java.io.File

fun main(args: Array<String>) {
    data class TransformRanges(val source: LongRange, val destination: LongRange) {
        constructor(sourceRangeStart: Long, destinationRangeStart: Long, rangeLength: Long) : this(
            source = LongRange(sourceRangeStart, sourceRangeStart + rangeLength - 1),
            destination = LongRange(destinationRangeStart, destinationRangeStart + rangeLength - 1)
        )
    }

    var lines = File("src/main/resources/input").readLines()

    var seeds = lines
        .first()
        .split(": ")[1]
        .split(" ")
        .map { it.toLong() }
        .chunked(2)
        .map { (a, b) -> LongRange(a, a + b) }
        .sortedBy { it.first }
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
            .sortedBy { it.source.first }
        lines = lines.drop(2 + mapping.size)

        transitions.putIfAbsent(from, mutableMapOf())
        transitions[from]!![to] = mapping
    }

    var state = "seed"
    while (state != "location") {
        val (to, stateTransitions) = transitions[state]!!.entries.first()

        val newSeedRanges = mutableListOf<LongRange>()
        for (seedRange in seeds) {
            newSeedRanges.add(seedRange)

            for (stateTransition in stateTransitions) {
                // Entire inside does not need splitting

                // Entire outside
                if (newSeedRanges.last().first <= stateTransition.source.first && newSeedRanges.last().last >= stateTransition.source.last) {
                    val popped = newSeedRanges.removeLast()
                    if (popped.first < stateTransition.source.first) {
                        newSeedRanges.add(LongRange(popped.first, stateTransition.source.first - 1))
                    }
                    newSeedRanges.add(stateTransition.source)
                    if (popped.last > stateTransition.source.last) {
                        newSeedRanges.add(LongRange(stateTransition.source.last + 1, popped.last))
                    }
                }

                // From start
                else if (newSeedRanges.last().first < stateTransition.source.first && newSeedRanges.last().last >= stateTransition.source.first) {
                    val popped = newSeedRanges.removeLast()
                    newSeedRanges.add(LongRange(popped.first, stateTransition.source.first - 1))
                    newSeedRanges.add(LongRange(stateTransition.source.first, popped.last))
                }

                // From end
                else if (newSeedRanges.last().first <= stateTransition.source.last && newSeedRanges.last().last > stateTransition.source.last) {
                    val popped = newSeedRanges.removeLast()
                    newSeedRanges.add(LongRange(popped.first, stateTransition.source.last))
                    newSeedRanges.add(LongRange(stateTransition.source.last + 1, popped.last))
                }
            }
        }

        seeds = newSeedRanges
            .map { seedRange ->
                stateTransitions
                    .firstOrNull { it.source.contains(seedRange.first) && it.source.contains(seedRange.last) }
                    ?.let {
                        val difference = it.destination.first - it.source.first
                        LongRange(
                            start = difference + seedRange.first,
                            endInclusive = difference + seedRange.last,
                        )
                    } ?: seedRange
            }

        state = to
    }

    println(seeds.minOf { it.first })
}
