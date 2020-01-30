package cn.denghanxi.s1

private class Peter(private val name: String, val age: Int) {
    val what: Int
        get() {
            return 12
        }
}

class Petser(val name: String, val age: Int)

fun main(args: Array<String>) {
    val name = if (args.size > 0) args[0] else "Kotlin"
    println("Hello, $name")
}

fun max(a: Int, b: Int) = if (a > b) a else b

fun min(a: Int, b: Int): Int {
    return if (a < b) a else b
}
