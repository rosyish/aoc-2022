private class Monkey(
    val items: MutableList<Item>,
    val divisor: Int,
    val operation: (Int, Int, Int) -> Int,
    val leftOperand: Item?,
    val rightOperand: Item?,
    val successMonkey: Int,
    val failureMonkey: Int
) {
    override fun toString(): String {
        return "$items, $divisor, $leftOperand, $rightOperand, $successMonkey, $failureMonkey"
    }
}

private data class Item(val mods : List<Int>) {
    companion object {
        fun createItem(s : String) : Item {
            val v = s.trim().toInt()
            return Item(generateAllMods(v))
        }

        fun generateAllMods(value: Int): List<Int> {
            var another = mutableListOf<Int>()
            // Special case 0 so it is easy to deal with later in the code
            // Max divisor is 17 in the input file
            for (k in 0..20) {
                if (k == 0) another.add(0) else another += (value % k)
            }
            return another.toList()
        }
    }
}

// TODO: This only supports part2 at the moment. Refactor to also support part1
fun main() {
    val input = readInput("Day11_input")
    val rounds = 10000

    fun addition(a: Int, b: Int, divisor: Int): Int {
        if (divisor == 0) return 0
        return ((a % divisor) + (b % divisor)) % divisor
    }

    fun multiply(a: Int, b: Int, divisor: Int): Int {
        if (divisor == 0) return 0
        return ((a % divisor) * (b % divisor)) % divisor
    }

    val monkeys = mutableListOf<Monkey>()
    input.chunked(7).forEach {
        val items = it[1].substringAfter(": ").split(",")
            .map { str -> Item.createItem(str) }
            .toMutableList()
        val divisor = it[3].substringAfter("by ").trim().toInt()
        val (left, op, right) = it[2].substringAfter("= ").split(" ")
        val successMonkey = it[4].substringAfter("monkey ").trim().toInt()
        val failureMonkey = it[5].substringAfter("monkey ").trim().toInt()

        monkeys += Monkey(
            items,
            divisor,
            if (op == "+") ::addition else ::multiply,
            if (left == "old") null else Item.createItem(left),
            if (right == "old") null else Item.createItem(right),
            successMonkey,
            failureMonkey
        )
    }

    val inspectionCount = Array(monkeys.size) { 0 }
    repeat(rounds) {
        for (monkey in monkeys.withIndex()) {
            val m = monkey.value
            inspectionCount[monkey.index] += m.items.size
            for (item in m.items) {
                val left = m.leftOperand ?: item
                val right = m.rightOperand ?: item
                // val worry = m.operation(left, right) / 3
                val worry =
                    left.mods.zip(right.mods).withIndex()
                        .map { p -> m.operation(p.value.first, p.value.second, p.index) }
                        .let { mods -> Item(mods) }
                if (worry.mods[m.divisor] == 0) {
                    monkeys[m.successMonkey].items += worry
                } else {
                    monkeys[m.failureMonkey].items += worry
                }
            }
            m.items.clear()
        }
    }
    val sortedList = inspectionCount.toList().sortedDescending()
    println(1L * sortedList[0] * sortedList[1])
}