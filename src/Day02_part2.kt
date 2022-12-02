fun main() {
    val shapeScore = arrayOf(1, 2, 3)
    val result = readInput("Day02_input").fold(0) { total, str ->
        val theirPlay = str[0]-'A'
        var yourPlay: Int = -1
        var score = 0
        when (str[2]) {
            'X' -> {
                yourPlay = (theirPlay + 2) % 3
            }
            'Y' ->  {
                yourPlay = theirPlay
                score += 3
            }
            'Z' -> {
                yourPlay = (theirPlay + 1) % 3
                score +=  6
            }
        }
        score += shapeScore[yourPlay]
        total + score
    }
    println(result)
}