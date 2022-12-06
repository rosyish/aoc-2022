fun main() {
    val input = readFirst("Day06_input")
    val windowSize = 14
    val answer =
        input.windowedSequence(windowSize).withIndex().first { it.value.toSet().size == windowSize }.index + windowSize
    println(answer)
}