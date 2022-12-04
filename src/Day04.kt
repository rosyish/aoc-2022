fun fullyContains(x: IntRange, y: IntRange): Boolean {
    return (x.first <= y.first && x.last >= y.last) || (y.first <= x.first && y.last >= x.last)

    // alternative below inefficient
    // return x.all { y.contains(it) } || y.all { x.contains(it) }
}

fun overlaps(x: IntRange, y: IntRange): Boolean {
    return x.contains(y.first) || y.contains(x.first)

    // alternative below inefficient
    // return x.intersect(y).isNotEmpty()
}

fun main() {
    // Alternative mapToRanges
    fun mapToRangesUseRegexCaptures(str : String) : List<IntRange> {
        return Regex("(\\d+)-(\\d+),(\\d+)-(\\d+)")
            .matchEntire(str)!!
            .groupValues
            .drop(1)
            .chunked(2)
            .map { (start, end) -> start.toInt().rangeTo(end.toInt()) }
    }

    fun mapToRanges(str: String): List<IntRange> {
        return str.split(",")
            .map { interval -> interval.split("-").let { it[0].toInt().rangeTo(it[1].toInt()) } }
    }

    val input = readInput("Day04_input")
    val part1Result =
        input
            .asSequence()
            .map { mapToRanges(it) }
            .count { (range1, range2) -> fullyContains(range1, range2) }
    val part2Result =
        input
            .asSequence()
            .map { mapToRanges(it) }
            .count { (range1, range2) -> overlaps(range1, range2) }
    println(part1Result)
    println(part2Result)
}