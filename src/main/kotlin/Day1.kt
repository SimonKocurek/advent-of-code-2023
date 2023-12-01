import java.io.File
import java.math.BigDecimal


fun main(args: Array<String>) {
    var result = BigDecimal(0)

    File("src/main/resources/input").forEachLine { line ->
        val firstDigit = line.first { it.isDigit() }
        val lastDigit = line.last { it.isDigit() }

        result += BigDecimal("$firstDigit$lastDigit")
    }

    println(result)
}