package cn.denghanxi.s4

/**
 * Created by 邓晗熙 on 2020/2/11
 */
interface Clickable {
    fun click() {
        println("Clickable clicked")
    }

    fun showOff() = println("clickable")
}

interface Focusable {
    fun showOff() = println("focusable")
}

class Button : Clickable, Focusable {
    override fun click() {
        println("Button clicked")
    }

    override fun showOff() {
        super<Focusable>.showOff()
    }

}

class View : Clickable

class ConstructorTester(var age: Int = 3) {
    init {
        println("constructor1")
    }

    init {
        println("age inited")
    }

    init {
        println("constructor2")
    }

    init {
        println("constructor3")
    }

}

data class Home(val name: String = "default home", val isGood: Boolean = true) {}

class DelegatingCollection<T>(innerList: Collection<T> = ArrayList<T>()) : Collection<T> by innerList {
    private val name: String = ":"

    constructor() : this(ArrayList<T>()) {
        val innerList2: Collection<T> = ArrayList<T>()
    }

    init {

    }
}

fun main() {
    val button = Button()
    button.click()
    button.showOff()

    val view = View()
    view.click()

    val cons = ConstructorTester()
    val cons2 = ConstructorTester(12)

    val home1 = Home(isGood = false)
    val home2 = home1.copy()
    val home3 = Home(isGood = false)
    println(home1 == home2)
    println(home1 == home3)

    val list = listOf<String>("A", "B", "C", "AA")
    list
            .filter { it.startsWith("A") }
            .takeLast(3)
            .forEach {
                println(it)
            }
}