fun main() {
    fun part1(input: List<Int>): Int {
        return input.max()
    }

    fun part2(input: List<Int>): Int {
        return input.sortedDescending().subList(0, 3).sum()
    }

    fun computeCaloriesForElves(input: List<String>): List<Int> {
        var curVal = 0
        val output = mutableListOf<Int>()
        for (row in input) {
            val rowAsInt = row.toIntOrNull()
            if (rowAsInt != null) {
                curVal += rowAsInt
            } else {
                output.add(curVal)
                curVal = 0
            }
        }
        output.add(curVal)
        return output
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testCalories = computeCaloriesForElves(testInput)
    check(part1(testCalories) == 6000)
    check(part2(testCalories) == 10000)

    val input = readInput("Day01_input")
    val inputCalories = computeCaloriesForElves(input)
    println(part1(inputCalories))
    println(part2(inputCalories))
}
