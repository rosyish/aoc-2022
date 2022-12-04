fun fullyContains(x: IntRange, y: IntRange): Boolean {
    return x.all { y.contains(it) } || y.all { x.contains(it) }
}

fun overlaps(x: IntRange, y: IntRange): Boolean {
    return x.intersect(y).isNotEmpty()
}

val efficientFullyContains = fun(x: IntRange, y: IntRange): Boolean {
    return (x.first <= y.first && x.last >= y.last) || (y.first <= x.first && y.last >= x.last)
}

val efficientOverlaps = fun(x: IntRange, y: IntRange): Boolean {
    return x.contains(y.first) || y.contains(x.first)
}

fun main() {
    fun mapToRanges(str : String) : List<IntRange> {
        return Regex("(\\d+)-(\\d+),(\\d+)-(\\d+)")
            .matchEntire(str)!!
            .groupValues
            .drop(1)
            .chunked(2)
            .map { (start, end) -> start.toInt().rangeTo(end.toInt()) }
    }

    val input = readInput("Day04_input").map(::mapToRanges)
    val part1Result =  input.count { efficientFullyContains(it[0], it[1]) }
    val part2Result = input.count { efficientOverlaps(it[0], it[1]) }
    println(part1Result)
    println(part2Result)
}