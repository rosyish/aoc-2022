fun main() {
    val input = readInput("Day10_input")
    var spriteStart = 0
    var instructionIndex = 0
    var renderedOutput = ""
    var nextReadyCycle = 0
    var valueToAdd = 0
    repeat(240) {
        val cycle = it

        // Update X at the beginning of cycle
        if (cycle == nextReadyCycle) {
            spriteStart += valueToAdd
            valueToAdd = 0
        }

        // Render the pixel
        val cursor = cycle % 40
        renderedOutput += if (cursor in spriteStart until spriteStart + 3) {
            "#"
        } else {
            "."
        }
        if (cursor + 1 == 40) {
            renderedOutput += "\n"
        }

        // Read next instruction if we are done processing the last instruction
        if (nextReadyCycle == cycle) {
            when (val instruction = input[instructionIndex++]) {
                "noop" -> {
                    nextReadyCycle = cycle + 1
                }
                else -> {
                    valueToAdd = instruction.split(" ")[1].toInt()
                    nextReadyCycle = cycle + 2
                }
            }
        }
    }
    println(renderedOutput)
}