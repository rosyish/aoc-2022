import java.io.IOException
import kotlin.random.Random
import kotlin.reflect.typeOf

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

//    val i = 1
//    val x = if (i < 10) i else throw IOException("error")
//    println("${x}")

//      val x = fun (x: Int) {
//          println("hello world $x")
//      }
//      x(5)

//    val s = "abc"
//    println(s as? Int)    // null
//    println(s as Int?)    // exception

//    val foo1: Int = run {
//        println("Calculating the answer...")
//        42
//    }

//    var a = listOf(1,2,3)
//    val b = a
//
//    a+=5
//    println(a)
//    println(b)

//    println(listOf(1,2,3).zipWithNext())

//    val seq = generateSequence(0) {
//      //println(it)
//      println("generating current=$it")
//      it + 1
//    }
//    println(seq.take(2).toList())
//    println(seq.take(3).toList())

//      val fibonacci = sequence {
//          var a = 1
//          var b = 1
//          yield(a)
//          yield(b)
//          while (true) {
//              val c = a+b
//              yield(c)
//              a=b
//              b=c
//          }
//      }
//      println(fibonacci.take(5).toList())

}

class A {
    private lateinit var prop: String
    val abc: Any
        get() = "blah"

    fun setUp() {
        prop = "value"
    }

    fun display() {
        val xyz = abc
        if (xyz is String)  xyz.length
        val p = this::prop
        if (!this::prop.isInitialized) setUp()
        println(p)
        println(prop)
    }
}

class Person {
    val age: Int
        get() {
            println("val is read-only but not immutable")
            return Random.nextInt()
        }
}