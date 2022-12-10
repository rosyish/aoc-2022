fun main() {
    fun isInteresting(cycle : Int) : Boolean {
        return ((cycle - 20) % 40) == 0 && cycle < 221
    }

    val input = readInput("Day10_input")
    var currentCycle = 1
    var currentX = 1
    val interestingX = mutableListOf<Int>()
    run label@ {
        input.forEach {
            when(it) {
                "noop" -> {
                    currentCycle++
                }
                else -> {
                    if (isInteresting(currentCycle + 1)) {
                        println("cycle = $currentCycle, x=$currentX")
                        interestingX.add((currentCycle + 1) * currentX)
                    }

                    val v = it.split(" ")[1].toInt()
                    currentX += v
                    currentCycle += 2
                }
            }
            if (isInteresting(currentCycle)) {
                println("cycle = $currentCycle, x=$currentX")
                interestingX.add(currentCycle * currentX)
            }
            if (currentCycle > 220) {
                return@label
            }
        }
    }
    println(interestingX.sum())
}