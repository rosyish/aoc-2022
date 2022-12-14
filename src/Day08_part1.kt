fun main() {
    val lines = readInput("Day08_input")
    val rows = lines.size
    val cols = lines[0].length
    val input = Array(rows ) { i -> Array(cols) { j -> lines[i][j] - '0' } }
    val visible = Array(rows) { BooleanArray(cols) }

    val outerBounds = listOf(0 until rows, 0 until rows, 0 until cols, 0 until cols)
    val innerBounds = listOf(0 until cols, cols-1 downTo 0 , 0 until rows, rows - 1 downTo 0)
    val rowFirst = listOf(true, true, false, false)

    outerBounds.zip(innerBounds).zip(rowFirst).forEach {
        val outer = it.first.first
        val inner = it.first.second
        val rowFirst = it.second
        for (i in outer) {
            var maxSoFar = -1
            for (j in inner) {
                val r = if (rowFirst) i else j
                var c = if (rowFirst) j else i
                if (input[r][c] > maxSoFar) {
                    maxSoFar = input[r][c]
                    visible[r][c] = true
                }
            }
        }
    }

    var count = 0;
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (visible[i][j]) count++
        }
    }
    println(count)
}