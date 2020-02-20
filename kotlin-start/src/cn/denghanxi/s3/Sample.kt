package cn.denghanxi.s3

import java.lang.StringBuilder

/**
 * Created by 邓晗熙 on 2020/2/2
 */
@Volatile var timer: Long = 0
    get() {
        return field + 1
    }
    set(value) {
        field = 2 + value
    }

val set = hashSetOf(1, 7, 53)
val map = hashMapOf(1 to "one", 8 to "eight", 53 to "fifty-three")

fun String.secondChar(): Char = this.get(1)
fun String.thirdChar(): Char {return 'a'}

fun <T> joinToString(
        collection: Collection<T>,
        separator: String = " ",
        prefix: String,
        postfix: String
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun String.tell() = println(this)

fun main() {
    for (i: Int in set) {
        println(i)
    }

    for ((i, s) in map) {
        println("$i = $s")
    }

    val nums = listOf(1, 2, 3)
    println(joinToString(nums, "|", "{", "}"))
    println(joinToString(nums, separator = "|", prefix = "{", postfix = "}"))
    timer = 3
    println(timer)
    val s = "0123"
    println(s.secondChar())

    val ss = "asdasd"

    ss.tell()
    println(ss.thirdChar())
}