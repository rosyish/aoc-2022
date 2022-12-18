typealias Cube = Triple<Int, Int, Int>
fun main() {
    val cubes = readInput("Day18_input").map {
        val (x, y, z) = it.split(",").map { it.toInt() }
        Cube(x, y, z)
    }.toSet()
    val minDim = cubes.flatMap { it.toList() }.min()
    val maxDim = cubes.flatMap { it.toList() }.max()

    fun Cube.getAdjacent(): List<Cube> {
        return listOf(
            Cube(0, 0, 1),
            Cube(0, 0, -1),
            Cube(0, 1, 0),
            Cube(0, -1, 0),
            Cube(-1, 0, 0),
            Cube(1, 0, 0))
        .map { Cube(it.first + this.first, it.second + this.second, it.third + this.third) }
        .toList()
    }

    // Part 1
    var adj = 0
    for (cube in cubes) {
        for (adjCube in cube.getAdjacent()) {
            if (cubes.contains(adjCube)) {
                adj++
            }
        }
    }
    println(cubes.size * 6 - adj)

    // Part 2
    fun outsideBounds(cube: Cube): Boolean {
        return cube.toList().firstOrNull { it < minDim - 1 || it > maxDim + 1 } != null
    }

    val visited = mutableSetOf<Cube>()
    fun bfs(startCube: Cube) {
        val queue = ArrayDeque<Cube>()
        queue.add(startCube)
        while (queue.isNotEmpty()) {
            val airCube = queue.removeFirst()
            if (visited.contains(airCube)) {
                continue
            }
            visited.add(airCube)
            for (newAirCube in airCube.getAdjacent()) {
                if (!cubes.contains(newAirCube)
                    && !visited.contains(newAirCube)
                    && !outsideBounds(newAirCube)
                ) {
                    queue += newAirCube
                }
            }
        }
    }

    // recursion stack overflows, so do BFS
    bfs(Cube(minDim - 1, minDim - 1, minDim - 1))

    var result = 0
    for (cube in cubes) {
        for (adjCube in cube.getAdjacent()) {
            if (visited.contains(adjCube)) result++
        }
    }
    println(result)
}