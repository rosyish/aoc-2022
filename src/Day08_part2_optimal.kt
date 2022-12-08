fun main() {
    val lines = readInput("Day08_input")
    val rows = lines.size
    val cols = lines[0].length
    val input = Array(rows) { i -> Array(cols) { j -> lines[i][j] - '0' } }
    val visible = Array(rows) { Array(cols) { 1 } }

    val outerBounds = listOf(0 until rows, 0 until rows, 0 until cols, 0 until cols)
    val innerBounds = listOf(0 until cols, cols-1 downTo 0 , 0 until rows, rows - 1 downTo 0)
    val rowFirst = listOf(true, true, false, false)

    outerBounds.zip(innerBounds).zip(rowFirst).forEach {
        val outer = it.first.first
        val inner = it.first.second
        val rowFirst = it.second
        for (i in outer) {
            val stack : ArrayDeque<Pair<Int, Int>> = ArrayDeque(0)
            for (j in inner) {
                val r = if (rowFirst) i else j
                var c = if (rowFirst) j else i
                var popped = 0
                while (stack.isNotEmpty() && stack.last().first < input[r][c]) {
                    popped += 1 + stack.last().second
                    stack.removeLast()
                }
                if (stack.isNotEmpty()) {
                    visible[r][c] *= (popped + 1)
                } else {
                    visible[r][c] *= popped
                }
                stack.add(Pair(input[r][c], popped))
            }
        }
    }

    println(visible.flatten().max())
}