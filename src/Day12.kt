private data class Cell(val x: Int, val y: Int, val elevation: Char, val steps: Int)

fun main() {
    fun elevation(input: Char): Char {
        return when (input) {
            'S' -> 'a'
            'E' -> 'z'
            else -> input
        }
    }

    fun solve(input: List<String>, isStartingCell: (Char) -> Boolean) {
        val queue: ArrayDeque<Cell> = ArrayDeque()
        input.forEachIndexed { x, str ->
            str.forEachIndexed { y, ch ->
                if (isStartingCell(ch)) {
                    queue.add(Cell(x, y, elevation('a'), 0))
                }
            }
        }

        val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
        val next = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))
        while (queue.isNotEmpty()) {
            val current: Cell = queue.removeFirst()
            if (Pair(current.x, current.y) in visited) continue
            for ((dx, dy) in next) {
                val (neighborX, neighborY) = Pair(current.x + dx, current.y + dy)
                if (neighborX in input.indices && neighborY in 0 until input[0].length) {
                    val neighborElevation = elevation(input[neighborX][neighborY])
                    if (neighborElevation - current.elevation <= 1) {
                        queue.add(Cell(neighborX, neighborY, neighborElevation, current.steps + 1))
                    }
                }
            }
            visited.add(Pair(current.x, current.y))
            if (input[current.x][current.y] == 'E') {
                println(current.steps)
                break
            }
        }
    }

    val input = readInput("Day12_input")

    val isStartingCellPart1: (Char) -> Boolean = { ch -> ch == 'S' }
    val isStartingCellPart2: (Char) -> Boolean = { ch -> ch == 'S' || ch == 'a' }

    solve(input, isStartingCellPart1)
    solve(input, isStartingCellPart2)
}