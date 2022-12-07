data class Node(val name: String, var size: Int, val isDir: Boolean, val children: MutableList<Node>, val parent: Node?)

val part1MaxVal = 100000
var part1answer = 0
var minDirSize = Int.MAX_VALUE

fun fillSizes(current: Node) {
    if (!current.isDir) return;
    if (current.children.isEmpty()) return;
    for (c in current.children) {
        fillSizes(c);
        current.size += c.size
    }
    if (current.size <= part1MaxVal) part1answer += current.size
}

fun findSmallerDirWithRequiredSpace(current: Node, spaceToFreeUp: Int) {
    if (!current.isDir) return
    if (current.size in spaceToFreeUp until minDirSize) {
        if (current.size < minDirSize) {
            minDirSize = current.size
        }
    }
    for (c in current.children) {
        findSmallerDirWithRequiredSpace(c, spaceToFreeUp)
    }
}

fun main() {
    val root = Node("/", 0, true, mutableListOf(), null)
    var pwd = root
    readInput("Day07_input").drop(1).forEach {
        when {
            it == "$ cd .." -> pwd = pwd.parent ?: pwd
            it == "$ ls" -> return@forEach
            it.startsWith("""$ cd""") ->
                pwd = pwd.children.find { child -> child.name == it.split(" ")[2] }!!
            it.startsWith("dir") -> {
                val dirname = it.split(" ")[1]
                if (pwd.children.none { child -> child.name == dirname }) pwd.children.add(
                    Node(
                        dirname,
                        0,
                        true,
                        mutableListOf(),
                        pwd
                    )
                )
            }
            else -> {
                val (fileSize, fileName) = it.split(" ").let { Pair(it[0].toInt(), it[1]) }
                pwd.children.add(Node(fileName, fileSize, false, mutableListOf(), pwd))
            }
        }
    }
    fillSizes(root)
    println(part1answer)

    val totalDiskSpace = 70000000;
    val minUnusedSpace = 30000000;
    val unusedSpace = totalDiskSpace - root.size;
    val spaceToFreeUp = minUnusedSpace - unusedSpace;
    findSmallerDirWithRequiredSpace(root, spaceToFreeUp)
    println(minDirSize)
}

