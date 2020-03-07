package cn.denghanxi.s10

import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmName

/**
 * Created by 邓晗熙 on 2020/2/26
 */

@Deprecated("a123sd", ReplaceWith("greeting2(s)"))
fun greeting(s: String) {
    println(s)
}

fun greeting2(s: String, times: Int = 1) {
    for (i in 1..times) {
        println(s)
    }
}

annotation class JsonName(val name: String)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class JsonExclude()

data class Person(@JsonName("alias") val firstName: String,
                  @JsonExclude val age: Int? = null
) {
    fun go() {}
}

fun Person.dance() = { println("dance1") }


fun <T : Any> printDetail(t: T) {
    val kClass = t.javaClass.kotlin
    println(kClass.simpleName)
    println(kClass.jvmName)
    kClass.memberProperties.forEach { println(it.name) }
}

fun main() {
    val person = Person("Alice", 29)

    val kClass = person.javaClass.kotlin
    println(kClass.simpleName)
    println(kClass.jvmName)
    kClass.memberProperties.forEach { println(it.name) }

    printDetail(person)


}
