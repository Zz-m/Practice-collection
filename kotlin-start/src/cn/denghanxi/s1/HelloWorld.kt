package cn.denghanxi.s1

private class Peter(private val name: String, val age: Int) {
    val what: Int
        get() {
            return 12
        }
}

class Petser(val name: String, val age: Int)

fun main() {
    println("hello world")
    println(max(3, 5))
//    val p1 = Peter("asd", 12)

    println(Peter("as", 5).what)
    println(Petser("as", 5))
}

fun max(a: Int, b: Int) = if (a > b) a else b

