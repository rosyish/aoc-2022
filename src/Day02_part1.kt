fun main() {
    val shapeScore = arrayOf(1, 2, 3)
    val result = readInput("Day02_input").fold(0) { total, str ->
        val theirPlay = str[0]-'A'
        val yourPlay = str[2] - 'X'
        var score = shapeScore[yourPlay]
        if (theirPlay == yourPlay) {
            score += 3
        } else if (yourPlay == (theirPlay + 1) % 3) {
            score += 6
        }
        total+score
    }
    println(result)
}