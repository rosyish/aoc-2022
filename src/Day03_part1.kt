fun main() {
    fun findCommonElementInCompartments(str: String): Char {
        val firstHalf = str.substring(0 until str.length / 2).toSet()
        val secondHalf = str.substring(str.length / 2 until str.length).toSet()
        val common = firstHalf.intersect(secondHalf)
        if (common.size != 1) {
            throw IllegalArgumentException("Compartments do not contain exactly one common element")
        }
        return common.first()
    }

    fun getElementPriority(ch: Char): Int {
        return when (ch) {
            in 'a'..'z' -> 1 + (ch - 'a')
            in 'A'..'Z' -> 27 + (ch - 'A')
            else -> throw IllegalArgumentException("Character not a letter")
        }
    }

    val result = readInput("Day03_input").fold(0) { totalPriority, str ->
        val priority = when {
            str.isEmpty() -> 0
            str.length % 2 == 0 -> getElementPriority(findCommonElementInCompartments(str))
            else -> throw IllegalArgumentException("Invalid rucksack string")
        }
        totalPriority + priority
    }

    println(result)
}