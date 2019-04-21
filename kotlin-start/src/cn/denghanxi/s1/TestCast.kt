package cn.denghanxi.s1

import java.lang.IllegalArgumentException

const val ddd = 2
interface Expre

val a = 3

class Num(val value: Int) : Expre
class Sum(val left: Expre, val right: Expre) : Expre

fun eval(e: Expre): Int {
    if (e is Num) {
        return e.value
    }
    if (e is Sum) {
        return eval(e.left) + eval(e.right)
    }

    throw IllegalArgumentException("Not an expression")
}

fun main(args: Array<String>){
    println(eval(Sum(Sum(Num(3), Num(5)), Num(7))))

    val list = listOf<String>("args: ", *args)
    println(list)
}