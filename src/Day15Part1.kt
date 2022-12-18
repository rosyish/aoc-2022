import kotlin.math.abs
import kotlin.math.max

class Day15Part1 {
    companion object {
        fun main() {
            val regex = Regex("-?\\d+")
            val input = readInput("Day15_input")
            val y = 2000000
            val sensors = input.asSequence()
                .mapNotNull { regex.findAll(it).toList() }
                .map { match ->
                    val (sensorPt, beaconPt) =
                        match
                            .map { it.groupValues.first().toInt() }
                            .chunked(2)
                            .map { Pair(it[0], it[1]) }
                    Sensor(sensorPt, beaconPt)
                }
                .toList()

            val result =
                sensors
                    .mapNotNull { sensor -> sensor.getNonDistressSegment(y) }
                    .sortedWith { p1, p2 ->
                        if (p1.first != p2.first)
                            p1.first - p2.first
                        else p1.second - p2.second
                    }
                    .fold(ArrayDeque<Pair<Int, Int>>()) { merged, interval ->
                        val last = merged.lastOrNull()
                        if (last != null && interval.first in last.first..last.second) {
                            merged.removeLast()
                            merged.add(Pair(last.first, max(last.second, interval.second)))
                        } else {
                            merged.add(interval)
                        }
                        merged
                    }
                    .sumOf { it.second - it.first + 1 }
                    .minus(
                        sensors
                            .map { sensor -> sensor.nearestBeacon }
                            .filter { beacon -> beacon.second == y }
                            .toSet().size)
            println(result)
        }
    }

    private data class Sensor(val position: Pair<Int, Int>, val nearestBeacon: Pair<Int, Int>) {
        fun getNonDistressSegment(y: Int): Pair<Int, Int>? {
            val distance = abs(position.first - nearestBeacon.first) + abs(position.second - nearestBeacon.second)
            val dx = distance - abs(y - position.second)
            if (dx < 0) return null
            return Pair(position.first - dx, position.first + dx)
        }
    }
}

fun main() {
    Day15Part1.main()
}