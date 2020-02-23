package cn.denghanxi.s5

/**
 * Created by 邓晗熙 on 2020/2/22
 */

class Book(val title: String, val authors:List<String>)

fun main() {
    val books = listOf(Book("asd", listOf("张三", "李四")), Book("ddd", listOf("张三", "王二")))

    val set = books.flatMap { it.authors }.toSet()
    println(set)

    val set2 = books.map { it.authors }.flatten().toSet()
    println(set2)
}