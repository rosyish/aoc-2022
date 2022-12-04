fun fullyContains(x: IntRange, y: IntRange): Boolean {
    return x.all { y.contains(it) } || y.all { x.contains(it) }
}

fun overlaps(x: IntRange, y: IntRange): Boolean {
    return x.intersect(y).isNotEmpty()
}

fun main() {
    fun fullyContain(str: String) {
        // TODO: Implement without regex
    }

    fun doRangeCheck(str: String, rangeCheck: (IntRange, IntRange) -> Boolean): Boolean {
        val values = Regex("(\\d+)-(\\d+),(\\d+)-(\\d+)")
            .matchEntire(str)!!
            .groupValues
            .drop(1)
            .map { it.toInt() }
        val range1 = IntRange(values[0], values[1])
        val range2 = IntRange(values[2], values[3])
        return rangeCheck(range1, range2)
    }

    val input = readInput("Day04_input")
    val part1Result =  input.count { doRangeCheck(it, ::fullyContains) }
    val part2Result = input.count { doRangeCheck(it, ::overlaps) }
    println(part1Result)
    println(part2Result)
}