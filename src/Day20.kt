import kotlin.math.abs

fun main() {
    val multiplier = 811589153L
    val reps = 10
    val input = readInput("Day20_input").map{ it.trim().toLong() * multiplier }.toList()
    val output = input.mapIndexed{ index, value -> Pair(index, value) }.toMutableList()
    val originalPosToCurrentPos = input.indices.map { index -> index to index }.toMap().toMutableMap()

    val n = input.size
    repeat(reps) {
        for (originalIndex in input.indices) {
            val currentIndex = originalPosToCurrentPos[originalIndex]!!
            if (output[currentIndex].second == 0L) continue
            var newIndex = (currentIndex + (output[currentIndex].second % (n - 1))) % (n - 1)
            newIndex = if (newIndex <= 0) (n - 1 - abs(newIndex)) else newIndex

            //  println("moving ${output[currentIndex].second}, currentpos = $currentIndex, newPos=$newIndex")
            if ( newIndex.toInt()  != currentIndex) {
                val valueRemoved = output.removeAt(currentIndex)
                output.add(newIndex.toInt(), valueRemoved)
                for (i in output.indices) {
                    originalPosToCurrentPos[output[i].first] = i
                }
            }
            // println(output.map { it.second }.toList())
        }
    }

    val indexOfZero = originalPosToCurrentPos[output.find { it.second == 0L }!!.first]!!
    val computeIndex = { a: Int ->
        (indexOfZero + (a % n)) % n
    }
    //println(indexOfZero)
    println(listOf(1000, 2000, 3000).map { value -> 1L * output[computeIndex(value)].second }.sum())
}