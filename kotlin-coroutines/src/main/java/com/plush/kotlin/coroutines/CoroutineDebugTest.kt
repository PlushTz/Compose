package com.plush.kotlin.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/16
 * Email: lijt@eetrust.com
 */
fun main() = runBlocking<Unit> {
    val a = async {
        log("I'm computing a piece of the answer")
        6
    }
    val b = async {
        log("I'm computing another piece of the answer")
        7
    }

    log("The answer is ${a.await() * b.await()}")
}

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")