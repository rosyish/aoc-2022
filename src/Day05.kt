fun simulatePart1(stacks: List<MutableList<Char>>, n: Int, from: Int, to: Int): Unit {
    for (i in 1..n) {
        if (!stacks[from - 1].isEmpty()) stacks[to - 1].add(stacks[from - 1].removeLast())
    }
}

fun simulatePart2(stacks: List<MutableList<Char>>, n: Int, from: Int, to: Int): Unit {
    stacks[to - 1].addAll(stacks[from - 1].takeLast(n))
    for (i in 1..n) {
        stacks[from - 1].removeLast()
    }
}

fun main() {
    val allLines = readInput("Day05_input")
    val (inputStacks, moves) = allLines.partition {
        it.contains('[')
    }
    val numOfStacks = moves[0].trim().split(" ").last().toInt()
    val stacks = inputStacks.foldRight(List(numOfStacks) { mutableListOf<Char>() }) { str, acc ->
        str.forEachIndexed { index, ch ->
            if (ch.isLetter()) {
                acc[moves[0][index].toString().toInt() - 1].add(ch);
            }
        }
        acc
    }

    val pat = Regex("^move (\\d+) from (\\d+) to (\\d+)$")
    moves
        .drop(2)
        .forEach {
            val (n, from, to) = pat
                .matchEntire(it)!!
                .groupValues
                .drop(1)
                .map { it.toInt() }
            simulatePart2(stacks, n, from, to)
        }
    println(stacks.map { it.last() }.joinToString(separator = ""))
}