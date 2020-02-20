package cn.denghanxi.s4

import cn.denghanxi.s2.Person

/**
 * Created by 邓晗熙 on 2020/2/19
 */

interface Jumperable {
    fun jump()
}

object Payroll {
    private val name: String = "asd"
    val allEmployees = arrayListOf<Person>()

    fun calculateSalary() {
        for (person in allEmployees) {

        }
    }
}

fun newUser(nickname: String) = ""

class User private constructor(val nickname: String) {

    companion object CREATOR {
        fun newUser(nickname: String) = User(nickname)
    }

    fun newUser(nickname: String) = User(nickname)
    fun getUser(nickName: String) = User(nickname)
}

fun main() {
    val payroll = Payroll.calculateSalary()
    println(Payroll.toString())
    println(Payroll == Payroll)

    println(User.newUser("asd"))
    println("asd")
    println(newUser("asd"))
    println("asd")

    println(Thread.currentThread())

    object : Thread() {
        override fun run() {
            println(Thread.currentThread())
        }
    }.start()

    Thread.sleep(1000)
}