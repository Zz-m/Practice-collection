package cn.denghanxi.s2

import java.util.Random

/**
 * Created by 邓晗熙 on 2020/2/1
 */
class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() {
            println("is getting")
            return height == width
        }
}

fun createRandomRectangle(): Rectangle {
    val random = Random();
    return Rectangle(random.nextInt(), random.nextInt())
}

val s = "asd"

enum class Color(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0),
    ORANGE(255, 165, 0),
    YELLOW(255, 255, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    INDIGO(75, 0, 130),
    VIOLET(238, 130, 238);

    fun rgb() = (r * 256 + g) * 256 + b
}

fun getMnemonic(color: Color) =
        when(color) {
            Color.RED -> "Richard"
            Color.ORANGE -> "Of"
            Color.YELLOW -> "York"
            Color.GREEN -> "Gave"
            Color.BLUE -> "Battle"
            Color.INDIGO -> "In"
            Color.VIOLET -> "Vain"
        }

fun getWarmth(color: Color) = when(color) {
    Color.RED, Color.ORANGE, Color.YELLOW -> "warm"
    Color.GREEN -> "neutral"
    Color.BLUE, Color.INDIGO, Color.VIOLET -> "cold"
}

fun main() {
    val rectangle = Rectangle(12, 12)
    println(rectangle.isSquare)
    println(getMnemonic(Color.RED))
    println(getWarmth(Color.INDIGO))

    for (i in 100 downTo 1 step 2) {}
}