import java.io.IOException

fun main() {
//    val input = readln().toInt()
//    val abc = when {
//        input+1 == 1 -> "hello"
//        input == 2 -> "world"
//        else -> 1.1
//    }

//    val a: Int = 100
//    val boxedA: Int? = a
//    val anotherBoxedA: Int? = a
//
//    println(anotherBoxedA === boxedA)

    val i = 1
    val x = if (i < 10) i else throw IOException("error")
    println("${x}")

}