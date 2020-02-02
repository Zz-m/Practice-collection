package cn.denghanxi.s2

class Person {
    val name: String = "Default Name"
    var isMarried: Boolean = false

    fun getARectangle(): Rectangle {
        return cn.denghanxi.s2.createRandomRectangle()
    }
}

fun main() {
    val person = Person()
    person.isMarried
    person.name
    person.isMarried = true
    println(person.getARectangle().height)
    println(person.getARectangle().width)
    println(person.getARectangle().isSquare)
}