package cn.denghanxi.s8

/**
 * Created by 邓晗熙 on 2020/2/24
 */

val sum = { x: Int, y: Int -> (x + y) }

enum class Delivery { STANDARD, EXPEDITED }

data class Order(val name: String)

fun getShippingCostCalculator(delivery: Delivery): (Order) -> Double {
    if (delivery == Delivery.STANDARD) {
        return { order -> order.name.length + 12.2 }
    } else {
        return { order -> order.name.length - 1.2 }
    }
}

data class Person(val firstName: String,
                  val lastName: String,
                  val phoneNumber: String?)

class ContactListFilters {
    var prefix: String = ""
    var onlyWithPhoneNumber: Boolean = false

    fun getPredicate(): (Person) -> Boolean {
        val startsWithPrefix = { p: Person -> p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix) }
        if (!onlyWithPhoneNumber) {
            return startsWithPrefix
        }
        return { startsWithPrefix(it) && it.phoneNumber != null }
    }
}

fun main() {
    println(sum(1, 12))

    println(getShippingCostCalculator(Delivery.EXPEDITED).invoke(Order("asd")))
    val calculator = getShippingCostCalculator(Delivery.EXPEDITED);
    println(calculator(Order("123123")))

    val contacts = listOf(Person("Dmitry", "Jemerov", "123"),
            Person("stev", "is", null))
    val contactListFilter = ContactListFilters()
    contactListFilter.prefix = "Dm"
    contactListFilter.onlyWithPhoneNumber = true
    println(contacts.filter(contactListFilter.getPredicate()))
}