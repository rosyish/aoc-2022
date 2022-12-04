fun main() {
    fun fullyContain(str: String) {
        // TODO: Implement without regex
    }

    fun fullyContainUseRegexCaptures(str: String, rangeCheck: (IntRange, IntRange) -> Boolean): Boolean {
        val values = Regex("(\\d+)-(\\d+),(\\d+)-(\\d+)")
            .matchEntire(str)!!
            .groupValues
            .drop(1)
            .map { it.toInt() }
        val range1 = IntRange(values[0], values[1])
        val range2 = IntRange(values[2], values[3])
        return rangeCheck(range1, range2)
    }

    // TODO: How to define this function at the top level and directly pass into method without creating val?
    val fullyContains = fun(x: IntRange, y: IntRange): Boolean {
        return (x.first <= y.first && x.last >= y.last) || (y.first <= x.first && y.last >= x.last)
    }

    val part1Result =
        readInput("Day04_input")
            .filter { fullyContainUseRegexCaptures(it, fullyContains) }
            .count()

    val overlaps = fun(x: IntRange, y: IntRange): Boolean {
        return fullyContains(x, y)
                || (x.first >= y.first && x.first <= y.last)
                || (x.last >= y.first && x.last <= y.last)
    }

    val part2Result =
        readInput("Day04_input")
            .filter { fullyContainUseRegexCaptures(it, overlaps) }
            .count()

    println(part1Result)
    println(part2Result)
}