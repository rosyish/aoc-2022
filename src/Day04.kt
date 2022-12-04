fun fullyContains(x: IntRange, y: IntRange): Boolean {
    return x.all { y.contains(it) } || y.all { x.contains(it) }
}

fun overlaps(x: IntRange, y: IntRange): Boolean {
    return x.intersect(y).isNotEmpty()
}

fun efficientFullyContains(x: IntRange, y: IntRange): Boolean {
    return (x.first <= y.first && x.last >= y.last) || (y.first <= x.first && y.last >= x.last)
}

fun efficientOverlaps(x: IntRange, y: IntRange): Boolean {
    return x.contains(y.first) || y.contains(x.first)
}

fun main() {
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
            .map { mapToRanges(it) }
            .count { (range1, range2) -> efficientFullyContains(range1, range2) }
    val part2Result =
        input
            .map { mapToRanges(it) }
            .count { (range1, range2) -> efficientOverlaps(range1, range2) }
    println(part1Result)
    println(part2Result)
}