fun main() {
    open class Node(val name: String)
    class ValueNode(name: String, val value: Long) : Node(name)
    class ExpressionNode(
        name: String,
        val left: Node,
        val right: Node,
        val operation: (Long, Long) -> Long,
        val moveRightOperand: (Long, Long) -> Long,
        val moveLeftOperand: (Long, Long) -> Long
    ) : Node(name)

    val input = readInput("Day21_input")
    val nameToNode = input
        .map {
            val (first, second) = it.split(":")
            Pair(first, second)
        }
        .associate { pair ->
            val parts = pair.second.trim().split(' ')
            if (parts.size == 1) {
                pair.first to ValueNode(pair.first, parts[0].trim().toLong())
            } else {
                val left = Node(parts[0].trim())
                val right = Node(parts[2].trim())
                val operation: (Long, Long) -> Long = when (parts[1].first()) {
                    '+' -> { a, b -> a + b }
                    '-' -> { a, b -> a - b }
                    '*' -> { a, b -> a * b }
                    '/' -> { a, b -> a / b }
                    else -> throw IllegalArgumentException("Unexpected operator")
                }
                val moveRightOperand: (Long, Long) -> Long = when (parts[1].first()) {
                    '+' -> { a, b -> a - b }
                    '-' -> { a, b -> b + a }
                    '*' -> { a, b -> a / b }
                    '/' -> { a, b -> b * a }
                    else -> throw IllegalArgumentException("Unexpected operator")
                }
                val moveLeftOperand: (Long, Long) -> Long = when (parts[1].first()) {
                    '+' -> { a, b -> a - b }
                    '-' -> { a, b -> b - a }
                    '*' -> { a, b -> a / b }
                    '/' -> { a, b -> b / a }
                    else -> throw IllegalArgumentException("Unexpected operator")
                }
                pair.first to ExpressionNode(
                    pair.first,
                    left,
                    right,
                    operation,
                    moveRightOperand,
                    moveLeftOperand
                )
            }
        }

    fun solvePart1(current: Node): Long {
        if (current is ValueNode) return current.value
        if (current is ExpressionNode) {
            return current.operation(solvePart1(current.left), solvePart1(current.right))
        }
        return solvePart1(nameToNode[current.name]!!)
    }
    println(solvePart1(nameToNode["root"]!!))

    val nameToNodePart2 =
        nameToNode
            .filterKeys { key -> key != "humn" }
            .toMutableMap().apply { this.put("humn", Node("humn")) }

    fun evaluate(current: Node): Node {
        if (current is ValueNode) return current
        if (current is ExpressionNode) {
            val left = evaluate(current.left)
            val right = evaluate(current.right)
            return if (left is ValueNode && right is ValueNode) {
                ValueNode(current.name, current.operation(left.value, right.value))
            } else {
                ExpressionNode(
                    current.name,
                    left,
                    right,
                    current.operation,
                    current.moveRightOperand,
                    current.moveLeftOperand
                )
            }
        }
        if (current.name == "humn") return current
        return evaluate(nameToNodePart2[current.name]!!)
    }

    fun solveForX(current: Node, answer: Long): Long {
        if (current.name == "humn") return answer
        if (current is ExpressionNode && current.left is ValueNode) {
            val newAnswer = current.moveLeftOperand(answer, current.left.value)
            return solveForX(current.right, newAnswer)
        }
        if (current is ExpressionNode && current.right is ValueNode) {
            val newAnswer = current.moveRightOperand(answer, current.right.value)
            return solveForX(current.left, newAnswer)
        }
        throw IllegalArgumentException("Unexpected node $current")
    }

    val root = nameToNodePart2["root"] as ExpressionNode
    val left = evaluate(root.left)
    val right = evaluate(root.right)
    if (left is ExpressionNode) {
        println(solveForX(left, (right as ValueNode).value))
    } else if (right is ExpressionNode) {
        println(solveForX(right, (left as ValueNode).value))
    } else {
        throw IllegalStateException("Evaluation failed")
    }
}
