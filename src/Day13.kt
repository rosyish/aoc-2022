private data class SpecialList(val children: ArrayDeque<Any>, val parent: SpecialList?) {
    override fun toString(): String {
        val s = StringBuilder("")
        for (i in children.indices) {
            val c = children[i]
            if (c is Int) s.append(c)
            if (c is SpecialList) s.append("[").append(c).append("]")
            if (i < children.size - 1) {
                s.append(", ")
            }
        }
        return s.toString()
    }
}

fun main() {
    fun createSpecialList(str: String, current: SpecialList) {
        if (str.isNullOrBlank()) return

        if (str[0] == '[') {
            var child = SpecialList(ArrayDeque(), current)
            current.children.add(child)
            createSpecialList(str.substring(1), child)
            return
        }
        if (str[0] == ']') {
            createSpecialList(str.substring(1), current.parent!!)
            return
        }
        var nextPos = 1;
        if (str[0].isDigit()) {
            var value = str[0].digitToInt()
            if (str.length > 1 && str[1].isDigit()) {
                value = str.substring(0, 2).toInt()
                nextPos = 2
            }
            current.children.add(value)
        }

        createSpecialList(str.substring(nextPos), current)
    }

    fun checkRightness(left: SpecialList, right: SpecialList, leftIndex: Int, rightIndex: Int): Int {
        // println("checkRightness")
        // println(left)
        // println(right)
        var l = left.children.getOrNull(leftIndex)
        var r = right.children.getOrNull(rightIndex)
        if (l == null && r == null) {
            return 0
        }
        if (l == null) {
            // both lists empty or left emptied first
            return -1
        }
        if (r == null) {
            // left non-empty but right became empty
            return 1
        }

        if (l is Int && r is SpecialList) {
            val temp = SpecialList(ArrayDeque(), null)
            temp.children.add(l as Int)
            l = temp
        }
        if (l is SpecialList && r is Int) {
            val temp = SpecialList(ArrayDeque(), null)
            temp.children.add(r as Int)
            r = temp
        }
        if (l is SpecialList && r is SpecialList) {
            var result = checkRightness(l as SpecialList, r as SpecialList, 0, 0)
            if (result == 0) {
                result = checkRightness(left, right, leftIndex + 1, rightIndex + 1)
            }
            return result
        }

        l as Int
        r as Int
        return when {
            l < r -> -1
            l > r -> 1
            else -> {
                checkRightness(left, right, leftIndex + 1, rightIndex + 1)
            }
        }
    }

    fun solvePart1(input: List<String>) {
        val answer = input.chunked(3).mapIndexed { index, list ->
            val left = SpecialList(ArrayDeque(), null)
            val right = SpecialList(ArrayDeque(), null)
            createSpecialList(list[0], left)
            createSpecialList(list[1], right)
            if (checkRightness(left, right, 0, 0) == -1) {
                index + 1
            } else {
                0
            }
        }.sum()
        println(answer)
    }

    fun solvePart2(input: List<String>) {
        val input = input.toMutableList()
        val comparator = { left: SpecialList, right: SpecialList -> checkRightness(left, right, 0, 0) }
        input += "[[2]]"
        input += "[[6]]"

        val sortedInput = input
            .filter { str -> !str.isNullOrBlank() }
            .map { str ->
                val value = SpecialList(ArrayDeque(), null)
                createSpecialList(str, value)
                value
            }
            .sortedWith(comparator)

        var answer = 1
        sortedInput.forEachIndexed { index, value ->
            val str = value.toString().trim()
            if (str == "[[2]]" || str == "[[6]]") {
                answer *= (index + 1)
            }
        }
        println(answer)
    }

    val input = readInput("Day13_input")
    solvePart1(input)
    solvePart2(input)
}