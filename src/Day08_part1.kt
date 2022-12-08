fun main() {
    //val input = readInput("Day08_input").map { IntArray(it.length) { i -> it[i] - '0'} }.toTypedArray()
    val lines = readInput("Day08_input")
    val rows = lines.size
    val cols = lines[0].length
    val input = Array(rows) { i -> Array(cols) { j -> lines[i][j] - '0'} }

    val visible = Array(rows) { BooleanArray(cols) }

    for (i in 0 until rows) {
        var maxSoFar = -1
        for (j in 0 until cols) {
            if (input[i][j] > maxSoFar) {
                maxSoFar = input[i][j]
                visible[i][j] = true
            }
        }
    }

    for (i in 0 until rows) {
        var maxSoFar = -1
        for (j in cols - 1 downTo 0) {
            if (input[i][j] > maxSoFar) {
                maxSoFar = input[i][j]
                visible[i][j] = true
            }
        }
    }

    for (j in 0 until cols) {
        var maxSoFar = -1
        for (i in 0 until rows) {
            if (input[i][j] > maxSoFar) {
                maxSoFar = input[i][j]
                visible[i][j] = true
            }
        }
    }

    for (j in 0 until cols) {
        var maxSoFar = -1
        for (i in rows - 1 downTo 0) {
            if (input[i][j] > maxSoFar) {
                maxSoFar = input[i][j]
                visible[i][j] = true
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