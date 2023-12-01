import java.io.File
import java.math.BigDecimal


fun main(args: Array<String>) {
    val digits = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )
    val reversedDigits = digits.mapKeys { it.key.reversed() }

    val firstDigitRegex = Regex("(\\d|${digits.keys.joinToString("|")})")
    val lastDigitRegex = Regex("(\\d|${reversedDigits.keys.joinToString("|")})")

    var result = BigDecimal(0)
    File("src/main/resources/input").forEachLine { line ->
        val firstDigit = firstDigitRegex
            .find(line)
            ?.let { if (digits.containsKey(it.value)) digits[it.value] else it.value }

        val lastDigit = lastDigitRegex
            .find(line.reversed())
            ?.let { if (reversedDigits.containsKey(it.value)) reversedDigits[it.value] else it.value }

        result += BigDecimal("${firstDigit}${lastDigit}")
    }

    println(result)
}