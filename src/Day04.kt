fun fullyContains(x: IntRange, y: IntRange): Boolean {
    return x.all { y.contains(it) } || y.all { x.contains(it) }
}

fun overlaps(x: IntRange, y: IntRange): Boolean {
    return x.intersect(y).isNotEmpty()
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
    val part1Result =  input.count { fullyContains(it[0], it[1]) }
    val part2Result = input.count { overlaps(it[0], it[1]) }
    println(part1Result)
    println(part2Result)
}