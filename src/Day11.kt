private interface Item {
    fun isDivisible(divisor: Int): Boolean
}

private interface ItemCompanion {
    fun createItem(s: String): Item
    fun add(one: Item, two: Item): Item
    fun multiply(one: Item, two: Item): Item
}

private class Monkey(
    val items: MutableList<Item>,
    val divisor: Int,
    val operation: (Item, Item) -> Item,
    val leftOperand: Item?,
    val rightOperand: Item?,
    val successMonkey: Int,
    val failureMonkey: Int
) {
    override fun toString(): String {
        return "$items, $divisor, $leftOperand, $rightOperand, $successMonkey, $failureMonkey"
    }
}

private data class ItemWithMods(val mods: List<Int>) : Item {
    override fun isDivisible(divisor: Int): Boolean {
        return mods[divisor] == 0
    }

    companion object : ItemCompanion {
        override fun createItem(s: String): Item {
            val v = s.trim().toInt()
            return ItemWithMods(generateAllMods(v))
        }

        fun generateAllMods(value: Int): List<Int> {
            return (0..20).map { if (it > 0) value % it else 0 }.toList()
        }

        override fun add(one: Item, two: Item): Item {
            one as ItemWithMods
            two as ItemWithMods
            return one.mods.zip(two.mods).withIndex()
                .map { p -> if (p.index == 0) 0 else (p.value.first + p.value.second) % p.index }
                .let { mods -> ItemWithMods(mods) }
        }

        override fun multiply(one: Item, two: Item): Item {
            one as ItemWithMods
            two as ItemWithMods
            return one.mods.zip(two.mods).withIndex()
                .map { p -> if (p.index == 0) 0 else (p.value.first * p.value.second) % p.index }
                .let { mods -> ItemWithMods(mods) }
        }
    }
}

private data class SimpleItem(val value: Int) : Item {
    override fun isDivisible(divisor: Int): Boolean {
        return value % divisor == 0
    }

    companion object : ItemCompanion {
        override fun createItem(s: String): Item {
            val v = s.trim().toInt()
            return SimpleItem(v)
        }

        override fun add(one: Item, two: Item): Item {
            one as SimpleItem
            two as SimpleItem
            return SimpleItem((one.value + two.value) / 3)
        }

        override fun multiply(one: Item, two: Item): Item {
            one as SimpleItem
            two as SimpleItem
            return SimpleItem((one.value * two.value) / 3)
        }
    }
}

fun main() {
    fun solve(rounds: Int, monkeys: List<Monkey>): Long {
        val inspectionCount = Array(monkeys.size) { 0 }
        repeat(rounds) {
            for (monkey in monkeys.withIndex()) {
                val m = monkey.value
                inspectionCount[monkey.index] += m.items.size
                for (item in m.items) {
                    val left = m.leftOperand ?: item
                    val right = m.rightOperand ?: item
                    val worry = m.operation(left, right)
                    if (worry.isDivisible(m.divisor)) {
                        monkeys[m.successMonkey].items += worry
                    } else {
                        monkeys[m.failureMonkey].items += worry
                    }
                }
                m.items.clear()
            }
        }
        val sortedList = inspectionCount.toList().sortedDescending()
        return 1L * sortedList[0] * sortedList[1]
    }

    fun initMonkeys(input: List<String>, itemCompanion: ItemCompanion): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()
        input.chunked(7).forEach {
            val items = it[1].substringAfter(": ").split(",")
                .map { str -> itemCompanion.createItem(str) }
                .toMutableList()
            val divisor = it[3].substringAfter("by ").trim().toInt()
            val (left, op, right) = it[2].substringAfter("= ").split(" ")
            val successMonkey = it[4].substringAfter("monkey ").trim().toInt()
            val failureMonkey = it[5].substringAfter("monkey ").trim().toInt()

            monkeys += Monkey(
                items,
                divisor,
                if (op == "+") itemCompanion::add else itemCompanion::multiply,
                if (left == "old") null else itemCompanion.createItem(left),
                if (right == "old") null else itemCompanion.createItem(right),
                successMonkey,
                failureMonkey
            )
        }
        return monkeys
    }

    fun solvePart1(input: List<String>) {
        val rounds = 20
        println(solve(rounds, initMonkeys(input, SimpleItem.Companion)))
    }

    fun solvePart2(input: List<String>) {
        val rounds = 10000
        println(solve(rounds, initMonkeys(input, ItemWithMods.Companion)))
    }

    val input = readInput("Day11_input")
    solvePart1(input)
    solvePart2(input)
}