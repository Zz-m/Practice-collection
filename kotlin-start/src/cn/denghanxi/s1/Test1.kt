package cn.denghanxi.s1

data class Person(val name: String, val age: Int ?= 0)

private val d1 = "asd"

fun main(args: Array<String>) {
    val persons = listOf(Person("Alice"), Person("Bob", 12))
    val oldest = persons.maxBy { it.age ?: 0 }
    println("The oldest is $oldest")

    println(d1)
}