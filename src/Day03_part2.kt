fun main() {
    fun getElementPriority(ch: Char): Int {
        return when (ch) {
            in 'a'..'z' -> 1 + (ch - 'a')
            in 'A'..'Z' -> 27 + (ch - 'A')
            else -> throw IllegalArgumentException("Character not a letter")
        }
    }

    fun findCommonElement(input: List<String>): Char {
        val commonItems =
            input
                .map { str -> str.toSet() }
                .reduce() { a, b -> a.intersect(b) }

        return if (commonItems.size == 1) commonItems.first()
        else throw IllegalArgumentException("Sets of 3 have more than a single common item")
    }

    val result =
        readInput("Day03_input")
            .asSequence()
            .windowed(size = 3, step = 3) {
                getElementPriority(findCommonElement(it))
            }.sum()

    println(result)
}

