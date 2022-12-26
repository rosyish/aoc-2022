import kotlin.math.abs

fun main() {
    fun Char.snafuCharToInt(): Int {
        return when (this) {
            '2' -> 2
            '1' -> 1
            '0' -> 0
            '-' -> -1
            '=' -> -2
            else -> throw IllegalArgumentException("$this is not a snafu char")
        }
    }

    fun Int.snafuIntToChar(): Char {
        return when (this) {
            2 -> '2'
            1 -> '1'
            0 -> '0'
            -2 -> '='
            -1 -> '-'
            else -> throw IllegalArgumentException("$this is not a snafu int")
        }
    }

    fun snafuStrToDecimal(str: String): Long {
        var num = 0L
        var multiplier = 1L
        for (i in str.indices.reversed()) {
            num += multiplier * str[i].snafuCharToInt()
            multiplier *= 5L
        }
        return num
    }

    fun decimalToSnafuStr(num: Long): String {
        var p = 1L
        var sumOfPowers = 0L
        while (p < (abs(num)) / 2) {
            sumOfPowers += p
            p *= 5
        }
        var n = num
        var snafuStr = StringBuilder()
        while (p > 0) {
            var next = 0
            var absoluteN = abs(n)
            while (next < 2 && 2 * sumOfPowers < absoluteN) {
                next++
                absoluteN -= p
            }
            if (n > 0) {
                n -= (next * p)
            } else {
                n += (next * p)
                next = -next
            }
            snafuStr.append(next.snafuIntToChar())
            p /= 5
            sumOfPowers -= p
        }
        return snafuStr.toString()
    }

    readInput("Day25_input")
        .sumOf { str -> snafuStrToDecimal(str) }
        .also { println("Decimal value $it") }
        .let { num -> decimalToSnafuStr(num) }
        .also { println("Answer: $it") }
        .also { println("Snafu string back to decimal ${snafuStrToDecimal(it)}") }
}