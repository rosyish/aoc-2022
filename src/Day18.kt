import kotlin.math.abs

fun main() {
    fun adjacent(a: Triple<Int, Int, Int>, b: Triple<Int, Int, Int>): Boolean {
        val dx = abs(a.first - b.first)
        val dy = abs(a.second - b.second)
        val dz = abs(a.third - b.third)
        return (dx+dy+dz) == 1
    }

    val input = readInput("Day18_input")
    val cubes = mutableListOf<Triple<Int, Int, Int>>()

    input.map {
        val (x, y, z) = it.split(",").map { it.toInt() }
        cubes.add(Triple(x, y, z))
    }
    val minDim = cubes.flatMap { listOf(it.first, it.second, it.third) }.min()
    val maxDim = cubes.flatMap { listOf(it.first, it.second, it.third) }.max()

    // Part 1
    var adj = 0
    for (i in 0 until cubes.size) {
        for (j in 0 until cubes.size) {
            if (i != j) {
                if(adjacent(cubes[i], cubes[j])) {
                    adj++
                }
            }
        }
    }
    println(cubes.size * 6 - adj)

    // Part 2
    fun outsideBounds(cube: Triple<Int, Int, Int>): Boolean {
        return cube.toList().firstOrNull { it < minDim - 1 || it > maxDim + 1} != null
    }

    val visited = mutableSetOf<Triple<Int, Int, Int>>()
    fun bfs(startCube: Triple<Int, Int, Int>) {
        val queue = ArrayDeque<Triple<Int,Int, Int>>()
        queue.add(startCube)
        while (queue.isNotEmpty()) {
            val airCube = queue.removeFirst()
            if (outsideBounds(airCube)) {
                continue
            }
            if (visited.contains(airCube)) {
                continue
            }
            if (cubes.contains(airCube)) {
                continue
            }
            visited.add(airCube)
            for (dx in -1..1) {
                for (dy in -1..1) {
                    for (dz in -1..1) {
                        val newAirCube =
                            Triple(airCube.first + dx, airCube.second + dy, airCube.third + dz)
                        if (adjacent(airCube, newAirCube) && !visited.contains(newAirCube)) {
                            queue += newAirCube
                        }
                    }
                }
            }
        }
    }

    // DFS stack overflows, so do BFS
    bfs(Triple(minDim - 1, minDim - 1, minDim - 1))

    var result = 0
    for (cube in cubes) {
        for (air in visited) {
            if (adjacent(cube, air)) result++
        }
    }
    println(result)
}