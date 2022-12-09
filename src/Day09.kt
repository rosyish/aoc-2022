import kotlin.math.abs

private val moves = mapOf("R" to Pair(1, 0), "L" to Pair(-1, 0), "U" to Pair(0, 1), "D" to Pair(0, -1))

private data class Point(var x: Int, var y: Int) {
    fun move(direction: String, steps: Int) {
        x += steps * moves[direction]!!.first
        y += steps * moves[direction]!!.second
    }

    fun isFar(other: Point): Boolean {
        return abs(x-other.x) > 1 || abs(y-other.y) > 1
    }

    operator fun minus(other: Point): Point {
        return Point(x-other.x, y-other.y)
    }
}

fun main() {
    val knotsSize = 2
    val knots = buildList { for (i in 1..knotsSize) add(Point(0,0)) }

    // Having a set with data class of var types is a bad idea. To workaround this,
    // I am doing point.copy() before inserting
    val visited: MutableSet<Point> = mutableSetOf()
    visited.add(Point(0, 0).copy())

    val input = readInput("Day09_input")
    input.map { it.split(" ").let { l -> Pair(l[0], l[1].toInt()) } }.forEach { it ->
        val direction = it.first
        val steps = it.second
        for (i in 1..steps) {
            knots.first().move(direction, 1)
            knots.zipWithNext().forEach { pairOfKnots ->
                val head = pairOfKnots.first
                val tail = pairOfKnots.second
                if (tail.isFar(head)) {
                    val d = head - tail;
                    when {
                        tail.x == head.x -> tail.y += ((d.y) / abs(d.y))
                        tail.y == head.y -> tail.x += ((d.x) / abs(d.x))
                        else -> {
                            tail.x += ((d.x) / abs(d.x))
                            tail.y += ((d.y) / abs(d.y))
                        }
                    }
                }
            }
            visited.add(knots.last().copy())
        }
    }
    println(visited.size)
}
