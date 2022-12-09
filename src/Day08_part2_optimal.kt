fun main() {
    val lines = readInput("Day08_input")
    val rows = lines.size
    val cols = lines[0].length
    val heights = Array(rows) { i -> Array(cols) { j -> lines[i][j] - '0' } }
    val score = Array(rows) { Array(cols) { 1 } }

    val outerBounds = listOf(0 until rows, 0 until rows, 0 until cols, 0 until cols)
    val innerBounds = listOf(0 until cols, cols-1 downTo 0 , 0 until rows, rows - 1 downTo 0)
    val rowFirst = listOf(true, true, false, false)

    outerBounds.zip(innerBounds).zip(rowFirst).forEach {
        val (outer, inner) = it.first
        val rowFirst = it.second
        for (i in outer) {
            // Store tree height and the number of trees that came before strictly smaller than it
            val stack : ArrayDeque<Pair<Int, Int>> = ArrayDeque(0)
            for (j in inner) {
                val r = if (rowFirst) i else j
                var c = if (rowFirst) j else i
                var popped = 0
                while (stack.isNotEmpty() && stack.last().first < heights[r][c]) {
                    popped += 1 + stack.last().second
                    stack.removeLast()
                }
                if (stack.isNotEmpty()) {
                    score[r][c] *= (popped + 1)
                } else {
                    score[r][c] *= popped
                }
                stack.add(Pair(heights[r][c], popped))
            }
        }
    }
    println(score.flatten().max())
}