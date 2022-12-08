import kotlin.math.max

data class Score(var left: Int, var right: Int, var up: Int, var down: Int) {
    override fun toString() = "$left, $right, $up, $down"
}

fun main() {
    val input = readInput("Day08_input").map { IntArray(it.length) { i -> it[i] - '0'} }.toTypedArray()
    val rows = input.size
    val cols = input[0].size
    val scores = Array(rows) { Array(cols) { Score(0,0,0,0) } }

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            scores[i][j].left = j-0
            scores[i][j].right = cols - 1 - j
            scores[i][j].up = i - 0
            scores[i][j].down = rows - 1 - i
            for (k in j-1 downTo 0) {
                val onMyLeft = input[i][k]
                if (onMyLeft >= input[i][j]) {
                    scores[i][j].left = j - k
                    break
                }
            }
            for (k in j+1 until cols) {
                val onMyRight = input[i][k]
                if (onMyRight >= input[i][j]) {
                    scores[i][j].right = k - j
                    break
                }
            }
            for (k in i-1 downTo 0) {
                val above = input[k][j]
                if (above >= input[i][j]) {
                    scores[i][j].up = i-k
                    break
                }
            }
            for (k in i+1 until rows) {
                val below = input[k][j]
                if (below >= input[i][j]) {
                    scores[i][j].down = k-i
                    break
                }
            }
        }
    }
    var maxValue = 0;
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            val s = scores[i][j].left * scores[i][j].right * scores[i][j].up * scores[i][j].down
            maxValue = max(maxValue, s)

        }
    }
    println(maxValue)
}