package cn.denghanxi.s9

/**
 * Created by 邓晗熙 on 2020/2/25
 */

val authors = listOf("Dmitry", "Svetlana")

val readers: MutableList<String> = mutableListOf()
val readers2 = mutableListOf<String>()

val <T> List<T>.penultimate: T
    get() = this[size - 2]

fun printSum(c: Collection<*>) {
    val intList = c as? List<Int> ?: throw IllegalAccessError()
    println(intList.sum())
}

fun main() {
    val letters = ('a'..'z').toList()
    println(letters.slice<Char>(0..2))
    println(letters.slice(10..13))

    println(listOf(1, 2, 3, 4).penultimate)
    println(listOf(1, 2).penultimate)
}