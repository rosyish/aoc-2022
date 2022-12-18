import kotlin.math.max
import kotlin.system.measureTimeMillis

fun main() {
    val valves = readInput("Day16_input").mapIndexed { index, str ->
        val parts = str.split(" ")
        val name = parts[1]
        val flowRate = parts[4].split("=")[1].dropLast(1).toInt()
        val neighbors = parts.takeLast(parts.size - 9).map { it.trim { ch -> !ch.isLetter() } }
        name to Valve(index, flowRate, neighbors)
    }.toMap()

    val maximumForState = mutableMapOf<VolcanoScanState, Int>()
    fun calculateMaxStartingAtState(state: VolcanoScanState): Int {
        //  println(state)
        if (maximumForState.contains(state)) {
            return maximumForState[state]!!
        }
        if (state.timeLeft == 0) {
            maximumForState[state] = 0
            return maximumForState[state]!!
        }

        val currentValve = valves[state.currentValve]!!
        var maxValue = 0

        // If valve not opened and has some flow, try opening
        if (currentValve.flowRate > 0 && (state.openedValuesBitMask and (1L shl currentValve.index)) == 0L) {
            maxValue = max(
                maxValue,
                (currentValve.flowRate * (state.timeLeft - 1))
                        + calculateMaxStartingAtState(
                    VolcanoScanState(
                        state.currentValve,
                        state.openedValuesBitMask or (1L shl currentValve.index),
                        state.timeLeft - 1
                    )
                )
            )
        }

        // Go and visit valves
        for (n in currentValve.neighbors) {
            maxValue = max(
                maxValue,
                calculateMaxStartingAtState(VolcanoScanState(n, state.openedValuesBitMask, state.timeLeft - 1))
            )
        }

        maximumForState[state] = maxValue
        return maximumForState[state]!!
    }

    val maximumForEndingState = mutableMapOf<VolcanoScanState, Int>()
    fun calculateMaxEndingInState(targetState: VolcanoScanState): Int {
        //  println(state)
        if (maximumForEndingState.contains(targetState)) {
            return maximumForEndingState[targetState]!!
        }

        if (targetState.timeLeft == 0 || targetState.openedValuesBitMask == 0L) {
            maximumForEndingState[targetState] = 0
            return maximumForEndingState[targetState]!!
        }

        val currentValve = valves[targetState.currentValve]!!
        var maxValue = 0

        // If valve not opened and has some flow, try opening
        if ((targetState.openedValuesBitMask and (1L shl currentValve.index)) != 0L) {
            val maxStateIfCurrentOpened =
                calculateMaxEndingInState(
                    VolcanoScanState(
                        targetState.currentValve,
                        targetState.openedValuesBitMask xor (1L shl currentValve.index),
                        targetState.timeLeft - 1
                    )
                )
            maxValue = max(maxValue, currentValve.flowRate * (targetState.timeLeft - 1) + maxStateIfCurrentOpened)
        }

        // Go and visit valves
        for (n in currentValve.neighbors) {
            maxValue = max(
                maxValue,
                calculateMaxEndingInState(
                    VolcanoScanState(
                        n,
                        targetState.openedValuesBitMask,
                        targetState.timeLeft - 1
                    )
                )
            )
        }

        maximumForEndingState[targetState] = maxValue
        return maxValue
    }

    val part1 = calculateMaxStartingAtState(VolcanoScanState("AA", 0, 30))
    println("part1: $part1")

    // Part 2: The idea is that the set of valves opened by you and elephant will not intersect.
    // TODO: This takes ~50 seconds to run, optimize away the zero flow nodes by computing shortest paths
    //  between every two nodes
    val nonZeroValves = valves.filter { entry -> entry.value.flowRate > 0 }.map { entry -> entry.value.index }.toList()
    val masks = mutableMapOf<Int, Long>()
    for (i in 0 until (1 shl nonZeroValves.size)) {
        masks[i] = nonZeroValves.mapIndexed { index, value -> if ((i and (1 shl index)) != 0) (1L shl value) else 0L }
            .reduce { a, b -> a or b }
    }
    var maxValue = 0
    val timeToRun = measureTimeMillis {
        for (i in ((1 shl nonZeroValves.size) - 1) downTo 0) {
            val j = i xor ((1 shl nonZeroValves.size) - 1)
            val vali = calculateMaxEndingInState(VolcanoScanState("AA", masks[i]!!, 26))
            val valj = calculateMaxEndingInState(VolcanoScanState("AA", masks[j]!!, 26))
            maxValue = max(maxValue, vali + valj)
        }
    }
    println("part2: $maxValue, time: $timeToRun")
}

private data class Valve(val index: Int, val flowRate: Int, val neighbors: List<String>)

private data class VolcanoScanState(val currentValve: String, val openedValuesBitMask: Long, val timeLeft: Int) {
    override fun toString(): String {
        return "valve=$currentValve, opened=${openedValuesBitMask.toString(2)} timeLeft=$timeLeft"
    }
}