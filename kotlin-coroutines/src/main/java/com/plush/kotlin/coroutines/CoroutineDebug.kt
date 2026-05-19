package com.plush.kotlin.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/18
 * Email: lijt@eetrust.com
 */
fun main() = runBlocking<Unit>(CoroutineName("主协程")) {
    val a = async(CoroutineName("协程1")) {
        println("I'm computing part of the answer")
        6
    }
    val b = async(CoroutineName("协程2")) {
        println("I'm computing another part of the answer")
        7
    }
    println("The answer is ${a.await() * b.await()}")
}