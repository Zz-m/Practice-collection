package cn.denghanxi.s6

/**
 * Created by 邓晗熙 on 2020/2/23
 */

fun printAllCaps(s: String?) {
    val allCaps2: String = s?.toUpperCase()!!
    val allCaps: String? = s?.toUpperCase()
    println(allCaps)
    println(allCaps2)
}

fun printAllLow(s: String) {
    val allLow: String = s.toLowerCase()
}

fun go(s: String) {}


fun main() {
    val s1 = null
    val s2 = "asd"
    println(printAllCaps(s1))
    println(printAllCaps(s2))
    println(s2)
}