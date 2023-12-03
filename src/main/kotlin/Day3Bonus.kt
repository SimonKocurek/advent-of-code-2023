import java.io.File
import java.math.BigDecimal

private data class Index(val y: Int, val x: Int)

private val directions = arrayOf(
    arrayOf(1, -1),
    arrayOf(1, 0),
    arrayOf(1, 1),
    arrayOf(0, 1),
    arrayOf(-1, 1),
    arrayOf(-1, 0),
    arrayOf(-1, -1),
    arrayOf(0, -1),
)

fun main(args: Array<String>) {
    val engine = File("src/main/resources/input").readLines().map { it.toCharArray() }

    val gears = mutableMapOf<Index, MutableList<Int>>()

    var currentNumber = 0
    val currentNumberGears = mutableSetOf<Index>()
    for (y in engine.indices) {
        for (x in engine[y].indices) {
            val c = engine[y][x]

            if (c.isDigit()) {
                currentNumber = currentNumber * 10 + c.digitToInt()

                for (direction in directions) {
                    val newY = y + direction[0]
                    val newX = x + direction[1]

                    if (newY >= 0 && newY < engine.size &&
                        newX >= 0 && newX < engine[newY].size &&
                        engine[newY][newX] == '*'
                    ) {
                        currentNumberGears.add(Index(newY, newX))
                    }
                }

            } else {
                currentNumberGears.forEach {
                    gears.putIfAbsent(it, mutableListOf())
                    gears[it]!!.add(currentNumber)
                }

                currentNumber = 0
                currentNumberGears.clear()
            }
        }

        currentNumberGears.forEach {
            gears.putIfAbsent(it, mutableListOf())
            gears[it]!!.add(currentNumber)
        }

        currentNumber = 0
        currentNumberGears.clear()
    }

    val result = gears
        .filter { it.value.size == 2 }
        .values
        .sumOf { it.reduce { acc, i -> acc * i }.toLong() }

    println(result)
}
