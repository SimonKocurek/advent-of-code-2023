import java.io.File
import java.math.BigDecimal

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
    var result = BigDecimal(0)

    val engine = File("src/main/resources/input").readLines().map { it.toCharArray() }

    var currentNumber = 0
    var isPartNumber = false
    for (y in engine.indices) {
        for (x in engine[y].indices) {
            val c = engine[y][x]

            if (c.isDigit()) {
                currentNumber = currentNumber * 10 + c.digitToInt()
                isPartNumber = isPartNumber || checkIfPartNumber(y, x, engine)

            } else {
                if (isPartNumber) {
                    result += BigDecimal(currentNumber)
                }

                currentNumber = 0
                isPartNumber = false
            }
        }

        if (isPartNumber) {
            result += BigDecimal(currentNumber)
        }
        currentNumber = 0
        isPartNumber = false
    }

    println(result)
}

private fun checkIfPartNumber(y: Int, x: Int, engine: List<CharArray>): Boolean {
    for (direction in directions) {
        val newY = y + direction[0]
        val newX = x + direction[1]

        if (newY >= 0 && newY < engine.size &&
            newX >= 0 && newX < engine[newY].size &&
            engine[newY][newX].isSymbol()
        ) {
            return true
        }
    }

    return false
}

private fun Char.isSymbol() = !isDigit() && this != '.'
